package de.teamzhang.model;

import java.math.BigInteger;

import org.springframework.data.annotation.Id;

public class Course {

	protected Room room;
	@Id
	private BigInteger id;
	protected int slotsNeeded;
	protected Program program;
	protected String name;
	protected Teacher teacher;
	private int day;
	private int time;

	public Course() {
		// TODO Auto-generated constructor stub
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
}
