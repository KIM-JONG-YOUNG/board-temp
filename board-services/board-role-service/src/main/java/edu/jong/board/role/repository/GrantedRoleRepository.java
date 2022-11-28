package edu.jong.board.role.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.jong.board.role.entity.GrantedRoleEntity;

public interface GrantedRoleRepository extends JpaRepository<GrantedRoleEntity, GrantedRoleEntity.PK> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT g FROM GrantedRoleEntity g WHERE g.roleNo = :roleNo AND g.memberNo = :memberNo")
	Optional<GrantedRoleEntity> findByIdForUpdate(
			@Param("roleNo") long roleNo,
			@Param("memberNo") long memberNo); 

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT g FROM GrantedRoleEntity g WHERE g.memberNo = :memberNo")
	List<GrantedRoleEntity> findAllByMemberForUpdate(@Param("memberNo") long memberNo); 

}
 