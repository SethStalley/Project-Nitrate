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
import java.text.NumberFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.NumberFormatter;

import controller.Controller;
import values.Preferences;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;

public class Observer extends JFrame {
	private JTextField txtActualFolder;
	private JButton btnBrowse, btnStartStop;
	private Controller controller;
	private boolean running;
	private static Observer instance = null;
	private JFormattedTextField txtYellow;
	private JFormattedTextField txtRed;
	
	public Observer( Controller controller) {
		this.running = false;
		instance = this;
		this.controller = controller;
		setMinimumSize(new Dimension(500, 250));
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(Preferences.IMG_ICON)));
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
		
		JLabel lblAlertValues = new JLabel("Alert Values: ");
		
		NumberFormat format = NumberFormat.getInstance();
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(0);
	    formatter.setMaximum(Integer.MAX_VALUE);
	    formatter.setAllowsInvalid(false);
	    // If you want the value to be committed on each keystroke instead of focus lost
	    formatter.setCommitsOnValidEdit(true);
		
		txtYellow = new JFormattedTextField(formatter);
		txtYellow.setColumns(10);
		
		JLabel lblYellowValues = new JLabel("Yellow Values: ");
		
		JLabel lblRedValues = new JLabel("Red Values:");
		
		txtRed = new JFormattedTextField(formatter);
		txtRed.setColumns(10);
	
		
//------------------------------Layout-----------------------------------------------------------------------
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblAlertValues, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(381, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(92)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblYellowValues)
						.addComponent(lblRedValues))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(txtRed, 0, 0, Short.MAX_VALUE)
						.addComponent(txtYellow, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))
					.addContainerGap(246, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnStartStop, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblActualFolder, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtActualFolder, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(btnBrowse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(28))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(21)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblActualFolder)
						.addComponent(txtActualFolder, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBrowse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(29)
					.addComponent(lblAlertValues)
					.addGap(17)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtYellow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblYellowValues))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtRed, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRedValues))
					.addPreferredGap(ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
					.addComponent(btnStartStop, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
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
