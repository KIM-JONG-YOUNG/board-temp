package edu.jong.board.token.service;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import edu.jong.board.token.request.LoginParam;
import edu.jong.board.token.response.AuthTokens;

@Validated
public interface TokenService {

	String generateAccessToken(long memberNo);
	
	String generateRefreshToken(
			@NotBlank String accessToken);

	AuthTokens generateAuthTokens(
			@NotNull @Valid LoginParam param);

	void logoutAccessToken(
			@NotBlank String accessToken);
	
	boolean validateAccessToken(
			@NotBlank String accessToken);
	
	boolean validateRefreshToken(
			@NotBlank String accessToken,
			@NotBlank String refreshToken);

	long getMemberNoByAccessToken(
			@NotBlank String accessToken);
	
	AuthTokens refreshAuthTokens(
			@NotBlank String accessToken, 
			@NotBlank String refreshToken);
	
	void checkAccessible(
			@NotBlank String accessToken, 
			@NotBlank String accessCheckURL,
			@NotBlank String accessCheckMethod);
	
}
