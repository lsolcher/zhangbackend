package de.teamzhang.model;

import java.math.BigInteger;

public class Slot {
	public static int MONDAY = 0;
	public static int TUESDAY = 1;
	public static int WEDNESDAY = 2;
	public static int THURSDAY = 3;
	public static int FRIDAY = 4;

	public static int EIGHT = 0;
	public static int NINE = 1;
	public static int ELEVEN = 2;
	public static int FOURTEEN = 3;
	public static int FIFTEEN = 4;
	public static int SEVENTEEN = 5;
	public static int NINETEEN = 6;

	protected BigInteger id;
	protected Room room;
	protected int day;
	protected int time;

	public Slot() {
		// TODO Auto-generated constructor stub
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

}
