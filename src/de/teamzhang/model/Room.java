package de.teamzhang.model;

import java.math.BigInteger;

import org.springframework.data.annotation.Id;

public class Room {	
	protected String type;
	protected String name;
	@Id
	private BigInteger id;
	private int seat;
//	public static int WideRoom = 1;
//	public static int LongRoom=2;
//	public static int MacLab=3;
//	public static int PcLab=4;
	
	private boolean[][] roomOccupied = new boolean[5][7];
	
	public Room() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public int getSeat() {
		return seat;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}


	public boolean isAvailable(int randomDay, int randomTime) {
		return roomOccupied[randomDay][randomTime];
	}
	
	public void setOccupied(int randomDay, int randomTime) {
		roomOccupied[randomDay][randomTime]=true;
	}
	
	public void resetOccupied() {
		for (int index = 0; index < roomOccupied.length; index++)
			for (int inner = 0; inner < roomOccupied[index].length; inner++)
				roomOccupied[index][inner] = false;
	}

}
