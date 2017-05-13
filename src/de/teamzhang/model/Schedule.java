package de.teamzhang.model;

public class Schedule extends Prio {

	private int[] weightedDayTimeWishes = new int[42];

	public int[] getWeightedDayTimeWishes() {
		return weightedDayTimeWishes;
	}

	public void setWeightedDayTimeWishes(int[] weightedDayTimeWishes) {
		this.weightedDayTimeWishes = weightedDayTimeWishes;
	}

}
