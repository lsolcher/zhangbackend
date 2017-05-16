package de.teamzhang.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.annotation.Id;

public class Teacher {

	protected String name;
	@Id
	private BigInteger id;
	private ArrayList<Course> courses = new ArrayList<Course>(1);
	
	public void Teacher() {
	}
	
	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Collection<Course> getCourses() {
		return courses;
	}

	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}
}
