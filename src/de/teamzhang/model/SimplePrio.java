package de.teamzhang.model;

import java.math.BigInteger;

public class SimplePrio extends Prio {

	boolean acceptsFourHourCourses;

	public SimplePrio() {

		// TODO Auto-generated constructor stub
	}

	public SimplePrio(String string, BigInteger profID, BigInteger[] courses, boolean acceptsFourHourCourses) {
		super(string, profID, courses);
		this.acceptsFourHourCourses = acceptsFourHourCourses;
	}

}
