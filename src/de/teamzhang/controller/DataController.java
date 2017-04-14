package de.teamzhang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import de.teamzhang.model.User;

@Controller
public class DataController {

	@Autowired
	private MongoTemplate mongoTemplate;

	@GetMapping(value = "/signup")
	public String signUpPage(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@PostMapping(value = "/signup")
	public String registerUser(@ModelAttribute User user) {
		try {
			if (!mongoTemplate.collectionExists(User.class)) {
				mongoTemplate.createCollection(User.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		user.setFirstName("Jon");
		mongoTemplate.insert(user, "user");
		return user.toString();
	}

}
