package de.teamzhang.model;

import java.math.BigInteger;

public class SingleChoicePrio extends Prio {

	// 0 = Raumbeschaffenheit, 1 =...
	private short type;
	String text;
	boolean showCourses;

	SingleChoicePrio() {
		super();
	}

	public SingleChoicePrio(String string, BigInteger profID, BigInteger[] courses, int[] options, String text,
			boolean showCourses) {
		super(string, profID, courses);
		if (name.equals("Raumbeschaffenheit")) {
			type = 0;
		}
		this.text = text;
		this.showCourses = showCourses;
	}

}
