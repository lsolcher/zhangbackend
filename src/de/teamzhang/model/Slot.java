package de.teamzhang.model;

import java.math.BigInteger;

public class Slot {
	public static int MONDAY=0;
	public static int TUESDAY=1;
	public static int WEDNESDAY=2;
	public static int THURSDAY=3;
	public static int FRIDAY=4;
	protected BigInteger id;
	protected Room room;
	protected int day;
	
	public Slot() {
		// TODO Auto-generated constructor stub
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
