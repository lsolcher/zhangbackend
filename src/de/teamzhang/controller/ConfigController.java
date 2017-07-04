package de.teamzhang.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ConfigController {

	@Autowired
	private MongoTemplate mongoTemplate;

	private static Properties prop = new Properties();

	public static void setProperty(String key, String value) {
		prop.setProperty(key, value);
	}

	@RequestMapping(value = "/postConfig.json", method = RequestMethod.POST)
	public @ResponseBody void setProperties(@RequestBody String props) {
		ObjectMapper mapper = new ObjectMapper();

		/*try {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			List<HashMap> list = mapper.readValue(props, List.class);
			for (Map m : list) {
				StudentSettings setting = new StudentSettings();
				//setting.setName((String) m.get("title"));
				//				prio.setUserId(userId);
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
				if (m.get("type").equals("SingleChoicePrio")) {
					prio = new SingleChoicePrio();
					((SingleChoicePrio) prio).setOption(Integer.parseInt((String) m.get("option")));
				} else if (m.get("type").equals("SimplePrio")) {
					prio = new SimplePrio();
				} else if (m.get("type").equals("Schedule")) {
					prio = new Schedule();
					ArrayList<Integer> calendar = (ArrayList<Integer>) m.get("calendar");
					((Schedule) prio).setSchedule(calendar);
				} else if (m.get("type").equals("ExcludeDayCombinationPrio")) {
					prio = new ExcludeDayCombinationPrio();
					//					if (!m.get("title").equals("Tage ausschließen")) {
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
				teacher.addPrio(prio);
		
			}
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			List<HashMap> list = mapper.readValue(props, List.class);
			ArrayList<String> settings = new ArrayList<String>();
			try {
				if (!mongoTemplate.collectionExists("settings")) {
					mongoTemplate.createCollection("settings");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			mongoTemplate.insert(list, "settings");
		
			/*try {
				for (Map m : list) {
					if (m.get("title").equals("MaxStunden")) {
			
					}
			
			
				}
			} catch (Exception e) {
				e.printStackTrace();
			}*/
		/*} catch (Exception e) {
			e.printStackTrace();
		}*/

	}

	public static void mockProps(String name) {
		OutputStream output = null;
		try {

			output = new FileOutputStream(name + "settings.properties");

			// set the properties value
			setProperty("studentMaxHoursValue", "4");
			setProperty("studentMaxDaysValue", "4");
			setProperty("studentMaxBreakLengthValue", "2");
			setProperty("studentMaxHoursMinusPoints", "10");
			setProperty("studentMaxBreakLengthMinusPoints", "20");
			setProperty("studentMaxDaysMinusPoints", "10000");
			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
}