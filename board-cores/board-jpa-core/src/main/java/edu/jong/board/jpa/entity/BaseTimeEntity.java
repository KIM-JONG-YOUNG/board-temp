package edu.jong.board.jpa.entity;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

	@CreatedDate
	private LocalDateTime createDateTime;
	
	@LastModifiedBy
	private LocalDateTime lastUpdateDateTime;

}
