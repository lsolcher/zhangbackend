package de.teamzhang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WelcomeController {

	@RequestMapping("/index")
	public ModelAndView wishes() {
		ModelAndView model = new ModelAndView("HelloPage");
		return model;
	}
}
