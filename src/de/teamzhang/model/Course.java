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
	
	public Course() {
		// TODO Auto-generated constructor stub
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
