package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.CalibrationTable;
import model.DateTimeDeserializer;
import model.DateTimeSerializer;
import model.MainTable;

public class IntegrationTests {

	String projectRoot = System.getProperty("user.dir");
	String textDataPath = projectRoot + "/testData/STD 1 CON DEMI.txt";
	
	/*
	 * Checks that a MainTable object (with data) can be converted to JSON
	 * using GSON and deserialized again, into the same class.
	 */
	@Test
	public void convertMainTable2Json() {
		MainTable mTable = new MainTable();
		MainTable mTable2;
		
		Date key = mTable.addFile(textDataPath);
		
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(Date.class, new DateTimeDeserializer())
				.registerTypeAdapter(Date.class, new DateTimeSerializer())
				.create();
		
		String json = mTable.getAsJSON();
		mTable2 = gson.fromJson(json, MainTable.class);
		
		assertTrue(key != null && mTable.getAllFiles().size() == mTable2.getAllFiles().size());
	}
	
	/*
	 * Checks that a CalibrationTable object (with data) can be converted to JSON
	 * using GSON and deserialized again, into the same class.
	 */
	@Test
	public void convertCalibrationTable2Json() {
		
		CalibrationTable cTable = new CalibrationTable();
		CalibrationTable cTable2;
		
		//fill first cTable with some data
		ArrayList<Double> absorbances = new ArrayList<Double>();
		ArrayList<Double> concentrations = new ArrayList<Double>();
		
		absorbances.add(0.049);
		absorbances.add(0.068);
		concentrations.add(1.0);
		concentrations.add(1.5);
		
		cTable.addCalibration(absorbances, concentrations, "223.7");
		
		
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(Date.class, new DateTimeDeserializer())
				.registerTypeAdapter(Date.class, new DateTimeSerializer())
				.create();
		
		String json = cTable.getAsJSON();
		cTable2 = gson.fromJson(json, CalibrationTable.class);
		
		assertTrue(cTable.getCalibration(0).equals(cTable.getCalibration(0)));
	}
}
