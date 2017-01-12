package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import controller.Controller;
import controller.DB;
import values.Preferences;
import values.Strings;
import javax.swing.JTextArea;

public class About extends JFrame {
	private JLabel lblImageIcon, lblUsername, lblPassword;
	private JButton btnLogIn;
	
	public About() {
		setMinimumSize(new Dimension(690, 530));
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(Preferences.IMG_ICON)));
		setTitle("MOLABS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(204, 204, 204));
		
		initComponents();
		
		UIManager.put("OptionPane.okButtonText", Strings.ACCEPT_ERROR_BUTTON); //text on errors display
	}
	private void initComponents(){
		
		//Logo
		
		lblImageIcon = new JLabel("");
		lblImageIcon.setIcon(new ImageIcon(getClass().getResource(Preferences.IMG_LOGO)));
		
		lblUsername = new JLabel("Version: 1.0");
		lblUsername.setFont(new Font("Roboto Medium", Font.PLAIN, 16));
		
		lblPassword = new JLabel("Developed by:");
		lblPassword.setFont(new Font("Roboto Medium", Font.PLAIN, 16));
		
		btnLogIn= new JButton("Close");
		btnLogIn.setFocusable(false);

		btnLogIn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
			}
		});
		btnLogIn.setBorderPainted(false);
		btnLogIn.setFont(new Font("Roboto Medium", Font.BOLD, 20));
		btnLogIn.setBackground(new Color(247,163,94));
		btnLogIn.setForeground(Color.WHITE);
		
		JTextPane txtrAdasd = new JTextPane();
		txtrAdasd.setFocusable(false);
		txtrAdasd.setFont(new Font("Roboto Medium", Font.PLAIN, 13));
		txtrAdasd.setText(Strings.ABOUT_THE_SOFTWARE);
		txtrAdasd.setBackground(new Color(204, 204, 204));
		
		JLabel lblJosueArrietaSalas = new JLabel("Josue Arrieta Salas");
		lblJosueArrietaSalas.setFont(new Font("Roboto Medium", Font.PLAIN, 13));
		
		JLabel lblAdrianLopezQuesada = new JLabel("Adrian Lopez Quesada");
		lblAdrianLopezQuesada.setFont(new Font("Roboto Medium", Font.PLAIN, 13));
		
		JLabel lblSethStalley = new JLabel("Seth Stalley");
		lblSethStalley.setFont(new Font("Roboto Medium", Font.PLAIN, 13));
		
		JLabel lblWithCollaborationOf = new JLabel("With collaboration of: ");
		lblWithCollaborationOf.setFont(new Font("Roboto Medium", Font.PLAIN, 16));
		
		JLabel lblLauraHernandezAlpizar = new JLabel("Laura Hernandez Alpizar");
		lblLauraHernandezAlpizar.setFont(new Font("Roboto Medium", Font.PLAIN, 13));
		
		JLabel lblContact = new JLabel("Contact: ");
		lblContact.setFont(new Font("Roboto Medium", Font.PLAIN, 16));
		
		JLabel lblLahernandezitcraccr = new JLabel("lahernandez@itcr.ac.cr");
		lblLahernandezitcraccr.setFont(new Font("Roboto Medium", Font.PLAIN, 13));
		
//----------------------Layout-----------------------------------------------------------------------
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(301, Short.MAX_VALUE)
					.addComponent(lblUsername)
					.addGap(289))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(120)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblJosueArrietaSalas, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 129, Short.MAX_VALUE)
							.addComponent(lblLauraHernandezAlpizar, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE)
							.addGap(112))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblAdrianLopezQuesada, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(422, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblSethStalley, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(422, Short.MAX_VALUE))))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(69, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblContact, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblLahernandezitcraccr, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
							.addGap(210)
							.addComponent(btnLogIn))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
							.addGap(137)
							.addComponent(lblWithCollaborationOf, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE))
						.addComponent(txtrAdasd, GroupLayout.PREFERRED_SIZE, 543, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(156, Short.MAX_VALUE)
					.addComponent(lblImageIcon, GroupLayout.PREFERRED_SIZE, 370, GroupLayout.PREFERRED_SIZE)
					.addGap(148))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblImageIcon, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblUsername)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtrAdasd, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblWithCollaborationOf, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblJosueArrietaSalas, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblLauraHernandezAlpizar, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblAdrianLopezQuesada, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblSethStalley, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblContact, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblLahernandezitcraccr, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
							.addGap(47))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnLogIn, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
//----------------------------------------Ends Layout-------------------------------------------------------------------
		
		getContentPane().setLayout(groupLayout);
	}
}