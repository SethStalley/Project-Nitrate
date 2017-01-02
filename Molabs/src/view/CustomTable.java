package view;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import controller.Controller;

public abstract class CustomTable extends JTable {
	
	protected final int DATE_INDEX = 1;
	protected final int TIME_INDEX = 2;	
	private static final int headerHeigth = 28;
	protected Controller controller;
	
	public CustomTable(SortableJTableModel model, Controller controller) {
		this.setRowSelectionAllowed(true);
		createTableHeaders(model);
		this.controller = controller;
	}
	
	private void createTableHeaders(SortableJTableModel model) {
		JTableHeader header = getTableHeader();
        header.setBackground(new Color(236,134,50)); //orange
        header.setForeground(Color.white); // white foreground
        header.setPreferredSize(new Dimension((int) header.getPreferredSize().getWidth(),headerHeigth)); //increase header height
        setModel(model); // sets model with header columns
        getTableHeader().setOpaque(false); //let the table display the header
    }
	
	public abstract void addRow(File file);
	public abstract void addColumn(Object header, Object[] columns);
	
	public abstract Object getColumnValues(Integer colum);
	
	protected void addDropdowns() {
    	TableColumn typeColumn = getColumnModel().getColumn(values.Strings.TYPE_COLUMN_INDEX);
    
    	JComboBox<String> comboBox = new JComboBox<String>();
    	comboBox.addItem(values.Strings.SAMPLE);
    	comboBox.addItem(values.Strings.STD);
    	comboBox.setEditable(true);
    	comboBox.setFocusable(false);
    	
    	typeColumn.setCellEditor(new DefaultCellEditor(comboBox));
    }

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

	public abstract void addBlankRow();
	
}
