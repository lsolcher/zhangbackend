package de.teamzhang.model;

import java.math.BigInteger;

public class SingleChoicePrio extends Prio {

	// 0 = Raumbeschaffenheit, 1 =...
	private short type;
	String text;
	boolean showCourses;
	int option;
	private int course;
	private boolean isValidForAllCourses;

	public SingleChoicePrio() {
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

	public void setOption(int theOption) {
		option = theOption;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isShowCourses() {
		return showCourses;
	}

	public void setShowCourses(boolean showCourses) {
		this.showCourses = showCourses;
	}

}
