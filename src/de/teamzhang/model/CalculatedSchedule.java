package de.teamzhang.model;

public class CalculatedSchedule {
	private String[][] schedule = new String[5][7];
	private String programName;

	public CalculatedSchedule() {
		for (int row = 0; row < 5; row++)
			for (int col = 0; col < 7; col++)
				schedule[row][col] = "-";
	}

	public void setFullSlot(int day, int time, String string) {
		schedule[day][time] = string;

	}

	public void setEmptySlot(int day, int time) {
		schedule[day][time] = "-";

	}

	public String[][] getSchedule() {
		return schedule;
	}

	public void setSchedule(String[][] schedule) {
		this.schedule = schedule;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

}
