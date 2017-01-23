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
		setMinimumSize(new Dimension(690, 540));
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(Preferences.IMG_ICON)));
		setTitle("MOLABS");
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
		btnLogIn.setOpaque(true);
		btnLogIn.setBackground(new Color(247,163,94));
		btnLogIn.setForeground(Color.WHITE);
		
		JTextPane txtrAdasd = new JTextPane();
		txtrAdasd.setFocusable(false);
		txtrAdasd.setFont(new Font("Roboto Medium", Font.PLAIN, 13));
		txtrAdasd.setText(Strings.ABOUT_THE_SOFTWARE);
		txtrAdasd.setBackground(new Color(204, 204, 204));
		
		JLabel lblJosueArrietaSalas = new JLabel("Josu\u00E9 Arrieta Salas");
		lblJosueArrietaSalas.setFont(new Font("Roboto Medium", Font.PLAIN, 13));
		
		JLabel lblSethStalley = new JLabel("Seth Stalley");
		lblSethStalley.setFont(new Font("Roboto Medium", Font.PLAIN, 13));
		
		JLabel lblWithCollaborationOf = new JLabel("Contacts: ");
		lblWithCollaborationOf.setFont(new Font("Roboto Medium", Font.PLAIN, 16));
		
		JLabel lblLauraHernandezAlpizar = new JLabel("Laura Hernandez Alpizar");
		lblLauraHernandezAlpizar.setFont(new Font("Roboto Medium", Font.PLAIN, 13));
		
		JLabel lblContact = new JLabel("Client: ");
		lblContact.setFont(new Font("Roboto Medium", Font.PLAIN, 16));
		
		JLabel lblLahernandezitcraccr = new JLabel("lahernandez@itcr.ac.cr");
		lblLahernandezitcraccr.setFont(new Font("Roboto Medium", Font.PLAIN, 13));
		
		JLabel lblJosuearrietasalasgmailcom = new JLabel("josuearrietasalas@gmail.com");
		lblJosuearrietasalasgmailcom.setFont(new Font("Roboto Medium", Font.PLAIN, 13));
		
		JLabel lblAdrianlqgmailcom = new JLabel("adrianlq8@gmail.com");
		lblAdrianlqgmailcom.setFont(new Font("Roboto Medium", Font.PLAIN, 13));
		
		JLabel lblSethstalleygmailcom = new JLabel("sethstalley@gmail.com");
		lblSethstalleygmailcom.setFont(new Font("Roboto Medium", Font.PLAIN, 13));
		
		JLabel label = new JLabel("Adrian Lopez Quesada");
		label.setFont(new Font("Roboto Medium", Font.PLAIN, 13));
		
//----------------------Layout-----------------------------------------------------------------------
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(145)
					.addComponent(lblImageIcon, GroupLayout.PREFERRED_SIZE, 363, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
					.addComponent(lblUsername)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(84)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(27)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(label, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblJosueArrietaSalas, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblSethStalley, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE))))
							.addPreferredGap(ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblJosuearrietasalasgmailcom, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblWithCollaborationOf, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblAdrianlqgmailcom, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblLahernandezitcraccr, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
									.addGap(41)
									.addComponent(btnLogIn))
								.addComponent(lblSethstalleygmailcom, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)))
						.addComponent(lblContact, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(109)
					.addComponent(lblLauraHernandezAlpizar, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(384, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(74, Short.MAX_VALUE)
					.addComponent(txtrAdasd, GroupLayout.PREFERRED_SIZE, 543, GroupLayout.PREFERRED_SIZE)
					.addGap(57))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblImageIcon, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUsername))
					.addGap(11)
					.addComponent(txtrAdasd, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblWithCollaborationOf, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblJosuearrietasalasgmailcom, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblJosueArrietaSalas, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblAdrianlqgmailcom, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
								.addComponent(label, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
							.addGap(11)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblSethStalley, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblSethstalleygmailcom, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblContact, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblLauraHernandezAlpizar, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblLahernandezitcraccr, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
							.addGap(46))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnLogIn, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
//----------------------------------------Ends Layout-------------------------------------------------------------------
		
		getContentPane().setLayout(groupLayout);
	}
}