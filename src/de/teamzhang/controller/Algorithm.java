package de.teamzhang.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.teamzhang.model.CoursesPersistence;
import de.teamzhang.model.PrioPersistence;
import de.teamzhang.model.ProgramPersistence;
import de.teamzhang.model.RoomPersistence;
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

	private static Random randomGen = new Random();

	private static int optimalThreshold = 0;

	public static void main(String[] args) {
		generateMockData();
	}

	// 1. generate some testdata
	private static void generateMockData() {
		programs.generateMockData();
		teachers.generateMockData();
		rooms.generateMockData();
		slots.generate(5, rooms.list());
		prios.generateMockData(teachers.list());
		courses.generateMockData(programs.list(), teachers.list());

		printMap(programs.getPrograms());
		printMap(teachers.getTeachers());
		printMap(rooms.getRooms());
		printMap(slots.getSlots());
		printMap(prios.getPrios());
		printMap(courses.getCourses());

		weightPrios();

		calculateRandomSchedule();
		int minusPoints = getMinusPoints();

		while (minusPoints > 50) {

			for (Teacher t : teachers.getTeachers().values()) {
				t.resetSchedule();
				teachers.update(t);
			}
			calculateRandomSchedule();
			//Liefert irgendwie immer den gleichen wert?

			minusPoints = getMinusPoints();
			System.out.println(minusPoints);
		}

		//TODO: stundenten miteinberechnen?
		//TODO: stundenplaene verbessern - bewerten und neu berechnen
		//TODO: stundenplaene darstellen

		// Calculate based on the above slots
		// alle slots vergeben pro Tag x Tagen
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
		for( Teacher t : teachers.list() ) {
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
				weightedDayTimeWishes[days][time] = randomGen.nextInt(4);
				//System.out.print(weightedDayTimeWishes[days][time] + "\t");
			}
		}
		t.setWeightedDayTimeWishes(weightedDayTimeWishes);
		//TODO: add prios to prioritize schedule

	}

	@SuppressWarnings("rawtypes")
	public static void printMap(Map mp) {
		Iterator it = mp.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
		}
	}

	private static void calculateRandomSchedule() {
		//for (Program p : programs.getPrograms().values()) {
		for (Slot slot : slots.getSlots().values()) {
			Object[] teacherObjs = teachers.getTeachers().values().toArray();
			Teacher randomTeacher = (Teacher) teacherObjs[randomGen.nextInt(teacherObjs.length)];
			//TODO: exit condition if all teachers are busy at that time
			while (randomTeacher.getFullSlots()[slot.getDay()][slot.getTime()] == true || randomTeacher.isBusy()) {
				randomTeacher = (Teacher) teacherObjs[randomGen.nextInt(teacherObjs.length)];
			}
			randomTeacher.setFullSlot(slot.getDay(), slot.getTime());
		}

		//}
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
