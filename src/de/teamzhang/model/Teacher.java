package de.teamzhang.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;

public class Teacher {

	protected String firstName;
	protected String lastName;

	@Id
	private BigInteger id;
	private boolean isProf;
	private List<Course> courses = new ArrayList<Course>();
	private int[][] weightedDayTimeWishes = new int[5][7];
	private boolean[][] fullSlots = new boolean[5][7];
	private int freeHours;
	private List<Prio> prios = new ArrayList<Prio>();
	private int minusPoints = 0;
	private User user;

	public Teacher() {
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isBusy() {
		// if ((isProf && teachingHours >= 6) || (!isProf && teachingHours >=
		// 2))
		// return true;
		if (freeHours == 0)
			return true;
		else
			return false;
	}

	public int getFreeHours() {
		return freeHours;
	}

	public void setFreeHours(int freeHours) {
		this.freeHours = freeHours;
	}

	public List<Prio> getPrios() {
		return prios;
	}

	public void setPrios(List<Prio> prios) {
		this.prios = prios;
	}

	public void addPrio(Prio prio) {
		prios.add(prio);
	}

	public boolean[][] getFullSlots() {
		return fullSlots;
	}

	public void setFullSlot(int day, int time) {
		// teachingHours++;
		fullSlots[day][time] = true;
	}

	public void setFreeSlot(int day, int time) {
		fullSlots[day][time] = false;
		// teachingHours--;
	}

	public boolean isProf() {
		return isProf;
	}

	public void setProf(boolean isProf) {
		this.isProf = isProf;
	}

	public int[][] getWeightedDayTimeWishes() {
		return weightedDayTimeWishes;
	}

	public void setWeightedDayTimeWishes(int[][] weightedDayTimeWishes) {
		this.weightedDayTimeWishes = weightedDayTimeWishes;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public Collection<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courseList) {
		this.courses = courseList;
	}

	public void resetSchedule() {
		for (int index = 0; index < fullSlots.length; index++)
			for (int inner = 0; inner < fullSlots[index].length; inner++)
				fullSlots[index][inner] = false;

	}

	public boolean priosDontFit(Slot slot) {
		int day = slot.getDay();
		int time = slot.getTime();
		boolean prioDoesntFit = false;
		for (Prio p : prios) {
			if (p instanceof FreeTextInputPrio)
				;
			else if (p instanceof SingleChoicePrio)
				prioDoesntFit = checkIfSingleChoicePrioFits((SingleChoicePrio) p, day, time);
			else if (p instanceof SimplePrio) // no need to check, simplePrio is
													// weighted in schedule and not
												// excluding
				;
			else if (p instanceof ExcludeDayCombinationPrio)
				prioDoesntFit = checkIfExcludeDayCombinationPrioFits((ExcludeDayCombinationPrio) p, day, time);
		}
		return prioDoesntFit;
	}

	private boolean checkIfSingleChoicePrioFits(SingleChoicePrio p, int day, int time) {
		boolean prioDoesntFit = false;
		if (p.getName().equals("Anzahl Veranstaltungen pro Tag"))
			prioDoesntFit = checkClassesPerDay((SingleChoicePrio) p, day, time);
		if (p.getName().equals("Pausen"))
			prioDoesntFit = checkBreaks((SingleChoicePrio) p, day, time);
		if (p.getName().equals("Maximale Lehrtage pro Woche"))
			prioDoesntFit = checkMaximalTeachingDays((SingleChoicePrio) p, day, time);
		if (p.getName().equals("Maximale Anzahl aufeinanderfolgender Lehrtage"))
			prioDoesntFit = checkMaximalCondecutiveTeachingDays((SingleChoicePrio) p, day, time);
		return prioDoesntFit;
	}

	private boolean checkMaximalCondecutiveTeachingDays(SingleChoicePrio p, int day, int checkTime) {
		boolean prioDoesntFit = false;
		boolean[] daysOccupied = new boolean[5];
		int maxDays = p.getOption();
		fullSlots[day][checkTime] = true;
		int[][] weightedDayTimeWishes = p.getTeacher().getWeightedDayTimeWishes();
		for (int days = 0; days < weightedDayTimeWishes.length; days++) {
			for (int time = 0; time < weightedDayTimeWishes[days].length; time++) {
				if (fullSlots[day][time] == true)
					daysOccupied[days] = true;
			}
		}
		int count = 0;
		int maxCount = 0;
		for (int i = 0; i < daysOccupied.length; i++) {
			if (daysOccupied[i] == true && i == 0)
				count++;
			else if (daysOccupied[i] == true && daysOccupied[i - 1] == true)
				count++;
			else if (daysOccupied[i] == false) {
				maxCount = count;
				count = 0;
			}
		}
		if (maxCount > maxDays) {
			fullSlots[day][checkTime] = false;
			prioDoesntFit = true;
		}
		return prioDoesntFit;
	}

	private boolean checkMaximalTeachingDays(SingleChoicePrio p, int day, int checkTime) {
		boolean prioDoesntFit = false;
		int count = 0;
		int maxDays = p.getOption();
		fullSlots[day][checkTime] = true;
		int[][] weightedDayTimeWishes = p.getTeacher().getWeightedDayTimeWishes();
		for (int days = 0; days < weightedDayTimeWishes.length; days++) {
			for (int time = 0; time < weightedDayTimeWishes[days].length; time++) {
				if (fullSlots[day][time] == true)
					count++;

			}
		}
		if (count > maxDays) {
			fullSlots[day][checkTime] = false;
			prioDoesntFit = true;
		}
		return prioDoesntFit;
	}

	private boolean checkBreaks(SingleChoicePrio p, int day, int checkTime) {
		boolean prioDoesntFit = false;
		int breakTime = p.getOption();
		fullSlots[day][checkTime] = true;
		int[][] weightedDayTimeWishes = p.getTeacher().getWeightedDayTimeWishes();
		for (int days = 0; days < weightedDayTimeWishes.length; days++) {
			int count = 0;
			for (int time = 0; time < checkTime; time++) {
				if (time == 0 && fullSlots[day][time] == true)
					count++;
				else if (count == time && fullSlots[day][time] == true)
					count++;

			}
			if (count >= breakTime)
				prioDoesntFit = true;
			count = 0;
		}
		if (prioDoesntFit)
			fullSlots[day][checkTime] = false;
		return prioDoesntFit;
	}

	private boolean checkClassesPerDay(SingleChoicePrio p, int day, int checkTime) {
		boolean moreClassesPerDayPreferred = true;
		fullSlots[day][checkTime] = true;
		if (p.getOption() == 1)
			moreClassesPerDayPreferred = false;
		int[][] weightedDayTimeWishes = p.getTeacher().getWeightedDayTimeWishes();
		int count = 0;
		for (int time = 0; time < weightedDayTimeWishes[day].length; time++) {
			if (fullSlots[day][time] == true)
				count++;
		}
		if (count >= 2 && !moreClassesPerDayPreferred) {
			fullSlots[day][checkTime] = false;
			return true;
		}
		count = 0;
		for (int days = 0; days < weightedDayTimeWishes.length; days++) {
			boolean dayOccupied = false;
			for (int time = 0; time < weightedDayTimeWishes[days].length; time++) {
				if (fullSlots[day][time] == true)
					dayOccupied = true;
			}
			if (dayOccupied)
				count++;
		}
		if (count >= 2) {
			fullSlots[day][checkTime] = false;
			return true;
		}
		return false;
	}

	private boolean checkIfExcludeDayCombinationPrioFits(ExcludeDayCombinationPrio p, int day, int prioTime) {
		boolean prioDoesntFit = false;
		if (p.isExcluding())
			prioDoesntFit = checkExcluding(p, day, prioTime);
		else if (p.isHasTime())
			prioDoesntFit = checkExcludeTime(p, day, prioTime);
		return prioDoesntFit;
	}

	private boolean checkExcludeTime(ExcludeDayCombinationPrio p, int day, int prioTime) {
		boolean prioDoesntFit = false;
		if (day == p.getDayOne() || day == p.getDayTwo()) {
			int otherDay = 0;
			int otherTime = 0;
			if (day == p.getDayOne())
				otherDay = p.getDayTwo();
			else
				otherDay = p.getDayOne();
			if (prioTime == p.getTimeOne())
				otherTime = p.getTimeTwo();
			else
				otherTime = p.getTimeOne();
			if (fullSlots[otherDay][otherTime] == true)
				prioDoesntFit = true;
		}
		return prioDoesntFit;
	}

	private boolean checkExcluding(ExcludeDayCombinationPrio p, int day, int prioTime) {
		boolean prioDoesntFit = false;
		if (day == p.getDayOne() || day == p.getDayTwo()) {
			int otherDay = 0;
			if (day == p.getDayOne())
				otherDay = p.getDayTwo();
			else
				otherDay = p.getDayOne();
			for (int time = 0; time < fullSlots[otherDay].length; time++) {
				if (fullSlots[otherDay][time] == true)
					prioDoesntFit = true;
			}
		}
		return prioDoesntFit;
	}

	public void removeMinusPoints(int i) {
		minusPoints -= i;
	}

	public void addMinusPoints(int i) {
		minusPoints += i;
	}

	public int getMinusPoints() {
		return minusPoints;
	}

	public void resetMinuspoints() {
		minusPoints = 0;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;

	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setWeightedDayTimeWishes(ArrayList<Integer> calendar) {
		for (int i = 0; i < calendar.size(); i++) {
			System.out.println(i / 7);
			System.out.println(i % 7);
			weightedDayTimeWishes[i % 5][i / 5] = calendar.get(i);
		}
		System.out.println();

	}

	public void setWeightedDayTimeWishes(int[] calendar) {
		for (int i = 0; i < calendar.length; i++) {
			System.out.println(i / 7);
			System.out.println(i % 7);
			int points = calendar[i];
			if (points == 3)
				points = 10000;
			else
				points *= 10;
			weightedDayTimeWishes[i % 5][i / 5] = points;
		}
	}
}
