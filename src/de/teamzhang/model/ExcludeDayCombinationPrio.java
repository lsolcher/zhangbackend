package de.teamzhang.model;

import java.util.Collection;

public class ExcludeDayCombinationPrio extends Prio {

	//
	// Monday = 0; Sunday = 6
	private int dayOne;
	private int dayTwo;
	// time represented without :
	private int timeOne = 0;
	private int timeTwo = 0;

	// priorities may exlude only days or days together with time (that would be
	// hastime = true)
	private boolean hasTime;

	// true if is excluding, false if is combining
	private boolean isExcluding;

	public ExcludeDayCombinationPrio() {
		// TODO Auto-generated constructor stub
	}

	public ExcludeDayCombinationPrio(String string, Teacher teacher, Collection<Course> courses) {
		super(string, teacher, courses);
	}

	public int getDayOne() {
		return dayOne;
	}

	public void setDayOne(int dayOne) {
		this.dayOne = dayOne;
	}

	public int getDayTwo() {
		return dayTwo;
	}

	public void setDayTwo(int dayTwo) {
		this.dayTwo = dayTwo;
	}

	public int getTimeOne() {
		return timeOne;
	}

	public void setTimeOne(int timeOne) {
		this.timeOne = timeOne;
	}

	public int getTimeTwo() {
		return timeTwo;
	}

	public void setTimeTwo(int timeTwo) {
		this.timeTwo = timeTwo;
	}

	public boolean isHasTime() {
		return hasTime;
	}

	public void setHasTime(boolean hasTime) {
		this.hasTime = hasTime;
	}

	public boolean isExcluding() {
		return isExcluding;
	}

	public void setExcluding(boolean isExcluding) {
		this.isExcluding = isExcluding;
	}

}
