package de.teamzhang.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
		JSONParser parser = new JSONParser();
		JSONArray priosJSON;
		try {
			priosJSON = (JSONArray) parser.parse(prios);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*Prio exludeDayCombinationPrio = new ExcludeDayCombinationPrio(string, profID, courses, text, dayOne, dayTwo, timeOne, timeTwo, hasTime, isExcluding);
		Prio freeTextInputPrio = new FreeTextInputPrio(string, profID, courses, textFieldInput);
		Prio simplePrio = new SimplePrio(string, profID, courses, acceptsFourHourCourses);
		Prio singleChoicePrio = new SingleChoicePrio(string, profID, courses, options, text, showCourses);*/

		ObjectMapper mapper = new ObjectMapper();
		//List<?> list = null;
		try {
			List<?> list = mapper.readValue(prios, List.class);
			for (Object o : list)
				System.out.println(o.toString());
			//System.out.println(list.toString());

			//List<Prio> prioList = mapper.readValue(prios, new TypeReference<List<Prio>>(){});
			//Map<String, String> myMap = new HashMap<String, String>();
			//myMap = mapper.readValue(prios, HashMap.class);
			//System.out.println("Map is: " + myMap);

			//List<SingleChoicePrio> prioList = (List<SingleChoicePrio>) mapper.readValue(prios, SingleChoicePrio.class);
			//mapper.readValue(prios,
			//TypeFactory.defaultInstance().constructCollectionType(List.class,  
			//Prio.class));
			// Example - convert JSON string to Object
			//String jsonInString = "{\"name\":\"mkyong\",\"salary\":7500,\"skills\":[\"java\",\"python\"]}";
			//Staff staff1 = mapper.readValue(jsonInString, Staff.class);
			//System.out.println(staff1.getName());

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