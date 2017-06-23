package de.teamzhang.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.teamzhang.model.ExcludeDayCombinationPrio;
import de.teamzhang.model.FreeTextInputPrio;
import de.teamzhang.model.Prio;

import de.teamzhang.model.Schedule;
import de.teamzhang.model.SecUserDetails;
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

	@SuppressWarnings("unchecked")
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
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		String currentPrincipalName = authentication.getName();
//		SecUserDetails userDetails = (SecUserDetails) authentication.getPrincipal();
//		BigInteger userId = userDetails.getId();
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(prios);
		try {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			List<HashMap> list = mapper.readValue(prios, List.class);
			for (Map m : list) {
				Prio prio = new Prio();
				try {
					if (m.get("course").equals("Alle Kurse")) {
						prio.setValidForAllCourses(true);
					} else
						prio.setCourse(Integer.parseInt((String) m.get("course")));
				} catch (NullPointerException ne) { // no courses chosen, we
														// assume all
					prio.setValidForAllCourses(true);
				}
				prio.setName((String) m.get("title"));
//				prio.setUserId(userId);
				try {
					@SuppressWarnings("unchecked")
					List<String> text = (List<String>) m.get("text");
					StringBuilder sb = new StringBuilder();
					for (String s : text) {
						sb.append(s);

						sb.append("\t");
					}
					prio.setText((sb.toString()));
				} catch (ClassCastException e) {
					prio.setText((String) m.get("text"));
				}
				if (m.get("type").equals("SingleChoicePrio")) {
					prio = new SingleChoicePrio();
					((SingleChoicePrio) prio).setOption(Integer.parseInt((String) m.get("option")));
				} else if (m.get("type").equals("SimplePrio")) {
					prio = new SimplePrio();
				} else if (m.get("type").equals("Schedule")) {
					prio = new Schedule();
//					((Schedule) prio).setSchedule( m.get("calendar" ));
				} else if (m.get("type").equals("ExcludeDayCombinationPrio")) {
					prio = new ExcludeDayCombinationPrio();

//					if (!m.get("title").equals("Tage ausschließen")) {
						if( m.get("dayOne").equals(Type.class) ) {
							((ExcludeDayCombinationPrio) prio).setDayOne(Integer.parseInt((String) m.get("dayOne")));
						}
						if( m.get("dayOne").equals(Type.class) ) {
							((ExcludeDayCombinationPrio) prio).setDayTwo(Integer.parseInt((String) m.get("dayTwo")));
						}
						if( m.get("dayOne").equals(Type.class) ) {
							((ExcludeDayCombinationPrio) prio).setTimeOne(Integer.parseInt((String) m.get("timeOne")));
						}
						if( m.get("dayOne").equals(Type.class) ) {
							((ExcludeDayCombinationPrio) prio).setTimeTwo(Integer.parseInt((String) m.get("timeTwo")));
						}
//						System.out.println(m.get("dayOne"));
//						@SuppressWarnings("unchecked")
//						List<String> dayAndTimeOne = (ArrayList<String>) m.get("dayOne");
//						@SuppressWarnings("unchecked")
//						List<String> dayAndTimeTwo = (ArrayList<String>) m.get("dayTwo");
//						((ExcludeDayCombinationPrio) prio).setDayOne(Integer.parseInt(dayAndTimeOne.get(0)));
//						((ExcludeDayCombinationPrio) prio).setDayTwo(Integer.parseInt(dayAndTimeTwo.get(0)));
//						((ExcludeDayCombinationPrio) prio).setTimeOne(Integer.parseInt(dayAndTimeOne.get(1)));
//						((ExcludeDayCombinationPrio) prio).setTimeTwo(Integer.parseInt(dayAndTimeTwo.get(1)));
//					}

				} else if (m.get("type").equals("FreeTextInputPrio")) {
					prio = new FreeTextInputPrio();
				}
				try {
					if (!mongoTemplate.collectionExists("prios")) {
						mongoTemplate.createCollection("prios");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				mongoTemplate.insert(prio, "prios");
			}
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}