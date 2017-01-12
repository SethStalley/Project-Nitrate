package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import values.Strings;


public class MainTable extends JSON_Exportable{
	private int absorbanceStartColumn = Strings.CONCENTRATION_COLUMN_INDEX+1;
	private Hashtable<String,TextFile> files;
	private ArrayList<WorkingWavelength> workingWavelength;
	
	public MainTable() {
		this.files = new Hashtable<String,TextFile>();
		this.workingWavelength = new ArrayList<>();
	}
	
	/*
	 * Parses and stores the data from a valid textFile containing spectrometer data.
	 */
	public Date addFile(String path) {
		TextFile txtData = new TextFile(path);
		
		if(txtData.getDate() != null && this.files.get(txtData.getDate()) == null) {
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(txtData.getDate());
			this.files.put(date, txtData);
			return txtData.getDate();
		}
		
		return null;
	}
	
	public boolean removeFile(Date key) {
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(key);
		return this.files.remove(time) != null;
	}
	
	public boolean addRow(String name, Date date) {
		TextFile file = new TextFile(name, date);
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date);
		return this.files.put(time, file) == null;
	}
	
	public void addWorkingWavelength(String wavelength) {
		this.workingWavelength.add(new WorkingWavelength(wavelength));
	}
	
	public boolean removeWorkingConcentrationColumn(int column) {
		column %= absorbanceStartColumn;
		
		for (int i=0; i<workingWavelength.size();i++) {
			column--;
			WorkingWavelength ww = workingWavelength.get(i);
			int numColumns = ww.workingConcentrationColumns.size();
			if (numColumns +i< column) {
				column -= numColumns;
				continue;
			} else{
				ww.workingConcentrationColumns.remove(column);
				return true;
			}
		}
	
		return false;
	}
	
	public WorkingWavelength getWavelengthWithWavelength(String wavelength){
		for(WorkingWavelength ww : workingWavelength){
			if(ww.getWavelength().equals(wavelength)){
				return ww;
			}
		}
		return null; // should not happen
	}
	
	public boolean removeWorkingWavelength(int index) {
		WorkingWavelength ww = getWorkingWavelengthFromViewIndex(index);
		
		if (ww != null) {
			workingWavelength.remove(ww);
		}
		return false;
	}
	
	public boolean checkForWorkingWavelength(String wavelength){
		for(WorkingWavelength ww : this.workingWavelength){
			if (ww.getWavelength().equals(wavelength)){
				return true;
			}
		}
		return false;
	}

	
	public Hashtable<String,TextFile> getAllFiles() {
		return this.files;
	}
	
	public TextFile getFile(Date dateKey) {
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(dateKey);
		return this.files.get(time);
	}
	
	public ArrayList<WorkingWavelength> getWorkingWaveLengths() {
		return this.workingWavelength;
	}

	public boolean addWorkingConcentration(int absorbanceKey, Calibration cal) {
		WorkingWavelength wavelength = getWorkingWavelengthFromViewIndex(absorbanceKey);
		
		if (cal != null && wavelength != null) {
			wavelength.addWorkingConcentration(cal);
			return true;
		}
		return false;
	}
	
	private WorkingWavelength getWorkingWavelengthFromViewIndex(int viewIndex) {
		viewIndex %= absorbanceStartColumn;
		
		for (int i=0; i<workingWavelength.size(); i++){
			WorkingWavelength ww = workingWavelength.get(i);
			
			if (i == viewIndex) {
				return ww;
			}
			
			viewIndex -= ww.getNumOfConcentrations();
		}
	
		return null;
	}

	public int getAbsorbanceColumnIndex(String wavelength) {
		int result = absorbanceStartColumn;
		
		for (int i=0 ;i<workingWavelength.size();i++) {
			WorkingWavelength ww = workingWavelength.get(i);
			if (ww.getWavelength().endsWith(wavelength)) {
				return i + result;
			}
			
			result += ww.getNumOfConcentrations();
		}
		
		return -1;
	}

	public boolean doesConcentrationColumnExist(Date key) {
		for (WorkingWavelength ww : workingWavelength) {
			for (Calibration cal : ww.workingConcentrationColumns) {
				if (cal.getDate().compareTo(key) == 0 ){
					return true;
				}
			}
		}
		
		return false;
	}

	public WorkingWavelength getAbsorbanceColumn(int absorbanceIndex) {
		return getWorkingWavelengthFromViewIndex(absorbanceIndex);
	}
	
}
