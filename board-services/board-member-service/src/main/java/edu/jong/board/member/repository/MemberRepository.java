package edu.jong.board.member.repository;

import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.jong.board.member.entiity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

	boolean existsByUsername(String username);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT m FROM MemberEntity m WHERE m.no = :memberNo")
	Optional<MemberEntity> findByIdForUpdate(@Param("memberNo") long memberNo); 

	Optional<MemberEntity> findByUsername(String username); 

}
