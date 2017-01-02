package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import javax.swing.JOptionPane;

import model.CalibrationTable;
import model.MainTable;
import model.TextFile;
import view.MainWindow;

public class Controller {
	
	private MainTable mainTable;
	private CalibrationTable calibrationTable;
	private MainWindow graphicInterface;
	
	public Controller(MainWindow GUI) {
		instanceComponents();
		graphicInterface = GUI;
	}
	
	/*
	 * Create default model objects, as blanks, without data
	 */
	private void instanceComponents() {
		this.mainTable = new MainTable();
		this.calibrationTable = new CalibrationTable();
	}
	
	
	/**
	 * Get working wavelengths from mainTable property
	 * @return ArrayList<String> with all active wavelengths
	 */
	public ArrayList<String> getMainTableWavelengths() {
		return this.mainTable.getWorkingWaveLengths();
	}
	
	/**
	 * Add new file to the mainTable model.
	 * @param String absolute path to the file 
	 * @return Date which is the files key.
	 */
	public Date addFile(String path) {
		return this.mainTable.addFile(path);
	}
	
	/**
	 * @param Date key of file.
	 * @return String name of data File.
	 */
	public String getFileName(Date key) {
		TextFile file = this.mainTable.getFile(key);
		return file.getName();
	}
	
	
	/**
	 * @param Date key of file.
	 * @return String type of data File.
	 */
	public String getFileType(Date key) {
		TextFile file = this.mainTable.getFile(key);
		return file.getType();
	}
	
	public boolean removeFile(Date key) {
		return this.mainTable.removeFile(key);
	}
	
	/**
	 * 	
	 * @param wavelength to find the absorbance
	 * @param dates array of all key values of text files
	 * @return an array with all the absorbances
	 */
	public ArrayList<String> getAbsorbances(String wavelength, ArrayList<Date> dates){
		ArrayList<String> absorbances = new ArrayList<String>();
		String currentAbsorbance = null;
		if(dates.isEmpty()){
			return null;
		}
		for(Date d: dates){
			currentAbsorbance = this.mainTable.getFile(d).getAbsorbance(wavelength);
			if(currentAbsorbance == null){
				return null;
			}
			absorbances.add(currentAbsorbance);
		}
		return absorbances;
	}
}
