package edu.jong.board.role.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import edu.jong.board.jpa.utils.QueryDslUtils;
import edu.jong.board.role.entity.QRoleEntity;
import edu.jong.board.role.entity.RoleEntity;
import edu.jong.board.role.mapper.RoleMapper;
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
	
	private final JPAQueryFactory jpaQueryFactory;
	private final QRoleEntity role = QRoleEntity.roleEntity;
	
	@Transactional
	@Override
	public RoleDetails addRole(@NotNull @Valid RoleAddParam param) {
		
		if (roleRepository.existsByName(param.getName()))
			throw new EntityExistsException("동일한 권한명이 존재합니다.");

		return roleMapper.toDetails(roleRepository.save(roleMapper.toEntity(param)));
	}

	@Transactional
	@Override
	public RoleDetails modifyRole(long roleNo, @NotNull @Valid RoleModifyParam param) {

		RoleEntity role = roleRepository.findByIdForUpdate(roleNo)
				.orElseThrow(() -> new EntityNotFoundException("권한이 존재하지 않습니다."));

		return roleMapper.toDetails(roleRepository.save(roleMapper.updateEntity(param, role)));
	}

	@Transactional
	@Override
	public void removeRole(long roleNo) {
		roleRepository.delete(roleRepository.findByIdForUpdate(roleNo)
				.orElseThrow(() -> new EntityNotFoundException("권한이 존재하지 않습니다.")));
	}

	@Transactional(readOnly = true)
	@Override
	public RoleDetails getRole(long roleNo) {
		return roleMapper.toDetails(roleRepository.findById(roleNo)
				.orElseThrow(() -> new EntityNotFoundException("권한이 존재하지 않습니다.")));
	}

	@Transactional(readOnly = true)
	@Override
	public List<RoleDetails> searchRoles(@Valid RoleSearchCond cond) {
		
		JPAQuery<RoleEntity> query = jpaQueryFactory
				.selectFrom(role)
				.where(
					QueryDslUtils.containsIfPresent(role.name, cond.getName()),
					QueryDslUtils.equalsIfPresent(role.method, cond.getMethod()),
					QueryDslUtils.containsIfPresent(role.urlPattern, cond.getUrlPattern()),
					QueryDslUtils.betweenIfPresent(role.createDateTime, cond.getFrom(), cond.getTo()));

		return query.fetch().stream()
				.map(x -> roleMapper.toDetails(x))
				.collect(Collectors.toList());
	}

}
