package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.EventObject;
import java.util.Hashtable;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.JTextComponent;

import controller.Controller;
import validation.Validation;
import values.Strings;

public class MainTable extends CustomTable {
	
	private int lastBlankRow = 1;
	
	public MainTable(SortableJTableModel model, Controller controller){
		super(model, controller);
		resizeColumns();
	}

	@Override
	public void addRow(Object pFile) {
		File file = (File)pFile;
		Date key = controller.addFile(file.getAbsolutePath());
		if(key != null)
			renderNewFile(key);
	}
	
	/**
	 * Overrides JTable's edit cell method. When a edits a cell with existing
	 * data, the data is overwritten instead of being appended.
	 */
	public boolean editCellAt(int row, int column, EventObject e){
        boolean result = super.editCellAt(row, column, e);
        final Component editor = getEditorComponent();
        if (editor == null || !(editor instanceof JTextComponent)) {
            return result;
        }
        
        if (e instanceof KeyEvent) {
            ((JTextComponent) editor).selectAll();
        }
        
        return result;
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
		selectedColumn = -1;
		resizeColumns();
	}
	
	public void calculateConcentrations(int key) {
		// could receive a calibration instead, or a key to that calibration
		if(this.selectedColumn < 0){
			JOptionPane.showMessageDialog(null, Strings.ERROR_SELECT_ABSORBANCE_COLUMN);
			return;
		}
		@SuppressWarnings("unchecked")
		ArrayList<String> listAbsorbance = (ArrayList<String>) getColumnValues(this.selectedColumn);
		ArrayList<String> concentrations = new ArrayList<String>();
		for(String absorbance : listAbsorbance){
			Double concentrationValue = Double.parseDouble(absorbance);
			concentrationValue = controller.getConcentration(key, concentrationValue);
			concentrations.add(new DecimalFormat("#.######").format(concentrationValue));
		}
		addColumn("Concentration("+controller.getCalibrationData(key).getWavelength()+")",
				concentrations.toArray());
		controller.addWorkingCalibration(key, this.model.getColumnCount()-1);
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
	@Override //calibrate
	public void actionButton(){
		if(selectedColumn == -1)
			JOptionPane.showMessageDialog(null, Strings.ERROR_NO_ABSORBANCE_SELECTED);
		else{
		
			ArrayList<Double> listAbsorbance = getStdValuesFromColumn(selectedColumn);
	    	ArrayList<Double> listConcentration = getStdValuesFromColumn(Strings.CONCENTRATION_COLUMN_INDEX);
	 
	    	if(listConcentration.size() > 1) {
	    		controller.addCalibration(listAbsorbance, listConcentration, getWaveLength(selectedColumn));
	    	}else{
	    		JOptionPane.showMessageDialog(null, Strings.ERROR_NEW_CALIBRATION);
	    	}
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
	private void resizeColumns(){
		for(int i = 0; i<Strings.NUMBER_DEFAULT_COLUMNS; i++){
			getColumnModel().getColumn(i).setMinWidth(Strings.DEFAULT_COLUMN_WIDTH);
		}

		int contColumns = getModel().getColumnCount();
		for(int i = Strings.NUMBER_DEFAULT_COLUMNS; i<contColumns; i++){
			getColumnModel().getColumn(i).setPreferredWidth(Strings.ADDED_COLUMN_WIDTH);
		}
		this.getTableHeader().setPreferredSize(new Dimension(10000,32)); //no tengo idea... pero si lo quito los headers no se mueven
		
		//centers cells
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		for(int x=0;x<getColumnCount();x++){
	         getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
	        }
		
		//resets date format
		
		this.getColumnModel().getColumn(DATE_INDEX).setCellRenderer(new CellRenderDateAsYYMMDD());
		this.getColumnModel().getColumn(TIME_INDEX).setCellRenderer(new CellRenderDateAsTimeOfDay());
		
	}



}
