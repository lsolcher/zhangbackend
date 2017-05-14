package de.teamzhang.model;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProgramPersitence {
	private final Map<BigInteger, Program> programs = new HashMap<BigInteger, Program>();
	private BigInteger id = new BigInteger("0");

	public void create(final Program room) {
		((Prio) programs).setId(id);
		programs.put(id, room);
		id.add(new BigInteger("1"));
	}

	public Collection<Program> list() {
		return programs.values();
	}

	public Program read(final int id) {
		return programs.get(id);
	}

	public boolean update(final Program program) {
		boolean success = false;
		if (programs.containsKey(program.getId())) {
			programs.put(program.getId(), program);
			success = true;
		}
		return success;
	}

	public boolean delete(final int roomId) {
		boolean success = false;
		if (programs.containsKey(roomId)) {
			programs.remove(roomId);
			success = true;
		}
		return success;
	}

}
