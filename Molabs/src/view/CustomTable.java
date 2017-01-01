package view;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public abstract class CustomTable extends JTable {
	
	private static final int headerHeigth = 28;
	
	public CustomTable(DefaultTableModel model) {
		this.setRowSelectionAllowed(true);
		createTableHeaders(model);
	}
	
	private void createTableHeaders(DefaultTableModel model) {
		JTableHeader header = getTableHeader();
        header.setBackground(new Color(236,134,50)); //orange
        header.setForeground(Color.white); // white foreground
        header.setPreferredSize(new Dimension((int) header.getPreferredSize().getWidth(),headerHeigth)); //increase header height
        setModel(model); // sets model with header columns
        getTableHeader().setOpaque(false); //let the table display the header
    }
	
	public abstract void addRow(File file);
	
	public void addDropdowns() {
    	TableColumn typeColumn = getColumnModel().getColumn(values.Strings.TYPE_COLUMN_INDEX);
    
    	JComboBox<String> comboBox = new JComboBox<String>();
    	comboBox.addItem(" ");
    	comboBox.addItem(values.Strings.STD);
    	comboBox.addItem(values.Strings.SAMPLE);
    	comboBox.setEditable(true);
    	comboBox.setFocusable(false);
    	
    	typeColumn.setCellEditor(new DefaultCellEditor(comboBox));
    }
}
