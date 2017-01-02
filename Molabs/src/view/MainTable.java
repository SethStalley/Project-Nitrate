package view;

import java.awt.Component;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import controller.Controller;
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
		
		//add our dropdown options
		addDropdowns();
		
		//sort table by date
		this.model.sortAddedRowByDate(DATE_INDEX);
	}

	
	/*
	 * Renders just added file within model
	 */
	private void renderNewFile(Date key) {
		String name = controller.getFileName(key);
		String type = controller.getFileType(key);
		Date date = key;
		ArrayList<String> waveLengths = controller.getMainTableWavelengths();
		
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


}
