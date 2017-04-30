package de.teamzhang.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.teamzhang.model.User;
import de.teamzhang.repository.UserRepository;
import de.teamzhang.service.TheUserPrincipal;

@Service
public class TheUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByFirstName(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new TheUserPrincipal(user);
	}
}