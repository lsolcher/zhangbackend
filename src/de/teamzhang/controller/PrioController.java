package de.teamzhang.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import de.teamzhang.config.SpringMongoConfig;
import de.teamzhang.model.Prio;
import de.teamzhang.model.PrioPersistence;

@Controller
//@RequestMapping("/wishes")
public class PrioController {

	ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
	MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

	@Resource
	private PrioPersistence persistence;

	@RequestMapping("/wishes")
	public ModelAndView wishes(@PathVariable Map<String, String> pathVars) {
		ModelAndView model = new ModelAndView("wishes");
		return model;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> create(@RequestBody final Prio prio) {
		mongoOperation.save(prio);
		final Map<String, Object> response = new HashMap<String, Object>();
		response.put("success", "true");
		response.put("id", prio.getId());
		return response;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public Collection<Prio> list() {
		return persistence.list();
	}

	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Prio read(@PathVariable final int id) {
		return persistence.read(id);
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	public Map<String, Object> update(@RequestBody final Prio prio) {
		final boolean success = persistence.update(prio);
		final Map<String, Object> response = new HashMap<String, Object>();
		response.put("success", success);
		return response;
	}

	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> delete(@PathVariable final int id) {
		final boolean success = persistence.delete(id);
		final Map<String, Object> response = new HashMap<String, Object>();
		response.put("success", success);
		return response;
	}

	@ResponseBody
	@RequestMapping(value = "/prios", method = RequestMethod.GET)
	public ArrayList<Prio> getPrioList() {
		Prio prio = new Prio();
		prio.setName("Prio1");

		ArrayList<Prio> prioList = new ArrayList<Prio>();
		prioList.add(prio);

		return prioList;
	}

}
