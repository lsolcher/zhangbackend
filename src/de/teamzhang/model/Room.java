package de.teamzhang.model;

import java.math.BigInteger;

import org.springframework.data.annotation.Id;

public class Room {	
	protected int type;
	protected String name;
	@Id
	private BigInteger id;
	public static int WideRoom = 1;
	public static int LongRoom=2;
	public static int MacLab=3;
	public static int PcLab=4;
	
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
