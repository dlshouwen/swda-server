package com.dlshouwen.swda.core.security.mobile;

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
 * mobile authentication provider
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class MobileAuthenticationProvider implements AuthenticationProvider, InitializingBean, MessageSourceAware {
	
	/** messages */
	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	
	/** authorities mapper */
	private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
	
	/** mobile user details service */
	private final MobileUserDetailsService mobileUserDetailsService;
	/** mobile verify code service */
	private final MobileVerifyCodeService mobileVerifyCodeService;

	/**
	 * constructor
	 * @param mobileUserDetailsService
	 * @param mobileVerifyCodeService
	 */
	public MobileAuthenticationProvider(MobileUserDetailsService mobileUserDetailsService, MobileVerifyCodeService mobileVerifyCodeService) {
//		set mobile user details service
		this.mobileUserDetailsService = mobileUserDetailsService;
//		set mobile verify code service
		this.mobileVerifyCodeService = mobileVerifyCodeService;
	}

	/**
	 * authenticate
	 * @param authentication
	 * @return authencation
	 * @throws authencation exception
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//		assert type
		Assert.isInstanceOf(MobileAuthenticationToken.class, authentication, () -> messages.getMessage("MobileAuthenticationProvider.onlySupports", "Only MobileAuthenticationProvider is supported"));
//		convert to mobile authentication token
		MobileAuthenticationToken authenticationToken = (MobileAuthenticationToken) authentication;
//		get mobile, code
		String mobile = authenticationToken.getName();
		String code = (String) authenticationToken.getCredentials();
//		typ catch exception
		try {
//			load user by mobile
			UserDetails userDetails = mobileUserDetailsService.loadUserByMobile(mobile);
//			if user details is null
			if (userDetails == null) {
//				throw bad credentials exception
				throw new BadCredentialsException("Bad credentials");
			}
//			verify mobile code
			if (mobileVerifyCodeService.verifyCode(mobile, code)) {
//				create success authentication and return
				return createSuccessAuthentication(authentication, userDetails);
			} else {
//				throw bad credenticals exception
				throw new BadCredentialsException("mobile code is not matched");
			}
		} catch (UsernameNotFoundException ex) {
//			throw bad credenticals exception
			throw new BadCredentialsException(this.messages.getMessage("MobileAuthenticationProvider.badCredentials", "Bad credentials"));
		}
	}

	/**
	 * create success authentication
	 * @param authentication
	 * @param user
	 * @return authentication
	 */
	protected Authentication createSuccessAuthentication(Authentication authentication, UserDetails user) {
//		create mobild authentication token
		MobileAuthenticationToken token = new MobileAuthenticationToken(user, null, authoritiesMapper.mapAuthorities(user.getAuthorities()));
//		set details
		token.setDetails(authentication.getDetails());
//		return token
		return token;
	}

	/**
	 * supports
	 * @param authentication
	 * @return is support
	 */
	@Override
	public boolean supports(Class<?> authentication) {
//		is assignabled from token
		return MobileAuthenticationToken.class.isAssignableFrom(authentication);
	}

	/**
	 * after properties set
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
//		assert not null
		Assert.notNull(mobileUserDetailsService, "mobileUserDetailsService must not be null");
		Assert.notNull(mobileVerifyCodeService, "mobileVerifyCodeService must not be null");
	}

	/**
	 * set message source
	 * @param messageSource
	 */
	@Override
	public void setMessageSource(MessageSource messageSource) {
//		set messages
		this.messages = new MessageSourceAccessor(messageSource);
	}

}
