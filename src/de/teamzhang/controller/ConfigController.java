package de.teamzhang.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.teamzhang.model.StudentSettings;

@Controller
public class ConfigController {

	@Autowired
	private MongoTemplate mongoTemplate;

	@RequestMapping(value = "/postConfig.json", method = RequestMethod.POST)
	public @ResponseBody void setProperties(@RequestBody String props) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			List<HashMap> list = mapper.readValue(props, List.class);
			for (Map m : list) {
				StudentSettings setting = new StudentSettings();
				setting.setType((String) m.get("title"));
				switch ((String) m.get("prio")) {
				case "hoch":
					setting.setMinusPoints(100);
					break;
				case "mittel":
					setting.setMinusPoints(30);
					break;
				case "niedrig":
					setting.setMinusPoints(10);
					break;
				}
				System.out.println(m.get("options"));
				setting.setValue(Integer.parseInt((String) m.get("options")));
				try {
					if (!mongoTemplate.collectionExists("settings")) {
						mongoTemplate.createCollection("settings");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				mongoTemplate.insert(setting, "settings");
			}
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(mongoTemplate.getCollection("settings").getCount());

	}

}