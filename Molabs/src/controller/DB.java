package controller;

import java.net.NoRouteToHostException;
import java.net.SocketException;
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

	static {
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");
	}
	
	private static DB instance = null;
	private String username;
	private String password; // current username and password
	private String url;
	private String type;
	private static String AesKey;

	private DB() {
		url = "https://54.144.112.150/api/";
	}
	
	private DB(String username, String password){
		this.username = username;
		this.password = password;
		url = "https://54.144.112.150/api/";
	}
	
	public String getAesKey() {
		return loadAesKey();
	}
	
	public String getUser(){
		return this.username;
	}
	
	private String loadAesKey() {
		try {
			JSONObject json = new JSONObject();
		    json.put("pUserName", username);// maybe need quotation ??
		    json.put("pPassword", password);
		    
		   
		    JSONArray jsonA = new JSONArray(this.postRequest("selectAesKey", json));
		    JSONObject resultJson =   (JSONObject) (((JSONArray) jsonA.get(0)).get(0));
		    
		    return resultJson.getString("key");

		}
		catch (HttpHostConnectException e){ //not working
			return null;
		}
		catch (java.net.NoRouteToHostException e){
			return null;
		}
		catch (Exception ex) {

		    //JOptionPane.showMessageDialog(null, ex.toString());

		}
		return null;
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
		    /*JSONObject resultJson =   new JSONObject(this.postRequest("validateUser", json));
		    JOptionPane.showMessageDialog(null, resultJson);*/
		    
		    type = resultJson.getString("type");
		    return type;

		}
		catch (HttpHostConnectException e){ //not working
			return Strings.ERROR_NO_INTERNET;
		}
		catch (java.net.NoRouteToHostException e){
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
		    	String[] allData = {resultJson.getString("userName"), resultJson.getString("type"), resultJson.getString("completeName"),
			    			resultJson.getString("email") ,resultJson.getString("telephoneNumber")};
		    	users.add(allData);
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
	
	public String updateUser(String[] userData, Controller control){
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
			if(pass.isEmpty()){
				System.out.println(JSONObject.NULL);
				json.put("pNewPassword", JSONObject.NULL);
				System.out.print(json.isNull("pNewPassword"));
			}else{
				json.put("pNewPassword", pass);
			}
			json.put("pType", type);
			
			json.put("pCompleteName", name);
			json.put("pTelephoneNumber", phone);
			json.put("pEmail", email);
			
		    json.put("pUserName", username);
		    json.put("pPassword", password);
		    
		    System.out.println(json);
		    
		    JSONObject resultJson =   new JSONObject(this.postRequest("updateUser", json));
	
		    if (resultJson.has("code")){
			    if(resultJson.getString("code").equals("ER_DUP_ENTRY")){
			    	return Strings.ERROR_USER_REPEATED;
			    }
			    else{
			    	if(newUserToUpdate.equals(username)){
			    		this.username = newUser;
			    		if(!pass.isEmpty())
			    			this.password = pass;
			    		control.changeUser(this.username);
			    	}
			    	return null;
			    }
		    }
		    else{
		    	if(newUserToUpdate.equals(username)){
		    		this.username = newUser;
		    		if(!pass.isEmpty())
		    			this.password = pass;
		    		control.changeUser(this.username);
		    	}
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
			return Strings.ERROR_NO_INTERNET;
		}
		
		
		return null;
	}
	
	public String updateAlertValues(Double minValue, Double maxValue){
		// return null if everything it´s ok, otherwise a string value
		try{
			/*molabsdb.updateAlertValues(pMinValue DOUBLE, pMaxValue DOUBLE,
					pUserName VARCHAR(45), pPassword VARCHAR(45))*/
			
			JSONObject json = new JSONObject();
			
			json.put("pMinValue", minValue);
			json.put("pMaxValue", maxValue);
			
		    json.put("pUserName", username);
		    json.put("pPassword", password);
		    
		    JSONObject resultJson =   new JSONObject(this.postRequest("updateAlertValues", json));
		    
		    //JOptionPane.showMessageDialog(null, resultJson);
		    
		    return null;
	
		}
		catch (Exception e){
			//JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		return Strings.ERROR_NO_INTERNET;
	}
	
	public Double[] getAlertValues(){
		/* return an empty list of string if it does not find something. This should not happen*/
		// returns null if there is an exception, probably due to no internet connection
		/* user type name email phone*/
		Double[] values = new Double[2];
		
		try{
			/*molabsdb.selectAlertValues(
					pUserName VARCHAR(45), pPassword VARCHAR(45)) */
			JSONObject json = new JSONObject();
		    json.put("pUserName", username);
		    json.put("pPassword", password);
		    
		    //JSONObject resultJson =   new JSONObject(this.postRequest("selectAlertValues", json));
		    JSONArray jsonA = new JSONArray(this.postRequest("selectAlertValues", json));
		    JSONObject resultJson =   (JSONObject) (((JSONArray) jsonA.get(0)).get(0));
	
		    
		    values[0] = Double.parseDouble(resultJson.getString("valueMin"));
		    values[1] = Double.parseDouble(resultJson.getString("valueMax"));
		   
		    
		}
		catch(Exception e){
			// JOptionPane.showMessageDialog(null, e.getMessage());
			return null; // probably internet
		}
		//JOptionPane.showMessageDialog(null, Arrays.toString(values));
		return values;
	}
	
	public String updateGraph(ArrayList<?> points, String graphType, String slope, String intercept, String pearson, String wavelength){
		// points must be like : [{1,2},{2,4}]
		// graphType: ABSvsConce , ConcenVsTime, CalibrationGraph
		// updateGraphForUser(graphType VARCHAR(45), newJson VARCHAR(1000), pUserName VARCHAR(45), pPassword VARBINARY(512))
		// slop and intercept is for calibration graphic. Send empty string "" if not needed
		JSONObject pointsJson = new JSONObject();
		JSONObject bigJson = new JSONObject();
		
		String[] xValues = new String[points.size()];
		String[] yValues  =new String[points.size()];
		try{
			for (int i = 0; i < points.size() ; i++){
				if(graphType.equals("CalibrationGraph")){
					Double[] currentPoint = (Double[]) points.get(i);
					xValues[i] = currentPoint[0].toString();
					yValues[i] = currentPoint[1].toString();
				}else if(graphType.equals("ConcenVsTime")){
					String[] currentPoint = (String[]) points.get(i);
					xValues[i] = currentPoint[0];
					yValues[i] = currentPoint[1];
				}
				
			}
			pointsJson.put("x", Arrays.toString(xValues));
			pointsJson.put("y", Arrays.toString(yValues));
			pointsJson.put("slope", slope);
			pointsJson.put("intercept", intercept);
			pointsJson.put("pearson", pearson);
			pointsJson.put("wavelength", wavelength);
			
			bigJson.put("graphType", graphType);
			bigJson.put("newJson", pointsJson.toString());
			bigJson.put("pUserName", username);
		    bigJson.put("pPassword", password);
		    
		    JSONObject resultJson = new JSONObject(this.postRequest("updateGraphForUser", bigJson));
		    
		}
		catch(Exception e){
			//JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		return null;
	}
	
	
	
	private String postRequest(String procedure, JSONObject parameters) throws SocketException{
		
		try {
			SSLContextBuilder builder = new SSLContextBuilder();
	
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
			@SuppressWarnings("deprecation")
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
		catch (java.net.NoRouteToHostException e){
			throw e;
		}
		
		catch (java.net.SocketException e){
			throw e;
		}
		
		catch (Exception ex) {

		    JOptionPane.showMessageDialog(null, ex.toString()+  ". This is a generic exception.");

		}
		return null;
	}
	
	public String getType(){
		return type;
	}
	
	public void changeUserData(String username, String password){
		this.username = username;
		this.password = password;
	}

}
