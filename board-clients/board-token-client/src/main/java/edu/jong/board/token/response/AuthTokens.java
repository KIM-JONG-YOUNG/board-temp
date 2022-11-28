package edu.jong.board.token.response;

import java.io.Serializable;

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
public class AuthTokens implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "접근 토큰")
	private String accessToken;
	
	@Schema(description = "재발급을 위한 토큰")
	private String refreshToken;
	
}
