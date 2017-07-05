package de.teamzhang.model;

import java.util.ArrayList;

public class Schedule extends Prio {

	// x = day
	// y = slot
	private int[] weightedDayTimeWishes = new int[35];

	public void setSchedule(ArrayList<Integer> schedule) {
		for (int i = 0; i < schedule.size(); i++)
			weightedDayTimeWishes[i] = schedule.get(i);
	}

	public int[] getWeightedDayTimeWishes() {
		return weightedDayTimeWishes;
	}

	public void setWeightedDayTimeWishes(int[] weightedDayTimeWishes) {
		this.weightedDayTimeWishes = weightedDayTimeWishes;
	}

}
