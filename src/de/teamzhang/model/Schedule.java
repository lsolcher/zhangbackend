package de.teamzhang.model;

public class Schedule extends Prio {

	// x = day
	// y = slot
	private int[][] weightedDayTimeWishes = new int[5][35];

	public int[][] getWeightedDayTimeWishes() {
		return weightedDayTimeWishes;
	}

	public void setWeightedDayTimeWishes(int[][] weightedDayTimeWishes) {
		this.weightedDayTimeWishes = weightedDayTimeWishes;
	}

}
