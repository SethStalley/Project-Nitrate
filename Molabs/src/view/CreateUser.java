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
	private static CreateUser instance = null;
	private JTextField txtUsername, txtName, txtEmail, txtPhone;
	private JPasswordField txtPassword, txtConfirmPassword;
	private ButtonGroup radioGroup;
	private JRadioButton rdbtnUser, rdbtnAdmin;
	private JLabel lblPhone,lblUsername, lblPassword, lblConfirmPassword, lblName, lblEmail, lblType; 
	
	public CreateUser( Controller controller) {
		instance = this;
		this.controller = controller;
		setMinimumSize(new Dimension(500, 360));
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/Resources/Icon.png")));
		setTitle("MOLABS Create User");
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(Preferences.WINDOW_NORMAL_RGB));
		initComponents();
	}
	
	public static CreateUser getInstance(Controller controller) {
		if (instance == null) {
			return new CreateUser(controller);
		}
		
		return instance;
	}
	
	private void initComponents(){
		lblUsername = new JLabel("New Username");
		lblUsername.setFont(new Font("Roboto Medium", Font.PLAIN, 11));
		
		txtUsername = new JTextField();
		txtUsername.setColumns(10);

		
		lblPassword = new JLabel("Password: ");
		lblPassword.setFont(new Font("Roboto Medium", Font.PLAIN, 11));
		
		txtPassword = new JPasswordField();
		txtPassword.setColumns(10);
		
		lblConfirmPassword = new JLabel("Confirm Password: ");
		lblConfirmPassword.setFont(new Font("Roboto Medium", Font.PLAIN, 11));
		
		txtConfirmPassword = new JPasswordField();
		txtConfirmPassword.setColumns(10);
		
		lblName = new JLabel("Name: ");
		lblName.setFont(new Font("Roboto Medium", Font.PLAIN, 11));
		
		txtName = new JTextField();
		txtName.setColumns(10);
		
		lblEmail = new JLabel("Email: ");
		lblEmail.setFont(new Font("Roboto Medium", Font.PLAIN, 11));
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		
		txtPhone = new JTextField();
		txtPhone.setColumns(10);
		
		lblPhone = new JLabel("Phone: ");
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
		
		btnExit = new GenericRoundedButton("Exit");
		setButtonProperties(btnExit);
		btnExit.addMouseListener(setButtonsListeners(btnExit));
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		
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
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
							.addGap(57))
						.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtPhone, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(rdbtnUser)
							.addGap(40)
							.addComponent(rdbtnAdmin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(160)))
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(289, Short.MAX_VALUE)
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
								.addComponent(rdbtnAdmin)
								.addComponent(lblType, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(280)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnCreate, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(16, Short.MAX_VALUE))
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
}
