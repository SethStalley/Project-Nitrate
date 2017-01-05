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

import controller.Controller;
import values.Preferences;
import values.Strings;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import java.awt.Component;

public class CreateUser extends JFrame {
	private JButton btnExit, btnCreate;
	private Controller controller;
	private JTextField txtUsername, txtName, txtEmail, txtPhone;
	private JPasswordField txtPassword, txtConfirmPassword;
	private ButtonGroup radioGroup;
	private JRadioButton rdbtnUser, rdbtnAdmin;
	private JLabel lblPhone,lblUsername, lblPassword, lblConfirmPassword, lblName, lblEmail, lblType; 
	private JRadioButton rdbtnMaster;
	
	public CreateUser( Controller controller, boolean admin, boolean update) {
		this.controller = controller;
		setMinimumSize(new Dimension(500, 360));
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/Resources/Icon.png")));
		setTitle("MOLABS Create User");
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(Preferences.WINDOW_NORMAL_RGB));
		initComponents();
		if(admin){
			setAdminValues();
		}
		if(update){
			setUpdateValues();
		}
	}
	
	private void initComponents(){
		lblUsername = new JLabel("New Username");
		lblUsername.setFont(new Font("Roboto Medium", Font.PLAIN, 11));
		
		txtUsername = new JTextField();
		txtUsername.setColumns(10);
		txtUsername.getDocument().addDocumentListener(setCreateButton());

		
		lblPassword = new JLabel("Password: ");
		lblPassword.setFont(new Font("Roboto Medium", Font.PLAIN, 11));
		
		txtPassword = new JPasswordField();
		txtPassword.setColumns(10);
		txtPassword.getDocument().addDocumentListener(setCreateButton());
		
		lblConfirmPassword = new JLabel("Confirm Password: ");
		lblConfirmPassword.setFont(new Font("Roboto Medium", Font.PLAIN, 11));
		
		txtConfirmPassword = new JPasswordField();
		txtConfirmPassword.setColumns(10);
		txtConfirmPassword.getDocument().addDocumentListener(setCreateButton());
		
		lblName = new JLabel("Name (optional): ");
		lblName.setFont(new Font("Roboto Medium", Font.PLAIN, 11));
		
		txtName = new JTextField();
		txtName.setColumns(10);
		
		lblEmail = new JLabel("Email (optional): ");
		lblEmail.setFont(new Font("Roboto Medium", Font.PLAIN, 11));
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		
		txtPhone = new JTextField();
		txtPhone.setColumns(10);
		
		lblPhone = new JLabel("Phone (optional): ");
		lblPhone.setFont(new Font("Roboto Medium", Font.PLAIN, 11));
		
		rdbtnUser = new JRadioButton("User");
		rdbtnUser.setSelected(true);
		rdbtnUser.setBackground(new Color(Preferences.WINDOW_NORMAL_RGB));
		
		rdbtnAdmin = new JRadioButton("Administrator");
		rdbtnAdmin.setBackground(new Color(Preferences.WINDOW_NORMAL_RGB));
		
		radioGroup = new ButtonGroup();
		radioGroup.add(rdbtnAdmin);
		radioGroup.add(rdbtnUser);
		
		lblType = new JLabel("Type: ");
		lblType.setFont(new Font("Roboto Medium", Font.PLAIN, 11));
		
		btnCreate = new GenericRoundedButton("Create");
		setButtonProperties(btnCreate);
		btnCreate.addMouseListener(setButtonsListeners(btnCreate));
		btnCreate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(inspectPassword()){
					//controller hacer usuario
				}
			}
		});
		btnCreate.setVisible(false);
		
		btnExit = new GenericRoundedButton("Exit");
		setButtonProperties(btnExit);
		btnExit.addMouseListener(setButtonsListeners(btnExit));
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		rdbtnMaster = new JRadioButton("Master");
		rdbtnMaster.setBackground(new Color(204, 204, 204));
		
		
//------------------------------Layout-----------------------------------------------------------------------
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUsername, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblConfirmPassword, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblEmail, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPhone, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblType, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE))
					.addGap(36)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(txtName, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtConfirmPassword, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtPhone, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(rdbtnUser)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(rdbtnAdmin, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rdbtnMaster, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(65, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(277, Short.MAX_VALUE)
					.addComponent(btnCreate, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
					.addGap(51))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblUsername)
								.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblPassword)
								.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblConfirmPassword)
								.addComponent(txtConfirmPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblEmail, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtPhone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPhone, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(rdbtnUser)
								.addComponent(lblType, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
								.addComponent(rdbtnAdmin)
								.addComponent(rdbtnMaster)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(280)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnCreate, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))))
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
	private boolean inspectPassword(){
		String pass = new String(txtPassword.getPassword());
		String pass2 = new String(txtConfirmPassword.getPassword());
		if(pass.equals(pass2)){
			return true;
		}
		else
			JOptionPane.showMessageDialog(null, Strings.ERROR_PASSWORD_CONFIRMATION);
		return false;
	}
	private void setUpdateValues(){
		btnCreate.setText("Update");
		
	}
	private void setAdminValues(){
		rdbtnAdmin.setVisible(false);
		rdbtnMaster.setVisible(false);
		rdbtnUser.setVisible(false);
		lblType.setVisible(false);
	}
	private DocumentListener setCreateButton(){
		DocumentListener doc = new DocumentListener() {
			@Override
			  public void changedUpdate(DocumentEvent e) {
			    changed();
			  }
			@Override
			  public void removeUpdate(DocumentEvent e) {
			    changed();
			  }
			@Override
			  public void insertUpdate(DocumentEvent e) {
			    changed();
			  }

			  public void changed() {
				  String pass1 = new String(txtPassword.getPassword());
				  String pass2 = new String(txtConfirmPassword.getPassword());
			     if (txtUsername.getText().equals("") || pass1.equals("") || pass2.equals("")){
			       btnCreate.setVisible(false);
			     }
			     else {
			       btnCreate.setVisible(true);
			    }

			  }
			};
			return doc;
	}
}
