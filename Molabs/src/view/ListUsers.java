package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;
import javax.swing.table.DefaultTableCellRenderer;

import controller.Controller;
import controller.DB;
import values.Preferences;
import values.Strings;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ListUsers extends JFrame {
	private JButton btnExit, btnUpdate, btnDelete;
	private Controller controller;
	private boolean isAdmin;
	private JTable userTable;
	
	public ListUsers( Controller controller, boolean admin) {
		this.controller = controller;
		setMinimumSize(new Dimension(585, 360));
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Icon.png")));
		setTitle("MOLABS Users");
		setLocationRelativeTo(null);
		isAdmin = false; /// por ahorta por que no hay users.
		getContentPane().setBackground(new Color(Preferences.WINDOW_NORMAL_RGB));
		initComponents();
		String[] user1 = {"usuario", "owner", "nombre", "email@gmail.com", "1234-1234"};
		String[] user2 = {"usuario2", "owner2", "nombre2 ","email2@gmail.com", "1234-1234-2"};
		((view.userTable) userTable).addUser(user1);
		((view.userTable) userTable).addUser(user2);
		((view.userTable) userTable).loadUsers(DB.getInstance().getUsersForUsername());
	}
	
	private void initComponents(){
		
		btnUpdate = new GenericRoundedButton("Update");
		setButtonProperties(btnUpdate);
		btnUpdate.addMouseListener(setButtonsListeners(btnUpdate));
		btnUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// search for user to udpate
				String userToUpdate = ((view.userTable) userTable).getSelectedUser();
				if (userToUpdate != null){
					new CreateUser(controller,isAdmin,true,userToUpdate).setVisible(true);
					dispose();
				}
				else{
					JOptionPane.showMessageDialog(null, Strings.ERROR_NO_USER_SELECTED);
				}
			}
		});
		
		btnExit = new GenericRoundedButton("Exit");
		setButtonProperties(btnExit);
		btnExit.addMouseListener(setButtonsListeners(btnExit));
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		btnDelete = new GenericRoundedButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String userToUpdate = ((view.userTable) userTable).getSelectedUser();
				if (userToUpdate != null){
					String result = DB.getInstance().deleteUser(userToUpdate);
					if(result == null){
						((view.userTable) userTable).deleteUser();
						JOptionPane.showMessageDialog(null, "User: " + userToUpdate + " deleted.");
					}
					else{
						JOptionPane.showMessageDialog(null, result);
					}
				}
				else{
					JOptionPane.showMessageDialog(null, Strings.ERROR_NO_USER_SELECTED);
				}
			}
		});
		setButtonProperties(btnDelete);
		btnDelete.addMouseListener(setButtonsListeners(btnDelete));
		
		JScrollPane scrollPane = new JScrollPane();
		userTable = new userTable(new SortableJTableModel(
				new String[] {
						"Username","Type","Name","Email","Phone" },0), controller); 
		userTable.setRowHeight(24);
		
		userTable.getTableHeader().setFont(new Font("Roboto Medium", Font.BOLD, 12));
		userTable.setColumnSelectionAllowed(false);
		
		
		userTable.getTableHeader().setReorderingAllowed(false);
		
		scrollPane.setViewportView(userTable);		
		
		
//------------------------------Layout-----------------------------------------------------------------------
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(307, Short.MAX_VALUE)
					.addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 533, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(18, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(21)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(15, Short.MAX_VALUE))
		);
		
//---------------------------------------Ends Layout ------------------------------------------------------------
		
		getContentPane().setLayout(groupLayout);
	}
	
	
	private void setButtonProperties(JButton button){
		button.setFont(new Font("Roboto Medium", Font.BOLD, 12));
		button.setBackground(new Color(15,101,131));
	}
	
	private MouseAdapter setButtonsListeners(JButton button){
		return new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				button.setBackground(new Color(21,81,104));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				button.setBackground(new Color(15,101,131));
			}
		};
		
	}
}
