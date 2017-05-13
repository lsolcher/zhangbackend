package de.teamzhang.model;

import java.math.BigInteger;

public class Slot {
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
	
	protected BigInteger id;
	protected Room room;
}
