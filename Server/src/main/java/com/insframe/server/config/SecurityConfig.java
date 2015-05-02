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

import com.insframe.server.security.CustomLogoutSuccessHandler;
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
	@Autowired
	private CustomLogoutSuccessHandler logoutHandler;

    @Bean
    public TokenBasedRememberMeServices rememberMeServices() {
//    	TokenBasedRememberMeServices tokenServices;
    	
//        tokenServices = new TokenBasedRememberMeServices("remember-me-key", userService);
//        tokenServices.setTokenValiditySeconds(1209600);
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
            .authorizeRequests()
                .antMatchers("/", "/users/", "/users/**", "/inspectionobject/", "/inspectionobject/**", "/assignment/", "/assignment/**", "/attachment/", "/attachment/**").permitAll()
                .anyRequest().authenticated()
            .and()
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
	            	.deleteCookies("JSESSIONID")
	            	.logoutSuccessHandler(logoutHandler)
            .and()
            	.sessionManagement()
            		.maximumSessions(10);
	   
        http.authorizeRequests()
	        	.antMatchers("/login", "/favicon.ico", "/resources/**").permitAll()
	        	.anyRequest().authenticated();
//        .antMatchers("/users/**").access("hasRole('ROLE_ADMIN')")

    }
}