package edu.jong.board.token.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import edu.jong.board.BoardConstants.HeaderNames;
import edu.jong.board.token.request.LoginParam;
import edu.jong.board.token.response.AuthTokens;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "인증 토큰", description = "인증 토큰 API 목록")
@FeignClient(name = "token-service")
public interface TokenFeignClient {

	@PostMapping(value = "/tokens",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<AuthTokens> generateToken(
			@RequestBody LoginParam param);

	@DeleteMapping(value = "/tokens")
	ResponseEntity<Void> logoutToken(
			@RequestHeader(HeaderNames.ACCESS_TOKEN) String accessToken);

	@GetMapping(value = "/tokens/check")
	ResponseEntity<Void> checkAccessible(
			@RequestHeader(HeaderNames.ACCESS_TOKEN) String accessToken,
			@RequestHeader(HeaderNames.ACCESS_CHECK_URL) String accessCheckURL,
			@RequestHeader(HeaderNames.ACCESS_CHECK_METHOD) String accessCheckMethod);

	@PostMapping(value = "/tokens/refresh")
	ResponseEntity<AuthTokens> refreshToken(
			@RequestHeader(HeaderNames.ACCESS_TOKEN) String accessToken,
			@RequestHeader(HeaderNames.REFRESH_TOKEN) String refreshToken);

}
