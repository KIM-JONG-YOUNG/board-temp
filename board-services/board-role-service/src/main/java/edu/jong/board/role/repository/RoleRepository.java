package edu.jong.board.role.repository;

import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.jong.board.role.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

	boolean existsByName(String name);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT r FROM RoleEntity r WHERE r.no = :roleNo")
	Optional<RoleEntity> findByIdForUpdate(@Param("roleNo") long roleNo); 

}
