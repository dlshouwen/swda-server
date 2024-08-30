package com.dlshouwen.swda.core.security.third;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * third autheication token
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class ThirdAuthenticationToken extends AbstractAuthenticationToken {
	
	/** serial version uid */
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
	
	/** principal */
	private final Object principal;
	/** credentials */
	private Object credentials;

	/**
	 * constructor
	 * @param principal
	 */
	public ThirdAuthenticationToken(Object principal) {
//		super
		super(null);
//		set principal
		this.principal = principal;
//		set authenticated false
		setAuthenticated(false);
	}

	/**
	 * constructor
	 * @param principal
	 * @param authorities
	 */
	public ThirdAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
//		super
		super(authorities);
//		set principal
		this.principal = principal;
//		set authenticated true
		super.setAuthenticated(true);
	}

	/**
	 * set authenticated
	 * @param isAuthenticated
	 */
	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
//		assert is true
		Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
//		set authenticated false
		super.setAuthenticated(false);
	}

	/**
	 * get credentials
	 * @return credentials
	 */
	@Override
	public Object getCredentials() {
		return this.credentials;
	}

	/**
	 * get principal
	 * @return principal
	 */
	@Override
	public Object getPrincipal() {
		return this.principal;
	}

	/**
	 * erase credentials
	 */
	@Override
	public void eraseCredentials() {
//		erase credentials
		super.eraseCredentials();
//		reset credentials
		this.credentials = null;
	}

}
