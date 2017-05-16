package de.teamzhang.model;

public class SingleChoicePrio extends Prio {

	// see Room.type
	int option;

	public SingleChoicePrio() {
		super();
	}

	public void setOption(int theOption) {
		option = theOption;
	}

}
