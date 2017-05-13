package de.teamzhang.model;

import java.math.BigInteger;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "prio")
public class Prio {

	protected String name;
	@Id
	private BigInteger id;

	protected BigInteger userId;

	protected int course;
	protected boolean isValidForAllCourses;
	private String text;

	public Prio(String string, BigInteger profID, BigInteger[] courses) {
		this.name = string;
	}

	public Prio() {
		// TODO Auto-generated constructor stub
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(final BigInteger id) {
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

	public BigInteger getUserId() {
		return userId;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

}
