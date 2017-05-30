package de.teamzhang.model;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RoomPersistence {
	private final Map<BigInteger, Room> rooms = new HashMap<BigInteger, Room>();
	private BigInteger id = new BigInteger("0");

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Map<BigInteger, Room> getRooms() {
		return rooms;
	}

	public void create(final Room room) {
		//((Prio) rooms).setId(id);
		rooms.put(id, room);
		id = id.add(new BigInteger("1"));
	}

	public Collection<Room> list() {
		return rooms.values();
	}

	public Room read(final int id) {
		return rooms.get(id);
	}

	public boolean update(final Room room) {
		boolean success = false;
		if (rooms.containsKey(room.getId())) {
			rooms.put(room.getId(), room);
			success = true;
		}
		return success;
	}

	public boolean delete(final int roomId) {
		boolean success = false;
		if (rooms.containsKey(roomId)) {
			rooms.remove(roomId);
			success = true;
		}
		return success;
	}

	// create useful rooms for testing
	public void generateMockData() {
		for (int i = 0; i < 10; i++) {
			Room r1 = new Room();
			r1.setName("C54" + i);
			r1.setType(i % 4);
			create(r1);
		}
	}

}
