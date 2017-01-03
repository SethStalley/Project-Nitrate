package view;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import javax.swing.JFrame;
import javax.swing.JMenu;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Button;

import javax.swing.JToolBar;
import javax.swing.UIManager;

import java.awt.Toolkit;
import java.awt.Dimension;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JSplitPane;
import javax.swing.JInternalFrame;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import controller.Controller;
import model.Calibration;
import validation.Validation;
import values.Strings;

import javax.swing.JTabbedPane;

public class MainWindow extends JFrame {
	
	private Controller controller;
	
	//Storage components
	
	private File[] files; //possibly in the future
	
	
	// GUI componentes;
	private static final int HEIGTH_TABS = 30;
	
	private JMenuBar menuBar;
	private JMenu mnFile, mnEdit, mnTools, mnUsers;
	
	private JMenuItem mntmOpenProject, mntmSaveProject, mntmOpenData, mntmDeleteDataFile, mntmPrint, mntmExit; //File
	
	private JMenuItem mntmAddRow, mntmCopyRow, mntmPasteRow, mntmDeleteRow; //Edit
	
	private JMenuItem mntmFindObsorbance, mntmCalibrate, mntmCalibrationGraph, 
					  mntmConcentrationGraph, mntmOpenObserver, mntmCloseObserver, 
					  mntmAlertValues, mntmExportExcel; //Tools
	
	private JMenuItem mntmAddUser, mntmDeleteUser; //Users
	private JPanel pnMain;
	private JButton btnOpenFile, btnAddRow, btnDeleteRow, btnSaveProject, btnConcentration, btnAbsorbance; // first row buttons
	private JButton btnRemoveCalibration, btnCalibrate; //buttons second row panel
	public JTextField txtWavelength; //single textfield
	private JPanel pnFirstRow, pnSecondRow; // Container panels
	public CustomTable mainTable, calibrationTable; // Tables
	private JScrollPane mainTablePane, scrollPaneCalibration; //scrollpane for tables
	private JPanel calibrationGraph, concentrationGraph, concentrationGraphR; //panel tabs
	private JLabel lblPearson, lblIntercept, lblSlope; //labels tab 1
	private JLabel lblPearsonValue;
	private JLabel lblInterceptValue;
	private JLabel lblSlopeValue;
	
