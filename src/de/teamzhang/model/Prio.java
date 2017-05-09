package de.teamzhang.model;

import java.math.BigInteger;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "prio")
public class Prio {

	protected String name;
	@Id
	private int id;

	protected BigInteger profID;
	protected BigInteger[] courses;

	public Prio(String string, BigInteger profID, BigInteger[] courses) {
		this.name = string;
	}

	public Prio() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return id + ": " + name;
	}

}
