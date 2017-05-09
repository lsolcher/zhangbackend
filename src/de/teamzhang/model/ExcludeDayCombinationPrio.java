package de.teamzhang.model;

import java.math.BigInteger;

public class ExcludeDayCombinationPrio extends Prio {

	//
	private String text;
	// Monday = 0; Sunday = 6
	private int dayOne;
	private int dayTwo;
	// time represented without :
	private int timeOne = 0;
	private int timeTwo = 0;

	// priorities may exlude only days or days together with time (that would be
	// hastime = true)
	private boolean hasTime;

	// true if is excluding, false if is combining
	private boolean isExcluding;

	public ExcludeDayCombinationPrio(String string, BigInteger profID, BigInteger[] courses, String text, int dayOne,
			int dayTwo, int timeOne, int timeTwo, boolean hasTime, boolean isExcluding) {
		super(string, profID, courses);
		this.text = text;
		this.dayOne = dayOne;
		this.dayTwo = dayTwo;
		this.timeOne = timeOne;
		this.timeTwo = timeTwo;
		this.hasTime = hasTime;
		this.isExcluding = isExcluding;
	}

}