	public MainWindow() {
		controller = new Controller(this);
		
		setMinimumSize(new Dimension(1280, 720));
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/Resources/Icon.png")));
		setTitle("MOLABS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(204, 204, 204));
		initComponents();
	}
	
	private void initComponents(){
		setMenuBar();
		setMenu();
		setMenuItems();
		setFirstPanel();
		setMainTable();
		setSecondPanel();
		setMainPanelLayout();
		
		txtWavelength.addKeyListener(new EnterKey(this));
	}
	
//----------------------initial setup of components section-------------------------------------------------------
	private void setMainPanelLayout(){
		getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		pnMain = new JPanel();
		pnMain.setBackground(new Color(204, 204, 204));
		pnMain.setPreferredSize(new Dimension(10, 50));
		pnMain.setMinimumSize(new Dimension(10, 100));
		getContentPane().add(pnMain);
		
//--------Layout Main Panel--------------------------------------------------------------------------
		
		GroupLayout gl_pnMain = new GroupLayout(pnMain);
		gl_pnMain.setHorizontalGroup(
			gl_pnMain.createParallelGroup(Alignment.LEADING)
				.addComponent(pnFirstRow, GroupLayout.DEFAULT_SIZE, 1264, Short.MAX_VALUE)
				.addGroup(gl_pnMain.createSequentialGroup()
					.addGap(21)
					.addGroup(gl_pnMain.createParallelGroup(Alignment.TRAILING)
						.addComponent(pnSecondRow, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(mainTablePane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1219, Short.MAX_VALUE))
					.addGap(24))
		);
		gl_pnMain.setVerticalGroup(
			gl_pnMain.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnMain.createSequentialGroup()
					.addComponent(pnFirstRow, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
					.addGap(28)
					.addComponent(mainTablePane, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(pnSecondRow, GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
					.addContainerGap())
		);
				
//--------Ends Layout Main Panel--------------------------------------------------------------------------
		
		pnMain.setLayout(gl_pnMain);
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
		mntmOpenData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				OpenFile(arg0);
			}
		});
		
		mntmDeleteDataFile = new JMenuItem("Delete Data File");
		setMenuItemProperties(mntmDeleteDataFile, mnFile);
		mntmDeleteDataFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainTable.deleteSelectedFiles();
			}
		});
		
		mntmPrint = new JMenuItem("Print");
		setMenuItemProperties(mntmPrint, mnFile);
		
		mntmExit = new JMenuItem("Exit");
		setMenuItemProperties(mntmExit, mnFile);
		
		//Edit
		mntmAddRow = new JMenuItem("Add Row");
		setMenuItemProperties(mntmAddRow, mnEdit);
		mntmAddRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainTable.addBlankRow();
			}
		});
		
		mntmCopyRow = new JMenuItem("Copy Row");
		setMenuItemProperties(mntmCopyRow, mnEdit);
		
		mntmPasteRow = new JMenuItem("Paste Row");
		setMenuItemProperties(mntmPasteRow, mnEdit);
		
		mntmDeleteRow = new JMenuItem("Delete Row");
		setMenuItemProperties(mntmDeleteRow, mnEdit);
		mntmDeleteRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainTable.deleteSelectedFiles();
			}
		});
		
		
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
		mntmOpenObserver.addActionListener(new java.awt.event.ActionListener() {
	        @Override
	        public void actionPerformed(java.awt.event.ActionEvent evt) {
	            new Observer(controller).setVisible(true);
	        }
	    });
		
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
	private void setFirstPanel(){		
		pnFirstRow = new JPanel();
		pnFirstRow.setMinimumSize(new Dimension(10, 20));
		pnFirstRow.setPreferredSize(new Dimension(10, 20));
		pnFirstRow.setBackground(new Color(204, 204, 204));		
		
		// btn Open files
		
		btnOpenFile = new GenericRoundedButton("Open File(s)");
		btnOpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				OpenFile(arg0);
			}
		});
		setButtonProperties(btnOpenFile, pnMain);
		btnOpenFile.addMouseListener(setButtonsListeners(btnOpenFile));
		
		//btn Add Row
		
		btnAddRow = new GenericRoundedButton("Add Row");
		setButtonProperties(btnAddRow, pnMain);
		btnAddRow.addMouseListener(setButtonsListeners(btnAddRow));
		btnAddRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainTable.addBlankRow();
			}
		});
		
		//btn Delete Row
		
		btnDeleteRow = new GenericRoundedButton("Delete Row");
		setButtonProperties(btnDeleteRow, pnMain);
		btnDeleteRow.addMouseListener(setButtonsListeners(btnDeleteRow));
		btnDeleteRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainTable.deleteSelectedFiles();
			}
		});
		
		//btn Save Project
				
		btnSaveProject = new GenericRoundedButton("Save Project");
		setButtonProperties(btnSaveProject, pnMain);
		btnSaveProject.addMouseListener(setButtonsListeners(btnSaveProject));
				
		JLabel lblWavelengthnm = new JLabel("Wavelength (nm): ");
		lblWavelengthnm.setFont(new Font("Roboto Light", Font.PLAIN, 12));
				
		//txt Absorbance
				
		txtWavelength = new JTextField();
		txtWavelength.setColumns(10);
				
		//btn Absorbance
				
		btnAbsorbance = new GenericRoundedButton("Absorbance");
		btnAbsorbance.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent arg0) {
				((MainTable) mainTable).addAbsorbanceColumnFromWavelength(txtWavelength.getText());
			}
		});
		setButtonProperties(btnAbsorbance, pnMain);
		btnAbsorbance.addMouseListener(setButtonsListeners(btnAbsorbance));
				
		//btn Concentration

		btnConcentration = new GenericRoundedButton("Concentration");
		btnConcentration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calibrationTable.actionButton();
			}
		});
		setButtonProperties(btnConcentration, pnMain);
		btnConcentration.addMouseListener(setButtonsListeners(btnConcentration));		
		
