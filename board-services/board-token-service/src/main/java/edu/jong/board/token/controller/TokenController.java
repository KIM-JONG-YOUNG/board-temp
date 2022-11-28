package edu.jong.board.token.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import edu.jong.board.token.client.TokenFeignClient;
import edu.jong.board.token.request.LoginParam;
import edu.jong.board.token.response.AuthTokens;
import edu.jong.board.token.service.TokenService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TokenController implements TokenFeignClient {

	private final TokenService tokenService;
	
	@Override
	public ResponseEntity<AuthTokens> generateToken(LoginParam param) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(tokenService.generateAuthTokens(param));
	}

	@Override
	public ResponseEntity<Void> logoutToken(String accessToken) {
		tokenService.logoutAccessToken(accessToken);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@Override
	public ResponseEntity<Void> checkAccessible(String accessToken, String accessCheckURL, String accessCheckMethod) {
		tokenService.checkAccessible(accessToken, accessCheckURL, accessCheckMethod);;
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@Override
	public ResponseEntity<AuthTokens> refreshToken(String accessToken, String refreshToken) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(tokenService.refreshAuthTokens(accessToken, refreshToken));
	}

}
