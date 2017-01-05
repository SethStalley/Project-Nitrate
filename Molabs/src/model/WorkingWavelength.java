package model;

import java.util.ArrayList;

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
	
	public ArrayList<Calibration> getWokringConcentrationColumns() {
		return this.workingConcentrationColumns;
	}
	
	public boolean removeWorkingConcentration(int index) {
		if (this.workingConcentrationColumns.get(index) != null) {
			this.workingConcentrationColumns.remove(index);
			return true;
		}
		return false;
	}
	
	public String getWavelength() {
		return this.wavelength;
	}
	
	public int getNumOfConcentrations() {
		return this.workingConcentrationColumns.size();
	}	
	
}
