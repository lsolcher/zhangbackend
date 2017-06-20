package de.teamzhang.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class TeachersPersistence {

	private final Map<BigInteger, Teacher> teachers = new LinkedHashMap<BigInteger, Teacher>();
	private BigInteger id = new BigInteger("0");
	private Schedule schedule;
	private Random r = new Random();

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
		teacher.setId(id);
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
		Program ba = new Program();
		ba.setName("Bachelor IMI");
		Program ma = new Program();
		ma.setName("Master IMI");

		Teacher t = new Teacher();
		t.setName("Lenz");
		t.setProf(true);
		t.setFreeHours(4);
		ArrayList<Course> courses = new ArrayList<Course>();

		Course c = new Course();
		c.setName("GT 1 SU");
		c.setSlotsNeeded(1);
		c.setProgram(ba);
		Room room = new Room();
		room.setName("GT 1 Room");
		c.setRoom(room);
		c.setTeacher(t);
		courses.add(c);

		c = new Course();
		c.setName("GT 1 Ü");
		c.setSlotsNeeded(1);
		c.setProgram(ba);
		room = new Room();
		room.setName("GT 1 Ü Room");
		c.setRoom(room);
		c.setTeacher(t);
		courses.add(c);

		c = new Course();
		c.setName("GT 1 M");
		c.setSlotsNeeded(2);
		c.setProgram(ma);
		room = new Room();
		room.setName("GT 1 M Room");
		c.setRoom(room);
		c.setTeacher(t);
		courses.add(c);

		t.setCourses(courses);
		create(t);

		courses = new ArrayList<Course>();
		t = new Teacher();
		t.setName("Escher");
		t.setProf(false);
		t.setFreeHours(3);

		c = new Course();
		c.setName("MW SU");
		c.setSlotsNeeded(2);
		c.setProgram(ba);
		room = new Room();
		room.setName("MW SU Room");
		c.setRoom(room);
		c.setTeacher(t);
		courses.add(c);

		c = new Course();
		c.setName("MW Ü");
		c.setSlotsNeeded(1);
		c.setProgram(ba);
		room = new Room();
		room.setName("MW Ü Room");
		c.setRoom(room);
		c.setTeacher(t);
		courses.add(c);

		t.setCourses(courses);
		create(t);

		courses = new ArrayList<Course>();
		t = new Teacher();
		t.setName("Vigerske");
		t.setProf(false);
		t.setFreeHours(2);

		c = new Course();
		c.setName("MA SU");
		c.setSlotsNeeded(2);
		c.setProgram(ba);
		room = new Room();
		room.setName("MA SU Room");
		c.setRoom(room);
		c.setTeacher(t);
		courses.add(c);

		t.setCourses(courses);
		create(t);

		courses = new ArrayList<Course>();
		t = new Teacher();
		t.setName("Barthel");
		t.setProf(true);
		t.setFreeHours(5);

		c = new Course();
		c.setName("GDM SU");
		c.setSlotsNeeded(2);
		c.setProgram(ba);
		room = new Room();
		room.setName("GDM SU Room");
		c.setRoom(room);
		c.setTeacher(t);
		courses.add(c);

		c = new Course();
		c.setName("VCAT 1 SU");
		c.setSlotsNeeded(1);
		c.setProgram(ba);
		room = new Room();
		room.setName("VCAT 1 SU Room");
		c.setRoom(room);
		c.setTeacher(t);
		courses.add(c);

		c = new Course();
		c.setName("VC 1");
		c.setSlotsNeeded(2);
		c.setProgram(ma);
		room = new Room();
		room.setName("VC 1 Room");
		c.setRoom(room);
		c.setTeacher(t);
		courses.add(c);

		t.setCourses(courses);
		create(t);
		/*for (int i = 0; i < 8; i++) {
			Teacher t1 = new Teacher();
			t1.setName("Prof " + i);
			t1.setProf(true);
			create(t1);
		}
		for (int i = 0; i < 5; i++) {
			Teacher t1 = new Teacher();
			t1.setName("Teacher " + i);
			t1.setProf(false);
			create(t1);
		}*/
	}

}
