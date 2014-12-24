package com.insframe.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import com.insframe.server.account.UserSrv;

@Configuration
@EnableWebMvcSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserSrv userSrv() {
        return new UserSrv();
    }

    @Bean
    public TokenBasedRememberMeServices rememberMeServices() {
        return new TokenBasedRememberMeServices("remember-me-key", userSrv());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
	}

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .eraseCredentials(true)
            .userDetailsService(userSrv())
            .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/**", "/favicon.ico", "/resources/**", "/signup", "/user/", "/user/**", "/inspectionobject").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/signin")
                .permitAll()
                .failureUrl("/signin?error=1")
                .loginProcessingUrl("/authenticate")
                .and()
            .logout()
                .logoutUrl("/logout")
                .permitAll()
                .logoutSuccessUrl("/signin?logout")
                .and()
            .rememberMe()
                .rememberMeServices(rememberMeServices())
                .key("remember-me-key");
    }
}