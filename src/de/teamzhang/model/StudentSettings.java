package de.teamzhang.model;

public class StudentSettings {

	private String type = "";
	private boolean isSet = false;
	private int value = 0;
	private int minusPoints = 0;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
