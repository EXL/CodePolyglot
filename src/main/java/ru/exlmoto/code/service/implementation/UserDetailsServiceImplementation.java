package ru.exlmoto.code.service.implementation;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ru.exlmoto.code.configuration.CodeConfiguration;

import java.util.Collections;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {
	private final CodeConfiguration config;

	public UserDetailsServiceImplementation(CodeConfiguration config) {
		this.config = config;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return new User(
			config.getAdminUsername(),
			new BCryptPasswordEncoder().encode(config.getAdminPassword()),
			Collections.singleton(new SimpleGrantedAuthority("Owner"))
		);
	}
}
