package de.teamzhang.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

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
	protected Teacher teacher;
	protected Collection<Course> courses = (Collection<Course>) new ArrayList(1);

	public Prio(String string, Teacher teacher, Collection<Course> courses) {
		this.name = string;
		this.teacher = teacher;
		this.courses = courses;
		createId();
	}

	private void createId() {
		BigInteger n = new BigInteger("999999999999");
		Random rand = new Random();
		BigInteger result = new BigInteger(n.bitLength(), rand);
		while (result.compareTo(n) >= 0) {
			result = new BigInteger(n.bitLength(), rand);
		}
		id = result;
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

	public void setExcluding(boolean nextBoolean) {
		// TODO Auto-generated method stub

	}

	public boolean isExcluding() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setDayOne(int nextInt) {
		// TODO Auto-generated method stub

	}

	public void setHasTime(boolean nextBoolean) {
		// TODO Auto-generated method stub

	}

	public void setDayTwo(int nextInt) {
		// TODO Auto-generated method stub

	}

	public boolean isHasTime() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setTimeOne(int nextInt) {
		// TODO Auto-generated method stub

	}

	public void setTimeTwo(int nextInt) {
		// TODO Auto-generated method stub

	}

	public void setOption(int nextInt) {
		// TODO Auto-generated method stub

	}

}
