package model;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.math3.stat.regression.SimpleRegression;

public class Calibration {
	SimpleRegression sr;
	public ArrayList<Double> absorbances;
	public ArrayList<Double> concentrations;
	private Date date;
	private String wavelength;
	
	public Calibration(ArrayList<Double> absorbances, ArrayList<Double> concentrations,
			String wavelength) {
		this.date = new Date();
		this.sr = new SimpleRegression();
		this.concentrations = concentrations;
		this.absorbances = absorbances;
		this.wavelength = wavelength;
		
		fillRegressionData();
	}
	
	private void fillRegressionData() {
		for(int i=0; i<absorbances.size();i++) {
			sr.addData(concentrations.get(i),absorbances.get(i));
		}
	}
	
	public double getConcentration(double absorbance) {
		return (absorbance - sr.getIntercept()) / sr.getSlope();
	}
	
	/*
	 * Return the R correlation between the two data sets
	 */
	public double getPearson() {
		return sr.getRSquare();
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public double getSlope() {
		return sr.getSlope();
	}
	
	public double getIntercept() {
		return sr.getIntercept();
	}

	public String getWavelength() {
		return this.wavelength;
	}
}
