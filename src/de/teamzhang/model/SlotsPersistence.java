package de.teamzhang.model;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SlotsPersistence {

	public SlotsPersistence() {
		// TODO Auto-generated constructor stub
	}

	private final Map<BigInteger, Slot> slots = new HashMap<BigInteger, Slot>();
	private BigInteger id = new BigInteger("0");

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Map<BigInteger, Slot> getSlots() {
		return slots;
	}

	public void generate(int noOfSlots, Collection<Room> rooms) {
		//for (Room room : rooms) {
			for (int i = 0; i < noOfSlots; i++) {
				Slot slot = new Slot();
				slot.setId(id);
				//slot.setRoom(room);
				slot.setDay(i % 5); //assigns weekdays
				slot.setTime(i % 7);
				//((Slot) slots).setId(id);
				slots.put(id, slot);
				id = id.add(new BigInteger("1"));
			}
		//}
	}

	public Collection<Slot> list() {
		return slots.values();
	}

	public Slot read(final int id) {
		return slots.get(id);
	}

	public boolean update(final Slot slot) {
		boolean success = false;
		if (slots.containsKey(slot.getId())) {
			slots.put(slot.getId(), slot);
			success = true;
		}
		return success;
	}

	public boolean delete(final int roomId) {
		boolean success = false;
		if (slots.containsKey(roomId)) {
			slots.remove(roomId);
			success = true;
		}
		return success;
	}
}
