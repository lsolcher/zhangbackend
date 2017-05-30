package de.teamzhang.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.annotation.Id;

public class Teacher {

	protected String name;
	@Id
	private BigInteger id;
	private boolean isProf;
	private ArrayList<Course> courses = new ArrayList<Course>(1);
	private int[][] weightedDayTimeWishes = new int[5][35];
	private boolean[][] fullSlots = new boolean[5][35];

	public Teacher() {
	}

	public boolean[][] getFullSlots() {
		return fullSlots;
	}

	public void setFullSlot(int day, int time) {
		fullSlots[day][time] = true;
	}

	public void setFreeSlot(int day, int time) {
		fullSlots[day][time] = false;
	}

	public boolean isProf() {
		return isProf;
	}

	public void setProf(boolean isProf) {
		this.isProf = isProf;
	}

	public int[][] getWeightedDayTimeWishes() {
		return weightedDayTimeWishes;
	}

	public void setWeightedDayTimeWishes(int[][] weightedDayTimeWishes) {
		this.weightedDayTimeWishes = weightedDayTimeWishes;
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

	public void resetSchedule() {
		fullSlots = new boolean[5][35];
	}
}
