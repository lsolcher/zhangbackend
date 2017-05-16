package de.teamzhang.model;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.annotation.Id;

public class Program {
	static int MAX_DAYS=4;
	static int MAX_SLOTS_PER_DAY=4;
	@Id
	private BigInteger id;
	private String name;
	int noOfDays;
	List<Slot> assignedSlots;

	
	void Program() {
		
	}
	
	public int getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}
	
	public boolean hasMoreDays() {
		return noOfDays<MAX_DAYS;
	}
	
	//max 4 slots per day constraint
	public boolean hasMoreSlotsPerDay(int day) {
		int cnt=1;
		for (Slot slot : assignedSlots) {
			if( slot.getDay()==day ) {
				cnt++;
				if( cnt>MAX_SLOTS_PER_DAY ) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void addSlot(Slot slot) {
		assignedSlots.add(slot);
	}
	
	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
