package de.teamzhang.model;

import java.util.Collection;

public class SingleChoicePrio extends Prio {

	// see Room.type
	int option;

	public SingleChoicePrio() {
		super();
	}

	public SingleChoicePrio(String string, Teacher teacher, Collection<Course> courses) {
		super(string, teacher, courses);
	}

	public void setOption(int theOption) {
		option = theOption;
	}

}
