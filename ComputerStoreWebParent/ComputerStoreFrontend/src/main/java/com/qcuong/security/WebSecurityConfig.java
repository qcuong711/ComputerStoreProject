package com.qcuong.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/images/**", "/webjars/**", "/fontawesome/**", "/style.css", "/js/**", 
				"/richtext/**", "/star-rising/**");
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomerUserDetailsService();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		
		provider.setUserDetailsService(userDetailsService());
		provider.setPasswordEncoder(passwordEncoder());;
		
		return provider;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/update_account", "/cart", "/checkout", 
				"/place_order", "/cusOrder", "/orders/**", "/write_review/**", "/post_review").authenticated()
			.anyRequest().permitAll()
			.and()
			.formLogin().loginPage("/login")
						.usernameParameter("email")
						.permitAll()
			.and().logout().permitAll();
	}

}
