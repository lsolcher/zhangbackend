package de.teamzhang.core;

import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

import de.teamzhang.config.SpringMongoConfig;
import de.teamzhang.model.Prio;

public class App {

	public static void main(String[] args) {
		BasicConfigurator.configure();

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

		// enter document
		DBCollection collection = mongoOperation.getCollection("test");
		collection.insert(new BasicDBObject().append("name", "myName"));

		/*
		 * mongo
		 * use mongotest
		 * db.getCollectionNames();
		 * db.test.find({});
		 * 
		 * security:
		 * mongoConfig: 
		 * mongod --config "C:\Program Files\MongoDB\Server\3.4\mongod.cfg"
		 * 
		 * use admin
		 * db.createUser({user:"admin",pwd:"password",roles:[{role:"root",db:"admin"}]});
		 * use mongotest
		 * db.createUser({user:"test",pwd:"test",roles:[{role:"dbAdmin",db:"mongotest"}]});
		 * db.grantRolesToUser("test",[{role:"readWrite",db:"mongotest"}]);
		 */

	}

}