//--------Layout Four butons to the left and 2 and a textfield to the right--------------------------------
		
		GroupLayout gl_pnFirstRow = new GroupLayout(pnFirstRow);
		gl_pnFirstRow.setHorizontalGroup(
		gl_pnFirstRow.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_pnFirstRow.createSequentialGroup()
			.addContainerGap()
			.addComponent(btnOpenFile, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(ComponentPlacement.UNRELATED)
			.addComponent(btnAddRow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(ComponentPlacement.UNRELATED)
			.addComponent(btnDeleteRow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(ComponentPlacement.UNRELATED)
			.addComponent(btnSaveProject, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(ComponentPlacement.RELATED, 196, Short.MAX_VALUE)
			.addComponent(lblWavelengthnm)
			.addPreferredGap(ComponentPlacement.RELATED)
			.addComponent(txtWavelength, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(ComponentPlacement.UNRELATED)
			.addComponent(btnAbsorbance, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(ComponentPlacement.UNRELATED)
			.addComponent(btnConcentration, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
			.addContainerGap())
		);
		gl_pnFirstRow.setVerticalGroup(
			gl_pnFirstRow.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnFirstRow.createSequentialGroup()
				.addContainerGap(13, Short.MAX_VALUE)
				.addGroup(gl_pnFirstRow.createParallelGroup(Alignment.BASELINE)
					.addComponent(txtWavelength, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(btnAbsorbance, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(btnConcentration, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(lblWavelengthnm))
				.addContainerGap())
			.addGroup(gl_pnFirstRow.createSequentialGroup()
				.addContainerGap(16, Short.MAX_VALUE)
				.addGroup(gl_pnFirstRow.createParallelGroup(Alignment.BASELINE)
					.addComponent(btnOpenFile, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(btnAddRow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(btnDeleteRow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(btnSaveProject, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addContainerGap())
		);
		
//--------Ends Layout Four butons to the left and 2 and a textfield to the right--------------------------------	
		
		pnFirstRow.setLayout(gl_pnFirstRow);
		
		
	}
	private void setMainTable(){
		mainTablePane = new JScrollPane();
		
		mainTable = new MainTable(new SortableJTableModel(
				new String[] {
						"Sample", "Date", "Time", "Type","Concentration"},0), controller); 
		mainTable.setRowHeight(24);
		
		mainTable.getTableHeader().setFont(new Font("Roboto Medium", Font.BOLD, 12));
		mainTable.setColumnSelectionAllowed(true);
		
		mainTable.getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				tableHeaderClicked(arg0);
			}
		});
		
		
		mainTablePane.setViewportView(mainTable);
		
		
	}
	private void setSecondPanel(){
		
		//Table
	
		scrollPaneCalibration = new JScrollPane(); //Pane for headers
		
		calibrationTable = new CalibrationTable(new SortableCalibrateModel( //has to be changed to calibrateTable
				new String[] {
						"Status", "Date", "Wavelength"
				},0), controller); // number of rows, should be 0 but for testing uses its 3
		calibrationTable.setRowHeight(24);
		
		calibrationTable.getTableHeader().setFont(new Font("Roboto Medium", Font.BOLD, 12));
		
		calibrationTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
        	public void valueChanged(ListSelectionEvent event) {
        		setlblValues();
        	}
        });
		
		scrollPaneCalibration.setViewportView(calibrationTable);
		
		//Remove Calibration Button
		
		btnRemoveCalibration = new GenericRoundedButton("Remove Calibration");
		btnRemoveCalibration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				calibrationTable.deleteSelectedCalibrations();
			}
		});
		setButtonProperties(btnRemoveCalibration, pnSecondRow);
		btnRemoveCalibration.addMouseListener(setButtonsListeners(btnRemoveCalibration));
		
		//Calibrate Button
		
		btnCalibrate = new GenericRoundedButton("Calibrate");
		btnCalibrate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		setButtonProperties(btnCalibrate, pnSecondRow);
		btnCalibrate.addMouseListener(setButtonsListeners(btnCalibrate));
		btnCalibrate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				mainTable.actionButton(); //creates new calibration
			}
		});
		
		// TabbedPane for tabs
		

		UIManager.put("TabbedPane.selected", new Color(21,81,104));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Roboto Medium", Font.PLAIN, 11));
		tabbedPane.setBackground(new Color(15,101,131));
		tabbedPane.setForeground(Color.white);
		

		
		// Tab 1
		
		calibrationGraph = new JPanel();
		calibrationGraph.setBackground(Color.WHITE);
		tabbedPane.addTab("Calibration Graph", null, calibrationGraph, null);
		
		JLabel lblNewLabel_2 = new JLabel("Grafico calibracion"); //label para tests
		
		lblPearson = new JLabel("Pearson (R^2): ");
		
		lblIntercept = new JLabel("Intercept: ");
		
		lblSlope = new JLabel("Slope: ");
		
		lblPearsonValue = new JLabel("");
		
		lblInterceptValue = new JLabel("");
		
		lblSlopeValue = new JLabel("");
		
//----------------------------------------layout tab 1 ------------------------------------
		
		GroupLayout gl_calibrationGraph = new GroupLayout(calibrationGraph);
		gl_calibrationGraph.setHorizontalGroup(
			gl_calibrationGraph.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_calibrationGraph.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_calibrationGraph.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPearson)
						.addGroup(gl_calibrationGraph.createSequentialGroup()
							.addComponent(lblIntercept, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
							.addGap(185)
							.addComponent(lblNewLabel_2))
						.addComponent(lblSlope, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
					.addGap(178))
				.addGroup(gl_calibrationGraph.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblPearsonValue)
					.addContainerGap(475, Short.MAX_VALUE))
				.addGroup(gl_calibrationGraph.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblInterceptValue)
					.addContainerGap(475, Short.MAX_VALUE))
				.addGroup(gl_calibrationGraph.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblSlopeValue)
					.addContainerGap(475, Short.MAX_VALUE))
		);
		gl_calibrationGraph.setVerticalGroup(
			gl_calibrationGraph.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_calibrationGraph.createSequentialGroup()
					.addGap(50)
					.addComponent(lblPearson)
					.addGap(7)
					.addComponent(lblPearsonValue)
					.addGap(18)
					.addGroup(gl_calibrationGraph.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblIntercept)
						.addComponent(lblNewLabel_2))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblInterceptValue)
					.addGap(18)
					.addComponent(lblSlope)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblSlopeValue)
					.addContainerGap(95, Short.MAX_VALUE))
		);
		calibrationGraph.setLayout(gl_calibrationGraph);
		
