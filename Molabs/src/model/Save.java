package model;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Save extends JSON_Exportable{
	
	private MainTable mainTable;
	private CalibrationTable calibrationTable;
	
	public Save(MainTable mainTable, CalibrationTable calibrationTable) {
		this.mainTable = mainTable;
		this.calibrationTable = calibrationTable;
	}
	
	public static Save getSave(String filePath){
		  String data;
		try {
			data = new String(Files.readAllBytes(Paths.get(filePath)));
			Gson gson = new GsonBuilder()
					.registerTypeAdapter(Date.class, new DateTimeDeserializer())
					.registerTypeAdapter(Date.class, new DateTimeSerializer())
					.create();
			 
			return gson.fromJson(data, Save.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		  
		return null;
	}
	
	public void saveState(String completePath) {
		String data = this.getAsJSON();
		System.out.println(data);
		
		try (FileWriter file = new FileWriter(completePath)) {
			file.write(data);
			System.out.println("Successfully saved program data.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public MainTable getStateMainTable() {
		return this.mainTable;
	}
	
	public CalibrationTable getCalibrationTable() {
		return this.calibrationTable;
	}
	
}
