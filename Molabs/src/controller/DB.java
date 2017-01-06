package controller;

import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class DB {
	// controls access to database
	
	private static DB instance = null;
	private String username;
	private String password; // current username and password
	private String url;

	private DB() {
		url = "http://54.144.112.150/api/";
	}
	
	private DB(String username, String password){
		this.username = username;
		this.password = password;
		url = "http://54.144.112.150/api/";
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

		try {
			// molabsdb.validateUser(pUserName VARCHAR(45), pPassword VARBINARY(512))
			JSONObject json = new JSONObject();
		    json.put("pUserName", username);// maybe need quotation ??
		    json.put("pPassword", password);
		   
		    JSONArray jsonA = this.postRequest("validateUser", json);
		    
		    JSONObject resultJson =   (JSONObject) (((JSONArray) jsonA.get(0)).get(0));
		    
		    return resultJson.getString("type");

		}catch (Exception ex) {

		    //JOptionPane.showMessageDialog(null, ex.toString());

		}
		return null;
	}
	
	public ArrayList<String[]> getUsersForUsername(){
		/* return an empty list of string if it does not find something*/
		/* user type name email phone*/
		ArrayList<String[]> users = new ArrayList<String[]>();
		
		try{
			/*molabsdb.selectAllUsersForAdministrator(pUserName VARCHAR(45), pPassword VARBINARY(512))*/
			JSONObject json = new JSONObject();
		    json.put("pUserName", username);
		    json.put("pPassword", password);
		}
		catch(Exception e){
			
		}
		return users;
	}
	
	public String createUser(String[] userData){
		String user = userData[0];
		String pass = userData[1];
		String type = userData[2];
		String name = userData[3];
		String email = userData[4];
		String phone = userData[5];
		
		try{
			/* molabsdb.insertUser(pNewUserName VARCHAR(45), pNewPassword VARBINARY(512), pType VARCHAR(10),
			pCompleteName VARCHAR(85), pTelephoneNumber VARCHAR(20), pEmail VARCHAR(45),
			pUserName VARCHAR(45), pPassword VARBINARY(512)*/
			
			JSONObject json = new JSONObject();
			json.put("pNewUserName", user);
			json.put("pNewPassword", pass);
			json.put("pType", type);
			json.put("pCompleteName", name);
			json.put("pTelephoneNumber", phone);
			json.put("pEmail", email);
		    json.put("pUserName", username);
		    json.put("pPassword", password);
		}
		catch (Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		return null;
	}
	
	public String updateUser(String[] userData){
		/* retur null if everything ok, otherwise a string error message */
		String newUser = userData[0];
		String pass = userData[1];
		String type = userData[2];
		String name = userData[3];
		String email = userData[4];
		String phone = userData[5];
		String newUserToUpdate = userData[6];
		
		try{
			/*molabsdb.updateUser(pUserNameToUpdate VARCHAR (45), pNewUserName VARCHAR(45), pNewPassword VARBINARY(512), pType VARCHAR(10),
					pCompleteName VARCHAR(85), pTelephoneNumber VARCHAR(20), pEmail VARCHAR(45),
					pUserName VARCHAR(45), pPassword VARBINARY(512)) -- these 2 is for user validation*/
			
			JSONObject json = new JSONObject();
			json.put("pUserNameToUpdate", newUserToUpdate);
			json.put("pNewUserName", newUser);
			json.put("pNewPassword", pass);
			json.put("pType", type);
			json.put("pCompleteName", name);
			json.put("pTelephoneNumber", phone);
			json.put("pEmail", email);
		    json.put("pUserName", username);
		    json.put("pPassword", password);
		}
		catch (Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		return null;
	}
	
	public String deleteUser(String pUsername){
		// returns null if everything ok. otherwise a string error message.
		try{
			/*molabsdb.deleteUserByUsername(pUserNameToDelete VARCHAR(45), pUserName VARCHAR(45), pPassword VARBINARY(512))*/
			JSONObject json = new JSONObject();
			json.put("pUserNameToDelete", pUsername);
		    json.put("pUserName", username);
		    json.put("pPassword", password);
		}
		catch(Exception e){
			
		}
		
		
		return null;
	}
	
	private JSONArray postRequest(String procedure, JSONObject parameters){
		HttpClient httpClient = HttpClientBuilder.create().build(); 
		try {
			

		    HttpPost request = new HttpPost(url + procedure);
		    StringEntity params = new StringEntity(parameters.toString());
		    request.addHeader("content-type", "application/json");
		    request.setEntity(params);
		    HttpResponse response = httpClient.execute(request);
		    
		    String jsonString = EntityUtils.toString(response.getEntity());
		    
		    JSONArray json = new JSONArray(jsonString);
		    
		    //JOptionPane.showMessageDialog(null,  (((JSONArray) json.get(0)).get(0).getClass()));
		    
		    return  json;

		    
		    

		}catch (Exception ex) {

		    //JOptionPane.showMessageDialog(null, ex.toString());

		}
		return null;
	}

}