//----------------------------------------Ends layout tab 1 ------------------------------------
		
		JLabel lab = new JLabel(); //Label para modificar tama�o tabs
		lab.setText("Calibration Graph");
		lab.setFont(new Font("Roboto Medium", Font.PLAIN, 11));
		lab.setForeground(Color.white);
	    lab.setPreferredSize(new Dimension(lab.getPreferredSize().width,HEIGTH_TABS)); //30 el height actual
	    tabbedPane.setTabComponentAt(0, lab);  // tab index, jLabel
	    
	    
	    
	    
	    // Tab 2
		
		concentrationGraph = new JPanel();
		concentrationGraph.setBackground(Color.WHITE);
		tabbedPane.addTab("Concentration Graph", null, concentrationGraph, null);
		
		// Tab 3
		
		concentrationGraphR = new JPanel();
		concentrationGraphR.setBackground(Color.WHITE);
		tabbedPane.addTab("ConcentrationGraph (Real Time)", null, concentrationGraphR, null);
		
	    //-----------------------Labels primer iteracion-------------------------------------
	    JLabel lblNewLabel2 = new JLabel("Grafico calibracion"); //Label para tests
		concentrationGraph.add(lblNewLabel2);
		
		JLabel lblNewLabel = new JLabel("Grafico concentracion"); //Label para tests
		concentrationGraph.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Grafico concentracion tiempo real"); //Label para tests
		concentrationGraphR.add(lblNewLabel_1);
		//-----------------------Fin labels primer iteracion-----------------------------------
		
		
		
		//Panel Configuration
		
		pnSecondRow = new JPanel();
		pnSecondRow.setBackground(new Color(204, 204, 204));		
		
