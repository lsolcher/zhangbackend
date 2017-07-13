package de.teamzhang.model;

import java.util.ArrayList;

public class Schedule extends Prio {

	// x = day
	// y = slot
	private int[] schedule = new int[35];

	public void setSchedule(ArrayList<Integer> schedule) {
		for (int i = 0; i < schedule.size(); i++)
			this.schedule[i] = schedule.get(i);
	}

	public int[] getSchedule() {
		return schedule;
	}

	public int getSchedule(int index) {
		return schedule[index];
	}

	public void setSchedule(int[] weightedDayTimeWishes) {
		this.schedule = weightedDayTimeWishes;
	}

}
