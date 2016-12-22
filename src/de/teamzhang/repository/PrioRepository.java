package de.teamzhang.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.teamzhang.model.Prio;

public interface PrioRepository extends MongoRepository<Prio, String> {

	public Prio findByFirstName(String firstName);

	public List<Prio> findByLastName(String lastName);

}