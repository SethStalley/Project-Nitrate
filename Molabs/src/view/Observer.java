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
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.NumberFormatter;

import controller.Controller;
import controller.DB;
import values.Preferences;
import values.Strings;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;

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
		setMinimumSize(new Dimension(500, 150));
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
				observerAction(!running);
			}
		});
		
		NumberFormat format = NumberFormat.getInstance();
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Double.class);
	    formatter.setMaximum(Double.MAX_VALUE);
		
	
		
//------------------------------Layout-----------------------------------------------------------------------
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblActualFolder, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtActualFolder, GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
							.addGap(18))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(205)
							.addComponent(btnStartStop, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(92)))
					.addComponent(btnBrowse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
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
					.addGap(18)
					.addComponent(btnStartStop, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		
//---------------------------------------Ends Layout ------------------------------------------------------------
		
		getContentPane().setLayout(groupLayout);
	}
	
	public void observerAction(boolean start) {
		if (start) {
			if(!running)
				startObserver();
		} else {
			if(running)
				stopObserver();
		}	
		btnStartStop.setUI(btnStartStop.getUI());
	}
	
	private void startObserver() {
		String path = txtActualFolder.getText();
		
		if(new File(path).isDirectory()) {
			boolean success = controller.startObserver(path);
			if(success){
				this.running = true;
				btnStartStop.setBackground(new Color(Preferences.BTN_COLOR_RED));
				btnStartStop.setText("Stop");
				getContentPane().setBackground(new Color(Preferences.WINDOW_OBSERVER_RUNNING_RGB));
			}
		}else{
			JOptionPane.showMessageDialog(null, Strings.ERROR_PATH_OBSERVER,"Error",JOptionPane.INFORMATION_MESSAGE);
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
