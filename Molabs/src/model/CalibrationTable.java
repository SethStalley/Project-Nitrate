package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class CalibrationTable extends JSON_Exportable{

	private Hashtable<String,Calibration> calibrations;
	
	public CalibrationTable() {
		this.calibrations = new Hashtable<String,Calibration>();
	}
	
	public Calibration addCalibration(ArrayList<Date> fileKeys,
			ArrayList<Double> absorbances, ArrayList<Double> concentrations,
			String wavelength) {
		Calibration cal = new Calibration(fileKeys, absorbances, concentrations, wavelength);
		
		if (cal.getWavelength() != null && !Double.isNaN(cal.getPearson())) {
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(cal.getDate());
			this.calibrations.put(date, cal);
			return cal;
		}
		
		return null;
	}
	
	public boolean removeCalibration(Date index){
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(index);
		if(this.calibrations.containsKey(date)) {
			this.calibrations.remove(date);
			return true;
		}
		return false;
	}
	
	public Calibration getCalibration(Date index) {
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(index);
		return this.calibrations.get(date);	
	}
	
	public Hashtable<String,Calibration> getAllCalibration() {
		return this.calibrations;
	}
	
	public ArrayList<Calibration> getAllCalibrations() {
		Enumeration<String> keys = this.calibrations.keys();
		ArrayList<Calibration> calibrations = new ArrayList<Calibration>();
		
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			calibrations.add(this.calibrations.get(key));
		}
		
		return calibrations;
	}
	
}
