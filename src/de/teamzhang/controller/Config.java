package de.teamzhang.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class Config {

	private static Properties prop = new Properties();

	private static void setStudentMaxHours() {

	}

	public static void mockProps() {
		OutputStream output = null;
		try {

			output = new FileOutputStream("settings.properties");

			// set the properties value
			prop.setProperty("studentMaxHours", "4");
			prop.setProperty("dbuser", "mkyong");
			prop.setProperty("dbpassword", "password");

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
