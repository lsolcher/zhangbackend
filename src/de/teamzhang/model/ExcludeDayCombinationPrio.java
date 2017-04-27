package de.teamzhang.model;

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

	public ExcludeDayCombinationPrio() {
		super();
	}

}
