package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.JTextComponent;

import controller.Controller;
import model.WorkingWavelength;
import validation.Validation;
import values.Strings;
import values.rightclickIdentifier;

public class MainTable extends CustomTable {
	
	private int lastBlankRow = 1;
	
	public MainTable(SortableJTableModel model, Controller controller){
		super(model, controller);
		resizeColumns();
		//setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
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
		Hashtable<Integer, WorkingWavelength> waveLengths = controller.getMainTableWavelengths();
		
		Enumeration<Integer> keys = waveLengths.keys();
		
		while(keys.hasMoreElements()) {
			Integer column = keys.nextElement();
			WorkingWavelength wavelength = waveLengths.get(column);
			String absorbance = controller.getAbsorbance(wavelength.getWavelength(), (Date) getValueAt(getRowCount()-1, DATE_INDEX));
			
			if (absorbance != null) {
				this.setValueAt(absorbance, getRowCount()-1, column);
			
				//add concentration columns
				Enumeration<Integer> concentrationKeys = wavelength.getWokringConcentrationColumns().keys();
				while (concentrationKeys.hasMoreElements()) {
					column = concentrationKeys.nextElement();
					Double concentrationValue = wavelength.getWokringConcentrationColumns().get(column).getConcentration( Double.parseDouble(absorbance));
		
					this.setValueAt(new DecimalFormat("#.######").format(concentrationValue), getRowCount()-1, column);
				}
			}
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

	public void addBlankRow() {
		Date date = new Date();
		String name = "Custom Row " + this.lastBlankRow++;
		this.addRow(new Object[]{name,date, date, Strings.SAMPLE});;
		controller.addCustomRow(name, date);
	}

	public void addColumn(Object header, Object[] columns) {
		model.addColumn(header, columns);
		//add our dropdown options
				
		//sort table by date
		this.model.sortAddedRowByDate(DATE_INDEX);
		selectedColumn = -1;
		this.model.fireTableStructureChanged();
		resizeColumns();
		addDropdowns();
	}
	
	public void calculateConcentrations(Date key) {
		String wavelength = controller.getCalibrationData(key).getWavelength();
		System.out.println(wavelength);
		int absorbanceIndex = controller.getAbsorbanceColumnIndex(wavelength);
		
		if (absorbanceIndex > 0) {
			if(!this.controller.concentrationColumnExists(key)) {
				
				ArrayList<String> listAbsorbance = (ArrayList<String>) getColumnValues(absorbanceIndex);
				ArrayList<String> concentrations = new ArrayList<String>();
				for(String absorbance : listAbsorbance){
					Double absorbanceValue = Double.parseDouble(absorbance);
					Double concentrationValue = controller.getConcentration(key, absorbanceValue);
					
					concentrations.add(new DecimalFormat("#.######").format(concentrationValue));
				}
			
				addColumn("Concentration("+wavelength+")",
						concentrations.toArray());
				
				//move column to desired place
				getColumnModel().moveColumn(getColumnCount()-1, absorbanceIndex+controller.getNumberWorkingConcentrations(absorbanceIndex)+1);
				controller.addWorkingCalibration(absorbanceIndex,key);
				
			} else { 
				JOptionPane.showMessageDialog(null, Strings.ERROR_COCENTRATION_EXISTS);
			}
		} else {
			JOptionPane.showMessageDialog(null, Strings.ERROR_ABSORBANCE_COLUMN_MISSING);
		}
	}
	
	
	
	public void addAbsorbanceColumnFromWavelength(String wavelength) {
		String message = Validation.validWavelength(wavelength);
		
		if(controller.checkForWorkingWavelength(wavelength)){
			message = "That wavelength was previously selected. Please choose a different wavelength.";
		}
		if (getRowCount() <= 0) {
			message = "There are currently no working files.";
		}
		
		if(message == null){
			ArrayList<Date> keys =  (ArrayList<Date>) this.getColumnValues(DATE_INDEX);
			ArrayList<String> absorbances = new ArrayList<String>();
			
			for (Date key : keys) {
				String absorbance = controller.getAbsorbance(wavelength, key);
				
				if (absorbance == null && !controller.getFileName(key).contains("Custom")) {
					absorbance = "";
					JOptionPane.showMessageDialog(null, "No absorbance value was found for that wavelength. Please choose different wavelength.");
					return;
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
	    	ArrayList<Date> fileKeys = getSelectedFileKeys(Strings.MAINTABLE_COLUMN_DATE);
	    	
		
	    	if(listConcentration.size() > 1) {
	    		controller.addCalibration(fileKeys, listAbsorbance, listConcentration, getWaveLength(selectedColumn));
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
			if (isSTDRow(rows[i])) {
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
	
	/**
	 * Returns all keys of selected rows.
	 * @return ArrayList<Date> of the rows. 
	 */
	private ArrayList<Date> getSelectedFileKeys(int index) {
		DefaultTableModel model = (DefaultTableModel) this.getModel();
		ArrayList<Date> values = new ArrayList<Date>();
		
		int numRows = this.getSelectedRowCount();
		int[] rows = this.getSelectedRows();
		
		for (int i=0; i<numRows; i++ ) {
			if (isSTDRow(rows[i])) {
				
				if (model.getValueAt(rows[i], index) != null) {
					values.add((Date) model.getValueAt(rows[i], index));
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
	
	private boolean isSTDRow(int index) {
		//if STD file type
		Object value = model.getValueAt(index, Strings.TYPE_COLUMN_INDEX);
		if (value != null && value.toString().equals(Strings.STD)) {
			return true;
		}
		return false;
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
	
	
	public void highlightLightRowsRelatedToConcentration(String wavelength, ArrayList<Date> keys) {
		clearSelection();
		
		for (int i=0; i< getRowCount();i++) {
			Date rowKey = (Date) getValueAt(i, Strings.MAINTABLE_COLUMN_DATE);
			if (keys.contains(rowKey)) {
				this.addRowSelectionInterval(i, i);
		
    			this.addColumnSelectionInterval(0, Strings.CONCENTRATION_COLUMN_INDEX);
    			int absorbanceIndex = controller.getAbsorbanceColumnIndex(wavelength);
    			this.addColumnSelectionInterval(absorbanceIndex, absorbanceIndex);
			}
		}
	}


	@Override
	public void rightClickAction(MouseEvent evt) {
		int selectedColumn = this.columnAtPoint(evt.getPoint());
		rightclickIdentifier type;
		if(getSelectedHeader(selectedColumn).substring(0, 1).equals("A"))
			type = rightclickIdentifier.ABSORBANCE;
		else
			type = rightclickIdentifier.CONCENTRATION;
		
		if (selectedColumn > Strings.CONCENTRATION_COLUMN_INDEX) {
			PopUpMenu menu = new PopUpMenu(controller,type, selectedColumn);
			menu.show(evt.getComponent(), evt.getX(), evt.getY());
		}
	}

	@Override
	public void leftClickAction(MouseEvent evt) {
		int selectedColumn = this.columnAtPoint(evt.getPoint());
		DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
		
		//clear last selection
		if (this.selectedColumn != -1 && this.selectedColumn < this.getColumnModel().getColumnCount()){
			TableCellRenderer oldHeader = this.getColumnModel().getColumn(0).getHeaderRenderer();
					
			this.getColumnModel().getColumn(this.selectedColumn).setHeaderRenderer(oldHeader);
					
				}
				
			headerRenderer.setBackground(new Color(15, 110, 135));
		    headerRenderer.setForeground(Color.white); // white foreground
		    headerRenderer.setFont(new Font("Roboto Medium", Font.BOLD, 14));
		    headerRenderer.setHorizontalAlignment( JLabel.CENTER );
		        
				
			if (selectedColumn > Strings.CONCENTRATION_COLUMN_INDEX) {
				this.selectedColumn = selectedColumn;
				String headerValue = this.getSelectedHeader(this.selectedColumn);
				if(headerValue.substring(0, 1).equals("A")){
					this.getColumnModel().getColumn(this.selectedColumn).setHeaderRenderer(headerRenderer);
				}
				else{
					this.selectedColumn = -1;
				}
			} else {
				this.selectedColumn = -1;
			}		
	}
	
	@Override
	public void deleteColumn(int index){
		model.removeColumn(this.convertColumnIndexToModel(index));
		resizeColumns();
	}


}
