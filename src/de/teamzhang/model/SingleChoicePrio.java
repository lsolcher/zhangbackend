package de.teamzhang.model;

public class SingleChoicePrio extends Prio {

	// 0 = Raumbeschaffenheit, 1 =...
	int option;

	public SingleChoicePrio() {
		super();
	}

	public void setOption(int theOption) {
		option = theOption;
	}

}
