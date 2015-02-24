package com.insframe.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.insframe.server.rest.UserController;
import com.insframe.server.security.RestAuthenticationAccessDeniedHandler;
import com.insframe.server.security.RestAuthenticationEntryPoint;
import com.insframe.server.security.RestAuthenticationFailureHandler;
import com.insframe.server.security.RestAuthenticationSuccessHandler;
import com.insframe.server.service.UserService;
import com.insframe.server.support.service.SimpleCORSFilter;

@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RestAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private RestAuthenticationSuccessHandler successHandler;
	@Autowired
	private RestAuthenticationAccessDeniedHandler accessDeniedHandler;
	@Autowired
	private RestAuthenticationFailureHandler failureHandler;

    @Bean
    public TokenBasedRememberMeServices rememberMeServices() {
        return new TokenBasedRememberMeServices("remember-me-key", userService);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
	}
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .eraseCredentials(true)
            .userDetailsService(userService)
            .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.csrf().disable()
        	.addFilterBefore(new SimpleCORSFilter(), ChannelProcessingFilter.class)
        	.exceptionHandling()
        		.authenticationEntryPoint(authenticationEntryPoint)
        		.accessDeniedHandler(accessDeniedHandler)
        		.and()
            .formLogin()
            	.permitAll()
            	.loginProcessingUrl("/login")
            	.usernameParameter("username")
            	.passwordParameter("password")
            	.successHandler(successHandler)
            	.failureHandler(failureHandler)
                .and()
            .logout()
            	.permitAll()
            	.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            	.and()
            .sessionManagement()
            .maximumSessions(10);
	   
        http.authorizeRequests()
	        	.antMatchers("/login", "/favicon.ico", "/resources/**").permitAll()
	        	.anyRequest().authenticated();
//        .antMatchers("/users/**").access("hasRole('ROLE_ADMIN')")

    }
}