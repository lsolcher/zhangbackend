package de.teamzhang.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

@Controller
public class ResultController extends AbstractController {

	@Autowired
	private MongoTemplate mongoTemplate;

	@RequestMapping(value = "/results", method = RequestMethod.GET)
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse arg1)
			throws Exception {

		ModelAndView modelandview = new ModelAndView("results");
//		modelandview.addObject("welcomeMessage", "Hi User");
		
		return modelandview;

	}
}