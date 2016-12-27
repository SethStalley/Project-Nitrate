package view;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.JFrame;
import javax.swing.JMenu;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Button;

import javax.swing.JToolBar;
import javax.swing.UIManager;

import java.awt.Toolkit;
import java.awt.Dimension;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JSplitPane;
import javax.swing.JInternalFrame;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class MainWindow extends JFrame {
	
	private JMenuBar menuBar;
	private JMenu mnFile, mnEdit, mnTools, mnUsers;
	
	private JMenuItem mntmOpenProject, mntmSaveProject, mntmOpenData, mntmDeleteDataFile, mntmPrint, mntmExit; //File
	
	private JMenuItem mntmAddRow, mntmCopyRow, mntmPasteRow, mntmDeleteRow; //Edit
	
	private JMenuItem mntmFindObsorbance, mntmCalibrate, mntmCalibrationGraph, 
					  mntmConcentrationGraph, mntmOpenObserver, mntmCloseObserver, 
					  mntmAlertValues, mntmExportExcel; //Tools
	
	private JMenuItem mntmAddUser, mntmDeleteUser; //Users
	private JPanel pnFirstRow;
	private JButton btnOpenFile, btnAddRow, btnDeleteRow, btnSaveProject, btnConcentration, btnAbsorbance; // first row buttons
	private JTextField txtAbsorbance;
	
	
	private void initComponents(){
		setMenuBar();
		setMenu();
		setMenuItems();
		setFirstPanel();
		
	}
	private void setMenuBar(){
		menuBar = new JMenuBar();
		menuBar.setPreferredSize(new Dimension(0, 30));
		menuBar.setMaximumSize(new Dimension(0, 20));
		menuBar.setMinimumSize(new Dimension(0, 10));
		menuBar.setForeground(Color.WHITE);
		menuBar.setBackground(new Color(51, 51, 51));
		setJMenuBar(menuBar);
		
	}
	private void setMenuItems(){
		UIManager.put("MenuItem.selectionBackground",new Color(204,204,204));
		UIManager.put("MenuItem.selectionForeground", Color.white);
				
		//File
		
		mntmOpenProject = new JMenuItem("Open Project");
		setMenuItemProperties(mntmOpenProject, mnFile);
		
		mntmSaveProject = new JMenuItem("Save Project");
		setMenuItemProperties(mntmSaveProject, mnFile);
		
		mntmOpenData = new JMenuItem("Open Data File");
		setMenuItemProperties(mntmOpenData, mnFile);
		
		mntmDeleteDataFile = new JMenuItem("Delete Data File");
		setMenuItemProperties(mntmDeleteDataFile, mnFile);
		
		mntmPrint = new JMenuItem("Print");
		setMenuItemProperties(mntmPrint, mnFile);
		
		mntmExit = new JMenuItem("Exit");
		setMenuItemProperties(mntmExit, mnFile);
		
		//Edit
		mntmAddRow = new JMenuItem("Add Row");
		setMenuItemProperties(mntmAddRow, mnEdit);
		
		mntmCopyRow = new JMenuItem("Copy Row");
		setMenuItemProperties(mntmCopyRow, mnEdit);
		
		mntmPasteRow = new JMenuItem("Paste Row");
		setMenuItemProperties(mntmPasteRow, mnEdit);
		
		mntmDeleteRow = new JMenuItem("Delete Row");
		setMenuItemProperties(mntmDeleteRow, mnEdit);
		
		
		//Tools
		mntmFindObsorbance = new JMenuItem("Find Absorbance");
		setMenuItemProperties(mntmFindObsorbance, mnTools);
		
		mntmCalibrate = new JMenuItem("Calibrate");
		setMenuItemProperties(mntmCalibrate, mnTools);
		
		mntmCalibrationGraph = new JMenuItem("Calibration Graph");
		setMenuItemProperties(mntmCalibrationGraph, mnTools);
		
		mntmConcentrationGraph = new JMenuItem("Concentration Graph");
		setMenuItemProperties(mntmConcentrationGraph, mnTools);
		
		mntmOpenObserver = new JMenuItem("Open Observer");
		setMenuItemProperties(mntmOpenObserver, mnTools);
		
		mntmCloseObserver = new JMenuItem("Close Observer");
		setMenuItemProperties(mntmCloseObserver, mnTools);
		
		mntmAlertValues = new JMenuItem("Alert Values");
		setMenuItemProperties(mntmAlertValues, mnTools);
		
		mntmExportExcel = new JMenuItem("Export Excel");
		setMenuItemProperties(mntmExportExcel, mnTools);
		
		//Users
		mntmAddUser = new JMenuItem("Add User");
		setMenuItemProperties(mntmAddUser, mnUsers);
		
		mntmDeleteUser = new JMenuItem("Delete User");
		setMenuItemProperties(mntmDeleteUser, mnUsers);
		
	}
	private void setMenu(){
		
		UIManager.put("Menu.selectionBackground",new Color(204,204,204));
		UIManager.put("Menu.selectionForeground", Color.white);

		//File
		
		mnFile = new JMenu("File");
		setMenuProperties(mnFile);
		
		//Edit
		
		mnEdit = new JMenu("Edit");
		setMenuProperties(mnEdit);
		
		//Tools
		
		mnTools = new JMenu("Tools");
		setMenuProperties(mnTools);
		
		//Users
		
		mnUsers = new JMenu("Users");
		setMenuProperties(mnUsers);
		
	}
	public MainWindow() {
		setMinimumSize(new Dimension(1024, 720));
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/Resources/Icon.png")));
		setTitle("MOLABS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(new Color(204, 204, 204));
		initComponents();
		
		
		
	}
	private void setFirstPanel(){
		pnFirstRow = new JPanel();
		pnFirstRow.setBackground(new Color(204, 204, 204));
		pnFirstRow.setPreferredSize(new Dimension(10, 50));
		pnFirstRow.setMinimumSize(new Dimension(10, 100));
		getContentPane().add(pnFirstRow, BorderLayout.NORTH);
		
		// btn Open files
		
		btnOpenFile = new GenericRoundedButton("Open File(s)");
		setButtonProperties(btnOpenFile, pnFirstRow);
		btnOpenFile.addMouseListener(setButtonsListeners(btnOpenFile));
		
		//btn Add Row
		
		btnAddRow = new GenericRoundedButton("Add Row");
		setButtonProperties(btnAddRow, pnFirstRow);
		btnAddRow.addMouseListener(setButtonsListeners(btnAddRow));
		
		//btn Delete Row
		
		btnDeleteRow = new GenericRoundedButton("Delete Row");
		setButtonProperties(btnDeleteRow, pnFirstRow);
		btnDeleteRow.addMouseListener(setButtonsListeners(btnDeleteRow));

		//btn Save Project
		
		btnSaveProject = new GenericRoundedButton("Save Project");
		setButtonProperties(btnSaveProject, pnFirstRow);
		btnSaveProject.addMouseListener(setButtonsListeners(btnSaveProject));
		
		//btn Concentration

		btnConcentration = new GenericRoundedButton("Concentration");
		setButtonProperties(btnConcentration, pnFirstRow);
		btnConcentration.addMouseListener(setButtonsListeners(btnConcentration));
		
		//btn Absorbance
		
		btnAbsorbance = new GenericRoundedButton("Absorbance");
		setButtonProperties(btnAbsorbance, pnFirstRow);
		btnAbsorbance.addMouseListener(setButtonsListeners(btnAbsorbance));
		
		//txt Absorbance
		
		txtAbsorbance = new JTextField();
		txtAbsorbance.setColumns(10);
		
		JLabel lblWavelengthnm = new JLabel("Wavelength (nm): ");
		lblWavelengthnm.setFont(new Font("Roboto Light", Font.PLAIN, 11));
		
		
		//Layout Four butons to the left and 2 and a textfield to the right
		
		GroupLayout gl_pnFirstRow = new GroupLayout(pnFirstRow);
		gl_pnFirstRow.setHorizontalGroup(
			gl_pnFirstRow.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnFirstRow.createSequentialGroup()
					.addComponent(btnOpenFile, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnAddRow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnDeleteRow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSaveProject, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 229, Short.MAX_VALUE)
					.addComponent(lblWavelengthnm)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txtAbsorbance, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnAbsorbance, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnConcentration, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_pnFirstRow.setVerticalGroup(
			gl_pnFirstRow.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnFirstRow.createSequentialGroup()
					.addGap(12)
					.addComponent(btnOpenFile, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_pnFirstRow.createSequentialGroup()
					.addGap(13)
					.addGroup(gl_pnFirstRow.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAddRow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDeleteRow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSaveProject, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
				.addGroup(gl_pnFirstRow.createSequentialGroup()
					.addGap(13)
					.addGroup(gl_pnFirstRow.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnConcentration, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAbsorbance, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtAbsorbance, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblWavelengthnm)))
		);
		pnFirstRow.setLayout(gl_pnFirstRow);
		
	}
	private void setButtonProperties(JButton button, JPanel panel){
		button.setFont(new Font("Roboto Medium", Font.BOLD, 11));
		button.setBackground(new Color(15,101,131));
	}
	
	
	private MouseAdapter setMenuListeners(JMenu menu){
		return new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				menu.setBackground(new Color(204,204,204));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				menu.setBackground(new Color(51,51,51));
			}
		};
		
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
	private void setMenuItemProperties(JMenuItem menuItem, JMenu father){
		menuItem.setForeground(Color.WHITE);
		menuItem.setFont(new Font("Roboto Medium", Font.BOLD, 12));
		menuItem.setBackground(new Color(51,51,51));
		father.add(menuItem);
	}
	private void setMenuProperties(JMenu menu){
		menu.setOpaque(true);
		menu.addMouseListener(setMenuListeners(menu));
		menu.setBackground(new Color(51,51,51));
		menu.setForeground(Color.WHITE);
		menu.setFont(new Font("Roboto Medium", Font.BOLD, 12));
		menuBar.add(menu);
	}
}
