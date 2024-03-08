/*
package cm.yowyob.auth.app.application.filters;

import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class JWTAuthorizationFilter extends OncePerRequestFilter {


	private final String SECRET_KEY = "tonzonimdmndmjkbjbw6767ry1yr9yr97ydy98y9y19r4y9ry9rnmdnnjwnwjnjdnwej";


	private final String HEADER = "AuthorizationEntity";
	private final String PREFIX = "Bearer ";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		String authorizationHeader = request.getHeader(HEADER);

		if (authorizationHeader == null || !authorizationHeader.startsWith(PREFIX)) {
			chain.doFilter(request, response);
			return;

		} else {

			try {
				if (checkJWTToken(request, response)) {
					Claims claims = validateToken(request);
					if (claims.get("authorities") != null) {
						setUpSpringAuthentication(claims);
					} else {
						SecurityContextHolder.clearContext();
					}
				} else {
					SecurityContextHolder.clearContext();
				}
				chain.doFilter(request, response);
			} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
				return;
			}

		}

	}

	public Claims validateToken(HttpServletRequest request) {
		String jwtToken = request.getHeader(HEADER).trim().replace(PREFIX, "").trim();
		return Jwts.parser().setSigningKey(Base64.getEncoder().encode(SECRET_KEY.getBytes())).parseClaimsJws(jwtToken).getBody();
	}

	*/
/**
	 * Authentication method in Spring flow
	 *
	 *//*

	private void setUpSpringAuthentication(Claims claims) {
		@SuppressWarnings("unchecked")
		List<String> authorities = (List<String>) claims.get("authorities");

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		SecurityContextHolder.getContext().setAuthentication(auth);

	}

	private boolean checkJWTToken(HttpServletRequest request, HttpServletResponse res) {
		String authenticationHeader = request.getHeader(HEADER);
		return authenticationHeader != null && authenticationHeader.startsWith(PREFIX);
	}

}*/
