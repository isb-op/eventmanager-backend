package com.neki.eventmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.neki.eventmanager.models.User;
import com.neki.eventmanager.repositories.UserRepository;

@Service
public class UserAuthorizationService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		return userRepository.findByLogin(login);
	}

	public User getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			throw new UsernameNotFoundException("Login não autenticado.");
		}
		String login = authentication.getName();
		if (login == null || login.isEmpty()) {
			throw new UsernameNotFoundException("Login não encontrado na autenticação.");
		}
		User user = (User) userRepository.findByLogin(login);
		if (user == null) {
			throw new UsernameNotFoundException("Login não encontrado");
		}
		return user;
	}
}
