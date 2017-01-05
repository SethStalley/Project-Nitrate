package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import controller.Controller;

public class userTable extends JTable {
	
	Controller controller;
	private static final int headerHeigth = 28;

	public userTable(TableModel model, Controller controller) {
		this.setRowSelectionAllowed(true);
		createTableHeaders();
		setModel(model);
		this.controller = controller;
	}
	
	private void createTableHeaders() {
		JTableHeader header = getTableHeader();
        header.setBackground(new Color(236,134,50)); //orange
        header.setForeground(Color.white); // white foreground
        header.setPreferredSize(new Dimension((int) header.getPreferredSize().getWidth(),headerHeigth)); //increase header height
        header.setOpaque(false); //let the table display the header
    }

}
