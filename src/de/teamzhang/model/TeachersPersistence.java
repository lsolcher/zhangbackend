package de.teamzhang.model;

import java.math.BigInteger;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class TeachersPersistence {

	private final Map<BigInteger, Teacher> teachers = new LinkedHashMap<BigInteger, Teacher>();
	private BigInteger id = new BigInteger("0");
	private Schedule schedule;

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Map<BigInteger, Teacher> getTeachers() {
		return teachers;
	}

	public void create(final Teacher teacher) {
		//((Prio) teachers).setId(id);
		teachers.put(id, teacher);
		id = id.add(new BigInteger("1"));
	}

	public Collection<Teacher> list() {
		return teachers.values();
	}

	public Teacher read(final int id) {
		return teachers.get(id);
	}

	public boolean update(final Teacher teacher) {
		boolean success = false;
		if (teachers.containsKey(teacher.getId())) {
			teachers.put(teacher.getId(), teacher);
			success = true;
		}
		return success;
	}

	public boolean delete(final int roomId) {
		boolean success = false;
		if (teachers.containsKey(roomId)) {
			teachers.remove(roomId);
			success = true;
		}
		return success;
	}

	// create useful teacher for learning
	public void generateMockData() {
		Random r = new Random();
		for (int i = 0; i < 10; i++) {
			Teacher t1 = new Teacher();
			t1.setName("Teacher" + i);
			t1.setProf(r.nextBoolean());
			create(t1);
		}
	}

}
