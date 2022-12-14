package com.britto.pagamentoapi.pagamento.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http
			.httpBasic()
			.and()
			.csrf().disable()
			.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS, "/*").permitAll()
			.and()
			.sessionManagement().disable();
	}
	
}
