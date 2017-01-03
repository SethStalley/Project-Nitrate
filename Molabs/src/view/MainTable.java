package view;

import java.awt.Component;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import controller.Controller;
import model.Calibration;
import validation.Validation;
import values.Strings;

public class MainTable extends CustomTable {
	
	private int lastBlankRow = 1;
	
	public MainTable(SortableJTableModel model, Controller controller){
		super(model, controller);
	}

	@Override
	public void addRow(Object pFile) {
		File file = (File)pFile;
		Date key = controller.addFile(file.getAbsolutePath());
		renderNewFile(key);
	}
	
	private void addRow(Object[] object) {
		model.addRow(object);
		
		//render date's using a custom format
		this.getColumnModel().getColumn(DATE_INDEX).setCellRenderer(new CellRenderDateAsYYMMDD());
		this.getColumnModel().getColumn(TIME_INDEX).setCellRenderer(new CellRenderDateAsTimeOfDay());
		
		//fill out absorbance values
		Hashtable<Integer, String> waveLengths = controller.getMainTableWavelengths();
		
		Enumeration<Integer> keys = waveLengths.keys();
		
		while(keys.hasMoreElements()) {
			Integer column = keys.nextElement();
			String wavelength = waveLengths.get(column);
			String absorbance = controller.getAbsorbance(wavelength, (Date) getValueAt(getRowCount()-1, DATE_INDEX));
			
			if (absorbance != null)
				this.setValueAt(absorbance, getRowCount()-1, column);
		}
		
		//sort table by date
		this.model.sortAddedRowByDate(DATE_INDEX);
		
		//add our dropdown options
		addDropdowns();
	}

	
	/*
	 * Renders just added file within model
	 */
	private void renderNewFile(Date key) {
		String name = controller.getFileName(key);
		String type = controller.getFileType(key);
		Date date = key;
		
		this.addRow(new Object[]{name,date, date, type});
	}

	@Override
	public void addBlankRow() {
		Date date = new Date();
		this.addRow(new Object[]{"Custom Row " + this.lastBlankRow++ ,date, date, Strings.SAMPLE});;
	}

	@Override
	public Object getColumnValues(Integer column) {
		ArrayList<Object> result = new ArrayList<Object>();
		for(int row = 0; row < this.model.getRowCount(); row++){
			result.add(this.model.getValueAt(row, column));
		}
		return result;
	}

	@Override
	public void addColumn(Object header, Object[] columns) {
		model.addColumn(header, columns);
		//add our dropdown options
		addDropdowns();
				
		//sort table by date
		this.model.sortAddedRowByDate(DATE_INDEX);
	}
	
	public void calculateConcentrations(int key) {
		// could receive a calibration instead, or a key to that calibration
		if(this.selectedColumn < 0){
			JOptionPane.showMessageDialog(null, Strings.ERROR_SELECT_ABSORBANCE_COLUMN);
			return;
		}
		@SuppressWarnings("unchecked")
		ArrayList<String> listAbsorbance = (ArrayList<String>) getColumnValues(this.selectedColumn);
		ArrayList<Double> concentrations = new ArrayList<Double>();
		for(String absorbance : listAbsorbance){
			concentrations.add(controller.getConcentration(key, Double.parseDouble(absorbance)));
		}
		addColumn("Concentration("+controller.getCalibrationData(key).getWavelength()+")",
				concentrations.toArray());
		controller.removeWorkingCalibration(key, this.model.getColumnCount()-1);
	}
	
	
	public void addAbsorbanceColumnFromWavelength(String wavelength) {
		String message = Validation.validWavelength(wavelength);
		
		if (getRowCount() <= 0) {
			message = "There are currently no working files.";
		}
		
		if(message == null){
			ArrayList<Date> keys =  (ArrayList<Date>) this.getColumnValues(DATE_INDEX);
			ArrayList<String> absorbances = new ArrayList<String>();
			
			for (Date key : keys) {
				String absorbance = controller.getAbsorbance(wavelength, key);
				
				if (absorbance == null) {
					absorbance = "";
				}
				absorbances.add(absorbance);
			}
			
			this.addColumn("Absorbance("+wavelength+")", absorbances.toArray());
			
			//save this as a working wavelength in model
			controller.addWorkingWavelength(this.getColumnCount()-1, wavelength);
		}
		else{
			JOptionPane.showMessageDialog(null, message);
		}
	}
	@Override
	public void actionButton(){
		ArrayList<Double> listAbsorbance = getStdValuesFromColumn(selectedColumn);
    	ArrayList<Double> listConcentration = getStdValuesFromColumn(Strings.CONCENTRATION_COLUMN_INDEX);
 
    	if(listConcentration.size() > 1) {
    		controller.addCalibration(listAbsorbance, listConcentration, getWaveLength(selectedColumn));
    	}else{
    		JOptionPane.showMessageDialog(null, Strings.ERROR_NEW_CALIBRATION);
    	}
	}
	
	private ArrayList<Double> getStdValuesFromColumn(int index) {
		DefaultTableModel model = (DefaultTableModel) this.getModel();
		ArrayList<Double> values = new ArrayList<Double>();
		
		int numRows = this.getSelectedRowCount();
		int[] rows = this.getSelectedRows();
		
		for (int i=0; i<numRows; i++ ) {
			//if STD file type
			Object value = model.getValueAt(rows[i], Strings.TYPE_COLUMN_INDEX);
			if (value != null && value.toString().equals(Strings.STD)) {
				
				if (model.getValueAt(rows[i], index) != null) {
					values.add(Double.parseDouble(model.getValueAt(rows[i], index).toString()));
				}
				else {
					//error value no inserted
					JOptionPane.showMessageDialog(null, "No value at row #" + i + " column #" + index);
					break;
				}
			}
		}
		
		return values;
	}
	private String getWaveLength(int column){	
		String headerValue = this.columnModel.getColumn(column).getHeaderValue().toString();	
		return (String) headerValue.subSequence(headerValue.indexOf('(')+1, headerValue.lastIndexOf(')'));
	}
	

	@Override
	public void addDropdowns() {
		TableColumn typeColumn = getColumnModel().getColumn(values.Strings.TYPE_COLUMN_INDEX);
	    
    	JComboBox<String> comboBox = new JComboBox<String>();
    	comboBox.addItem(values.Strings.SAMPLE);
    	comboBox.addItem(values.Strings.STD);
    	comboBox.setEditable(true);
    	comboBox.setFocusable(false);
    	
    	typeColumn.setCellEditor(new DefaultCellEditor(comboBox));
	}



}
