package com.dlshouwen.swda.core.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dlshouwen.swda.core.security.cache.TokenCache;
import com.dlshouwen.swda.core.security.user.UserDetail;
import com.dlshouwen.swda.core.security.utils.TokenUtils;

import java.io.IOException;

/**
 * authentication token filter
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Component
@AllArgsConstructor
public class AuthenticationTokenFilter extends OncePerRequestFilter {
	
	/** token store cache */
	private final TokenCache tokenStoreCache;

	/**
	 * do filter internal
	 * @param request
	 * @param response
	 * @param chain
	 * @throws ServletException, IOException
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//		get access token
		String accessToken = TokenUtils.getAccessToken(request);
//		if access token is blank
		if (StringUtils.isBlank(accessToken)) {
//			do filter
			chain.doFilter(request, response);
//			return
			return;
		}
//		get user info
		UserDetail user = tokenStoreCache.getUser(accessToken);
//		if user is null
		if (user == null) {
//			do filter
			chain.doFilter(request, response);
//			return
			return;
		}
//		get authentication
		Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//		create context set authentication
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);
//		set context
		SecurityContextHolder.setContext(context);
//		do filter
		chain.doFilter(request, response);
	}

}
