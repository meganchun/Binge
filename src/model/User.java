package model;

import java.util.ArrayList;
import java.util.Queue;

/*
 * This class will create a user object that will be used as a unique identifier when running the program
 * so it can call information specific to a different user
 */
public class User {

	// atrributes of a user
	String name;
	String username;
	String password;
	ArrayList<String> preferenceData;

	// constructor that will create an instance of the user object with the name,
	// username, password and preference data as a parameter
	public User(String name, String username, String password, ArrayList<String> preferenceData) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.preferenceData = preferenceData;
	}

	// getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<String> getPreferenceData() {
		return preferenceData;
	}

	public void setPreferenceData(ArrayList<String> preferenceData) {
		this.preferenceData = preferenceData;
	}

}
