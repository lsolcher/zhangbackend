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
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

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
				if (m.get("type").equals("additionalPrio")) {
					String program = ((String) m.get("program"));
					List<HashMap> props2 = (List<HashMap>) m.get("props");
					for (Map prop : props2) {
						StudentSettings setting = new StudentSettings();
						setting.setType((String) prop.get("title"));
						try {
							switch ((String) prop.get("prio")) {
							case "hoch":
								setting.setMinusPoints(10000);
								break;
							case "mittel":
								setting.setMinusPoints(500);
								break;
							case "niedrig":
								setting.setMinusPoints(50);
								break;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							setting.setValue(Integer.parseInt((String) prop.get("option")));
						} catch (Exception e) {
							e.printStackTrace();
						}
						setting.setProgram(program);
						try {
							if (!mongoTemplate.collectionExists("settings")) {
								mongoTemplate.createCollection("settings");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						DBCollection collection = mongoTemplate.getCollection("settings");
						BasicDBObject query = new BasicDBObject();
						query.put("type", setting.getType());
						query.put("program", setting.getProgram());
						//DBCursor cursor = collection.find(new BasicDBObject("user.mail", setting.getProgram(), setting.getType()));
						DBCursor cursor = collection.find(query);
						if (cursor.size() > 0) {
							DBObject obj = cursor.next();
							collection.update(obj, (DBObject) mongoTemplate.getConverter().convertToMongoType(setting));
						}

						else
							mongoTemplate.insert(setting, "settings");
					}
				}

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