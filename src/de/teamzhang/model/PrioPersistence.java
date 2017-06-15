package de.teamzhang.model;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class PrioPersistence {
	private final Map<BigInteger, Prio> prios = new HashMap<BigInteger, Prio>();
	private BigInteger id = new BigInteger("0");

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Map<BigInteger, Prio> getPrios() {
		return prios;
	}

	public void create(final Prio prio) {
		//((Prio) prios).setId(id);
		prios.put(id, prio);
		id = id.add(new BigInteger("1"));
	}

	private Prio randomize(Teacher teacher) {
		Prio prio = null;
		int rn = 0 + (int) (Math.random() * 4);
		System.out.println(rn);
		switch (rn) {
		case 0:
			prio = randomizeExcludeDayCombinationPrio(teacher);
			break;
		case 1:
			prio = new FreeTextInputPrio("prio_" + teacher.getName(), teacher, teacher.getCourses());
			prio.setText("blalvsfas");
			break;
		case 2:
			prio = randomizeSingleChoicePrio(teacher);
			break;
		case 3:
			prio = new SimplePrio("prio_" + teacher.getName(), teacher, teacher.getCourses());
			break;
		}
		return prio;

	}

	private Prio randomizeSingleChoicePrio(Teacher teacher) {
		Random r = new Random();

		Prio prio = new SingleChoicePrio("prio_" + teacher.getName(), teacher, teacher.getCourses());
		int prioType = r.nextInt(4);
		switch (prioType) {
		case 0:
			prio.setName("Unterrichtsbeginn");
			prio.setOption(r.nextInt(1));
			break;
		case 1:
			prio.setName("Anzahl Veranstaltungen pro Tag");
			prio.setOption(r.nextInt(1));
			break;
		case 2:
			prio.setName("Pausen");
			prio.setOption(r.nextInt(5));
			break;
		case 3:
			prio.setName("Maximale Lehrtage pro Woche");
			prio.setOption(r.nextInt(5));
			break;
		case 4:
			prio.setName("Maximale Anzahl aufeinanderfolgender Lehrtage");
			prio.setOption(r.nextInt(5));
			break;
		}

		return prio;
	}

	private ExcludeDayCombinationPrio randomizeExcludeDayCombinationPrio(Teacher teacher) {
		Random r = new Random();

		Prio prio = new ExcludeDayCombinationPrio("prio_" + teacher.getName(), teacher, teacher.getCourses());
		prio.setExcluding(r.nextBoolean());
		if (!prio.isExcluding())
			prio.setHasTime(r.nextBoolean());
		prio.setDayOne(r.nextInt(4));
		prio.setDayTwo(r.nextInt(4));
		if (prio.isHasTime()) {
			prio.setTimeOne(r.nextInt(7));
			prio.setTimeTwo(r.nextInt(7));
		}

		return (ExcludeDayCombinationPrio) prio;

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
	public void generateMockData(Collection<Teacher> teachers) {
		for (Teacher teacher : teachers) {
			for (int i = 0; i < 2; i++) {
				Prio prio = randomize(teacher);
				teacher.addPrio(prio);
				create(prio);
			}
		}
	}

}
