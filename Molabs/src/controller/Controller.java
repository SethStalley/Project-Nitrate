package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import javax.swing.JOptionPane;

import model.Calibration;
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
	public Hashtable<Integer, String> getMainTableWavelengths() {
		return this.mainTable.getWorkingWaveLengths();
	}
	
	public void addWorkingWavelength(int index, String wavelength) {
		this.mainTable.addWorkingWavelength(index, wavelength);
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
	
	public boolean removeCalibration(Integer key){
		return this.calibrationTable.removeCalibration(key);
	}
	
	public String getAbsorbance(String wavelength, Date key) {
		TextFile file = this.mainTable.getFile(key);
		
		if (file != null) {
			return file.getAbsorbance(wavelength);

		}
		return null;
	}
	
	public void addCalibration(ArrayList<Double> absorbances, ArrayList<Double> concentrations,
			String wavelength){
		if(calibrationTable.addCalibration(absorbances, concentrations, wavelength)){
			graphicInterface.setNewCalibration(calibrationTable.getLastCalibration()); 
		}else
			graphicInterface.errorOnCalibration();
	}
	
	public Calibration getCalibrationData(int index){
		return calibrationTable.getCalibration(index);
	}
	
	/**
	 * 
	 * @param index 
	 * @param absorbance of the concentration to be calculated
	 * @return sample concentration for a given absorbance
	 */
	public Double getConcentration(int index, double absorbance){
		return calibrationTable.getCalibration(index).getConcentration(absorbance);
	}

}
