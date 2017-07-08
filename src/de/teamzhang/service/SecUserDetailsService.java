package de.teamzhang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.teamzhang.model.SecUserDetails;
import de.teamzhang.model.User;
import de.teamzhang.repository.UserRepository;

@Service
@ComponentScan

public class SecUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = null;
		if (username.equals("admin"))
			user = userRepository.findByUsername("admin");
		else
			user = userRepository.findByMail(username);

		if (user == null) {
			throw new UsernameNotFoundException(username);
		} else {
			UserDetails details = new SecUserDetails(user);
			System.out.println(details.getAuthorities());
			return details;
		}
	}
}
