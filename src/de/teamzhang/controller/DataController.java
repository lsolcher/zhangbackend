package de.teamzhang.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.BasicDBObject;

import de.teamzhang.model.User;

@Controller
public class DataController {

	@Autowired
	private MongoTemplate mongoTemplate;

	/*
	 * @InitBinder void allowFields(WebDataBinder webDataBinder) {
	 * webDataBinder.setAllowedFields("*"); }
	 */

	@GetMapping(value = "/signup")
	protected ModelAndView signupPage(HttpServletRequest request, HttpServletResponse arg1) {
		ModelAndView modelandview = new ModelAndView("signup");
		User user = new User();
		user.setFirstName("test");
		modelandview.addObject("user", user);

		return modelandview;
	}

	@PostMapping(value = "/signup")
	public String registerUser(Model model, @ModelAttribute("user") User user) {
		/*
		 * This is for saving collections, i.e. prios try { if
		 * (!mongoTemplate.collectionExists(User.class)) {
		 * mongoTemplate.createCollection(User.class); } } catch (Exception e) {
		 * e.printStackTrace(); } mongoTemplate.insert(user, "user");
		 */
		Map<String, Object> commandArguments = new BasicDBObject();
		commandArguments.put("createUser", user.getLastName());
		commandArguments.put("pwd", user.getPassword());
		String[] roles = { "readWrite" };
		commandArguments.put("roles", roles);
		BasicDBObject command = new BasicDBObject(commandArguments);
		mongoTemplate.executeCommand(command);
		return "signupSuccess";
	}

}