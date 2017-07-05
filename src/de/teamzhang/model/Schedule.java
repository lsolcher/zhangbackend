package de.teamzhang.model;

import java.util.ArrayList;

public class Schedule extends Prio {

	// x = day
	// y = slot
<<<<<<< HEAD
	private int[] weightedDayTimeWishes = new int[42];

	public void setSchedule(int[] schedule) {
		weightedDayTimeWishes = schedule;
	}
	
	public int[] getWeightedDayTimeWishes() {
		return weightedDayTimeWishes;
	}

=======
	private int[] weightedDayTimeWishes = new int[35];

	public void setSchedule(ArrayList<Integer> schedule) {
		for (int i = 0; i < schedule.size(); i++)
			weightedDayTimeWishes[i] = schedule.get(i);
	}

	public int[] getWeightedDayTimeWishes() {
		return weightedDayTimeWishes;
	}

>>>>>>> 7bf24e55ea71fb244c10bcc6f1d75bc7b40dfee5
	public void setWeightedDayTimeWishes(int[] weightedDayTimeWishes) {
		this.weightedDayTimeWishes = weightedDayTimeWishes;
	}

}
