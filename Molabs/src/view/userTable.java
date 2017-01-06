package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
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
	
	public void loadUsers(ArrayList<String[]> users){
		for(String[] user : users){
			addUser(user);
		}
	}
	
	private void addUser(String[] data){
		DefaultTableModel model = (DefaultTableModel) this.getModel();
		
		model.addRow(new Object[]{data[0], data[1], data[2], data[3], data[4]});
		setFormat();
	}
	public String[] getSelectedUser(){
		Integer index = this.getSelectedRow();
		if (index >= 0){
			String[] user = {(String) this.getValueAt(index, 0), (String) this.getValueAt(index, 2),
					(String) this.getValueAt(index, 3), (String) this.getValueAt(index, 4), (String) this.getValueAt(index, 1)};
			return user;
		}else{
			return null;
		}
	}
	
	private void setFormat(){
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment( JLabel.CENTER );
			for(int x=0;x<getColumnCount();x++){
		         getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
		    }
	}
	public void deleteUser(){
		Integer index = this.getSelectedRow();
		((DefaultTableModel)this.getModel()).removeRow(index);
	}
	

}
