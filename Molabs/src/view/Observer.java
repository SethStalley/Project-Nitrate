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
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import controller.Controller;
import values.Preferences;

import javax.swing.JButton;
import javax.swing.JFileChooser;

public class Observer extends JFrame {
	private JTextField txtActualFolder;
	private JButton btnBrowse, btnStartStop;
	private Controller controller;
	private boolean running;
	private static Observer instance = null;
	
	public Observer( Controller controller) {
		this.running = false;
		instance = this;
		this.controller = controller;
		setMinimumSize(new Dimension(500, 180));
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Icon.png")));
		setTitle("MOLABS Observer");
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(Preferences.WINDOW_NORMAL_RGB));
		initComponents();
	}
	
	public static Observer getInstance(Controller controller) {
		if (instance == null) {
			return new Observer(controller);
		}
		
		return instance;
	}
	
	private void initComponents(){
		JLabel lblActualFolder = new JLabel("Actual Folder");
		
		txtActualFolder = new JTextField();
		txtActualFolder.setColumns(10);
		txtActualFolder.setEditable(false);
		
		btnBrowse = new GenericRoundedButton("Browse");
		setButtonProperties(btnBrowse);
		setButtonsListeners(btnBrowse);
		btnBrowse.addMouseListener(setButtonsListeners(btnBrowse));
		btnBrowse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setLiveFolderPath();
			}
		});
		
		
		btnStartStop = new GenericRoundedButton("Start");
		btnStartStop.setOpaque(false);
		setButtonProperties(btnStartStop);
		btnStartStop.addMouseListener(setButtonsListeners(btnStartStop));
		btnStartStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				observerAction();
			}
		});
	
		
//------------------------------Layout-----------------------------------------------------------------------
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.CENTER)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblActualFolder, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtActualFolder, GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(btnBrowse)
							.addGap(28))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnStartStop)
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
						.addComponent(btnStartStop))
					.addGap(26))
		);
		
//---------------------------------------Ends Layout ------------------------------------------------------------
		
		getContentPane().setLayout(groupLayout);
	}
	
	public void observerAction() {
		if (running) {
			stopObserver();
		} else {
			startObserver();
		}	
		btnStartStop.setUI(btnStartStop.getUI());
	}
	
	private void startObserver() {
		String path = txtActualFolder.getText();
		
		if(new File(path).isDirectory()) {
			this.running = true;
			btnStartStop.setText("Stop");
			btnStartStop.setBackground(new Color(Preferences.BTN_COLOR_RED));
			
			controller.startObserver(path);
			getContentPane().setBackground(new Color(Preferences.WINDOW_OBSERVER_RUNNING_RGB));
		}
	
	}
	
	private void stopObserver() {
		this.running = false;
		btnStartStop.setText("Start");
		btnStartStop.setBackground(new Color(Preferences.BTN_COLOR_BLUE));
		
		controller.stopObserver();
		getContentPane().setBackground(new Color(Preferences.WINDOW_NORMAL_RGB));
	}
	
	private void setLiveFolderPath() {
		String path = getLiveFolderPath();
		this.txtActualFolder.setText(path);
	}
	
	
	//Prompt user for directory to observe for new files.
	private String getLiveFolderPath() {
		String path = "";
		JFileChooser chooser;
		
		
		chooser = new JFileChooser(); 
	    chooser.setDialogTitle("Choose a folder to watch for new data files.");
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	   
	    chooser.setAcceptAllFileFilterUsed(false);

	    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
	    	path = chooser.getSelectedFile().getAbsolutePath();
	    }
	    
		return path;
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
