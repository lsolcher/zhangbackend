package de.teamzhang.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.portlet.ModelAndView;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import de.teamzhang.model.CalculatedSchedule;
import de.teamzhang.model.Course;
import de.teamzhang.model.CoursesPersistence;
import de.teamzhang.model.Prio;
import de.teamzhang.model.PrioPersistence;
import de.teamzhang.model.Program;
import de.teamzhang.model.ProgramPersistence;
import de.teamzhang.model.Room;
import de.teamzhang.model.RoomPersistence;
import de.teamzhang.model.SingleChoicePrio;
import de.teamzhang.model.SlotsPersistence;
import de.teamzhang.model.StudentSettings;
import de.teamzhang.model.Teacher;
import de.teamzhang.model.TeachersPersistence;

@Controller
public class Algorithm {

	@Autowired
	private MongoTemplate mongoTemplate;

	// temporary holding the model
	// TODO: replace with real models
	private static CoursesPersistence courses = new CoursesPersistence();;
	private static PrioPersistence prios = new PrioPersistence();
	private static ProgramPersistence programs = new ProgramPersistence();
	private static RoomPersistence rooms = new RoomPersistence();
	private static SlotsPersistence slots = new SlotsPersistence();
	private static TeachersPersistence teachers = new TeachersPersistence();

	private List<Teacher> allTeachers = new ArrayList<Teacher>();
	private List<StudentSettings> allSettings = new ArrayList<StudentSettings>();

	private static int MINUSPOINTTHRESHOLD = 10;

	private static int RANDOMGENERATIONMINUSPOINTSTHRESHOLD = 150000;
	static ArrayList<Course> allCourses = new ArrayList<Course>();
	static ArrayList<Program> allPrograms = new ArrayList<Program>();

	private static Random randomGen = new Random();

	// private static

	private static int optimalThreshold = 0;
	private static List<String> notOccupiedSlots = new ArrayList<String>();

