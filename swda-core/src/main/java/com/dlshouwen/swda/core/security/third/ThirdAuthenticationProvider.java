package com.dlshouwen.swda.core.security.third;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

/**
 * third authentication provider
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public class ThirdAuthenticationProvider implements AuthenticationProvider, InitializingBean, MessageSourceAware {
	
	/** messages */
	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	
	/** authorities mapper */
	private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
	
	/** third user details service */
	private final ThirdUserDetailsService thirdUserDetailsService;
	/** third open id service */
	private final ThirdOpenIdService thirdOpenIdService;

	/**
	 * constructor
	 * @param thirdUserDetailsService
	 * @param thirdOpenIdService
	 */
	public ThirdAuthenticationProvider(ThirdUserDetailsService thirdUserDetailsService, ThirdOpenIdService thirdOpenIdService) {
//		set third user details service
		this.thirdUserDetailsService = thirdUserDetailsService;
//		set third open id service
		this.thirdOpenIdService = thirdOpenIdService;
	}

	/**
	 * authenticate
	 * @param authentication
	 * @return authentication
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//		assert instance of
		Assert.isInstanceOf(ThirdAuthenticationToken.class, authentication, () -> messages.getMessage("ThirdAuthenticationProvider.onlySupports", "Only ThirdAuthenticationProvider is supported"));
//		convert to authentication token
		ThirdAuthenticationToken authenticationToken = (ThirdAuthenticationToken) authentication;
//		get principal to third login
		ThirdLogin login = (ThirdLogin) authenticationToken.getPrincipal();
//		try catch
		try {
//			get open id
			String openId = thirdOpenIdService.getOpenId(login);
//			get user details
			UserDetails userDetails = thirdUserDetailsService.loadUserByOpenTypeAndOpenId(login.getOpenType(), openId);
//			if user details is empty
			if (userDetails == null) {
//				throw bad credetials exception
				throw new BadCredentialsException("Bad credentials");
			}
//			create success authentication and return
			return createSuccessAuthentication(authentication, userDetails);
		} catch (UsernameNotFoundException ex) {
//			throw bad credentials exception
			throw new BadCredentialsException(this.messages.getMessage("ThirdAuthenticationProvider.badCredentials", "Bad credentials"));
		}
	}

	/**
	 * create success authentication
	 * @param authentication
	 * @param user
	 * @return authentication
	 */
	protected Authentication createSuccessAuthentication(Authentication authentication, UserDetails user) {
//		create third authentication token
		ThirdAuthenticationToken token = new ThirdAuthenticationToken(user, authoritiesMapper.mapAuthorities(user.getAuthorities()));
//		set details
		token.setDetails(authentication.getDetails());
//		return token
		return token;
	}

	/**
	 * supports
	 * @param authentication
	 * @return is supports
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return ThirdAuthenticationToken.class.isAssignableFrom(authentication);
	}

	/**
	 * after properties set
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
//		assert not null
		Assert.notNull(thirdUserDetailsService, "thirdUserDetailsService must not be null");
		Assert.notNull(thirdOpenIdService, "thirdOpenIdService must not be null");
	}

	/**
	 * set message source
	 * @param messageSource
	 */
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}

}
