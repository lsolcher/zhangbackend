package de.teamzhang.model;

import java.math.BigInteger;

public class FreeTextInputPrio extends Prio {

	private String textFieldInput;

	public FreeTextInputPrio(String string, BigInteger profID, BigInteger[] courses, String textFieldInput) {
		super(string, profID, courses);
		this.textFieldInput = textFieldInput;
	}

	public FreeTextInputPrio() {
		// TODO Auto-generated constructor stub
	}

}
