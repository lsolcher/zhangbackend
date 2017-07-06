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

	@Autowired
	PasswordEncoder passwordEncoder;

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

		http.authorizeRequests().antMatchers("/signup**", "/login**", "/index**", "signupSuccess**").permitAll()
				.anyRequest().authenticated().and().formLogin().loginPage("/index.html")
				.defaultSuccessUrl("/calendar.html", true).permitAll().and().logout().permitAll();

	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

}
