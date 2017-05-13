package de.teamzhang.model;

public class SingleChoicePrio extends Prio {

	// 0 = Raumbeschaffenheit, 1 =...
	Room.Type option;

	public SingleChoicePrio() {
		super();
	}

	public void setOption(Room.Type theOption) {
		option = theOption;
	}

}
