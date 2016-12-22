package de.teamzhang.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class PrioPersistence {
	private final Map<Integer, Prio> prios = new HashMap<Integer, Prio>();
	private Integer id = 0;

	public void create(final Prio prio) {
		((Prio) prios).setId(id);
		prios.put(id, prio);
		id++;
	}

	public Collection<Prio> list() {
		return prios.values();
	}

	public Prio read(final int id) {
		return prios.get(id);
	}

	public boolean update(final Prio prio) {
		boolean success = false;
		if (prios.containsKey(prio.getId())) {
			prios.put(prio.getId(), prio);
			success = true;
		}
		return success;
	}

	public boolean delete(final int prioId) {
		boolean success = false;
		if (prios.containsKey(prioId)) {
			prios.remove(prioId);
			success = true;
		}
		return success;
	}
}
