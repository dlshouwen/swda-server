package com.dlshouwen.swda.core.security.user;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * user detail
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
public class UserDetail implements UserDetails {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;
	
	/** login id */
	private Long loginId;
	
	/** user id */
	private Long userId;
	
	/** user name */
	private String username;
	
	/** real name */
	private String realName;
	
	/** password */
	private String password;
	
	/** avatar */
	private String avatar;
	
	/** gender */
	private Integer gender;
	
	/** email */
	private String email;
	
	/** mobile */
	private String mobile;
	
	/** organ id */
	private Long organId;
	
	/** organ name */
	private String organName;
	
	/** status */
	private Integer status;
	
	/** super admin */
	private Integer superAdmin;

	/** tanant id */
	private Long tenantId;
	
	/** create time */
	private LocalDateTime createTime;
	
	/** data scope list */
	private List<Long> dataScopeList;
	
	/** is account non expired */
	private boolean isAccountNonExpired = true;
	
	/** is account non locked */
	private boolean isAccountNonLocked = true;
	
	/** is credentials non expired */
	private boolean isCredentialsNonExpired = true;
	
	/** is enabled */
	private boolean isEnabled = true;
	
	/** authority set */
	private Set<String> authoritySet;

	/**
	 * get authorities
	 */
	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authoritySet.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
	}

	/**
	 * is account non expired
	 * @return is account non expired
	 */
	@Override
	public boolean isAccountNonExpired() {
		return this.isAccountNonExpired;
	}

	/**
	 * is account non locked
	 * @return is account non locked
	 */
	@Override
	public boolean isAccountNonLocked() {
		return this.isAccountNonLocked;
	}

	/**
	 * is credentials non expired
	 * @return is credentials non expired
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return this.isCredentialsNonExpired;
	}

	/**
	 * is enabled
	 * @return is enabled
	 */
	@Override
	public boolean isEnabled() {
		return this.isEnabled;
	}

}
