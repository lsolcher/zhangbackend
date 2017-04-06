package de.teamzhang.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

@Controller
public class CalendarController extends AbstractController {

	@RequestMapping(value = "/calendar", method = RequestMethod.GET)
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse arg1)
			throws Exception {

		ModelAndView modelandview = new ModelAndView("calendar");
		// modelandview.addObject("welcomeMessage", "Hi User");
		modelandview.addObject("veranstaltungen", getVeranstaltungen(request));

		return modelandview;

	}

	protected String getVeranstaltungen(HttpServletRequest request) {
		// TODO read from veranstaltungen.json
		JSONParser parser = new JSONParser();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getSession().getServletContext()
					.getResourceAsStream("/resources/json/veranstaltungen.json")));
			Object obj = parser.parse(reader);
			JSONArray array = new JSONArray();
			array.add(obj);
			return array.toString().replace("[[", "[").replace("]]", "]");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		//return "[{\"stg_name\": \"Angewandte Informatik (B)\",\"veranstaltungsart\": \"Übung\",\"parallelgruppe\": \"\",\"semester\": \"20162\",\"rhythmus\": \"Einzeltermin\",\"kurzname\": \"DeO1Ws\",\"sws\": \"4\"}]";
	}

	@RequestMapping(value = "/post", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody void updateData(@RequestBody String jsonString) {

		System.out.println(jsonString);

	}
}