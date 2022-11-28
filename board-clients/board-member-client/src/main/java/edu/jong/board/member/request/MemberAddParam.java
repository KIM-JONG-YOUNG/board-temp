package edu.jong.board.member.request;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import edu.jong.board.member.type.Gender;
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
public class MemberAddParam implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "계정")
	@NotBlank
	@Size(max = 30)
	private String username;

	@Schema(description = "비밀번호")
	@NotBlank
	private String password;

	@Schema(description = "이름")
	@NotBlank
	@Size(max = 30)
	private String name;

	@Schema(description = "성별")
	@NotNull
	private Gender gender;

	@Schema(description = "이메일")
	@NotBlank
	@Email
	@Size(max = 60)
	private String email;

}