	// 1. generate some testdata
	@RequestMapping(value = "/algorithm", method = RequestMethod.GET)
	private ModelAndView generateCalendar() {
		setPrograms();
		setTeachers();
		mockCourseProgramMapping();
		setStudentPrios();
		for (Teacher t : allTeachers) {
			for (Course c : t.getCourses())
				allCourses.add(c);
		}
		mockRooms();

		addTeachersToCourses();

		//programs.generateMockData();
		//teachers.generateMockData(allCourses, allPrograms);
		//rooms.generateMockData();
		//slots.generate(72, rooms.list());
		//prios.generateMockData(teachers.list(), 4);

		//courses.generateMockData(programs.list(), teachers.list());

		/*printMap(programs.getPrograms());
		printMap(teachers.getTeachers());
		printMap(rooms.getRooms());
		printMap(slots.getSlots());
		printMap(prios.getPrios());
		printMap(courses.getCourses());*/

		weightPrios();

		int minusPoints = 0;
		int count = 0;
		int minusPointsThreshold = 400000;
		do {
			reset();
			count++;
			calculateRandomSchedule();
			boolean hillclimbingReached = false;
			minusPoints = getMinusPoints();
			System.out.println(minusPoints);
			if (minusPoints < RANDOMGENERATIONMINUSPOINTSTHRESHOLD) {
				hillclimbingReached = true;
				System.out.println("Minuspoints: " + minusPoints);
				climbHill(100);
				minusPoints = getMinusPoints();
				System.out.println("Minuspoints after hillclimbing with threshold 100: " + minusPoints);
				climbHill(10);
				minusPoints = getMinusPoints();
				System.out.println("Minuspoints after hillclimbing with threshold 10: " + minusPoints);
				climbHill(5);
				minusPoints = getMinusPoints();
				System.out.println("Minuspoints after hillclimbing with threshold 5: " + minusPoints);
				for (Program p : allPrograms) {
					System.out.println("Program " + p.getName() + " has " + p.getProgramMinusPoints() + " minusPoints");
				}
			}
			if (!hillclimbingReached && count % 10000 == 0) {
				// TODO
				// inspectTeachers();
				// inspectStudentPrios();
				RANDOMGENERATIONMINUSPOINTSTHRESHOLD += 100;
				System.out.println("Iteration " + count + ", new threshold for random generation: "
						+ RANDOMGENERATIONMINUSPOINTSTHRESHOLD);
			}
			if (count % 1000000 == 0) {
				minusPointsThreshold += 25;
				System.out.println("Iterations over " + count + ". New minuspoint-threshold: " + minusPointsThreshold);
			}
		} while (minusPoints > minusPointsThreshold);
		System.out.println("Done! Generated a schedule with " + minusPoints + " minuspoints. It took " + count
				+ " iterations to create it.");
		for (Program p : allPrograms) {
			StringBuilder builder = new StringBuilder();
			int[][] board = new int[5][7];
			// boolean[][] isTeaching = t.getFullSlots();
			// builder.append(";Montag;Dienstag;Mittwoch;Donnerstag;Freitag\n");
			CalculatedSchedule cs = new CalculatedSchedule();
			cs.setProgramName(p.getName());
			for (int i = 0; i < board.length; i++)// for each row
			{
				for (int j = 0; j < board[0].length; j++)// for each column
				{
					// if (j == 0)
					// builder.append("Zeit " + i + ";");
					boolean isCourse = false;
					for (Course c : p.getCourses()) {
						if (c.getTime() == j && c.getDay() == i) {
							builder.append(c.getName() + ", " + c.getTeacher().getLastName() + ", "
									+ c.getRoom().getName() + ", " + c.getSlotsNeeded() + " Doppelstunden"
									+ ", Minuspunkte: " + c.getTeacher().getWeightedDayTimeWishes()[i][j]);
							cs.setFullSlot(c.getDay(), c.getTime(),
									c.getName() + ", " + c.getTeacher().getLastName() + ", " + c.getRoom().getName()
											+ ", " + c.getSlotsNeeded() + " Doppelstunden" + ", Minuspunkte: "
											+ c.getTeacher().getWeightedDayTimeWishes()[i][j]);
						}
						isCourse = true;
					}
					if (!isCourse) {
						cs.setEmptySlot(i, j);
						builder.append("-" + "");
					}
					if ((i <= board.length - 1) && (j < board[0].length - 1))
						builder.append(";");
				}
				if (i < board.length - 1)
					builder.append("\n");// append new line at the end of the
										// row
			}
			try {
				if (!mongoTemplate.collectionExists("schedules")) {
					mongoTemplate.createCollection("schedules");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			mongoTemplate.insert(cs, "schedules");
			BufferedWriter writer;
			try {
				// File file = new File("\\WebContent\\resources\\" +
				// p.getName() + ".csv");
				File file = new File(p.getName() + ".csv");
				// file.getParentFile().mkdirs();
				writer = new BufferedWriter(new FileWriter(file));
				writer.write(builder.toString());
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*for (Teacher t : teachers.getTeachers().values()) {
			StringBuilder builder = new StringBuilder();
			int[][] board = t.getWeightedDayTimeWishes();
			boolean[][] isTeaching = t.getFullSlots();
			for (int i = 0; i < board.length; i++)// for each row
			{
				for (int j = 0; j < board[i].length; j++)// for each column
				{
					if (isTeaching[i][j])
						builder.append(board[i][j] + "");// append to the output
														// string
					else
						builder.append("0" + "");// append to the output string
					if (j < board[i].length - 1)// if this is not the last row
													// element
						builder.append(",");// then add comma (if you don't like
					// commas you can use spaces)
				}
				builder.append("\n");// append new line at the end of the row
			}
			BufferedWriter writer;
			try {
				writer = new BufferedWriter(new FileWriter(t.getLastName() + ".csv"));
				writer.write(builder.toString());
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}*/

		// TODO: stundenten miteinberechnen?
		// TODO: stundenplaene verbessern - bewerten und neu berechnen
		// TODO: stundenplaene darstellen

		// printMap(slots.getSlots());

		optimalThreshold = 700;
		ModelAndView modelandview = new ModelAndView("calendar");
		return modelandview;
		//return "generated a plan with " + minusPoints + " minuspoints.";
	}

	private void mockRooms() {
		for (Course c : allCourses) {
			Room r = new Room();
			r.setName("C441");
			c.setRoom(r);
		}

	}

	private void addTeachersToCourses() {
		for (Teacher t : allTeachers) {
			for (Course c : t.getCourses()) {
				c.setTeacher(t);
			}
		}

	}

	private void mockCourseProgramMapping() {
		for (Teacher t : allTeachers) {
			for (Course c : t.getCourses()) {
				int rand = (int) (Math.random() * 8);
				Program p = allPrograms.get(rand);
				c.setProgram(p);
			}
		}

	}

	private void setPrograms() {

		Program ba1 = new Program();
		ba1.setName("BachelorIMI1");
		allPrograms.add(ba1);

		Program ba2 = new Program();
		ba2.setName("BachelorIMI2");
		allPrograms.add(ba2);

		Program ba3 = new Program();
		ba3.setName("BachelorIMI3");
		allPrograms.add(ba3);

		Program ba4 = new Program();
		ba4.setName("BachelorIMI4");
		allPrograms.add(ba4);

		Program ba5 = new Program();
		ba5.setName("BachelorIMI5");
		allPrograms.add(ba5);

		Program ma1 = new Program();
		ma1.setName("MasterIMI1");
		allPrograms.add(ma1);

		Program ma2 = new Program();
		ma2.setName("MasterIMI2");
		allPrograms.add(ma2);

		Program ma3 = new Program();
		ma3.setName("MasterIMI3");
		allPrograms.add(ma3);

	}

	private void setStudentPrios() {
		DBCollection settingsDB = mongoTemplate.getCollection("settings");
		DBCursor cursor = settingsDB.find();
		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			StudentSettings s = mongoTemplate.getConverter().read(StudentSettings.class, obj);
			allSettings.add(s);
		}
	}

	private void setTeachers() {
		DBCollection teachersDB = mongoTemplate.getCollection("teachers");
		DBCursor cursor = teachersDB.find();
		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			Teacher t = mongoTemplate.getConverter().read(Teacher.class, obj);
			allTeachers.add(t);
		}

	}

	//public static void main(String[] args) {
	//generateMockData();
	//}

	/*private static void generateMockData() {
	
		programs.generateMockData();
		teachers.generateMockData(allCourses, allPrograms);
		rooms.generateMockData();
		slots.generate(72, rooms.list());
		prios.generateMockData(teachers.list(), 4);
	
		// courses.generateMockData(programs.list(), teachers.list());
	
		printMap(programs.getPrograms());
		printMap(teachers.getTeachers());
		printMap(rooms.getRooms());
		printMap(slots.getSlots());
		printMap(prios.getPrios());
		printMap(courses.getCourses());
	
		weightPrios();
	
		int minusPoints = 0;
		int count = 0;
		int minusPointsThreshold = 400;
		do {
			reset();
			count++;
			calculateRandomSchedule();
			boolean hillclimbingReached = false;
			minusPoints = getMinusPoints();
			System.out.println(minusPoints);
			if (minusPoints < RANDOMGENERATIONMINUSPOINTSTHRESHOLD) {
				hillclimbingReached = true;
				System.out.println("Minuspoints: " + minusPoints);
				climbHill(100);
				minusPoints = getMinusPoints();
				System.out.println("Minuspoints after hillclimbing with threshold 100: " + minusPoints);
				climbHill(10);
				minusPoints = getMinusPoints();
				System.out.println("Minuspoints after hillclimbing with threshold 10: " + minusPoints);
				climbHill(5);
				minusPoints = getMinusPoints();
				System.out.println("Minuspoints after hillclimbing with threshold 5: " + minusPoints);
				for (Program p : allPrograms) {
					System.out.println("Program " + p.getName() + " has " + p.getProgramMinusPoints() + " minusPoints");
				}
			}
			if (!hillclimbingReached && count % 10000 == 0) {
				// TODO
				// inspectTeachers();
				// inspectStudentPrios();
				RANDOMGENERATIONMINUSPOINTSTHRESHOLD += 100;
				System.out.println("Iteration " + count + ", new threshold for random generation: "
						+ RANDOMGENERATIONMINUSPOINTSTHRESHOLD);
			}
			if (count % 1000000 == 0) {
				minusPointsThreshold += 25;
				System.out.println("Iterations over " + count + ". New minuspoint-threshold: " + minusPointsThreshold);
			}
		} while (minusPoints > minusPointsThreshold);
		System.out.println("Done! Generated a schedule with " + minusPoints + " minuspoints. It took " + count
				+ " iterations to create it.");
		for (Program p : allPrograms) {
			StringBuilder builder = new StringBuilder();
			int[][] board = new int[5][7];
			// boolean[][] isTeaching = t.getFullSlots();
			// builder.append(";Montag;Dienstag;Mittwoch;Donnerstag;Freitag\n");
	
			for (int i = 0; i < board.length; i++)// for each row
			{
	
				for (int j = 0; j < board[0].length; j++)// for each column
				{
					// if (j == 0)
					// builder.append("Zeit " + i + ";");
					boolean isCourse = false;
					for (Course c : p.getCourses()) {
						if (c.getTime() == j && c.getDay() == i)
							builder.append(c.getName() + ", " + c.getTeacher().getLastName() + ", "
									+ c.getRoom().getName() + ", " + c.getSlotsNeeded() + " Doppelstunden"
									+ ", Minuspunkte: " + c.getTeacher().getWeightedDayTimeWishes()[i][j]);
						isCourse = true;
					}
					if (!isCourse)
						builder.append("-" + "");
					if ((i <= board.length - 1) && (j < board[0].length - 1))
						builder.append(";");
				}
				if (i < board.length - 1)
					builder.append("\n");// append new line at the end of the
										// row
			}
			BufferedWriter writer;
			try {
				// File file = new File("\\WebContent\\resources\\" +
				// p.getName() + ".csv");
				File file = new File(p.getName() + ".csv");
				// file.getParentFile().mkdirs();
				writer = new BufferedWriter(new FileWriter(file));
				writer.write(builder.toString());
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (Teacher t : teachers.getTeachers().values()) {
			StringBuilder builder = new StringBuilder();
			int[][] board = t.getWeightedDayTimeWishes();
			boolean[][] isTeaching = t.getFullSlots();
			for (int i = 0; i < board.length; i++)// for each row
			{
				for (int j = 0; j < board[i].length; j++)// for each column
				{
					if (isTeaching[i][j])
						builder.append(board[i][j] + "");// append to the output
														// string
					else
						builder.append("0" + "");// append to the output string
					if (j < board[i].length - 1)// if this is not the last row
													// element
						builder.append(",");// then add comma (if you don't like
					// commas you can use spaces)
				}
				builder.append("\n");// append new line at the end of the row
			}
			BufferedWriter writer;
			try {
				writer = new BufferedWriter(new FileWriter(t.getLastName() + ".csv"));
				writer.write(builder.toString());
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		}
	
		// TODO: stundenten miteinberechnen?
		// TODO: stundenplaene verbessern - bewerten und neu berechnen
		// TODO: stundenplaene darstellen
	
		// printMap(slots.getSlots());
	
		optimalThreshold = 700;
	
	}*/

	private static void inspectTeachers() {
		System.out.println("Couldn't find a schedule with selected teacher-prios, inspecting teachers...");
		for (Teacher t : teachers.getTeachers().values()) {
			System.out.println(t.getLastName() + " has " + t.getMinusPoints() + " minuspoints.");
		}
	}

	private static void climbHill(int threshold) {
		for (Program p : allPrograms) {
			for (Course c : p.getCourses()) {
				// if minusPoints are
				if (c.getTeacher().getWeightedDayTimeWishes()[c.getDay()][c.getTime()] > threshold) {

					Teacher teacher = c.getTeacher();

					// Slot slot = new Slot();
					Random r = new Random();
					int randomTime = r.nextInt(7);
					int randomDay = r.nextInt(5);
					teacher.setFreeSlot(c.getDay(), c.getTime());
					teacher.removeMinusPoints(teacher.getWeightedDayTimeWishes()[c.getDay()][c.getTime()]);
					p.setFreeSlot(c.getDay(), c.getTime());
					if (c.getSlotsNeeded() == 2) {
						teacher.setFreeSlot(c.getDay(), c.getTime() + 1);
						p.setFreeSlot(c.getDay(), c.getTime() + 1);
						teacher.removeMinusPoints(teacher.getWeightedDayTimeWishes()[c.getDay()][c.getTime() + 1]);
					}
					if (c.getSlotsNeeded() == 2 && randomTime == 6)
						randomTime -= 1;
					int iteration = 0;
					while ((p.isTimeOccupied(randomTime, randomDay) && ((c.getSlotsNeeded() == 1
							|| c.getSlotsNeeded() == 2 && p.isTimeOccupied(randomTime + 1, randomDay))))
							|| (c.getTeacher().getWeightedDayTimeWishes()[randomDay][randomTime] > threshold)
									&& iteration < 1000) {
						iteration++;
						randomTime = r.nextInt(7);
						randomDay = r.nextInt(5);
						if (c.getSlotsNeeded() == 2 && randomTime == 6)
							randomTime -= 1;
					}
					p.setFullSlot(randomDay, randomTime);
					c.setDay(randomDay);
					c.setTime(randomTime);
					teacher.setFullSlot(randomDay, randomTime);
					teacher.addMinusPoints(teacher.getWeightedDayTimeWishes()[randomDay][randomTime]);
					if (c.getSlotsNeeded() == 2) {
						teacher.removeMinusPoints(teacher.getWeightedDayTimeWishes()[randomDay][randomTime]);
						teacher.setFullSlot(randomDay, randomTime + 1);
						p.setFullSlot(randomDay, randomTime + 1);
					}
					c.setSet(true);
				}
			}

		}

		for (Program p : allPrograms) {
			p.resetMinusPoints();
			addStudentMinusPoints(p);
		}

	}

	private void reset() {
		for (Teacher t : allTeachers) {
			t.resetSchedule();
			teachers.update(t);
			t.resetMinuspoints();
		}
		for (Teacher t : teachers.getTeachers().values()) {
			t.resetSchedule();
			teachers.update(t);
			t.resetMinuspoints();
		}
		for (Course c : allCourses)
			c.setSet(false);
		for (Program p : allPrograms) {
			p.resetFullSlots();
			p.resetMinusPoints();
		}
	}

	private static int getMinusPoints() {
		int minusPoints = 0;
		for (Teacher t : teachers.list()) {
			int[][] weightedDayTimeWishes = t.getWeightedDayTimeWishes();
			boolean[][] isTeaching = t.getFullSlots();
			for (int days = 0; days < weightedDayTimeWishes.length; days++) {
				for (int time = 0; time < weightedDayTimeWishes[days].length; time++) {
					if (isTeaching[days][time])
						minusPoints += weightedDayTimeWishes[days][time];
				}
			}
		}
		for (Program p : allPrograms) {
			minusPoints += p.getProgramMinusPoints();
		}
		return minusPoints;
	}

	private void weightPrios() {
		// avoid ConcurentModi Exception

		for (Teacher t : allTeachers) {
			weightSingleSchedule(t);
			//teachers.update(t);
		}
	}

	private static void weightSingleSchedule(Teacher t) {
		int[][] weightedDayTimeWishes = t.getWeightedDayTimeWishes();
		// 3 = auf keinen fall
		// 0 = top
		for (int days = 0; days < weightedDayTimeWishes.length; days++) {
			for (int time = 0; time < weightedDayTimeWishes[days].length; time++) {
				int random = randomGen.nextInt(4) * 10;
				if (random == 3)
					random = 9999;
				// add minus point if teacher is extern
				if (!t.isProf())
					random++;
				weightedDayTimeWishes[days][time] = random;
				for (Prio p : t.getPrios()) {
					if (p instanceof SingleChoicePrio) {
						if (p.getName().equals("Unterrichtsbeginn"))
							weightClassStart(t, (SingleChoicePrio) p);
					}
				}
			}
		}
		t.setWeightedDayTimeWishes(weightedDayTimeWishes);

	}

	// 1 minuspunkt wenn der teacher um 9 anfängt und early start gew�hlt
	// hat, 2 bei 12 etc
	// genau anders bei later
	private static void weightClassStart(Teacher t, SingleChoicePrio p) {
		boolean later = false;
		int[][] weightedDayTimeWishes = t.getWeightedDayTimeWishes();
		if (p.getOption() == 1)
			later = true; // teacher wants to start late
		for (int days = 0; days < weightedDayTimeWishes.length; days++) {
			for (int time = 0; time < weightedDayTimeWishes[days].length; time++) {
				if (later)
					weightedDayTimeWishes[days][time] += 6 - time;
				else
					weightedDayTimeWishes[days][time] += time;

			}
		}
	}

	@SuppressWarnings("rawtypes")
	public static void printMap(Map mp) {
		Iterator it = mp.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
		}
	}

	private static int calculateRandomSchedule() {

		notOccupiedSlots.clear();

		for (Program p : allPrograms) {
			for (Course c : p.getCourses()) {
				if (!c.isSet()) {

					Teacher teacher = c.getTeacher();
					Random r = new Random();
					int randomTime = r.nextInt(7);
					int randomDay = r.nextInt(5);
					if (c.getSlotsNeeded() == 2 && randomTime == 6)
						randomTime -= 1;
					while (p.isTimeOccupied(randomTime, randomDay) && ((c.getSlotsNeeded() == 1
							|| c.getSlotsNeeded() == 2 && p.isTimeOccupied(randomTime + 1, randomDay)))) {
						randomTime = r.nextInt(7);
						randomDay = r.nextInt(5);
						if (c.getSlotsNeeded() == 2 && randomTime == 6)
							randomTime -= 1;
					}
					p.setFullSlot(randomDay, randomTime);

					teacher.addMinusPoints(teacher.getWeightedDayTimeWishes()[randomDay][randomTime]);
					c.setDay(randomDay);
					c.setTime(randomTime);
					teacher.setFullSlot(randomDay, randomTime);
					if (c.getSlotsNeeded() == 2) {
						teacher.setFullSlot(randomDay, randomTime + 1);
						p.setFullSlot(randomDay, randomTime + 1);
						teacher.addMinusPoints(teacher.getWeightedDayTimeWishes()[randomDay][randomTime + 1]);
					}
					c.setSet(true);
				}
			}

		}
		for (Program p : allPrograms)
			addStudentMinusPoints(p);

		return 0;

	}

	private static void addStudentMinusPoints(Program p) {
		addMaxHoursMinusPoints(p);
		addMaxDaysMinusPoints(p);
		addMaxBreakLengthMinusPoints(p);
	}

	private static void addMaxBreakLengthMinusPoints(Program p) {
		try {
			int studentMaxBreakLengthValue = Integer.parseInt(p.getProp().getProperty("studentMaxBreakLengthValue"));
			int studentMaxBreakLengthMinusPoints = Integer
					.parseInt(p.getProp().getProperty("studentMaxBreakLengthMinusPoints"));
			boolean[][] fullSlots = p.getFullSlots();
			if (studentMaxBreakLengthMinusPoints > 0) {

				for (int days = 0; days < fullSlots.length; days++) {
					int count = 0;
					int maxValue = 0;
					for (int time = 0; time < fullSlots[days].length; time++) {
						if (fullSlots[days][time] == false && time > 0 && time < 7) {
							count++;
							if (count >= studentMaxBreakLengthValue) {
								p.addMinusPoints(studentMaxBreakLengthValue);
								count = 0;
								break;
							}
						} else
							count = 0;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void addMaxDaysMinusPoints(Program p) {
		try {
			int studentMaxDaysValue = Integer.parseInt(p.getProp().getProperty("studentMaxDaysValue"));
			int studentMaxDaysMinusPoints = Integer.parseInt(p.getProp().getProperty("studentMaxDaysMinusPoints"));
			boolean[][] fullSlots = p.getFullSlots();
			if (studentMaxDaysMinusPoints > 0) {
				boolean[] daysBusy = new boolean[7];
				for (int days = 0; days < fullSlots.length; days++) {
					for (int time = 0; time < fullSlots[days].length; time++) {
						if (fullSlots[days][time] == true)
							daysBusy[days] = true;
					}

				}
				int count = 0;
				for (boolean b : daysBusy)
					if (b == true)
						count++;
				if (count >= studentMaxDaysValue)
					p.addMinusPoints(studentMaxDaysMinusPoints);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void addMaxHoursMinusPoints(Program p) {
		try {
			int studentMaxHoursValue = Integer.parseInt(p.getProp().getProperty("studentMaxHoursValue"));
			int studentMaxHoursMinusPoints = Integer.parseInt(p.getProp().getProperty("studentMaxHoursMinusPoints"));
			boolean[][] fullSlots = p.getFullSlots();
			if (studentMaxHoursMinusPoints > 0) {
				for (int days = 0; days < fullSlots.length; days++) {
					int count = 0;
					for (int time = 0; time < fullSlots[days].length; time++) {
						if (fullSlots[days][time] == true) {
							if (time == 0)
								count++;
							else if (fullSlots[days][time - 1] == true)
								count++;
							else if (fullSlots[days][time] == false)
								count = 0;
							if (count >= studentMaxHoursValue) {
								p.addMinusPoints(studentMaxHoursValue);
								break;
							}
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 2. function to generate a simple Ur-Plan
	private static void generateUrPlan() {
		// iterate slots
		// iterate teachers
		// iterate courses
		// iterate rooms
		// find a matching combination
		// should come up with VALID stupid plan
	}

	// helper to get current for comparing and
	// getting closer to the optimum
	private static int calculateCurrentThresholdValue() {
		// iterate slots and calc value
		// based on continuously used slots per day
		// multiply with days
		return 7;
	}

	// 3. iterate plan and try to optimize per Teacher
	private static void climbHillWithTeachers() {
		// random pick and change slots
		// calc current threshold
		// and compare to old
		// use if better
		// do XXX Times
		// break condition?
	}

	// 4. iterate plan and try to optimize per Programs
	private static void climbHillWithPrograms() {
		// same as in climbHillWithTeachers
	}

	public static void generatePlan() {
		// use and run above functions
	}

}
