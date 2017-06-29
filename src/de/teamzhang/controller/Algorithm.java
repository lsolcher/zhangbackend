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

import de.teamzhang.model.Course;
import de.teamzhang.model.CoursesPersistence;
import de.teamzhang.model.Prio;
import de.teamzhang.model.PrioPersistence;
import de.teamzhang.model.Program;
import de.teamzhang.model.ProgramPersistence;
import de.teamzhang.model.RoomPersistence;
import de.teamzhang.model.SingleChoicePrio;
import de.teamzhang.model.SlotsPersistence;
import de.teamzhang.model.Teacher;
import de.teamzhang.model.TeachersPersistence;

public class Algorithm {

	// temporary holding the model
	// TODO: replace with real models
	private static CoursesPersistence courses = new CoursesPersistence();;
	private static PrioPersistence prios = new PrioPersistence();
	private static ProgramPersistence programs = new ProgramPersistence();
	private static RoomPersistence rooms = new RoomPersistence();
	private static SlotsPersistence slots = new SlotsPersistence();
	private static TeachersPersistence teachers = new TeachersPersistence();

	private static int MINUSPOINTTHRESHOLD = 10;
	static ArrayList<Course> allCourses = new ArrayList<Course>();
	static ArrayList<Program> allPrograms = new ArrayList<Program>();

	private static Random randomGen = new Random();

	// private static

	private static int optimalThreshold = 0;
	private static List<String> notOccupiedSlots = new ArrayList<String>();

	public static void main(String[] args) {
		generateMockData();
	}

	// 1. generate some testdata
	private static void generateMockData() {

		programs.generateMockData();
		teachers.generateMockData(allCourses, allPrograms);
		rooms.generateMockData();
		slots.generate(72, rooms.list());
		prios.generateMockData(teachers.list(), 4);

		for (Program p : allPrograms)
			p.generateMockConfig();
		// courses.generateMockData(programs.list(), teachers.list());

		printMap(programs.getPrograms());
		printMap(teachers.getTeachers());
		printMap(rooms.getRooms());
		printMap(slots.getSlots());
		printMap(prios.getPrios());
		printMap(courses.getCourses());

		weightPrios();

		int minusPoints = 0;
		// calculateRandomSchedule();
		// int minusPoints = getMinusPoints();
		// System.out.println(minusPoints);
		// int badSlots = 0;

		int count = 0;
		int minusPointsThreshold = 400;
		do {
			reset();
			count++;
			calculateRandomSchedule();
			boolean hillclimbingReached = false;
			minusPoints = getMinusPoints();
			if (minusPoints < 1200) {
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

			}
			// if (!hillclimbingReached && count % 10000 == 0)
			// inspectTeachers();
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
							builder.append(c.getName() + ", " + c.getTeacher().getName() + ", " + c.getRoom().getName()
									+ ", " + c.getSlotsNeeded() + " Doppelstunden" + ", Minuspunkte: "
									+ c.getTeacher().getWeightedDayTimeWishes()[i][j]);
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
				writer = new BufferedWriter(new FileWriter(t.getName() + ".csv"));
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

	}

	private static void inspectTeachers() {
		System.out.println("Couldn't find a schedule with selected teacher-prios, inspecting teachers...");
		for (Teacher t : teachers.getTeachers().values()) {
			System.out.println(t.getName() + " has " + t.getMinusPoints() + " minuspoints.");
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

	}

	private static void reset() {
		for (Teacher t : teachers.getTeachers().values()) {
			t.resetSchedule();
			teachers.update(t);
			t.resetMinuspoints();
		}
		for (Course c : allCourses)
			c.setSet(false);
		for (Program p : allPrograms) {
			p.resetFullSlots();
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
		return minusPoints;
	}

	private static void weightPrios() {
		// avoid ConcurentModi Exception
		ArrayList<Teacher> allTeachers = new ArrayList<Teacher>(10);
		for (Teacher t : teachers.list()) {
			allTeachers.add(t);
		}

		for (Teacher t : allTeachers) {
			weightSingleSchedule(t);
			teachers.update(t);
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
		// for()
		/*
		 * for (Slot slot : slots.getSlots().values()) { Object[] teacherObjs =
		 * teachers.getTeachers().values().toArray(); Teacher randomTeacher =
		 * (Teacher) teacherObjs[randomGen.nextInt(teacherObjs.length)]; //TODO:
		 * exit condition if all teachers are busy at that time int iteration =
		 * 0; while (randomTeacher.getFullSlots()[slot.getDay()][slot.getTime()]
		 * == true || randomTeacher.isBusy() || randomTeacher.priosDontFit(slot)
		 * && iteration < 10000) { randomTeacher = (Teacher)
		 * teacherObjs[randomGen.nextInt(teacherObjs.length)]; iteration++; } if
		 * (iteration < 10000) randomTeacher.setFullSlot(slot.getDay(),
		 * slot.getTime()); else { countNotOccupied++;
		 * notOccupiedSlots.add("Day: " + slot.getDay() + ", Time: " +
		 * slot.getTime()); } for (Room room : rooms.getRooms().values()) {
		 * 
		 * } }
		 */
		// return countNotOccupied;
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
