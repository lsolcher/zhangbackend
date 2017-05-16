package de.teamzhang.model;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class PrioPersistence {
	private final Map<BigInteger, Prio> prios = new HashMap<BigInteger, Prio>();
	private BigInteger id = new BigInteger("0");

	public void create(final Prio prio) {
		((Prio) prios).setId(id);
		prios.put(id, prio);
		id.add(new BigInteger("1"));
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
	
	// create (maybe) useful prios for testing
	public void generateMockData(Collection<Teacher> teachers){
		for (Teacher teacher : teachers) {			
				for (int i = 0; i < 2; i++) {
					Prio prio = new Prio("prio_"+teacher.getName(), teacher, teacher.getCourses());
					create(prio);
				}
		}
	}
}
