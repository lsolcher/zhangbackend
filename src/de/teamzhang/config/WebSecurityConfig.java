package de.teamzhang.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import de.teamzhang.service.SecUserDetailsService;

@Configuration
@EnableWebSecurity
@ComponentScan("de.teamzhang.**")

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	SecUserDetailsService userDetailsService;

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.authorizeRequests().antMatchers("/webjars/**").permitAll();
		//http.authorizeRequests().anyRequest().authenticated();
		http.csrf().disable();

		//http.formLogin().loginPage("/login.html").successHandler(authProvider).failureUrl("/login?error")
		//		.usernameParameter("email").permitAll().and().logout().logoutUrl("/logout").deleteCookies("remember-me")
		//		.logoutSuccessUrl("/").permitAll().and().rememberMe();

		//http.authorizeRequests().antMatchers("/", "/login", "/signup").permitAll().anyRequest().authenticated().and()
		//		.formLogin().loginPage("/login.html").permitAll().and().logout().permitAll();

		http.authorizeRequests().antMatchers("/signup**").permitAll().anyRequest().authenticated().and().formLogin()
				.loginPage("/login.html").defaultSuccessUrl("/calendar.html", true).permitAll().and().logout()
				.permitAll();

	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		/*User user = new User();
		user.setPassword("1");
		user.setLastName("e");
		Map<String, Object> commandArguments = new BasicDBObject();
		commandArguments.put("createUser", user.getLastName());
		commandArguments.put("pwd", user.getPassword());
		String[] roles = { "readWrite" };
		commandArguments.put("roles", roles);
		BasicDBObject command = new BasicDBObject(commandArguments);
		mongoTemplate.executeCommand(command);
		DBCollection collection = mongoTemplate.getCollection("users");
		List<User> myUsers = mongoTemplate.findAll(User.class);*/
		//User user = userRepository.findByUsername(username);

		auth.userDetailsService(userDetailsService);
		//auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

}
