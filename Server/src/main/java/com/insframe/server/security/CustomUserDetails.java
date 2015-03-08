package com.insframe.server.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.insframe.server.model.User;

@SuppressWarnings("serial")
public class CustomUserDetails implements UserDetails {
	
	private User user;

	boolean status = false;
	
	Collection<? extends GrantedAuthority> authorities = null;
	
	public CustomUserDetails(User user, BCryptPasswordEncoder encoder){
		this.user = user;
		authorities = new ArrayList<GrantedAuthority>();
		this.setAuthentication(true);
		String enc = encoder.encode(user.getPassword());
		this.setPassword(enc);
		Collection<UserRoles> roles = new ArrayList<UserRoles>();
		UserRoles customRole = new UserRoles();
		customRole.setAuthority(user.getRole());
		roles.add(customRole);
		this.setAuthorities(roles);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		return this.user.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public void setAuthorities(Collection<? extends GrantedAuthority> roles) {
		this.authorities = roles;
	}

	public void setAuthentication(boolean status) {
		this.status = status;
	}
	
	public void setPassword(String pass) {
		this.user.setPassword(pass);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
