package edu.jong.board.token.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class LoginParam implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "계정")
	@NotBlank
	@Size(max = 30)
	private String username;
	
	@Schema(description = "비밀번호")
	@NotBlank
	private String password;
	
}
