package edu.jong.board.role.request;

import java.io.Serializable;

import javax.validation.constraints.Size;

import edu.jong.board.role.type.APIMethod;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RoleModifyParam implements Serializable{

	private static final long serialVersionUID = 1L;

	private APIMethod method;
	
	@Size(max = 60)
	private String urlPattern;

}
