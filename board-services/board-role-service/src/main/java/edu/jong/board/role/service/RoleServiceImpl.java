package edu.jong.board.role.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import edu.jong.board.BoardConstants.CacheNames;
import edu.jong.board.jpa.utils.QueryDslUtils;
import edu.jong.board.member.client.MemberFeignClient;
import edu.jong.board.redis.service.RedisService;
import edu.jong.board.role.entity.GrantedRoleEntity;
import edu.jong.board.role.entity.QGrantedRoleEntity;
import edu.jong.board.role.entity.QRoleEntity;
import edu.jong.board.role.entity.RoleEntity;
import edu.jong.board.role.mapper.RoleMapper;
import edu.jong.board.role.repository.GrantedRoleRepository;
import edu.jong.board.role.repository.RoleRepository;
import edu.jong.board.role.request.RoleAddParam;
import edu.jong.board.role.request.RoleModifyParam;
import edu.jong.board.role.request.RoleSearchCond;
import edu.jong.board.role.response.RoleDetails;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

	private final RoleMapper roleMapper;
	private final RoleRepository roleRepository;
	private final GrantedRoleRepository grantedRoleRepository;
	private final RedisService redisService;
	private final MemberFeignClient memberFeignClient;
	
	private final JPAQueryFactory jpaQueryFactory;
	private final QRoleEntity role = QRoleEntity.roleEntity;
	private final QGrantedRoleEntity grantedRole = QGrantedRoleEntity.grantedRoleEntity;
	
	@Transactional
	@Override
	public RoleDetails addRole(@NotNull @Valid RoleAddParam param) {
		
		if (roleRepository.existsByName(param.getName()))
			throw new EntityExistsException("????????? ???????????? ???????????????.");

		RoleDetails details = roleMapper.toDetails(roleRepository.save(roleMapper.toEntity(param)));
		
		redisService.caching(CacheNames.ROLE + details.getNo(), details, 60);
		
		return details;
	}

	@Transactional
	@Override
	public RoleDetails modifyRole(long roleNo, @NotNull @Valid RoleModifyParam param) {

		RoleEntity role = roleRepository.findByIdForUpdate(roleNo)
				.orElseThrow(() -> new EntityNotFoundException("????????? ???????????? ????????????."));

		RoleDetails details = roleMapper.toDetails(roleRepository.save(roleMapper.updateEntity(param, role)));
		
		redisService.caching(CacheNames.ROLE + details.getNo(), details, 60);
		
		return details;
	}

	@Transactional
	@Override
	public void removeRole(long roleNo) {
		
		roleRepository.delete(roleRepository.findByIdForUpdate(roleNo)
				.orElseThrow(() -> new EntityNotFoundException("????????? ???????????? ????????????.")));
		
		if (redisService.remove(CacheNames.ROLE + roleNo)) 
			throw new RuntimeException("Redis ????????? ??????????????? ??????????????????.");
	}

	@Transactional(readOnly = true)
	@Override
	public RoleDetails getRole(long roleNo) {
		return redisService.get(CacheNames.ROLE + roleNo, new TypeReference<RoleDetails>() {})
				.orElse(roleMapper.toDetails(roleRepository.findById(roleNo)
				.orElseThrow(() -> new EntityNotFoundException("????????? ???????????? ????????????."))));
	}

	@Transactional(readOnly = true)
	@Override
	public List<RoleDetails> searchRoles(@Valid RoleSearchCond cond) {
		
		JPAQuery<RoleEntity> query = jpaQueryFactory.selectFrom(role);

		if (query != null) {
			query = query.where(
					QueryDslUtils.containsIfPresent(role.name, cond.getName()),
					QueryDslUtils.equalsIfPresent(role.method, cond.getMethod()),
					QueryDslUtils.containsIfPresent(role.urlPattern, cond.getUrlPattern()),
					QueryDslUtils.betweenIfPresent(role.createdDateTime, cond.getFrom(), cond.getTo()));
		}

		return query.fetch().stream()
				.map(x -> roleMapper.toDetails(x))
				.collect(Collectors.toList());
	}

	@Transactional
	@Override
	public void grantRoleToMember(long roleNo, long memberNo) {

		if (grantedRoleRepository.existsById(GrantedRoleEntity.PK.builder()
				.roleNo(roleNo)
				.memberNo(memberNo)
				.build())) throw new EntityExistsException("?????? ????????? ???????????????.");

		roleRepository.findById(roleNo)
			.orElseThrow(() -> new EntityNotFoundException("????????? ???????????? ????????????."));

		if (memberFeignClient.getMember(memberNo).getStatusCode() != HttpStatus.OK)
			throw new EntityNotFoundException("???????????? ???????????? ????????????.");
	
		grantedRoleRepository.save(GrantedRoleEntity.builder()
				.roleNo(roleNo)
				.memberNo(memberNo)
				.build());
		
		if (redisService.remove(CacheNames.MEMBER_ROLES + memberNo)) 
			throw new RuntimeException("Redis ????????? ??????????????? ??????????????????.");
	}

	@Transactional
	@Override
	public void revokeRoleToMember(long roleNo, long memberNo) {
		
		grantedRoleRepository.delete(grantedRoleRepository.findByIdForUpdate(roleNo, memberNo)
				.orElseThrow(() -> new EntityNotFoundException("????????? ????????? ???????????? ????????????.")));

		if (redisService.remove(CacheNames.MEMBER_ROLES + memberNo)) 
			throw new RuntimeException("Redis ????????? ??????????????? ??????????????????.");
	}

	@Transactional
	@Override
	public void revokeRolesToMember(long memberNo) {
		
		grantedRoleRepository.deleteAll(grantedRoleRepository.findAllByMemberForUpdate(memberNo));
		
		if (redisService.remove(CacheNames.MEMBER_ROLES + memberNo)) 
			throw new RuntimeException("Redis ????????? ??????????????? ??????????????????.");
	}

	@Transactional(readOnly = true)
	@Override
	public List<RoleDetails> getRolesToMember(long memberNo) {
		return redisService.get(CacheNames.MEMBER_ROLES + memberNo, new TypeReference<List<RoleDetails>>() {})
				.orElse(jpaQueryFactory
						.select(role)
						.from(grantedRole)
						.join(role).on(role.no.eq(grantedRole.roleNo))
						.where(grantedRole.memberNo.eq(memberNo))
						.fetch().stream()
						.map(x -> roleMapper.toDetails(x))
						.collect(Collectors.toList()));
	}

}
