package view;

import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import controller.DB;
import values.Preferences;
import values.Strings;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.json.JSONException;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginScreen extends JFrame {
	private JTextField txtUsername, txtPassword;
	private JLabel lblImageIcon, lblUsername, lblPassword;
	private JButton btnLogIn;
	int fontSize;
	
	public LoginScreen() {
		setFontSize();
		setMinimumSize(new Dimension(640, 480));
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(Preferences.IMG_ICON)));
		setTitle("MOLABS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(204, 204, 204));
		
		initComponents();
		
		txtUsername.addKeyListener(new EnterKeyLogIn(this));
		txtPassword.addKeyListener(new EnterKeyLogIn(this));
		
		UIManager.put("OptionPane.okButtonText", Strings.ACCEPT_ERROR_BUTTON); //text on errors display
	}
	
	private void setFontSize() {
		if (Preferences.isMac()) {
			this.fontSize = 14;
		} else {
			this.fontSize = 16;
		}
	}
	
	private void initComponents(){
		
		//Menu Bar
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setPreferredSize(new Dimension(0, 30));
		menuBar.setMaximumSize(new Dimension(0, 20));
		menuBar.setMinimumSize(new Dimension(0, 10));
		menuBar.setForeground(Color.WHITE);
		menuBar.setBackground(new Color(51, 51, 51));
		setJMenuBar(menuBar);
		
		//Logo
		
		lblImageIcon = new JLabel("");
		lblImageIcon.setIcon(new ImageIcon(getClass().getResource(Preferences.IMG_LOGO)));
		
		//Username
		
		txtUsername = new JTextField();
		txtUsername.setColumns(10);
		
		lblUsername = new JLabel("Username: ");
		lblUsername.setFont(new Font("Roboto Medium", Font.PLAIN, fontSize));
		
		//Password
		
		txtPassword = new JPasswordField();
		txtPassword.setColumns(10);
		
		lblPassword = new JLabel("Password: ");
		lblPassword.setFont(new Font("Roboto Medium", Font.PLAIN, fontSize));
		
		btnLogIn= new JButton("Sign In");

		btnLogIn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				validateEntry();
			}
		});
		btnLogIn.setBorderPainted(false);
		btnLogIn.setFont(new Font("Roboto Medium", Font.BOLD, 20));
		btnLogIn.setBackground(new Color(247,163,94));
		btnLogIn.setOpaque(true);
		btnLogIn.setForeground(Color.WHITE);
		this.addKeyListener(new EnterKeyLogIn(this));
		
//----------------------Layout-----------------------------------------------------------------------
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(131, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblImageIcon, GroupLayout.PREFERRED_SIZE, 370, GroupLayout.PREFERRED_SIZE)
							.addGap(123))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblUsername))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(txtUsername, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
								.addComponent(txtPassword, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
								.addComponent(btnLogIn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(189))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblImageIcon, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
					.addGap(34)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUsername))
					.addGap(31)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(44)
					.addComponent(btnLogIn, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(61, Short.MAX_VALUE))
		);
//----------------------------------------Ends Layout-------------------------------------------------------------------
		
		getContentPane().setLayout(groupLayout);
	}
	
	
	
	
	
	public void validateEntry() {
		String username = txtUsername.getText();
		String password = txtPassword.getText();
		DB db = DB.getInstance(username, password);
		String result = db.validateUser();
		if(result != null){
			if(result.equals("owner")){
				new MainWindowOwner(username + " (owner)").setVisible(true);
				dispose();
			}
			else if(result.equals("admin")){
				new MainWindowOwner(username + " (admin)").setVisible(true);
				dispose();
			}
			else if(result.equals("user")){
				new MainWindowUser(username + " (user)").setVisible(true);
				dispose();
			}
			else{
				JOptionPane.showMessageDialog(null, result,"Error",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "Combination of username and password incorrect.","Error",JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
