package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainTable extends JSON_Exportable{
	
	private Hashtable<Date,TextFile> files;
	private Hashtable<Integer, String> workingWavelengths;
	private Hashtable<Integer[],Calibration> workingCalibrations; // key must be column index in main table
	
	public MainTable() {
		this.files = new Hashtable<Date,TextFile>();
		this.workingWavelengths = new Hashtable<Integer, String>();
		this.workingCalibrations = new Hashtable<Integer[],Calibration>();
	}
	
	/*
	 * Parses and stores the data from a valid textFile containing spectrometer data.
	 */
	public Date addFile(String path) {
		TextFile txtData = new TextFile(path);
		
		if(txtData.getDate() != null && this.files.get(txtData.getDate()) == null) {
			this.files.put(txtData.getDate(), txtData);
			return txtData.getDate();
		}
		
		return null;
	}
	
	public boolean removeFile(Date key) {
		return this.files.remove(key) != null;
	}
	
	public boolean addRow(String name, Date date) {
		TextFile file = new TextFile(name, date);
		return this.files.put(date, file) == null;
	}
	
	public boolean addWorkingWavelength(int index, String wavelength) {
		if (!this.workingWavelengths.contains(wavelength)) {
			this.workingWavelengths.put(index, wavelength);
			return true;
		}
		return false;
	}
	
	public boolean removeWorkingWavelength(String wavelength) {
		if(this.workingWavelengths.contains(wavelength)) {
			this.workingWavelengths.remove(wavelength);
			return true;
		}
		return false;
	}
	
	// key (key,column)
	public boolean addWorkingCalibration(Integer[] key, Calibration calibration){
		if (!this.workingCalibrations.contains(calibration)) {
			this.workingCalibrations.put(key, calibration);
			return true;
		}
		return false;
	}
	
	// key (key,column)
	public boolean removeWorkingCalibration(int key[]){
		if(this.workingCalibrations.containsKey(key)) {
			this.workingCalibrations.remove(key);
			return true;
		}
		return false;
	}
	
	
	public Hashtable<Date,TextFile> getAllFiles() {
		return this.files;
	}
	
	public TextFile getFile(Date dateKey) {
		return this.files.get(dateKey);
	}
	
	public Hashtable<Integer, String> getWorkingWaveLengths() {
		return this.workingWavelengths;
	}

}
