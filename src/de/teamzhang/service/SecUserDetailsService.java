package de.teamzhang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.teamzhang.model.SecUserDetails;
import de.teamzhang.model.User;
import de.teamzhang.repository.UserRepository;

@Service
//@EnableMongoRepositories({ "de.teamzhang.repository" })
@ComponentScan

public class SecUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		//List<User> myUsers = mongoTemplate.findAll(User.class);
		User user = userRepository.findByLastName(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		} else {
			UserDetails details = new SecUserDetails(user);
			return details;
		}
	}
}
