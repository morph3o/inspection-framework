package com.insframe.server.security;

import org.springframework.security.core.GrantedAuthority;

public class UserRoles implements GrantedAuthority{
	
	String role = null;

	@Override
	public String getAuthority() {
		return role;
	}

	public void setAuthority(String roleName) {
		this.role = roleName;
	}

}
