package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

public class CalibrationTable extends JSON_Exportable{

	private Hashtable<Date,Calibration> calibrations;
	
	public CalibrationTable() {
		this.calibrations = new Hashtable<Date,Calibration>();
	}
	
	public Calibration addCalibration(ArrayList<Date> fileKeys,
			ArrayList<Double> absorbances, ArrayList<Double> concentrations,
			String wavelength) {
		Calibration cal = new Calibration(fileKeys, absorbances, concentrations, wavelength);
		
		if (cal.getWavelength() != null && !Double.isNaN(cal.getPearson())) {
			this.calibrations.put(cal.getDate(), cal);
			return cal;
		}
		
		return null;
	}
	
	public boolean removeCalibration(Date index){
		if(this.calibrations.containsKey(index)) {
			this.calibrations.remove(index);
			return true;
		}
		return false;
	}
	
	public Calibration getCalibration(Date index) {
		return this.calibrations.get(index);	
	}
	
	public Hashtable<Date,Calibration> getAllCalibration() {
		return this.calibrations;
	}
	
}
