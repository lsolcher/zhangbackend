package de.teamzhang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
public class SpringMongoConfig extends AbstractMongoConfiguration {

	@Override
	public String getDatabaseName() {
		return "mongotest";
	}

	@Override
	@Bean
	public Mongo mongo() throws Exception {
		/*
		 * 		List<ServerAddress> seeds = new ArrayList<ServerAddress>();
				seeds.add(new ServerAddress("127.0.0.1"));
				List<MongoCredential> credentials = new ArrayList<MongoCredential>();
				credentials.add(MongoCredential.createMongoCRCredential("test", "mongotest", "test".toCharArray()));
				MongoClient mongo = new MongoClient(seeds, credentials);
		
				return mongo;
		 */
		return new MongoClient("127.0.0.1");
	}
}