//----------------------------------------layout pnSecondRow ------------------------------------		
		GroupLayout gl_pnSecondRow = new GroupLayout(pnSecondRow);
		gl_pnSecondRow.setHorizontalGroup(
			gl_pnSecondRow.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnSecondRow.createSequentialGroup()
					.addGroup(gl_pnSecondRow.createParallelGroup(Alignment.LEADING)
						.addComponent(btnCalibrate, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pnSecondRow.createParallelGroup(Alignment.LEADING)
							.addComponent(btnRemoveCalibration, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(scrollPaneCalibration, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, 231, Short.MAX_VALUE)
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 536, GroupLayout.PREFERRED_SIZE))
		);
		gl_pnSecondRow.setVerticalGroup(
			gl_pnSecondRow.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnSecondRow.createSequentialGroup()
					.addGroup(gl_pnSecondRow.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnSecondRow.createSequentialGroup()
							.addComponent(btnCalibrate, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addGap(35)
							.addComponent(scrollPaneCalibration, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnRemoveCalibration, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnSecondRow.createSequentialGroup()
							.addGap(11)
							.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 290, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		pnSecondRow.setLayout(gl_pnSecondRow);
//---------------------------------------- Ends layout pnSecondRow ------------------------------------
		
	}
	
	
	
	
//----------------------------Properties and listeners setup section--------------------------------------------
	
	private void setButtonProperties(JButton button, JPanel panel){
		button.setFont(new Font("Roboto Medium", Font.BOLD, 12));
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
	
// ----------------------------------------- Actions ------------------------------------------
	private void OpenFile(ActionEvent evt) {
		
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new NitratoFileType());
			fileChooser.setMultiSelectionEnabled(true);
	        
			int returnVal = fileChooser.showOpenDialog(this);
	        
	        if (returnVal != 0) {
	            System.out.println("File access cancelled by user.");
	            return;
	        }
	        
	        File[] arrfile = this.files = fileChooser.getSelectedFiles();  
	
	        for(int i=0; i< arrfile.length; i++) {
	            File file = arrfile[i];
	            this.mainTable.addRow(file);
	        } 
	       
	    }
	
	
	private void tableHeaderClicked(MouseEvent evt){
		int selectedColumn = mainTable.columnAtPoint(evt.getPoint());
		DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
		
		//clear last selection
		if (mainTable.selectedColumn != -1 && mainTable.selectedColumn < mainTable.getColumnModel().getColumnCount()){
			TableCellRenderer oldHeader = mainTable.getColumnModel().getColumn(0).getHeaderRenderer();
			
			mainTable.getColumnModel().getColumn(mainTable.selectedColumn).setHeaderRenderer(oldHeader);
			
		}
		
		headerRenderer.setBackground(new Color(15, 110, 135));
        headerRenderer.setForeground(Color.white); // white foreground
        headerRenderer.setFont(new Font("Roboto Medium", Font.BOLD, 14));
        headerRenderer.setHorizontalAlignment( JLabel.CENTER );
        
		
		if (selectedColumn > Strings.CONCENTRATION_COLUMN_INDEX) {
			mainTable.selectedColumn = selectedColumn;
			String headerValue = (String)mainTable.getColumnModel().getColumn(mainTable.selectedColumn).getHeaderValue();
			if(headerValue.substring(0, 1).equals("A")){
				mainTable.getColumnModel().getColumn(mainTable.selectedColumn).setHeaderRenderer(headerRenderer);
			}
			else{
				mainTable.selectedColumn = -1;
			}
		} else {
			mainTable.selectedColumn = -1;
		}
	}
	private void setlblValues(){
		Calibration calibration = controller.getCalibrationData(calibrationTable.getSelectedRow());
		lblInterceptValue.setText(Double.toString(calibration.getIntercept()));
		lblPearsonValue.setText(Double.toString(calibration.getPearson()));
		lblSlopeValue.setText(Double.toString(calibration.getSlope()));
	}

	
//--------------------------------------Methods from controller ------------------------------------------
	public void setNewCalibration(Calibration calibration){
		calibrationTable.addRow(calibration);
	}
	public void errorOnCalibration(){
		JOptionPane.showMessageDialog(null, Strings.ERROR_CALIBRATE);
	}

	public void calculateConcentrations(int key) {
		mainTable.calculateConcentrations(key);
	}
}
