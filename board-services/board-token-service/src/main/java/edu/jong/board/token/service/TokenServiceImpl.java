package edu.jong.board.token.service;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import edu.jong.board.BoardConstants.CacheNames;
import edu.jong.board.member.client.MemberFeignClient;
import edu.jong.board.member.response.MemberDetails;
import edu.jong.board.redis.service.RedisService;
import edu.jong.board.role.client.RoleFeignClient;
import edu.jong.board.role.response.RoleDetails;
import edu.jong.board.role.type.APIMethod;
import edu.jong.board.token.request.LoginParam;
import edu.jong.board.token.response.AuthTokens;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

	private final RedisService redisService;
	private final RoleFeignClient roleFeignClient;
	private final MemberFeignClient memberFeignClient;
	private final PasswordEncoder encoder;
	
	private final AntPathMatcher antPathMatcher = new AntPathMatcher();
	
	@Override
	public String generateAccessToken(long memberNo) {
		
		Date now = new Date();
        return Jwts.builder()
        		.setClaims(Jwts.claims()
        				.setSubject(String.valueOf(memberNo)))
        		.setIssuedAt(now)
        		.setExpiration(new Date(now.getTime() + 60 * 1000))
        		.signWith(SignatureAlgorithm.HS512, "SECRET_KEY") 
        		.compact();	
	}

	@Override
	public String generateRefreshToken(@NotBlank String accessToken) {

		Date now = new Date();
		String refreshToken = Jwts.builder()
        		.setIssuedAt(now)
        		.setExpiration(new Date(now.getTime() + 600 * 1000))
        		.signWith(SignatureAlgorithm.HS512, "SECRET_KEY") 
        		.compact();
        
		redisService.caching(CacheNames.REFRESH_TOKEN + refreshToken, accessToken, 600);
		
		return refreshToken;	
	}

	@Override
	public AuthTokens generateAuthTokens(@NotNull @Valid LoginParam param) {

		ResponseEntity<MemberDetails> response = memberFeignClient.getMember(param.getUsername());
		
		if (response.getStatusCode() != HttpStatus.OK) 
			throw new RuntimeException("서비스 호출에 문제가 발생했습니다.");
		
		MemberDetails member = response.getBody();
		
		if (encoder.matches(param.getPassword(), member.getPassword()))
			throw new RuntimeException("비밀번호가 일치하지 않습니다.");
		
		String accessToken = generateAccessToken(member.getNo());
		String refreshToken = generateRefreshToken(accessToken);
	
		return AuthTokens.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
	}

	@Override
	public void logoutAccessToken(@NotBlank String accessToken) {
		redisService.caching(CacheNames.BLACKLIST + accessToken, "logout", 60);
	}

	@Override
	public boolean validateAccessToken(@NotBlank String accessToken) {

		try {
			Claims claims = Jwts.parser()
					.setSigningKey("SECRET_KEY")
					.parseClaimsJws(accessToken)
					.getBody();

			if (StringUtils.isNumeric(claims.getSubject())) 
				throw new MalformedJwtException("형식에 맞지 않는 토큰입니다.");
		
			if (redisService.has(CacheNames.BLACKLIST + accessToken))
				throw new RuntimeException("로그아웃 된 토큰입니다.");
			
		} catch (Exception e) {
			log.warn("Exception Class => {} | Message => {}", e.getClass(), e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean validateRefreshToken(@NotBlank String accessToken, @NotBlank String refreshToken) {

		try {
			Claims claims = Jwts.parser()
					.setSigningKey("SECRET_KEY")
					.parseClaimsJws(refreshToken)
					.getBody();

			String redisAccessToken = redisService.get(CacheNames.REFRESH_TOKEN + refreshToken)
					.orElseThrow(() -> new ExpiredJwtException(null, claims, "유효기간이 지난 Refresh Token 입니다."));

			if (redisAccessToken.equals(accessToken))
				throw new RuntimeException("Access Token과 Refresh Token이 맞지 않습니다.");
			
		} catch (Exception e) {
			log.warn("Exception Class => {} | Message => {}", e.getClass(), e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public long getMemberNoByAccessToken(@NotBlank String accessToken) {

		Claims claims = null;
		try {
			claims = Jwts.parser()
					.setSigningKey("SECRET_KEY")
					.parseClaimsJws(accessToken)
					.getBody();

		} catch (ExpiredJwtException e) {
			claims = e.getClaims();
		}
		
		return Long.parseLong(claims.getSubject());
	}

	@Override
	public AuthTokens refreshAuthTokens(@NotBlank String accessToken, @NotBlank String refreshToken) {

		if (validateRefreshToken(accessToken, refreshToken))
			throw new RuntimeException("유효하지 않은 Refresh Token입니다.");
		
		long memberNo = getMemberNoByAccessToken(accessToken);
		
		String newAccessToken = generateAccessToken(memberNo);
		String newRefreshToken = generateRefreshToken(newAccessToken);
		
		redisService.remove(CacheNames.REFRESH_TOKEN + refreshToken);
		
		return AuthTokens.builder()
				.accessToken(newAccessToken)
				.refreshToken(newRefreshToken)
				.build();
	}

	@Override
	public void checkAccessible(@NotBlank String accessToken, @NotBlank String accessCheckURL,
			@NotBlank String accessCheckMethod) {
		
		if (validateAccessToken(accessToken))
			throw new RuntimeException("유효하지 않은 Access Token 입니다.");

		HttpMethod method = HttpMethod.valueOf(accessCheckMethod.toUpperCase());
		APIMethod apiMethod = APIMethod.convert(method);
		
		long memberNo = getMemberNoByAccessToken(accessToken);
		
		ResponseEntity<List<RoleDetails>> response = roleFeignClient.getRolesToMember(memberNo);
		
		if (response.getStatusCode() != HttpStatus.OK) 
			throw new RuntimeException("서비스 호출에 문제가 발생했습니다.");
		
		List<RoleDetails> roleList = response.getBody();
		boolean isAccessibleURL = false;
		boolean isAccessibleMethod = false;
		
		for (RoleDetails roleDetails : roleList) {
			
			isAccessibleURL = antPathMatcher.match(roleDetails.getUrlPattern(), accessCheckURL);
			isAccessibleMethod = (roleDetails.getMethod() == APIMethod.ALL 
					|| roleDetails.getMethod() == apiMethod);
			
			if (isAccessibleURL && isAccessibleMethod) return;
		}
		
		if (isAccessibleMethod)
			throw new RuntimeException("접근이 불가능한 Method 입니다.");

		if (isAccessibleURL)
			throw new RuntimeException("접근이 불가능한 URL 입니다.");

	}


}
