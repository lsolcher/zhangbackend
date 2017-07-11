package de.teamzhang.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import de.teamzhang.model.User;

@Controller
public class DataController {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping(value = "/signup")
	protected ModelAndView signupPage(HttpServletRequest request, HttpServletResponse arg1) {
		ModelAndView modelandview = new ModelAndView("signup");
		return modelandview;
	}

	@PostMapping(value = "/signup")
	public ModelAndView registerUser(Model model, @ModelAttribute("user") User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setUsername(user.getMail());
		Collection<SimpleGrantedAuthority> oldAuthorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder
				.getContext().getAuthentication().getAuthorities();
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
		List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<SimpleGrantedAuthority>();
		updatedAuthorities.add(authority);
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken(
						SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
						SecurityContextHolder.getContext().getAuthentication().getCredentials(), updatedAuthorities));

		try {
			if (!mongoTemplate.collectionExists(User.class)) {
				mongoTemplate.createCollection(User.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		DBCollection users = mongoTemplate.getCollection("user");
		BasicDBObject mail = new BasicDBObject();
		mail.put("mail", user.getMail());
		boolean userExists = false;
		DBCursor curs = users.find(mail);
		if (curs.size() > 0)
			userExists = true;
		ModelAndView returnSite = null;
		if (!userExists) {
			mongoTemplate.insert(user, "user");
			returnSite = new ModelAndView(new RedirectView("index.html"));
			returnSite.addObject("registrationSuccess");
		} else {
			returnSite = new ModelAndView(new RedirectView("signup.html"));
			returnSite.addObject("userExists");
		}
		return returnSite;
	}

	@GetMapping(value = "/login")
	protected ModelAndView loginPage(HttpServletRequest request, HttpServletResponse arg1) {
		ModelAndView modelandview = new ModelAndView("login");
		return modelandview;
	}

	/*@PostMapping(value = "/login")
	public ModelAndView loginVerification(Model model, @ModelAttribute("user") User user) {
	
		user.getPassword();
		user.getLastName();
	
		DB db = mongoTemplate.getDb();
		// db.get
		// db.command()
	
		// mongoTemplate.getDb().
	
		// boolean auth = db.authenticate("user", "password".toCharArray());
		//
		// if (success)
		// if (auth) {
		// return new ModelAndView("loginSuccess");
		// } else {
		// return new ModelAndView("loginFail");
		return null;
		// }
	}*/

	@GetMapping(value = "/controlpanel")
	protected ModelAndView controlpanelPage(HttpServletRequest request, HttpServletResponse arg1) {
		ModelAndView modelandview = new ModelAndView("controlpanel");
		return modelandview;
	}
}
