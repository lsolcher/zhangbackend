package de.teamzhang.model;

public class SingleChoicePrio extends Prio {

	// 0 = Raumbeschaffenheit, 1 =...
	private short type;
	String text;
	boolean showCourses;

	SingleChoicePrio() {
		super();
	}

	public SingleChoicePrio(String string, int[] options, String text, boolean showCourses) {
		super(string);
		if (name.equals("Raumbeschaffenheit")) {
			type = 0;
		}
		this.text = text;
		this.showCourses = showCourses;
	}

}
