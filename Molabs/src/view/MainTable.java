package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.EventObject;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.JTextComponent;

import controller.Controller;
import de.erichseifert.gral.data.DataTable;
import model.Calibration;
import model.WorkingWavelength;
import validation.Validation;
import values.Preferences;
import values.Strings;
import values.rightclickIdentifier;

public class MainTable extends CustomTable {
	
	private int lastBlankRow = 1;
	private boolean pasteEditable;
	private ArrayList<String[]> graphPoints;
	private DataTable graphPointsRealTime;
	
	public MainTable(SortableJTableModel model, Controller controller){
		super(model, controller);
		graphPoints = new ArrayList<String[]>();
		graphPointsRealTime = new DataTable(Long.class, Double.class);
		setupRowSorter();
	}
	
	private void setupRowSorter() {
		//Limit columns that can be sorted
		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(getModel()) {
		    @Override
		    public boolean isSortable(int column) {
		        if(column < 2)
		            return true;
		        else 
		            return false;
		    };
		};
		List<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
		
		sorter.setSortKeys(sortKeys);
		this.setRowSorter(sorter);
	}

	@Override
	public void addRow(Object pFile) {
		File file = (File)pFile;
		Date key = controller.addFile(file.getAbsolutePath());
		if(key != null){
			renderNewFile(key);
			formatRows();
		}
	}
	
	public void addRowObserver(Object pFile){
		File file = (File)pFile;
		Date key = controller.addFile(file.getAbsolutePath());
		if(key != null){
			renderNewFile(key);
			System.out.println(model.getColumnCount()-1);
			graphPointsRealTime.add(key.getTime(),Double.parseDouble((String)getValueAt(model.getRowCount()-1,controller.getCalibrationIndex())));
			formatRows();
		}
		controller.graphRealTime(graphPointsRealTime);
	}
	
	/**
	 * Updates this view from the current model.
	 * This is called after loading a save file into the model.
	 */
	public void updateFromModel() {
		clearTable();
		
		//add columns
		addHeadersFromModel();
		
		Enumeration<Date> keys = controller.getAllFileKeys();
		
		while(keys.hasMoreElements()) {
			Date key = keys.nextElement();
			renderNewFile(key);
		}
	}
	
	private void addHeadersFromModel() {
		ArrayList<WorkingWavelength> wavelengths = controller.getMainTableWavelengths();
		for (WorkingWavelength ww : wavelengths) {
			addColumn(formattedAbsorbanceHeader(ww.getWavelength()), null);
			
			ArrayList<Calibration> calibrations = ww.getWorkingConcentrationColumns();
			for (Calibration cal : calibrations) {
				addColumn(formattedConcentrationHeader(cal.getWavelength()), null);
			}
		}
	}
	
	private String formattedAbsorbanceHeader(String wavelength) {
		return "Absorbance("+wavelength+")";
	}
	
	private String formattedConcentrationHeader(String wavelength) {
		return "Concentration("+wavelength+")";
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
		ArrayList<WorkingWavelength> waveLengths = controller.getMainTableWavelengths();
		
		int column = Strings.CONCENTRATION_COLUMN_INDEX+1;
		for(WorkingWavelength ww: waveLengths) {
			String absorbance = controller.getAbsorbance(ww.getWavelength(), (Date) getValueAt(getRowCount()-1, DATE_INDEX));
	
			if (absorbance != null) {
				this.setValueAt(absorbance, getRowCount()-1, column);
				column++;
			
				//add concentration columns
				for (Calibration cal : ww.getWorkingConcentrationColumns()) {
					Double concentrationValue = cal.getConcentration( Double.parseDouble(absorbance));
					this.setValueAt(new DecimalFormat("#.######").format(concentrationValue), getRowCount()-1, column);
					column++;
				}
			}
		}
		
	}
	
	private void formatRows(){
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
		String stdConcentration = controller.getStdConcentration(key);
		
		this.addRow(new Object[]{name,date, date, type, stdConcentration});
	}

