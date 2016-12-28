package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class CustomTable extends JTable {
	
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
}
