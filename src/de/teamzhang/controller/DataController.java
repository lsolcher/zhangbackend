package de.teamzhang.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.mongodb.DB;

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
		User user = new User();
		user.setFirstName("test");
		modelandview.addObject("user", user);

		return modelandview;
	}

	@PostMapping(value = "/signup")
	public ModelAndView registerUser(Model model, @ModelAttribute("user") User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		try {
			if (!mongoTemplate.collectionExists(User.class)) {
				mongoTemplate.createCollection(User.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mongoTemplate.insert(user, "user");
		ModelAndView index = new ModelAndView(new RedirectView("index.html"));
		index.addObject("test");
		return index;
	}

	@GetMapping(value = "/login")
	protected ModelAndView loginPage(HttpServletRequest request, HttpServletResponse arg1) {
		ModelAndView modelandview = new ModelAndView("login");
		return modelandview;
	}

	@PostMapping(value = "/login")
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
	}

	@GetMapping(value = "/controlpanel")
	protected ModelAndView controlpanelPage(HttpServletRequest request, HttpServletResponse arg1) {
		ModelAndView modelandview = new ModelAndView("controlpanel");
		return modelandview;
	}
}
