package ru.exlmoto.code.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ru.exlmoto.code.service.implementation.UserDetailsServiceImplementation;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	private final UserDetailsServiceImplementation userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public WebSecurityConfiguration(UserDetailsServiceImplementation userDetailsService) {
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/delete/**", "/logout/**")
				.authenticated()
			.anyRequest()
				.permitAll()
			.and()
				.formLogin()
				.defaultSuccessUrl("/")
			.and()
				.logout()
				.logoutSuccessUrl("/")
				.clearAuthentication(true)
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID");
	}
}
