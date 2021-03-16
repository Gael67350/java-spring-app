package eu.gael67350.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import eu.gael67350.api.auth.AuthenticationFilter;
import eu.gael67350.api.auth.AuthenticationProvider;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@EnableWebSecurity
@SecurityScheme(
		name = "bearerAuth",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "UUID",
		scheme = "bearer"
		)
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final RequestMatcher PROTECTED_URLS = new OrRequestMatcher(
			new AntPathRequestMatcher("/api/**"),
			new AntPathRequestMatcher("/auth/logout")
			);
	
	private AuthenticationProvider authProvider;
	
	public ApiSecurityConfig(AuthenticationProvider authProvider) {
		super();
		this.authProvider = authProvider;
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}
	
	 @Override
	 public void configure(WebSecurity web) {
		 web.ignoring().antMatchers("/auth/token");
	 }
	
	public void configure(HttpSecurity http) throws Exception {
		http
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.exceptionHandling()
		.and()
		.authenticationProvider(authProvider)
		.addFilterBefore(authenticationFilter(), AnonymousAuthenticationFilter.class)
		.authorizeRequests()
		.requestMatchers(PROTECTED_URLS)
		//.anyRequest()
		.authenticated()
		.and()
		.csrf().disable()
		.formLogin().disable()
		.httpBasic().disable()
		.logout().disable();
	}
	
	@Bean
	AuthenticationFilter authenticationFilter() throws Exception {
		AuthenticationFilter filter = new AuthenticationFilter(PROTECTED_URLS);
		filter.setAuthenticationManager(authenticationManager());
		return filter;
	}
	
	@Bean
	AuthenticationEntryPoint forbiddenEntryPoint() {
		return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
	}
}
