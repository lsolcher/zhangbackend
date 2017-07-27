package de.teamzhang.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import de.teamzhang.model.Course;
import de.teamzhang.model.ExcludeDayCombinationPrio;
import de.teamzhang.model.FreeTextInputPrio;
import de.teamzhang.model.Prio;
import de.teamzhang.model.Schedule;
import de.teamzhang.model.SecUserDetails;
import de.teamzhang.model.SimplePrio;
import de.teamzhang.model.SingleChoicePrio;
import de.teamzhang.model.Teacher;
import de.teamzhang.model.User;

@Controller
public class CalendarController extends AbstractController {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping(value = "/calendar", method = RequestMethod.GET)
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse arg1)
			throws Exception {
		ModelAndView modelandview = new ModelAndView("calendar");
		modelandview.addObject("veranstaltungen", getVeranstaltungen(request));
		//modelandview.addObject(attributeValue)
		//setUserTeachers();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SecUserDetails userDetails = (SecUserDetails) authentication.getPrincipal();
		//BigInteger userId = userDetails.getId();
		User user = userDetails.getUser();

		DBCollection teachers = mongoTemplate.getCollection("teachers");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("firstName", user.getFirstName());
		whereQuery.put("lastName", user.getLastName());
		DBCursor cursor = teachers.find(whereQuery);
		if (cursor.hasNext()) {
			DBObject resultElement = cursor.next();
			Map resultElementMap = resultElement.toMap();
			BasicDBList prios = (BasicDBList) resultElementMap.get("prios");
			BasicDBList courses = (BasicDBList) resultElementMap.get("courses");
			JSONObject prioMap = new JSONObject(prios.toMap());
			JSONObject courseMap = new JSONObject(courses.toMap());

			JSONArray prioArray = new JSONArray();
			prioArray.add(prioMap);

			JSONArray coursesArray = new JSONArray();
			coursesArray.add(courseMap);

			modelandview.addObject("prios", prioArray);
			modelandview.addObject("courses", coursesArray);

		}

		modelandview.addObject("firstName", user.getFirstName());

		return modelandview;
	}

	private void setUserTeachers() {
		List<Teacher> allTeachers = new ArrayList<Teacher>();
		List<User> allUsers = new ArrayList<User>();
		DBCollection teachersDB = mongoTemplate.getCollection("teachers");
		DBCursor cursor = teachersDB.find();
		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			Teacher t = mongoTemplate.getConverter().read(Teacher.class, obj);
			allTeachers.add(t);
		}
		teachersDB.drop();
		for (Teacher t : allTeachers) {
			String firstLetter = t.getFirstName().substring(0, 1);
			User user = new User();
			user.setFirstName(t.getFirstName());
			user.setLastName(t.getLastName());
			user.setPassword(passwordEncoder.encode("test"));
			String mail = firstLetter + "." + t.getLastName() + "@webmail.htw-berlin.de";
			user.setMail(mail);
			user.setUsername(mail);
			if (t.isProf())
				user.setRole(0);
			else
				user.setRole(1);
			t.setUser(user);
			allUsers.add(user);
		}
		DBCollection users = mongoTemplate.getCollection("user");
		users.drop();
		try {
			if (!mongoTemplate.collectionExists("teachers")) {
				mongoTemplate.createCollection("teachers");
			}
			if (!mongoTemplate.collectionExists("user")) {
				mongoTemplate.createCollection("user");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Teacher t : allTeachers) {
			mongoTemplate.insert(t, "teachers");
		}
		for (User t : allUsers) {
			mongoTemplate.insert(t, "user");
		}

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
	}

	@RequestMapping(value = "/post.json", method = RequestMethod.POST)
	public @ResponseBody void updateData(HttpServletRequest request, @RequestBody String prios) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SecUserDetails userDetails = (SecUserDetails) authentication.getPrincipal();
		BigInteger userId = userDetails.getId();
		String firstName = userDetails.getUser().getFirstName();
		String lastName = userDetails.getUser().getLastName();
		Teacher teacher = new Teacher();
		teacher.setFirstName(firstName);
		teacher.setLastName(lastName);
		teacher.setId(userId);
		boolean isProf = false;
		if (userDetails.getUser().getRole() == 0)
			isProf = true;
		teacher.setProf(isProf);

		User user = userDetails.getUser();
		teacher.setUser(user);

		ObjectMapper mapper = new ObjectMapper();
		// System.out.println(prios);
		List<Course> courseList = new ArrayList<Course>();
		try {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			List<HashMap> list = mapper.readValue(prios, List.class);
			for (Map m : list) {
				if (m.get("type").equals("Courses")) {
					List<HashMap> courses = (List<HashMap>) m.get("courses");
					for (Map<?, ?> course : courses) {
						Course c = new Course();
						c.setName((String) course.get("kurzname"));
						int sws = 0;
						try {
							sws = (Integer) course.get("sws");
						} catch (ClassCastException cce) {
							cce.printStackTrace();
							sws = Integer.parseInt((String) m.get("sws"));
						} catch (NullPointerException npe) {
							npe.printStackTrace();
							sws = 2;
						}
						if (sws % 2 == 1)
							sws += 1;
						int slotsNeeded = sws / 2;
						c.setSlotsNeeded(slotsNeeded);
						c.setGroup((String) course.get("parallelgruppe"));
						c.setCourseID(Integer.parseInt((String) course.get("courseID")));
						addSemesters(c, course);
						courseList.add(c);
					}
				} else if (m.get("type").equals("Schedule")) {
					Prio prio = new Schedule();
					ArrayList<Integer> calendar = (ArrayList<Integer>) m.get("calendar");
					((Schedule) prio).setSchedule(calendar);
					teacher.setWeightedDayTimeWishes(calendar);
					teacher.addPrio(prio);

				} else {
					Prio prio = new Prio();
					if (m.get("type").equals("SingleChoicePrio")) {
						prio = new SingleChoicePrio();
						((SingleChoicePrio) prio).setOption(Integer.parseInt((String) m.get("option")));
						if (m.get("title").equals("Raumbeschaffenheit")) {
							try {
								((SingleChoicePrio) prio).setCourse(Integer.parseInt((String) m.get("course")));
								((SingleChoicePrio) prio).setValidForAllCourses(false);
							} catch (Exception e) {
								((SingleChoicePrio) prio).setValidForAllCourses(true);
							}
						}
					} else if (m.get("type").equals("SimplePrio")) {
						prio = new SimplePrio();
					} else if (m.get("type").equals("ExcludeDayCombinationPrio")) {
						prio = new ExcludeDayCombinationPrio();
						// if (!m.get("title").equals("Tage ausschließen")) {
						if (m.get("dayOne").equals(Type.class)) {
							((ExcludeDayCombinationPrio) prio).setDayOne(Integer.parseInt((String) m.get("dayOne")));
						}
						if (m.get("dayOne").equals(Type.class)) {
							((ExcludeDayCombinationPrio) prio).setDayTwo(Integer.parseInt((String) m.get("dayTwo")));
						}
						if (m.get("dayOne").equals(Type.class)) {
							((ExcludeDayCombinationPrio) prio).setTimeOne(Integer.parseInt((String) m.get("timeOne")));
						}
						if (m.get("dayOne").equals(Type.class)) {
							((ExcludeDayCombinationPrio) prio).setTimeTwo(Integer.parseInt((String) m.get("timeTwo")));
						}
					} else if (m.get("type").equals("FreeTextInputPrio")) {
						prio = new FreeTextInputPrio();
					}
					// TODO: set valid for certain courses
					prio.setValidForAllCourses(true);
					// }
					prio.setName((String) m.get("title"));
					try {
						@SuppressWarnings("unchecked")
						List<String> text = (List<String>) m.get("text");
						StringBuilder sb = new StringBuilder();
						if (text != null && !text.equals(""))
							for (String s : text) {
								sb.append(s);
								sb.append("\t");
							}
						prio.setText((sb.toString()));
					} catch (ClassCastException e) {
						prio.setText((String) m.get("text"));
					}
					teacher.addPrio(prio);
				}
			}
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if (!mongoTemplate.collectionExists("teachers")) {
				mongoTemplate.createCollection("teachers");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		teacher.setCourses(courseList);
		try {
			DBCollection collection = mongoTemplate.getCollection("teachers");
			DBCursor cursor = collection.find(new BasicDBObject("user.mail", teacher.getUser().getMail()));

			if (cursor.size() > 0) {
				DBObject obj = cursor.next();
				collection.update(obj, (DBObject) mongoTemplate.getConverter().convertToMongoType(teacher));

			}

			else
				mongoTemplate.insert(teacher, "teachers");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void addSemesters(Course c, Map<?, ?> course) {
		String semesters = (String) course.get("semester");
		try {
			List<String> semesterList = Arrays.asList(semesters.split(","));
			for (String s : semesterList) {
				try {
					int semesterNumber = Integer.parseInt(s.replaceAll(" ", ""));
					c.addSemester(semesterNumber);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (NullPointerException n) {
			n.printStackTrace();
		}

	}

}