package model;

import java.util.Hashtable;

public class WorkingWavelength {
	private int absorbanceIndex;
	private String wavelength;
	Hashtable<Integer, Calibration> workingConcentrationColumns;
	
	public WorkingWavelength(int absorbanceIndex, String wavelength) {
		this.workingConcentrationColumns = new Hashtable<Integer,Calibration>();
		this.wavelength = wavelength;
		this.absorbanceIndex = absorbanceIndex;
	}
	
	public boolean addWorkingConcentration(Calibration calibration) {
		int insertIndex = absorbanceIndex + workingConcentrationColumns.size() + 1;
		
		if (!this.workingConcentrationColumns.contains(insertIndex)) {
			this.workingConcentrationColumns.put(insertIndex, calibration);
			return true;
		}
		return false;
	}
	
	public Calibration getWorkingCalibration(int index) {
		return this.workingConcentrationColumns.get(index);
	}
	
	public Hashtable<Integer, Calibration> getWokringConcentrationColumns() {
		return this.workingConcentrationColumns;
	}
	
	public boolean removeWorkingCalibration(int index) {
		if (this.workingConcentrationColumns.contains(index)) {
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
