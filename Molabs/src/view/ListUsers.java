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
import java.util.ArrayList;

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
		setMinimumSize(new Dimension(715, 360));
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/Resources/Icon.png")));
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Icon.png")));
		setTitle("MOLABS Users");
		setLocationRelativeTo(null);
		isAdmin = false; /// por ahorta por que no hay users.
		getContentPane().setBackground(new Color(Preferences.WINDOW_NORMAL_RGB));
		initComponents();
		
		ArrayList<String[]> users = DB.getInstance().getUsersForUsername();

		if (users == null){
			JOptionPane.showMessageDialog(null, Strings.ERROR_NO_INTERNET,"Error",JOptionPane.INFORMATION_MESSAGE);
		}
		else{
			((view.userTable) userTable).loadUsers(users);
		}
	}
	
	private void initComponents(){
		
		btnUpdate = new GenericRoundedButton("Update");
		setButtonProperties(btnUpdate);
		btnUpdate.addMouseListener(setButtonsListeners(btnUpdate));
		btnUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// search for user to udpate
				String[] userToUpdate = ((view.userTable) userTable).getSelectedUser();
				if (userToUpdate != null){
					
					new CreateUser(controller,isAdmin,true,userToUpdate).setVisible(true);
					dispose();
					
				}
				else{
					JOptionPane.showMessageDialog(null, Strings.ERROR_NO_USER_SELECTED,"Error",JOptionPane.INFORMATION_MESSAGE);
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
				String userToUpdate[] = ((view.userTable) userTable).getSelectedUser();
				if (userToUpdate != null){
					if (!userToUpdate[0].equals(DB.getInstance().getUser())){
						String result = DB.getInstance().deleteUser(userToUpdate[0]);
						if(result == null){
							((view.userTable) userTable).deleteUser();
							JOptionPane.showMessageDialog(null, "User: " + userToUpdate[0] + " deleted.","",JOptionPane.INFORMATION_MESSAGE);
						}
						else{
							JOptionPane.showMessageDialog(null, result,"",JOptionPane.INFORMATION_MESSAGE);
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "You cannot delete yourself!","Error",JOptionPane.INFORMATION_MESSAGE);
					}
				}
				else{
					JOptionPane.showMessageDialog(null, Strings.ERROR_NO_USER_SELECTED,"Error",JOptionPane.INFORMATION_MESSAGE);
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
					.addContainerGap(320, Short.MAX_VALUE)
					.addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 659, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(22, Short.MAX_VALUE))
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
