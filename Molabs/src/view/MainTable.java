package view;

import java.awt.Component;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import controller.Controller;
import validation.Validation;
import values.Strings;

public class MainTable extends CustomTable {
	
	private int lastBlankRow = 1;
	private SortableJTableModel model;
	
	public MainTable(SortableJTableModel model, Controller controller){
		super(model, controller);
		this.model = model;
	}

	@Override
	public void addRow(File file) {
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
	}
	
	public void addAbsorbanceColumnFromWavelength(String wavelength) {
		String message = Validation.validWavelength(wavelength);
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



}
