package de.teamzhang.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.teamzhang.model.Prio;
import de.teamzhang.model.SimplePrio;
import de.teamzhang.model.SingleChoicePrio;

@Controller
public class CalendarController extends AbstractController {

	@Autowired
	private MongoTemplate mongoTemplate;

	@RequestMapping(value = "/calendar", method = RequestMethod.GET)
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse arg1)
			throws Exception {

		ModelAndView modelandview = new ModelAndView("calendar");
		// modelandview.addObject("welcomeMessage", "Hi User");
		modelandview.addObject("veranstaltungen", getVeranstaltungen(request));

		return modelandview;

	}

	protected String getVeranstaltungen(HttpServletRequest request) {
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
		// return "[{\"stg_name\": \"Angewandte Informatik
		// (B)\",\"veranstaltungsart\": \"Übung\",\"parallelgruppe\":
		// \"\",\"semester\": \"20162\",\"rhythmus\":
		// \"Einzeltermin\",\"kurzname\": \"DeO1Ws\",\"sws\": \"4\"}]";
	}

	@RequestMapping(value = "/post.json", method = RequestMethod.POST)
	public @ResponseBody void updateData(@RequestBody String prios) {
		ObjectMapper mapper = new ObjectMapper();
		//List<?> list = null;
		try {

			List<HashMap> list = mapper.readValue(prios, List.class);
			for (Map m : list) {
				if (m.get("type").equals("SingleChoicePrio")) {
					Prio prio = new SingleChoicePrio();
					System.out.println(m.get("option"));
					((SingleChoicePrio) prio).setOption(Integer.parseInt((String) m.get("option")));
					System.out.println();
				} else if (m.get("type").equals("SimplePrio")) {
					Prio prio = new SimplePrio();
					//prio.set..
				}
				//...
			}
			System.out.println();

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//ObjectMapper mapper = new ObjectMapper();
		//List<Employe> list = mapper.readValue(jsonString, TypeFactory.collectionType(List.class, Employe.class));

		//System.out.println(prios);
		// for (Prio p : prios)
		// mongoTemplate.getCollection("prios").insert(p);
	}

}