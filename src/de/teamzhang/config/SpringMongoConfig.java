package de.teamzhang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Configuration
@EnableMongoRepositories(basePackages = "de.teamzhang.repository")
public class SpringMongoConfig {

	@Bean
	public Mongo mongo() throws Exception {
		MongoClientURI mcu = new MongoClientURI("mongodb://test:test@localhost/test");
		return new MongoClient(mcu);
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		MongoTemplate mt = new MongoTemplate(mongo(), "test");
		mongo().getUsedDatabases();
		return mt;
	}
}
