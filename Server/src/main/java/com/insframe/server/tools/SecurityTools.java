package com.insframe.server.tools;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.insframe.server.security.CustomUserDetails;

public class SecurityTools {
	public static boolean hasAuthority(CustomUserDetails userDetails, String role) {
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		for(GrantedAuthority authority : authorities) {
			if(authority.getAuthority().equals(role)) {
				return true;
			}
		}
		return false;
	}
}
