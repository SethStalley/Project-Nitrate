package controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import model.Calibration;
import model.CalibrationTable;
import model.FileObserver;
import model.MainTable;
import model.TextFile;
import model.WorkingWavelength;
import view.MainWindow;

public class Controller {
	
	private MainTable mainTable;
	private CalibrationTable calibrationTable;
	private MainWindow graphicInterface;
	private FileObserver fileObserver;
	
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
	
	public void startObserver(String path) {
		stopObserver();
		
		this.fileObserver = new FileObserver(this, (view.MainTable) graphicInterface.mainTable);
		Boolean success = !path.isEmpty() && this.fileObserver.startObserver(path);
		
		if (success) {
			graphicInterface.observerRunningColor();
		} else {
			System.out.println("here");
		    graphicInterface.errorStartingObserver();
		}
		
	}
	
	public void stopObserver() {
		if (this.fileObserver != null)
			this.fileObserver.stopObserver();
		this.fileObserver = null;
		graphicInterface.observerStoppedColor();
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
		Date date = this.mainTable.addFile(path);
		
		if (date == null) {
			graphicInterface.errorOnOpenFile();
		}
		return date;
	}
	
	public void addCustomRow(String name, Date key) {
		this.mainTable.addRow(name, key);
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
	
	/** 
	 * @param String wavelength of absorbance column
	 * @return int which is column index of the absorbance column 
	 */
	public int getAbsorbanceColumnIndex(String wavelength) {
		Enumeration<Integer> keys = mainTable.getWorkingWaveLengths().keys();
		while (keys.hasMoreElements() ) {
			int index = keys.nextElement();
			
			if (mainTable.getWorkingWaveLengths().get(index).getWavelength().equals(wavelength)){
				return index;
			}
		}
		return -1;
	}
	
	/**
	 * Checks if a active concentration column already exists.
	 * @param Date of the calibration
	 * @return boolean
	 */
	public boolean concentrationColumnExists(Date key) {
		Collection<WorkingWavelength> workingWavelengths = mainTable.getWorkingWaveLengths().values();
		Iterator<WorkingWavelength> iterWorking = workingWavelengths.iterator();
		
		while (iterWorking.hasNext() ) {
			Collection<Calibration> calibrations = iterWorking.next().getWokringConcentrationColumns().values();
			Iterator<Calibration> iterCalibrations = calibrations.iterator();
			
			while(iterCalibrations.hasNext()) {
				if (iterCalibrations.next().getDate().toString().equals(key.toString()) ) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean removeFile(Date key) {
		return this.mainTable.removeFile(key);
	}
	
	public boolean removeCalibration(Date key){
		return this.calibrationTable.removeCalibration(key);
	}
	
	
	public void removeWorkingConcentrationColumn(int key) {
		Collection<WorkingWavelength> workingColleciton = this.mainTable.getWorkingWaveLengths().values();
		Iterator<WorkingWavelength> iterWorking = workingColleciton.iterator();
		
		while (iterWorking.hasNext()) {
			WorkingWavelength workingWavelength = iterWorking.next();
			
			boolean success = workingWavelength.removeWorkingCalibration(key);
			
			if(success) {
				break;
			}
		}
		
	}
	public void removeConcentrationColumn(int key){
		removeWorkingConcentrationColumn(key);
		graphicInterface.deleteColumnMainTable(key);
	}
	
	public String getAbsorbance(String wavelength, Date key) {
		TextFile file = this.mainTable.getFile(key);
		
		if (file != null) {
			return file.getAbsorbance(wavelength);
		}
		return null;
	}
	
	public void addCalibration(ArrayList<Date> fileKeys, ArrayList<Double> absorbances, ArrayList<Double> concentrations,
			String wavelength){
		
		Calibration cal = calibrationTable.addCalibration(fileKeys, absorbances, concentrations, wavelength);
		
		if(cal != null){
			graphicInterface.setNewCalibration(cal); 

		}else
			graphicInterface.errorOnCalibration();
	}
	
	public Calibration getCalibrationData(Date index){
		return calibrationTable.getCalibration(index);
	}
	
	/**
	 * calculates all concentration on maintable, according to a absorbance and calibration
	 * @param key
	 */
	public void calculateConcentrations(Date key){
		graphicInterface.calculateConcentrations(key);
	}
	
	public boolean addWorkingCalibration(int absorbanceKey, Date calibrationKey){
		Calibration cal = calibrationTable.getCalibration(calibrationKey);
		WorkingWavelength wavelength = this.mainTable.getWorkingWaveLengths().get(absorbanceKey);
		
		if (cal != null && wavelength != null) {
			wavelength.addWorkingConcentration(cal);			
		}
		return false;
	}
	
	public int getNumberWorkingConcentrations(int absorbanceIndex) {
		WorkingWavelength workingWavelength = this.mainTable.getWorkingWaveLengths().get(absorbanceIndex);
		
		if (workingWavelength != null) {
			return workingWavelength.getNumOfConcentrations();
		}
		return -1;
	}
	
	/**
	 * 
	 * @param index 
	 * @param absorbance of the concentration to be calculated
	 * @return sample concentration for a given absorbance
	 */
	public Double getConcentration(Date index, double absorbance){
		return calibrationTable.getCalibration(index).getConcentration(absorbance);
	}

	public Hashtable<Integer, WorkingWavelength> getMainTableWavelengths() {
		return mainTable.getWorkingWaveLengths();
	}
	
	public boolean checkForWorkingWavelength(String wavelength){
		return mainTable.checkForWorkingWavelength(wavelength);
	}

}
