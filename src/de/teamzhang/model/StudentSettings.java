package de.teamzhang.model;

public class StudentSettings {

	private int type; // 0 = maxhours, 1 = maxBreaks, 2 = maxday
	private boolean isSet = false;
	private int value = 0;
	private int minusPoints = 0;
	private String program;

	public boolean isSet() {
		return isSet;
	}

	public void setSet(boolean isSet) {
		this.isSet = isSet;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getMinusPoints() {
		return minusPoints;
	}

	public void setMinusPoints(int minusPoints) {
		this.minusPoints = minusPoints;
	}

	/**
	 * 
	 * @return 0 = maxHours, 1 = maxBreak, 2 = maxDays
	 */
	public int getType() {
		return type;
	}

	public void setType(String theType) {
		if (theType.equals("MaxStunden"))
			type = 0;
		if (theType.equals("MaxPausen"))
			type = 1;
		if (theType.equals("MaxTage"))
			type = 2;

	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getProgram() {
		return program;
	}

}
