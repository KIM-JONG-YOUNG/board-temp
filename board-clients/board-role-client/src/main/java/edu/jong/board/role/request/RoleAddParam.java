package edu.jong.board.role.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import edu.jong.board.role.type.APIMethod;
import edu.jong.board.role.type.AntPahtPattern;
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
public class RoleAddParam implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotBlank
	@Size(max = 30)
	@Pattern(regexp = "^ROLE_[A-Z]+$")
	private String name;

	@NotNull
	private APIMethod method;
	
	@NotBlank
	@Size(max = 60)
	@AntPahtPattern
	private String urlPattern;

}
