package de.teamzhang.model;

import java.util.Collection;

public class SimplePrio extends Prio {

	/**
	 * 
	 * If this one is selected, the teacher prefers 4 instead of 2 hours lessons
	 */
	public SimplePrio() {

	}

	public SimplePrio(String string, Teacher teacher, Collection<Course> courses) {
		super(string, teacher, courses);
	}
}
