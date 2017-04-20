package de.teamzhang.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderGenerator {

	public static String hashPassword(String pw) {
		String hashedPassword;
		String password = pw;
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}
}