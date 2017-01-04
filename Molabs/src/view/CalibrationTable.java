package view;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controller.Controller;
import model.Calibration;
import values.Strings;
import values.rightclickIdentifier;

public class CalibrationTable extends CustomTable {

	public CalibrationTable(SortableJTableModel model, Controller controller) {
		super(model, controller);
	}

	@Override
	public void addRow(Object pCalibration) {
		Calibration calibration = (Calibration) pCalibration;
			
	    ((DefaultTableModel) getModel()).addRow(
	    		new Object[]{"",calibration.getDate(), calibration.getWavelength()});
	    
	    //render date format gui. That way Cell still holds a Date object.
	    this.getColumnModel().getColumn(DATE_INDEX).setCellRenderer(new CellRenderDateAsYYMMDD_TIME());
	    
	    addDropdowns();
	}

	@Override
	public void actionButton() {
	    DefaultTableModel model = (DefaultTableModel) getModel();
	    Boolean control = true;
	    for(int row = 0; row < this.model.getRowCount(); row++){
			String status = (String) this.model.getValueAt(row, Strings.STATUS_COLUMN_INDEX);
	    	if(status.equals("Active")){
	    		Date key = (Date) getValueAt(row, Strings.CALIBRATIONTABLE_COLUMN_DATE);
	    		controller.calculateConcentrations(key);
	    		control = false;
	    		break;
	    	}
	    }
	    if(control){
	    	JOptionPane.showMessageDialog(null, Strings.ERROR_NO_ACTIVE_CALIBRATION);
	    }
	}

	@Override
	public void addDropdowns() {
		TableColumn typeColumn = getColumnModel().getColumn(Strings.STATUS_COLUMN_INDEX);
	    
    	JComboBox<String> comboBox = new JComboBox<String>();
    	comboBox.addItem(" ");
    	comboBox.addItem(Strings.ACTIVE);
    	comboBox.setEditable(true);
    	comboBox.setFocusable(false);
    	comboBox.getSelectedItem().toString();
    	
    	typeColumn.setCellEditor(new DefaultCellEditor(comboBox));
	}

	@Override
	public void rightClickAction(MouseEvent evt) {
		// TODO Auto-generated method stubd
		
	}

	@Override
	public void leftClickAction(MouseEvent evt) {
		// TODO Auto-generated method stub
		
	}
	
	
}
