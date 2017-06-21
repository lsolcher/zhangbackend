package de.teamzhang.controller;

import java.io.BufferedWriter;
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
import de.teamzhang.model.Slot;
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

	static ArrayList<Course> allCourses = new ArrayList<Course>();
	static ArrayList<Program> allPrograms = new ArrayList<Program>();

	private static Random randomGen = new Random();

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
		prios.generateMockData(teachers.list());
		//courses.generateMockData(programs.list(), teachers.list());

		printMap(programs.getPrograms());
		printMap(teachers.getTeachers());
		printMap(rooms.getRooms());
		printMap(slots.getSlots());
		printMap(prios.getPrios());
		printMap(courses.getCourses());

		weightPrios();

		int minusPoints = 0;
		//calculateRandomSchedule();
		//int minusPoints = getMinusPoints();
		//System.out.println(minusPoints);
		//int badSlots = 0;

		do {
			for (Teacher t : teachers.getTeachers().values()) {
				t.resetSchedule();
				teachers.update(t);
			}
			calculateRandomSchedule();
			minusPoints = getMinusPoints();
			if (minusPoints < 850)
				System.out.println("Minuspoints: " + minusPoints);
		} while (minusPoints > 750);
		for (String s : notOccupiedSlots)
			System.out.println(s);
		for (Teacher t : teachers.getTeachers().values()) {
			StringBuilder builder = new StringBuilder();
			int[][] board = t.getWeightedDayTimeWishes();
			boolean[][] isTeaching = t.getFullSlots();
			for (int i = 0; i < board.length; i++)//for each row
			{
				for (int j = 0; j < board[i].length; j++)//for each column
				{
					if (isTeaching[i][j])
						builder.append(board[i][j] + "");//append to the output string
					else
						builder.append("0" + "");//append to the output string
					if (j < board[i].length - 1)//if this is not the last row element
						builder.append(",");//then add comma (if you don't like commas you can use spaces)
				}
				builder.append("\n");//append new line at the end of the row
			}
			BufferedWriter writer;
			try {
				writer = new BufferedWriter(new FileWriter(t.getName() + ".csv"));
				writer.write(builder.toString());//save the string representation of the board
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		//TODO: stundenten miteinberechnen?
		//TODO: stundenplaene verbessern - bewerten und neu berechnen
		//TODO: stundenplaene darstellen

		//printMap(slots.getSlots());

		optimalThreshold = 700;

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
		//3 = auf keinen fall
		//0 = top
		for (int days = 0; days < weightedDayTimeWishes.length; days++) {
			for (int time = 0; time < weightedDayTimeWishes[days].length; time++) {
				int random = randomGen.nextInt(4) * 10;
				if (random == 3)
					random = 9999;
				//add minus point if teacher is extern
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

	// 1 minuspunkt wenn der teacher um 9 anfängt und early start gewählt hat, 2 bei 12 etc
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
				Teacher teacher = c.getTeacher();
				Slot slot = new Slot();
				Random r = new Random();
				int randomTime = r.nextInt(7);
				int randomDay = r.nextInt(5);
				if (c.getSlotsNeeded() == 2 && randomTime == 6)
					randomTime -= 1;
				while (p.isTimeOccupied(randomTime, randomDay)) {
					randomTime = r.nextInt(7);
					randomDay = r.nextInt(5);
					if (c.getSlotsNeeded() == 2 && randomTime == 6)
						randomTime -= 1;
				}
				c.setDay(randomDay);
				c.setTime(randomTime);
				teacher.setFullSlot(randomDay, randomTime);
				if (c.getSlotsNeeded() == 2)
					teacher.setFullSlot(randomDay, randomTime + 1);
			}

		}

		//for()
		/*for (Slot slot : slots.getSlots().values()) {
			Object[] teacherObjs = teachers.getTeachers().values().toArray();
			Teacher randomTeacher = (Teacher) teacherObjs[randomGen.nextInt(teacherObjs.length)];
			//TODO: exit condition if all teachers are busy at that time
			int iteration = 0;
			while (randomTeacher.getFullSlots()[slot.getDay()][slot.getTime()] == true || randomTeacher.isBusy()
					|| randomTeacher.priosDontFit(slot) && iteration < 10000) {
				randomTeacher = (Teacher) teacherObjs[randomGen.nextInt(teacherObjs.length)];
				iteration++;
			}
			if (iteration < 10000)
				randomTeacher.setFullSlot(slot.getDay(), slot.getTime());
			else {
				countNotOccupied++;
				notOccupiedSlots.add("Day: " + slot.getDay() + ", Time: " + slot.getTime());
			}
			for (Room room : rooms.getRooms().values()) {
		
			}
		}*/
		//return countNotOccupied;
		return 0;

	}

	// 2. function to generate a simple Ur-Plan
	private static void generateUrPlan() {
		// iterate slots
		// 		iterate teachers
		//			iterate courses
		// 				iterate rooms
		//					find a matching combination
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
