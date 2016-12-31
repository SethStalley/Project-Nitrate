package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

public class Observer extends JFrame {
	private JTextField txtActualFolder;
	private JButton btnBrowse, btnStart, btnStop;
	
	
	public Observer() {
		setMinimumSize(new Dimension(500, 180));
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/Resources/Icon.png")));
		setTitle("MOLABS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(new Color(204, 204, 204));
		
		initComponents();
	}
	private void initComponents(){
		JLabel lblActualFolder = new JLabel("Actual Folder");
		
		txtActualFolder = new JTextField();
		txtActualFolder.setColumns(10);
		txtActualFolder.setEditable(false);
		
		btnBrowse = new GenericRoundedButton("Browse");
		setButtonProperties(btnBrowse);
		setButtonsListeners(btnBrowse);
		
		btnStart = new GenericRoundedButton("Start");
		setButtonProperties(btnStart);
		setButtonsListeners(btnBrowse);
		
		btnStop = new GenericRoundedButton("Stop");
		setButtonProperties(btnStop);
		setButtonsListeners(btnBrowse);
		
//------------------------------Layout-----------------------------------------------------------------------
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblActualFolder, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtActualFolder, GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(btnBrowse)
							.addGap(28))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnStart)
							.addGap(18)
							.addComponent(btnStop)
							.addContainerGap())))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(21)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblActualFolder)
						.addComponent(txtActualFolder, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBrowse))
					.addPreferredGap(ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnStart)
						.addComponent(btnStop))
					.addGap(26))
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
