package com.example.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.example.demo.security.OpenIdConnectFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private OAuth2RestTemplate restTemplate;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	@Bean
	public OpenIdConnectFilter myFilter() {
		final OpenIdConnectFilter filter = new OpenIdConnectFilter("/google-login");
		filter.setRestTemplate(restTemplate);
		return filter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.addFilterAfter(new OAuth2ClientContextFilter(), AbstractPreAuthenticatedProcessingFilter.class)
				.addFilterAfter(myFilter(), OAuth2ClientContextFilter.class).httpBasic()
				.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/google-login")).and()
				.authorizeRequests()
				// .antMatchers("/","/index*").permitAll()
				.anyRequest().authenticated();

		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

		http.logout().logoutSuccessUrl("http://localhost:8081/");

	}

}