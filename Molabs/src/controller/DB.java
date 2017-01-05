package controller;


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

}
