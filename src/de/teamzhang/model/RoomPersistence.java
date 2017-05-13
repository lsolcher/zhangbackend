package de.teamzhang.model;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RoomPersistence {
	private final Map<BigInteger, Room> rooms = new HashMap<BigInteger, Room>();
	private BigInteger id = new BigInteger("0");

	public void create(final Room room) {
		((Prio) rooms).setId(id);
		rooms.put(id, room);
		id.add(new BigInteger("1"));
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
	
}
