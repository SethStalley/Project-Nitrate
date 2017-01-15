package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import controller.Controller;
import model.Calibration;
import values.Strings;
import values.rightclickIdentifier;
import model.WorkingWavelength;

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
		resizeColumns();
	}
	
	protected void clearTable() {
		//clear the table
		model.getDataVector().removeAllElements();
		
		int numOfColumns = model.getColumnCount();
		
		for (int i=numOfColumns-1; i > Strings.CONCENTRATION_COLUMN_INDEX; i--) {
			model.removeColumn(i);
		}
		
		model.fireTableDataChanged();
	}
	
	private void createTableHeaders() {
		JTableHeader header = getTableHeader();
        header.setBackground(new Color(236,134,50)); //orange
        header.setForeground(Color.white); // white foreground
        header.setPreferredSize(new Dimension((int) header.getPreferredSize().getWidth(),headerHeigth)); //increase header height
        header.setOpaque(false); //let the table display the header
    }
	

	public abstract void addRow(Object value);
	public abstract void addDropdowns();

	public void deleteSelectedFiles() {
		int[] rows = this.getSelectedRows();
		
		for (int index : rows) {
			deleteFile(index);
			deleteSelectedFiles();
			break;
		}
	}
	
	private void deleteFile(int index) {
		Date key = getDateFromRow(index);
		this.controller.removeFile(key);
		((DefaultTableModel) this.getModel()).removeRow(index);
	}
	
	public void deleteSelectedCalibrations() {
		int[] rows = this.getSelectedRows();
		
		if (rows.length > 0){
			for (int index : rows) {
				deleteCalibration(index);
			}
		}
		else{
			JOptionPane.showMessageDialog(null, Strings.ERROR_NO_CALIBRATION);
		}
	}
	
	public void deleteCalibrationsByWavelength(String wavelength){
		for(int row = 0; row < this.model.getRowCount(); row++){
			if (this.model.getValueAt(row, Strings.CALIBRATIONTABLE_COLUMN_WAVELENGTH).equals(wavelength)){
				deleteCalibration(row);
			}
		}
	}
	
	private void deleteCalibration(int index) {
		Date date = getDateFromRow(index);
		Calibration calibration = controller.getCalibrationData(date);
		controller.removeWorkingConcentration(date.toString(), calibration.getWavelength());
		this.controller.removeCalibration(date);
		((DefaultTableModel) this.getModel()).removeRow(index);
	}
	
	protected Date getDateFromRow(int rowIndex) {
		return (Date) this.getValueAt(rowIndex, DATE_INDEX);
	}
	
	public Object getColumnValues(Integer column) {
		ArrayList<Object> result = new ArrayList<Object>();
		for(int row = 0; row < this.model.getRowCount(); row++){
			result.add(this.model.getValueAt(row, column));
		}
		return result;
	}
	
	public abstract void actionButton(); //calibrate or calculate calibration
	
	
	public String getSelectedHeader(int index){
		return (String)this.getColumnModel().getColumn(index).getHeaderValue();
	}
	
	public abstract void rightClickAction(MouseEvent evt);
	
	public abstract void leftClickAction(MouseEvent evt);
	
	protected void centerCells(){
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		for(int x=0;x<getColumnCount();x++){
	         getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
	        }
	}
	
	public void forceUpdate(){
		
	}
	public SortableJTableModel getAlternModel(){
		return this.model;
	}
	
	public abstract void resizeColumns();
	public abstract void updateFromModel();
	
}
