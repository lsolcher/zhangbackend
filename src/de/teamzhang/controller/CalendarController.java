package de.teamzhang.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

@Controller
public class CalendarController extends AbstractController {

	@RequestMapping(value = "/calendar", method = RequestMethod.GET)
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {

		ModelAndView modelandview = new ModelAndView("index");
		//modelandview.addObject("welcomeMessage", "Hi User");

		return modelandview;
	}

}