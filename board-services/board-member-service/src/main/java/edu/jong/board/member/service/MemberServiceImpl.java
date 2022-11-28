package edu.jong.board.member.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import edu.jong.board.BoardConstants.CacheNames;
import edu.jong.board.jpa.utils.QueryDslUtils;
import edu.jong.board.member.entiity.MemberEntity;
import edu.jong.board.member.entiity.QMemberEntity;
import edu.jong.board.member.mapper.MemberMapper;
import edu.jong.board.member.repository.MemberRepository;
import edu.jong.board.member.request.MemberAddParam;
import edu.jong.board.member.request.MemberModifyParam;
import edu.jong.board.member.request.MemberSearchCond;
import edu.jong.board.member.response.MemberDetails;
import edu.jong.board.redis.service.RedisService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberMapper memberMapper;
	private final MemberRepository memberRepository;
	private final PasswordEncoder encoder;
	private final RedisService redisService;

	private final JPAQueryFactory jpaQueryFactory;
	private final QMemberEntity member = QMemberEntity.memberEntity;
	
	@Transactional
	@Override
	public MemberDetails addMember(MemberAddParam param) {
		
		if (memberRepository.existsByUsername(param.getUsername())) 
			throw new EntityExistsException("동일한 계정이 존재합니다.");

		MemberDetails details = memberMapper.toDetails(
				memberRepository.save(memberMapper.toEntity(param, encoder)));

		redisService.caching(CacheNames.MEMBER + details.getNo(), details, 60);

		return details;
	}

	@Transactional
	@Override
	public MemberDetails modifyMember(long memberNo, @NotNull @Valid MemberModifyParam param) {
		
		MemberEntity member = memberRepository.findByIdForUpdate(memberNo)
				.orElseThrow(() -> new EntityNotFoundException("사용자가 존재하지 않습니다."));

		MemberDetails details = memberMapper.toDetails(
				memberRepository.save(memberMapper.updateEntity(param, encoder, member)));

		redisService.caching(CacheNames.MEMBER + details.getNo(), details, 60);

		return details;
	}

	@Transactional
	@Override
	public void removeMember(long memberNo) {
		
		memberRepository.delete(memberRepository.findByIdForUpdate(memberNo)
				.orElseThrow(() -> new EntityNotFoundException("사용자가 존재하지 않습니다.")));
		
		if (redisService.remove(CacheNames.MEMBER + memberNo))
			throw new RuntimeException("Redis 캐시를 삭제하는데 실패했습니다.");
	}

	@Transactional(readOnly = true)
	@Override
	public MemberDetails getMember(long memberNo) {
		return redisService.get(CacheNames.MEMBER, new TypeReference<MemberDetails>() {})
				.orElse(memberMapper.toDetails(memberRepository.findById(memberNo)
				.orElseThrow(() -> new EntityNotFoundException("사용자가 존재하지 않습니다."))));
	}

	@Transactional(readOnly = true)
	@Override
	public MemberDetails getMember(@NotBlank String username) {
		return memberMapper.toDetails(memberRepository.findByUsername(username)
				.orElseThrow(() -> new EntityNotFoundException("사용자가 존재하지 않습니다.")));
	}

	@Transactional(readOnly = true)
	@Override
	public List<MemberDetails> searchMembers(@Valid MemberSearchCond cond) {

		JPAQuery<MemberEntity> query = jpaQueryFactory.selectFrom(member);

		if (query != null) {
			query = query.where(
					QueryDslUtils.containsIfPresent(member.username, cond.getUsername()),
					QueryDslUtils.containsIfPresent(member.name, cond.getName()),
					QueryDslUtils.equalsIfPresent(member.gender, cond.getGender()),
					QueryDslUtils.containsIfPresent(member.email, cond.getEmail()),
					QueryDslUtils.betweenIfPresent(member.createdDateTime, cond.getFrom(), cond.getTo()));
		}

		return query.fetch().stream()
				.map(x -> memberMapper.toDetails(x))
				.collect(Collectors.toList());
	}

}
