package com.insframe.server.tools;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

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
	
	public static boolean currentUserHasAuthority(String role){
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.getPrincipal() instanceof CustomUserDetails) {
			Collection<? extends GrantedAuthority> authorities = ((CustomUserDetails) authentication.getPrincipal()).getAuthorities();
			for(GrantedAuthority authority : authorities) {
				if(authority.getAuthority().equals(role)) {
					return true;
				}
			}
		}
		return false;
	}
}
