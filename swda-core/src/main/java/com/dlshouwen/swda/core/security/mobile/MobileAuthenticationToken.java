package com.dlshouwen.swda.core.security.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * mobile authentication token
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class MobileAuthenticationToken extends AbstractAuthenticationToken {

	/** serial version uid */
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	/** principal */
	private final Object principal;

	/** code */
	private String code;

	/**
	 * constructor
	 * @param principal
	 * @param code
	 */
	public MobileAuthenticationToken(Object principal, String code) {
//		super
		super(null);
//		set principal, code
		this.principal = principal;
		this.code = code;
//		set authenticated false
		setAuthenticated(false);
	}

	/**
	 * constructor
	 * @param principal
	 * @param code
	 * @param authorities
	 */
	public MobileAuthenticationToken(Object principal, String code, Collection<? extends GrantedAuthority> authorities) {
//		super
		super(authorities);
//		set principal, code
		this.principal = principal;
		this.code = code;
//		set authenticated true
		super.setAuthenticated(true);
	}

	/**
	 * set authenticated
	 * @param is authenticated
	 */
	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
//		assert true
		Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
//		set authenticated false
		super.setAuthenticated(false);
	}

	/**
	 * get credentials
	 * @return code
	 */
	@Override
	public Object getCredentials() {
		return this.code;
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
//		reset code
		this.code = null;
	}

}
