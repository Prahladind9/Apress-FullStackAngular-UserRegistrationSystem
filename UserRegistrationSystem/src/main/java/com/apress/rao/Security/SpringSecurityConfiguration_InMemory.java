package com.apress.rao.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER) -- No Longer Needed in Spring Boot 2.0
public class SpringSecurityConfiguration_InMemory extends WebSecurityConfigurerAdapter{
	
	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.inMemoryAuthentication().withUser("user").password("password")
				.roles("USER");
		auth.inMemoryAuthentication().withUser("admin")
				.password("password")
				.roles("USER", "ADMIN");
		
	}
	
	/*
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.httpBasic().and()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/api/user/")
			.hasRole("USER")
			.antMatchers(HttpMethod.POST,"/api/user/")
			.hasRole("USER")
			.antMatchers(HttpMethod.PUT,"/api/user/**")
			.hasRole("USER")
			.antMatchers(HttpMethod.DELETE,"/api/user/**")
			.hasRole("ADMIN")
			.and()
			.csrf()	//cross site register forgery
			.disable();
	}*/
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.httpBasic().realmName("User Registration System")
		.and()
		.authorizeRequests()
		.antMatchers("/login/login.html","/template/home.html")
		.permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.csrf()
		.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}	
	
	@SuppressWarnings("deprecation")
	@Bean
	public PasswordEncoder passwordEncoder() {
	PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	((DelegatingPasswordEncoder)passwordEncoder).setDefaultPasswordEncoderForMatches(NoOpPasswordEncoder.getInstance());
	return passwordEncoder;
	}
	
}
