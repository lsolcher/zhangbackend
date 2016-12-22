package de.teamzhang.core;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import de.teamzhang.config.SpringMongoConfig;
import de.teamzhang.model.Prio;

public class App {

	public static void main(String[] args) {

		// For XML
		//ApplicationContext ctx = new GenericXmlApplicationContext("SpringConfig.xml");

		// For Annotation
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

		Prio prio = new Prio("test");
		prio.setId(1);

		// save
		mongoOperation.save(prio);

		// now user object got the created id.
		System.out.println("1. prio : " + prio);

		// query to search user
		Query searchUserQuery = new Query(Criteria.where("id").is(1));

		// find the saved user again.
		Prio savedUser = mongoOperation.findOne(searchUserQuery, Prio.class);
		System.out.println("2. find - savedUser : " + savedUser);

		// update password
		mongoOperation.updateFirst(searchUserQuery, Update.update("name", "new name"), Prio.class);

		// find the updated user object
		Prio updatedUser = mongoOperation.findOne(searchUserQuery, Prio.class);

		System.out.println("3. updatedPrio : " + updatedUser);
		List<Prio> listPrio = mongoOperation.findAll(Prio.class);
		System.out.println("4. Number of user = " + listPrio.size());

		// delete
		mongoOperation.remove(searchUserQuery, Prio.class);
		listPrio = mongoOperation.findAll(Prio.class);
		System.out.println("4. Number of prios = " + listPrio.size());

	}

}