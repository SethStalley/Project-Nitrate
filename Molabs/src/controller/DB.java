package controller;

import java.util.ArrayList;

public class DB {
	// controls access to database
	
	private static DB instance = null;
	private String username;
	private String password; // current username and password
	private String url;

	private DB() {
		// TODO Auto-generated constructor stub
	}
	
	private DB(String username, String password){
		this.username = username;
		this.password = password;
		url = "molabsdb";
	}
	
	public static DB getInstance(String username, String password) {
		if (instance == null) {
			return new DB(username, password);
		}
		return instance;
	}
	
	public static DB getInstance(){
		if (instance == null) {
			return new DB();
		}
		
		return instance;
	}
	
	public String validateUser(){//returns the type of the user if it is correct. Null otherwise
		return "owner";
	}
	
	public ArrayList<String[]> getUsersForUsername(){
		ArrayList<String[]> users = new ArrayList<String[]>();
		return users;
	}
	
	public String createUser(String[] userData){
		String user = userData[0];
		String pass = userData[1];
		String type = userData[2];
		String name = userData[3];
		String email = userData[4];
		String phone = userData[5];
		
		return null;
	}
	
	public String updateUser(String[] userData){
		String newUser = userData[0];
		String pass = userData[1];
		String type = userData[2];
		String name = userData[3];
		String email = userData[4];
		String phone = userData[5];
		String userToUpdate = userData[6];
		
		return null;
	}
	
	public String deleteUser(String username){
		
		return null;
	}

}