	public void addBlankRow() {
		Date date = new Date();
		String name = "Custom Row " + this.lastBlankRow++;
		this.addRow(new Object[]{name,date, date, Strings.SAMPLE});;
		controller.addCustomRow(name, date);
		addDropdowns();
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
	
	private void modifyVectorModel(int index){
		for (Object obj : this.model.getDataVector()) {
	        Vector obj2 = (Vector) obj;
	        obj2.add(index, obj2.get(obj2.size()-1));
	        obj2.remove(obj2.size()-1);
	        
	    }
		int columns = model.getColumnCount();
		Object[] arr = new Object[columns];
		
		for(int i = 0; i<model.getColumnCount(); i++){
			arr[i] = model.getColumnName(i);
		}
		
		Object temp = arr[columns-1];
		Object[] result = new Object[arr.length];
	    for(int i = 0; i < index; i++)
	        result[i] = arr[i];
	    result[index] = temp;
	    for(int i = index + 1; i < arr.length; i++)
	        result[i] = arr[i - 1];
	    
		
		model.setColumnIdentifiers(result);
	}
	
	public void calculateConcentrations(Date key) {
		String wavelength = controller.getCalibrationData(key).getWavelength();
		int absorbanceIndex = controller.getAbsorbanceColumnIndex(wavelength);
		
		if (absorbanceIndex > 0) {
			if(!this.controller.concentrationColumnExists(key)) {
				
				ArrayList<String> listAbsorbance = (ArrayList<String>) getColumnValues(absorbanceIndex);
				ArrayList<String> concentrations = new ArrayList<String>();
				for(String absorbance : listAbsorbance){
					if (absorbance != null){
						Double absorbanceValue = Double.parseDouble(((String)absorbance).replace(',', '.'));
						Double concentrationValue = controller.getConcentration(key, absorbanceValue);
						concentrations.add(new DecimalFormat("#.######").format(concentrationValue));
					}
					else{
						concentrations.add("");
					}
				}
			
				addColumn("Concentration("+wavelength+")",
						concentrations.toArray());
				
				controller.addWorkingConcentration(absorbanceIndex,key);
				//move column to desired place
				
				int insertPosition = absorbanceIndex+controller.getNumberWorkingConcentrations(absorbanceIndex);
				getColumnModel().moveColumn(getColumnCount()-1, insertPosition);
				modifyVectorModel(insertPosition);
				resizeColumns();
				addDropdowns();
				
			} else { 
				JOptionPane.showMessageDialog(null, Strings.ERROR_COCENTRATION_EXISTS,"Error",JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, Strings.ERROR_ABSORBANCE_COLUMN_MISSING,"Error",JOptionPane.INFORMATION_MESSAGE);
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
					JOptionPane.showMessageDialog(null, Strings.ERROR_NO_ABSORBANCE,"Error",JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				absorbances.add(absorbance);
			}
			
			this.addColumn("Absorbance("+wavelength+")", absorbances.toArray());
			
			//save this as a working wavelength in model
			controller.addWorkingWavelength(wavelength);
		}
		else{
			JOptionPane.showMessageDialog(null, message,"Error",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	@Override //calibrate
	public void actionButton(){

		if(selectedColumn == -1)
			JOptionPane.showMessageDialog(null, Strings.ERROR_NO_ABSORBANCE_SELECTED,"Error",JOptionPane.INFORMATION_MESSAGE);
		else{
		
			ArrayList<Double> listAbsorbance = getStdValuesFromColumn(selectedColumn);
	    	ArrayList<Double> listConcentration = getStdValuesFromColumn(Strings.CONCENTRATION_COLUMN_INDEX);
	    	ArrayList<Date> fileKeys = getSelectedFileKeys(Strings.MAINTABLE_COLUMN_DATE);
	    	
		
	    	if(listConcentration.size() > 1) {
	    		controller.addCalibration(fileKeys, listAbsorbance, listConcentration, getWaveLength(selectedColumn));
	    	}else{
	    		JOptionPane.showMessageDialog(null, Strings.ERROR_NEW_CALIBRATION,"Error",JOptionPane.INFORMATION_MESSAGE);
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
					String valueStr = ((String)model.getValueAt(rows[i], index).toString()).replace(',', '.');
					values.add(Double.parseDouble(valueStr));
				}
				else {
					//error value no inserted
					JOptionPane.showMessageDialog(null, "No value at row #" + i + " column #" + index,"Error",JOptionPane.INFORMATION_MESSAGE);
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
					JOptionPane.showMessageDialog(null, "No value at row #" + i + " column #" + index,"Error",JOptionPane.INFORMATION_MESSAGE);
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

    	for(int i = Strings.CONCENTRATION_COLUMN_INDEX; i<this.model.getColumnCount(); i++){
    		this.getColumnModel().getColumn(i).setCellEditor(new CellNumberEditor());
    	}
	}
	public void resizeColumns(){
		getColumnModel().getColumn(0).setMinWidth(Preferences.DATE_COLUMN_WIDTH);
		
		for(int i = 1; i<Strings.NUMBER_DEFAULT_COLUMNS; i++){
			getColumnModel().getColumn(i).setMinWidth(Preferences.DEFAULT_COLUMN_WIDTH);
		}

		int contColumns = getModel().getColumnCount();
		for(int i = Strings.NUMBER_DEFAULT_COLUMNS; i<contColumns; i++){
			getColumnModel().getColumn(i).setPreferredWidth(Preferences.ADDED_COLUMN_WIDTH);
		}

		this.getTableHeader().setPreferredSize(new Dimension(10000,32)); //no tengo idea... pero si lo quito los headers no se mueven
		
		//centers cells
		
		centerCells();
		
		//resets date format
		this.getColumnModel().getColumn(DATE_INDEX).setCellRenderer(new CellRenderDateAsYYMMDD());
		this.getColumnModel().getColumn(TIME_INDEX).setCellRenderer(new CellRenderDateAsTimeOfDay());
	}
	
	
	public void highlightLightRowsRelatedToConcentration(String wavelength, ArrayList<Date> keys) {
		clearSelection();
		int absorbanceIndex = -1;
		for (int i=0; i< getRowCount();i++) {
			Date rowKey = (Date) getValueAt(i, Strings.MAINTABLE_COLUMN_DATE);
			if (keys.contains(rowKey)) {
				this.addRowSelectionInterval(i, i);
		
    			this.addColumnSelectionInterval(0, Strings.CONCENTRATION_COLUMN_INDEX);
    			absorbanceIndex = controller.getAbsorbanceColumnIndex(wavelength);
    			this.addColumnSelectionInterval(absorbanceIndex, absorbanceIndex);
			}
		}
		
		//paint of column header and selection
		if(selectedColumn != -1 && this.selectedColumn < this.getColumnModel().getColumnCount()){
			TableCellRenderer oldHeader = this.getColumnModel().getColumn(0).getHeaderRenderer();
			
			this.getColumnModel().getColumn(this.selectedColumn).setHeaderRenderer(oldHeader);
		}
		selectedColumn = absorbanceIndex;
		
		this.getColumnModel().getColumn(this.selectedColumn).setHeaderRenderer(getSelectedHeaderRenderer());
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
		
		//clear last selection
		if (this.selectedColumn != -1 && this.selectedColumn < this.getColumnModel().getColumnCount()){
			TableCellRenderer oldHeader = this.getColumnModel().getColumn(0).getHeaderRenderer();
					
			this.getColumnModel().getColumn(this.selectedColumn).setHeaderRenderer(oldHeader);
					
				}
		        
				
			if (selectedColumn > Strings.CONCENTRATION_COLUMN_INDEX) {
				this.selectedColumn = selectedColumn;
				String headerValue = this.getSelectedHeader(this.selectedColumn);
				if(headerValue.substring(0, 1).equals("A")){
					this.getColumnModel().getColumn(this.selectedColumn).setHeaderRenderer(getSelectedHeaderRenderer());
				}
				else{
					this.selectedColumn = -1;
				}
			} else {
				this.selectedColumn = -1;
			}		
	}
	
	/**
	 * Do stuff with data that the user has inserted
	 */
	@Override
	public void setValueAt(Object aValue, int row, int column) {
		if(pasteEditable){
			if(model.isCellEditable(row, column)){
				super.setValueAt(aValue, row, column);
				changeTypeFromInput(aValue, row, column);
			}
		}else{
			super.setValueAt(aValue, row, column);
			changeTypeFromInput(aValue, row, column);
		}
	}
	
	private void changeTypeFromInput(Object aValue, int row, int column) {
		if (column == Strings.CONCENTRATION_COLUMN_INDEX) {
			Date key = (Date) this.getValueAt(row, DATE_INDEX);
			if (aValue == null) {
				setValueAt(Strings.SAMPLE, row, Strings.TYPE_COLUMN_INDEX);
				controller.setFileType(key, Strings.SAMPLE);
			} else {
				setValueAt(Strings.STD, row, Strings.TYPE_COLUMN_INDEX);
				controller.setFileType(key, Strings.STD);
				controller.setStdConcentration(key, aValue.toString());
			}
		}
		
		// check absrobances for custom rows
		int selectedRow = this.getSelectedRow();
		if(this.controller.getAbsorbanceColumn(column) != null  &&
				selectedRow != -1){
			Date key = (Date) this.getValueAt(selectedRow, Strings.MAINTABLE_COLUMN_DATE);
			WorkingWavelength absorbance = controller.getAbsorbanceColumn(column);
			String wavelength = absorbance.getWavelength();
			if (aValue != null) {
				String valueStr = ((String) aValue).replace(',', '.');
				controller.addManualAbsorbance(key, wavelength, (String) aValue);
				this.addConcentrationsForAbsorbance(column, absorbance.getWorkingConcentrationColumns(), this.getSelectedRow(),
						Double.parseDouble(valueStr));
			}
			else{
				controller.removeManualAbosrbance(key, wavelength);
				this.removeConcentrationsForAbsorbance(column, absorbance.getNumOfConcentrations(), this.getSelectedRow());
			}
		}
	}
	
	// for custom rows
	private void removeConcentrationsForAbsorbance(int column, int numberOfConcentrations, int row){
		for (int i = 0; i < numberOfConcentrations; i++){
			this.setValueAt("", row, column + i + 1);
		}
		addDropdowns();
	}
	// for custom rows
	private void addConcentrationsForAbsorbance(int column, ArrayList<Calibration> concentrations, int row, double absorbance){
		for(int i = 0; i < concentrations.size() ; i ++){
			Double concentration = concentrations.get(i).getConcentration(absorbance);
			concentration = (double)Math.round(concentration * 100000d) / 100000d;
			this.setValueAt(concentration, row, column + i + 1);
		}
		addDropdowns();
	}
	
	private DefaultTableCellRenderer getSelectedHeaderRenderer(){
		DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
		headerRenderer.setBackground(new Color(15, 110, 135));
	    headerRenderer.setForeground(Color.white); // white foreground
	    headerRenderer.setFont(new Font("Roboto Medium", Font.BOLD, 14));
	    headerRenderer.setHorizontalAlignment( JLabel.CENTER );
	    return headerRenderer;
	}
	
	public void deleteColumn(int index){
		index = this.convertColumnIndexToModel(index);
		super.model.removeColumn(index);
		resizeColumns();
		addDropdowns();
	}

	
	public void deleteExactColumn(int column){
		super.model.removeColumn(column);
		resizeColumns();
		addDropdowns();
	}

	public void setPasteEditable(boolean state){
		this.pasteEditable = state;

	}
	
	public DataTable getConcentrationsGraph(int index){
		DataTable data;
		if(index == -1){
			data = null;
		}else{
			graphPoints.clear();
			data = new DataTable(Long.class, Double.class);
			for(int i = 0; i<this.getModel().getRowCount(); i++){
				if(this.getValueAt(i, index) != null){
					if(this.getValueAt(i, index).getClass() == String.class && !((String)this.getValueAt(i, index)).isEmpty()){
						long time = ((Date)this.getValueAt(i, Strings.MAINTABLE_COLUMN_DATE+1)).getTime();
						data.add(time,Double.parseDouble((String)this.getValueAt(i, index)));
						String[] actual = {Long.toString(time),(String)this.getValueAt(i, index)};
						graphPoints.add(actual);
					}
					else if(this.getValueAt(i, index).getClass() == Double.class){
						long time = ((Date)this.getValueAt(i, Strings.MAINTABLE_COLUMN_DATE+1)).getTime();
						data.add(time,(Double)this.getValueAt(i, index));
						String[] actual = {Long.toString(time),((Double)this.getValueAt(i, index)).toString()};
						graphPoints.add(actual);
					}
				}
					
			}
		}
		
		return data;
	}
	
	public ArrayList<String[]> getConcentrationPoints(){
		return graphPoints;
	}
	
	public DataTable getConcentrationsGraphSelected(int index){
		DataTable data;
		int[] rows = this.getSelectedRows();
		data = new DataTable(Long.class, Double.class);
		for (int i = 0; i < rows.length; i++){
			if(this.getValueAt(rows[i], index) != null){
				if(this.getValueAt(rows[i], index).getClass() == String.class && !((String)this.getValueAt(rows[i], index)).isEmpty()){
					long time = ((Date)this.getValueAt(rows[i], Strings.MAINTABLE_COLUMN_DATE)).getTime();
					data.add(time,Double.parseDouble((String)this.getValueAt(rows[i], index)));
				}
				else if(this.getValueAt(rows[i], index).getClass() == Double.class){
					long time = ((Date)this.getValueAt(rows[i], Strings.MAINTABLE_COLUMN_DATE)).getTime();
					data.add(time,(Double)this.getValueAt(rows[i], index));
					String[] actual = {Long.toString(time),((Double)this.getValueAt(rows[i], index)).toString()};
					graphPoints.add(actual);
				}
			}
				
		}
		if(data.getRowCount()>0)
			return data;
		else
			return null;
	}
	
	public void cleanTable(){
		graphPointsRealTime.clear();
	}
	
}
