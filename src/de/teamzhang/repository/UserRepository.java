package de.teamzhang.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import de.teamzhang.model.User;

//@RepositoryRestResource(collectionResourceRel = "people", path = "people")
@Repository
public interface UserRepository extends MongoRepository<User, String> {

	List<User> findByFirstName(@Param("name") String name);

	User findByMail(String mail);

	User findByLastName(String name);

	User findByUsername(String username);

}