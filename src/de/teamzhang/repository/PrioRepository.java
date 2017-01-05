package de.teamzhang.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.teamzhang.model.Prio;

public interface PrioRepository extends MongoRepository<Prio, String> {

	public Prio findByName(String firstName);

	public Prio findById(int Id);
	//public List<Prio> findByCategory(String category);

}