package view;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import controller.Controller;

public abstract class CustomTable extends JTable {
	
	protected final int DATE_INDEX = 1;
	protected final int TIME_INDEX = 2;	
	private static final int headerHeigth = 28;
	protected int selectedColumn;
	protected Controller controller;
	protected SortableJTableModel model;
	
	public CustomTable(SortableJTableModel model, Controller controller) {
		this.setRowSelectionAllowed(true);
		createTableHeaders();
		setModel(model);
		this.model = model;
		this.controller = controller;
		selectedColumn = -1;
	}
	
	private void createTableHeaders() {
		JTableHeader header = getTableHeader();
        header.setBackground(new Color(236,134,50)); //orange
        header.setForeground(Color.white); // white foreground
        header.setPreferredSize(new Dimension((int) header.getPreferredSize().getWidth(),headerHeigth)); //increase header height
        header.setOpaque(false); //let the table display the header
    }
	
	public abstract void addRow(Object obj);
	public abstract void addColumn(Object header, Object[] columns);
	
	public abstract Object getColumnValues(Integer colum);
	
	public abstract void addDropdowns(); 

	public void deleteSelectedFiles() {
		int[] rows = this.getSelectedRows();
		
		for (int index : rows) {
			deleteFile(index);
		}
	}
	
	private void deleteFile(int index) {
		Date key = (Date) this.getValueAt(index, DATE_INDEX);
		this.controller.removeFile(key);
		((DefaultTableModel) this.getModel()).removeRow(index);
	}
	
	public void deleteSelectedCalibrations() {
		int[] rows = this.getSelectedRows();
		
		for (int index : rows) {
			deleteCalibration(index);
		}
	}
	
	private void deleteCalibration(int index) {
		
			((DefaultTableModel) this.getModel()).removeRow(index);
			this.controller.removeCalibration(index);

	}

	public abstract void addBlankRow();
	
	public abstract void actionButton(); //calibrate or calculate calibration
	
}
