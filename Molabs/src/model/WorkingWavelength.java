package model;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

public class WorkingWavelength {
	private String wavelength;
	ArrayList<Calibration> workingConcentrationColumns;
	
	public WorkingWavelength(String wavelength) {
		this.workingConcentrationColumns = new ArrayList<Calibration>();
		this.wavelength = wavelength;
	}
	
	public boolean addWorkingConcentration(Calibration calibration) {		
		if (!this.workingConcentrationColumns.contains(calibration)) {
			this.workingConcentrationColumns.add(calibration);
			return true;
		}
		return false;
	}
	
	public Calibration getWorkingCalibration(int index) {
		return this.workingConcentrationColumns.get(index);
	}
	
	public ArrayList<Calibration> getWorkingConcentrationColumns() {
		return this.workingConcentrationColumns;
	}
	
	public boolean removeWorkingConcentration(int index) {
		if (this.workingConcentrationColumns.get(index) != null) {
			this.workingConcentrationColumns.remove(index);
			return true;
		}
		return false;
	}
	
	public Integer removeWorkingConcentration(String date){
		//returns an offset from the biggining of the concentration
		Integer offset = 0;
		for (Calibration c: workingConcentrationColumns){
			offset++;
			if (c.getDate().toString().equals(date)){
				workingConcentrationColumns.remove(c);
				return offset;
			}
		}
		return -1;//should not happen
	}
	
	public Integer getIndexConcentration(String date){
		//returns an offset from the biggining of the concentration
		Integer offset = 0;
		for (Calibration c: workingConcentrationColumns){
			offset++;
			if (c.getDate().toString().equals(date)){
				return offset;
			}
		}
		return -1;//should not happen
	}
	
	
	public String getWavelength() {
		return this.wavelength;
	}
	
	public int getNumOfConcentrations() {
		return this.workingConcentrationColumns.size();
	}	
	
}
