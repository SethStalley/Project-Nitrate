package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.JOptionPane;

import model.Calibration;
import model.CalibrationTable;
import model.FileObserver;
import model.MainTable;
import model.PushGraph;
import model.Save;
import model.TextFile;
import model.WorkingWavelength;
import view.MainWindow;

public class Controller {
	
	private MainTable mainTable;
	private CalibrationTable calibrationTable;
	private MainWindow graphicInterface;
	private FileObserver fileObserver;
	private PushGraph pushGraph;
	
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
		    graphicInterface.errorStartingObserver();
		}
		
	}
	
	public void startPushGraph(view.CalibrationTable calibrationTable){
		this.pushGraph = new PushGraph(this, calibrationTable);
		this.pushGraph.start();
	}
	
	public void stopObserver() {
		if (this.fileObserver != null)
			this.fileObserver.stopObserver();
		this.fileObserver = null;
		graphicInterface.observerStoppedColor();
	}
	
	public void addWorkingWavelength(String wavelength) {
		this.mainTable.addWorkingWavelength(wavelength);
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
	
	public void setFileType(Date key, String type) {
		this.mainTable.getFile(key).setType(type);
	}
	
	/** 
	 * @param String wavelength of absorbance column
	 * @return int which is column index of the absorbance column 
	 */
	public int getAbsorbanceColumnIndex(String wavelength) {
		return mainTable.getAbsorbanceColumnIndex(wavelength);
	}
	
	public WorkingWavelength getWavelengthWithWavelength(String wavelength){
		return mainTable.getWavelengthWithWavelength(wavelength);
	}
	
	/**
	 * Checks if a active concentration column already exists.
	 * @param Date of the calibration
	 * @return boolean
	 */
	public boolean concentrationColumnExists(Date key) {		
		return mainTable.doesConcentrationColumnExist(key);
	}
	
	public boolean removeFile(Date key) {
		return this.mainTable.removeFile(key);
	}
	
	public boolean removeCalibration(Date key){
		return this.calibrationTable.removeCalibration(key);
	}
	
	
	public void removeAbsorbanceColumn(int key) {
		WorkingWavelength ww = this.mainTable.getAbsorbanceColumn(key);
		String wavelength = ww.getWavelength();
		graphicInterface.deleteCalibrationsByWavelength(wavelength);//delete calibrations for that absorbance
		this.mainTable.removeWorkingWavelength(key);
		graphicInterface.delteAbsorbanceMainTable(key, ww.getNumOfConcentrations());
	}
	
	public void removeConcentrationColumn(int key){
		this.mainTable.removeWorkingConcentrationColumn(key);
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
	
	public boolean addWorkingConcentration(int absorbanceKey, Date calibrationKey){
		Calibration cal = calibrationTable.getCalibration(calibrationKey);
		return mainTable.addWorkingConcentration(absorbanceKey, cal);
	}
	
	/**
	 * @param absorbanceIndex
	 * @return int number of concentration columns that correspond to an absorbency column
	 */
	public int getNumberWorkingConcentrations(int absorbanceIndex) {
		WorkingWavelength ww = mainTable.getAbsorbanceColumn(absorbanceIndex);
		
		if (ww != null) {
			return ww.getNumOfConcentrations();
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
	
	public void setStdConcentration(Date index, String concentration) {
		this.mainTable.getFile(index).saveConcentration(concentration);
	}
	
	public String getStdConcentration(Date index) {
		return this.mainTable.getFile(index).getConcentration();
	}
	
	public Enumeration<Date> getAllFileKeys() {
		Enumeration<String> keys = this.mainTable.getAllFiles().keys();
		ArrayList<Date> dateKeys = new ArrayList<Date>();
		
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			Date dateKey;
			try {
				dateKey = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(key);
				dateKeys.add(dateKey);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return Collections.enumeration(dateKeys);
	}
	
	public ArrayList<Calibration> getAllCalibrations() {
		return calibrationTable.getAllCalibrations();
	}

	public ArrayList<WorkingWavelength> getMainTableWavelengths() {
		return mainTable.getWorkingWaveLengths();
	}
	
	public boolean checkForWorkingWavelength(String wavelength){
		return mainTable.checkForWorkingWavelength(wavelength);
	}
	

	public void saveProgram(String file) {
		Save save = new Save(this.mainTable,this.calibrationTable);
		save.saveState(file);
	}

	public void loadProgram(String file) {
		Save save = Save.getSave(file);
		
		if (save != null) {
			this.mainTable = save.getStateMainTable();
			this.calibrationTable = save.getCalibrationTable();
			
			this.graphicInterface.mainTable.updateFromModel();
			this.graphicInterface.calibrationTable.updateFromModel();
		} else {
			//save is invalid send error msg
			this.graphicInterface.errorOpenSave();
		}
		
	}
	
	/*
	 * for custom rows
	 */
	public Boolean addManualAbsorbance(Date key,String wavelength, String absorbance){
		return mainTable.getFile(key).addManualAbsorbance(wavelength, absorbance);
	}
	public void removeManualAbosrbance(Date key,String wavelength){
		mainTable.getFile(key).removeManualAbsorbance(wavelength);
	}
	
	public WorkingWavelength getAbsorbanceColumn(int absorbanceIndex) {
		return mainTable.getAbsorbanceColumn(absorbanceIndex);
	}
	
	public Boolean removeWorkingConcentration(String calibrationDate, String wavelength){
		WorkingWavelength ww = getWavelengthWithWavelength(wavelength);
		Integer offset =  ww.removeWorkingConcentration(calibrationDate);
		if (offset > 0){
			Integer absorbanceColumn = getAbsorbanceColumnIndex(wavelength);
			graphicInterface.deleteColumnMainTable(offset + absorbanceColumn);
			return true;
		}
		else{
			return false;
		}
	}
	public Boolean setConcentrationGraph(String calibrationDate, String wavelength){
		WorkingWavelength ww = getWavelengthWithWavelength(wavelength);
		Integer offset =  ww.getIndexConcentration(calibrationDate);
		if (offset > 0){
			Integer absorbanceColumn = getAbsorbanceColumnIndex(wavelength);
			graphicInterface.graphConcentration(offset + absorbanceColumn);
			return true;
		}
		else{
			return false;
		}
		
	}
	
	public void initiateConcentrationGraph(){
		graphicInterface.initiateGraphConcentration();
	}
	
	public void cleanGraph(){
		graphicInterface.cleanGraph();
	}
	
	public ArrayList<String[]> getConcentrationGraphData(){
		return graphicInterface.getConcentrationPoints();
	}

}
