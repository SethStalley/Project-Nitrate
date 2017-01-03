package model;

import java.util.ArrayList;
import java.util.Hashtable;

public class CalibrationTable extends JSON_Exportable{

	private Hashtable<Integer,Calibration> calibrations;
	
	public CalibrationTable() {
		this.calibrations = new Hashtable<Integer,Calibration>();
	}
	
	public boolean addCalibration(ArrayList<Double> absorbances, ArrayList<Double> concentrations,
			String wavelength) {
		Integer last = calibrations.size();
		Calibration cal = new Calibration(absorbances, concentrations, wavelength);
		this.calibrations.put(last, cal);
		
		if (cal.getWavelength() != null) {
			return true;
		}
		
		return false;
	}
	
	public Calibration getCalibration(int index) {
		return this.calibrations.get(index);	
	}
	
	public Hashtable<Integer,Calibration> getAllCalibration() {
		return this.calibrations;
	}
	
	public Calibration getLastCalibration(){ //agregar en modelo
		return calibrations.get(calibrations.size()-1);
	}
	
}
