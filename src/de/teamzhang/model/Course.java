package de.teamzhang.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

public class Course implements Serializable {

	protected Room room;
	private int courseID;
	@Id
	private BigInteger id;
	protected int slotsNeeded;
	private String group;
	protected Program program;
	protected String name;
	protected Teacher teacher;
	private int day;
	private int time;
	private boolean isSet = false;
	private List<Integer> semesters = new ArrayList<Integer>();

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public Course() {
		// TODO Auto-generated constructor stub
	}

	public void addSemester(int semester) {
		semesters.add(semester);
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public boolean isSet() {
		return isSet;
	}

	public void setSet(boolean isSet) {
		this.isSet = isSet;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public int getSlotsNeeded() {
		return slotsNeeded;
	}

	public void setSlotsNeeded(int slotsNeeded) {
		this.slotsNeeded = slotsNeeded;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
		program.addCourse(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public List<Integer> getSemesters() {
		return semesters;
	}

	public void setSemester(List<Integer> semesters2) {
		semesters = semesters2;
	}
}
