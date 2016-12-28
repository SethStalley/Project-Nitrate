package view;

import java.awt.Color;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CustomTable extends JTable {
	public CustomTable(DefaultTableModel model) {
		this.setRowSelectionAllowed(true);
		createTableHeaders(model);
	}
	
	private void createTableHeaders(DefaultTableModel model) {
        getTableHeader().setBackground(new Color(236,134,50));
        getTableHeader().setForeground(Color.white);
        setModel(model);
        getTableHeader().setOpaque(false);
    }
}
