package edu.jong.board.role.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import edu.jong.board.BoardConstants.TableNames;
import edu.jong.board.jpa.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@ToString
@Table(name = TableNames.TB_GRANTED_ROLE)
@IdClass(GrantedRoleEntity.PK.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GrantedRoleEntity extends BaseTimeEntity {

	@Getter
	@Builder
	@ToString
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class PK implements Serializable {

		private static final long serialVersionUID = 1L;
		private long roleNo;
		private long memberNo;
	}
	
	@Id
	private long roleNo;

	@Id
	private long memberNo;

	@Builder
	public GrantedRoleEntity(long roleNo, long memberNo) {
		super();
		this.roleNo = roleNo;
		this.memberNo = memberNo;
	}
	
}
