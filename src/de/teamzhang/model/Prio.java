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

	protected int course;
	protected boolean isValidForAllCourses;
	private String text;

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

	public BigInteger getProfID() {
		return profID;
	}

	public void setProfID(BigInteger profID) {
		this.profID = profID;
	}

	public BigInteger[] getCourses() {
		return courses;
	}

	public void setCourses(BigInteger[] courses) {
		this.courses = courses;
	}

	public int getCourse() {
		return course;
	}

	public void setCourse(int course) {
		this.course = course;
	}

	public boolean isValidForAllCourses() {
		return isValidForAllCourses;
	}

	public void setValidForAllCourses(boolean isValidForAllCourses) {
		this.isValidForAllCourses = isValidForAllCourses;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
