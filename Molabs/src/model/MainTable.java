package model;

import java.util.Date;
import java.util.Hashtable;


public class MainTable extends JSON_Exportable{
	
	private Hashtable<Date,TextFile> files;
	private Hashtable<Integer, WorkingWavelength> workingWavelength;
	
	public MainTable() {
		this.files = new Hashtable<Date,TextFile>();
		this.workingWavelength = new Hashtable<Integer, WorkingWavelength>();
	}
	
	/*
	 * Parses and stores the data from a valid textFile containing spectrometer data.
	 */
	public Date addFile(String path) {
		TextFile txtData = new TextFile(path);
		
		if(txtData.getDate() != null && this.files.get(txtData.getDate()) == null) {
			this.files.put(txtData.getDate(), txtData);
			return txtData.getDate();
		}
		
		return null;
	}
	
	public boolean removeFile(Date key) {
		return this.files.remove(key) != null;
	}
	
	public boolean addRow(String name, Date date) {
		TextFile file = new TextFile(name, date);
		return this.files.put(date, file) == null;
	}
	
	public boolean addWorkingWavelength(int index, String wavelength) {
		if (!this.workingWavelength.contains(index)) {
			this.workingWavelength.put(index, new WorkingWavelength(index, wavelength));
			return true;
		}
		return false;
	}
	
	public boolean removeWorkingWavelength(int index) {
		if(this.workingWavelength.contains(index)) {
			this.workingWavelength.remove(index);
			return true;
		}
		return false;
	}
	public boolean checkForWorkingWavelength(String wavelength){
		for(WorkingWavelength ww : this.workingWavelength.values()){
			if (ww.getWavelength().equals(wavelength)){
				return true;
			}
		}
		return false;
	}
	
	
	
	public Hashtable<Date,TextFile> getAllFiles() {
		return this.files;
	}
	
	public TextFile getFile(Date dateKey) {
		return this.files.get(dateKey);
	}
	
	public Hashtable<Integer, WorkingWavelength> getWorkingWaveLengths() {
		return this.workingWavelength;
	}

}
