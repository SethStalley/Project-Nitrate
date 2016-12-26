package test;

import model.*;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

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

}
