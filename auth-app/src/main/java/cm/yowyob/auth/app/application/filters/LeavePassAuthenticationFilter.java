/*
 * Copyright (c) 2023. Create by Yowyob
 */

package cm.yowyob.auth.app.application.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.CookieRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public final class LeavePassAuthenticationFilter extends OncePerRequestFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/web/login", "GET");
    private static final Authentication ANONYMOUS_AUTHENTICATION = new AnonymousAuthenticationToken("anonymous", "anonymousUser", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
    private final RequestMatcher requiresAuthenticationRequestMatcher;
    private final SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();


    public LeavePassAuthenticationFilter() {
        this(new OrRequestMatcher(DEFAULT_ANT_PATH_REQUEST_MATCHER));
    }


    public LeavePassAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super();
        Assert.notNull(requiresAuthenticationRequestMatcher, " AuthenticationManager must not be null");
        this.requiresAuthenticationRequestMatcher = requiresAuthenticationRequestMatcher;
        this.successHandler.setRequestCache(new CookieRequestCache());
    }


    private boolean requiresAuthentication(HttpServletRequest request) {
        return this.requiresAuthenticationRequestMatcher.matches(request);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (!this.requiresAuthentication(request)) {
            filterChain.doFilter(request, response);
        } else {

            Authentication principal = SecurityContextHolder.getContext().getAuthentication();
            if (principal == null) {
                principal = ANONYMOUS_AUTHENTICATION;
            }

            if (principal instanceof AnonymousAuthenticationToken){

                filterChain.doFilter(request, response);
                return;
            }

            successfulAuthentication(request, response, principal);
        }
    }

    private void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException, ServletException {
        this.successHandler.onAuthenticationSuccess(request, response, authResult);
    }


}
