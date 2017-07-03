package de.teamzhang.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
		System.out.println(props);
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
		} catch (Exception e) {

		}

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