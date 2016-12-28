package controller;

import model.CalibrationTable;
import model.MainTable;

public class Controller {
	
	private MainTable mainTable;
	private CalibrationTable calibrationTable;
	
	public Controller() {
		instanceComponents();
	}
	
	/*
	 * Create default model objects, as blanks, without data
	 */
	private void instanceComponents() {
		this.mainTable = new MainTable();
		this.calibrationTable = new CalibrationTable();
	}
	
	public void addFile(String path) {
		this.mainTable.addFile(path);
	}
	
}
