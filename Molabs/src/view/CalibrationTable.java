package view;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controller.Controller;
import model.Calibration;
import values.Preferences;
import values.Strings;
import values.rightclickIdentifier;

public class CalibrationTable extends CustomTable {
	
	ButtonGroup activeGroup;

	public CalibrationTable(SortableJTableModel model, Controller controller) {
		super(model, controller);
		activeGroup = new ButtonGroup();
		TableColumn typeColumn = getColumnModel().getColumn(Strings.STATUS_COLUMN_INDEX);
		typeColumn.setCellRenderer(new RadioButtonRenderer());
    	typeColumn.setCellEditor(new RadioButtonEditor(new JCheckBox()));
	}

	@Override
	public void addRow(Object pCalibration) {
		Calibration calibration = (Calibration) pCalibration;
		JRadioButton radio = new JRadioButton("");
		radio.setHorizontalAlignment(JRadioButton.CENTER);
		activeGroup.add(radio);
	    ((DefaultTableModel) getModel()).addRow(
	    		new Object[]{radio,calibration.getDate(), calibration.getWavelength()});
	    
	    //render date format gui. That way Cell still holds a Date object.
	    this.getColumnModel().getColumn(DATE_INDEX).setCellRenderer(new CellRenderDateAsYYMMDD_TIME());
	    
	    addDropdowns();
	}
	
	

	@Override
	public void actionButton() {
	    DefaultTableModel model = (DefaultTableModel) getModel();
	    Boolean control = true;
	    for(int row = 0; row < this.model.getRowCount(); row++){
			JRadioButton status = (JRadioButton) this.model.getValueAt(row, Strings.STATUS_COLUMN_INDEX);
	    	if(status.isSelected()){
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
		typeColumn.setCellRenderer(new RadioButtonRenderer());
    	typeColumn.setCellEditor(new RadioButtonEditor(new JCheckBox()));
    	resizeColumns();
    	centerCells();
    	this.getColumnModel().getColumn(DATE_INDEX).setCellRenderer(new CellRenderDateAsYYMMDD_TIME());
    	
    	
    	
	}
	
	@Override
	protected void centerCells(){
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		for(int x=1;x<getColumnCount();x++){
	         getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
	        }
		
		//centerRenderer.setHorizontalAlignment(JRadioButton.CENTER); // try of centering the radio button....
		//getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		
	}

	@Override
	public void rightClickAction(MouseEvent evt) {
		// TODO Auto-generated method stubd
		
	}

	@Override
	public void leftClickAction(MouseEvent evt) {
		// TODO Auto-generated method stub
		
	}
	
	public void resizeColumns(){
		getColumnModel().getColumn(0).setMinWidth(Preferences.CALIBRATION_STATUS_WIDTH);
		getColumnModel().getColumn(1).setMinWidth(Preferences.CALIBRATION_DATE_WIDTH);
		getColumnModel().getColumn(2).setMinWidth(Preferences.CALIBRATION_WAVE_WIDTH);
	}

	@Override
	public void updateFromModel() {
		clearTable();
		
		ArrayList<Calibration> calibrations = controller.getAllCalibrations();
		
		for (Calibration cal : calibrations) {
			System.out.println("adding" + cal.getDate().toString());
			addRow(cal);
		}
	}
	
	
	
}
