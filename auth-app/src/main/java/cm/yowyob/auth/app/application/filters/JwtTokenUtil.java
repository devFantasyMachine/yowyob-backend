/*
 * Copyright (c) 2023. Create by Yowyob
 *//*


package cm.yowyob.auth.app.application.filters;


import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class JwtTokenUtil {


	public static String generateToken(String userId,
									   String deviceId,
									   Set<String> roles,
									   String issuer,
									   String secretKey,
									   Long expireDuration) {

		Instant now = Instant.now();

		return "Jwts.builder()
				.setSubject(userId)
				.setIssuer(issuer)
				.claim("deviceId", deviceId)
				.claim("deviceType", "web")
				.claim("roles",roles)
				.setIssuedAt(Date.from(now))
				.setExpiration(Date.from(now.plusSeconds(expireDuration)))
				.signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encode(secretKey.getBytes()))
				.compact()";

	}


	@SuppressWarnings("unchecked")
	public static List<String> getAuthorities(Claims claims) {

		return (List<String>) claims.get(YowyobAuthConfig.AUTHORITIES_CLAIM_NAME);

	}


	public static List<SimpleGrantedAuthority> getGrantedAuthorities(Claims claims) {
		List<String> authorities = getAuthorities(claims);

		return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}


	public static Claims validateToken(String secretKey, String value) {
		String jwtToken = value.trim();
		return Jwts.parser().setSigningKey(Base64.getEncoder().encode(secretKey.getBytes())).parseClaimsJws(jwtToken).getBody();
	}





}

*/
