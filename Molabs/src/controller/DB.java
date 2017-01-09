package controller;

import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.swing.JOptionPane;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import values.Strings;

public class DB {
	// controls access to database
	
	private static DB instance = null;
	private String username;
	private String password; // current username and password
	private String url;

	private DB() {
		url = "https://54.144.112.150/api/";
	}
	
	private DB(String username, String password){
		this.username = username;
		this.password = password;
		url = "https://54.144.112.150/api/";
	}
	
	
	public static DB getInstance(String username, String password) {
		if (instance == null) {
			instance = new DB(username, password);
		}
		instance.username = username;
		instance.password = password;
		return instance;
	}
	
	public static DB getInstance(){
		if (instance == null) {
			instance =  new DB();
		}
		
		return instance;
	}
	
	public String user(){
		return this.username;
	}
	
	public String validateUser(){//returns the type of the user if it is correct. Null otherwise

		try {
			// molabsdb.validateUser(pUserName VARCHAR(45), pPassword VARBINARY(512))
			JSONObject json = new JSONObject();
		    json.put("pUserName", username);// maybe need quotation ??
		    json.put("pPassword", password);
		    
		   
		    JSONArray jsonA = new JSONArray(this.postRequest("validateUser", json));
		    
		    JSONObject resultJson =   (JSONObject) (((JSONArray) jsonA.get(0)).get(0));
		    
		    return resultJson.getString("type");

		}
		catch (HttpHostConnectException e){ //not working
			return Strings.ERROR_NO_INTERNET;
		}
		catch (Exception ex) {

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
		    
		    JSONArray jsonA = new JSONArray( this.postRequest("selectAllUsersForAdministrator", json));
		    
		    JSONArray jsonArray =  (((JSONArray) jsonA.get(0)));
		    
		    for(int i = 0; i < jsonArray.length() ; i++){
		    	JSONObject resultJson = (JSONObject) jsonArray.get(i);
		    	String userName = resultJson.getString("userName");
		    	
		    	if (userName.equals(this.username)){
		    		String[] allData = {username, "you", resultJson.getString("completeName"),
			    			resultJson.getString("email") ,resultJson.getString("telephoneNumber")};
		    		users.add(0, allData);
		    	}
		    	else{
		    		String[] allData = {resultJson.getString("userName"), resultJson.getString("type"), resultJson.getString("completeName"),
			    			resultJson.getString("email") ,resultJson.getString("telephoneNumber")};
		    		users.add(allData);
		    	}

		    }
		    
		}
		catch(Exception e){
			return null;
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
		    json.put("pUserName", this.username);
		    json.put("pPassword", this.password);
		    
		   
		    //JOptionPane.showMessageDialog(null, jsonA);
		    
		    JSONObject resultJson =   new JSONObject(this.postRequest("insertUser", json));
		    
	
		    
		    if (resultJson.has("code")){
			    if(resultJson.getString("code").equals("ER_DUP_ENTRY")){
			    	return Strings.ERROR_USER_REPEATED;
			    }
			    else{
			    	return null;
			    }
		    }
		    else{
		    	return null;
		    }
		    
		}
		catch (Exception e){
			//JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return Strings.ERROR_NO_INTERNET;
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
		    
		    JSONObject resultJson =   new JSONObject(this.postRequest("updateUser", json));
	
		    if (resultJson.has("code")){
			    if(resultJson.getString("code").equals("ER_DUP_ENTRY")){
			    	return Strings.ERROR_USER_REPEATED;
			    }
			    else{
			    	return null;
			    }
		    }
		    else{
		    	return null;
		    }
		}
		catch (Exception e){
			//JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		return Strings.ERROR_NO_INTERNET;
	}
	
	public String deleteUser(String pUsername){
		// returns null if everything ok. otherwise a string error message.
		try{
			/*molabsdb.deleteUserByUsername(pUserNameToDelete VARCHAR(45), pUserName VARCHAR(45), pPassword VARBINARY(512))*/
			JSONObject json = new JSONObject();
			json.put("pUserNameToDelete", pUsername);
		    json.put("pUserName", username);
		    json.put("pPassword", password);
		    
		    JSONObject resultJson = new JSONObject(this.postRequest("deleteUserByUsername", json));
		}
		catch(Exception e){
			
		}
		
		
		return Strings.ERROR_NO_INTERNET;
	}
	
	public String updateGraph(ArrayList<Double[]> points, String graphType){
		// points must be like : [{1,2},{2,4}]
		// graphType: ABSvsConce , ConcenVsTime, CalibrationGraph
		// updateGraphForUser(graphType VARCHAR(45), newJson VARCHAR(1000), pUserName VARCHAR(45), pPassword VARBINARY(512))
		JSONObject pointsJson = new JSONObject();
		JSONObject bigJson = new JSONObject();
		
		String[] xValues = new String[points.size()];
		String[] yValues  =new String[points.size()];
		try{
			for (int i = 0; i < points.size() ; i++){
				Double[] currentPoint = points.get(i);
				xValues[i] = currentPoint[0].toString();
				yValues[i] = currentPoint[1].toString();
			}
			pointsJson.put("x", Arrays.toString(xValues));
			pointsJson.put("y", Arrays.toString(yValues));
			
			bigJson.put("graphType", graphType);
			bigJson.put("newJson", pointsJson.toString());
			bigJson.put("pUserName", username);
		    bigJson.put("pPassword", password);
		    
		    JOptionPane.showMessageDialog(null, bigJson.toString());
		    
		    JSONObject resultJson = new JSONObject(this.postRequest("updateGraphForUser", bigJson));
		    
		    JOptionPane.showMessageDialog(null, resultJson.toString());
		    
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		
		
		return null;
	}
	
	private String postRequest(String procedure, JSONObject parameters) throws HttpHostConnectException{
		
		//HttpClient httpClient = HttpClientBuilder.create().build(); 
		try {
			SSLContextBuilder builder = new SSLContextBuilder();
	
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		            builder.build(),SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			
			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(
		            sslsf).build();

		    HttpPost request = new HttpPost(url + procedure);
		    StringEntity params = new StringEntity(parameters.toString());
		    request.addHeader("content-type", "application/json");
		    request.setEntity(params);
		    HttpResponse response = httpClient.execute(request);
		    
		    String jsonString = EntityUtils.toString(response.getEntity());
		    
		    //JOptionPane.showMessageDialog(null, jsonString);
		    
		    //JOptionPane.showMessageDialog(null,  (((JSONArray) json.get(0)).get(0).getClass()));
		    
		    return  jsonString;

		    
		    

		}
		catch (HttpHostConnectException e){
			//JOptionPane.showMessageDialog(null, Strings.ERROR_NO_INTERNET);
			throw e;
		}
		
		catch (Exception ex) {

		    JOptionPane.showMessageDialog(null, ex.toString());

		}
		return null;
	}

}
