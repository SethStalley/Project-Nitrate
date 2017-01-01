package test;

import model.*;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

import org.junit.Test;

import controller.Controller;

public class UnitTests {
	
	String projectRoot = System.getProperty("user.dir");
	String textDataPath = projectRoot + "/testData/STD 1 CON DEMI.txt";

	@Test
	public void TextFileParse() {
		TextFile textFile = new TextFile(textDataPath);
		String fileName = textFile.getName();
		assertTrue(fileName.equals("STD 1 CON DEMI.txt"));
	}
	
	@Test
	public void TextFileGetAbsorbance() {
		TextFile textFile = new TextFile(textDataPath);
		String absorbance = textFile.getAbsorbance("223.7");
		
		assertTrue(absorbance.equals("0.020"));
	}
	
	@Test
	public void Calibration() {
		Calibration calibration;
		ArrayList<Double> absorbances = new ArrayList<Double>();
		ArrayList<Double> concentrations = new ArrayList<Double>();
		
		absorbances.add(0.049);
		absorbances.add(0.068);
		concentrations.add(1.0);
		concentrations.add(1.5);
		
		calibration = new Calibration(absorbances, concentrations, "223.7");

		assertTrue(calibration.getSlope() == 0.038000000000000006);
	}
	
	/*
	 * Test storing a text data file in the Hashtable
	 */
	@Test
	public void storeTextFile() {
		MainTable mTable = new MainTable();
		
		Date result = mTable.addFile(textDataPath);
		
		assertTrue(result != null);
	}
	
	/*
	 * Test storing a calibration formula in the Hashtable
	 */
	@Test
	public void storeCalibration() {
		CalibrationTable cTable = new CalibrationTable();
		ArrayList<Double> absorbances = new ArrayList<Double>();
		ArrayList<Double> concentrations = new ArrayList<Double>();
		
		absorbances.add(0.049);
		absorbances.add(0.068);
		concentrations.add(1.0);
		concentrations.add(1.5);
		
		cTable.addCalibration(absorbances, concentrations, "223.7");
		
		assertTrue(cTable.getCalibration(0).getWavelength().equals("223.7"));
	}

}
