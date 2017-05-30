package de.teamzhang.model;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CoursesPersistence {
	private final Map<BigInteger, Course> courses = new HashMap<BigInteger, Course>();
	private BigInteger id = new BigInteger("0");

	public void create(final Course course) {
		//((Prio) courses).setId(id);
		courses.put(id, course);
		id = id.add(new BigInteger("1"));
	}

	public Collection<Course> list() {
		return courses.values();
	}

	public Course read(final int id) {
		return courses.get(id);
	}

	public boolean update(final Course course) {
		boolean success = false;
		if (courses.containsKey(course.getId())) {
			courses.put(course.getId(), course);
			success = true;
		}
		return success;
	}

	public boolean delete(final int courseId) {
		boolean success = false;
		if (courses.containsKey(courseId)) {
			courses.remove(courseId);
			success = true;
		}
		return success;
	}

	// create useful Courses for testing
	public void generateMockData(Collection<Program> programs, Collection<Teacher> teachers) {
		for (Program program : programs) {
			for (Teacher teacher : teachers) {
				for (int i = 0; i < 4; i++) {
					Course c1 = new Course();
					c1.setName("course" + i);
					c1.setProgram(program);
					c1.setTeacher(teacher);
					create(c1);
				}
			}
		}
	}
}
