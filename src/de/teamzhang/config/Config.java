package de.teamzhang.config;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class Config {

	private static Properties prop = new Properties();

	private static void setProperty(String key, String value) {
		prop.setProperty(key, value);
	}

	public static void mockProps(String name) {
		OutputStream output = null;
		try {

			output = new FileOutputStream(name + "settings.properties");

			// set the properties value
			setProperty("studentMaxHoursValue", "4");
			setProperty("studentMaxDaysValue", "4");
			setProperty("studentMaxBreakLengthValue", "2");
			setProperty("studentMaxHoursMinusPoints", "10");
			setProperty("studentMaxBreakLengthMinusPoints", "20");
			setProperty("studentMaxDaysMinusPoints", "0");
			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
}
