package view;

import java.io.File;
import java.text.SimpleDateFormat;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controller.Controller;
import model.Calibration;
import values.Strings;

public class CalibrationTable extends CustomTable {

	public CalibrationTable(SortableJTableModel model, Controller controller) {
		super(model, controller);
	}

	@Override
	public void addRow(Object pCalibration) {
		Calibration calibration = (Calibration) pCalibration;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - hh:mm:ss");
			
	    ((DefaultTableModel) getModel()).addRow(
	    		new Object[]{"",dateFormat.format(calibration.getDate()), calibration.getWavelength()});
	    	
	    addDropdowns();
	}

	@Override
	public void addColumn(Object header, Object[] columns) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getColumnValues(Integer colum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addBlankRow() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionButton() {
		// Metodo para Calculate
		
	}

	@Override
	public void addDropdowns() {
		TableColumn typeColumn = getColumnModel().getColumn(Strings.STATUS_COLUMN_INDEX);
	    
    	JComboBox<String> comboBox = new JComboBox<String>();
    	comboBox.addItem(" ");
    	comboBox.addItem(Strings.ACTIVE);
    	comboBox.setEditable(true);
    	comboBox.setFocusable(false);
    	
    	typeColumn.setCellEditor(new DefaultCellEditor(comboBox));
	}
	
}
