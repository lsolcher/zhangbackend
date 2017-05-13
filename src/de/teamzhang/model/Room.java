package de.teamzhang.model;

import java.math.BigInteger;

import org.springframework.data.annotation.Id;

public class Room {
	
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

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public enum Type {
		WideRoom,
		LongRoom,
		MacLab,
		PcLab
	}
	
	protected Type type;
	protected String name;
	@Id
	private BigInteger id;
}
