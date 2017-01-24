package view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JMenu;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.UIManager;

import java.awt.Toolkit;
import java.awt.Dimension;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.GridLayout;


import javax.swing.JTable;

import controller.Controller;
import controller.DB;
import de.erichseifert.gral.data.DataTable;

import model.Calibration;
import values.Preferences;
import values.Strings;

import javax.swing.JTabbedPane;

public class MainWindow extends JFrame {
	
	protected Controller controller;
	private boolean isMaster;
	
	//Storage components
	
	protected File[] files; //possibly in the future
	
	
	// GUI componentes;
	private static final int HEIGTH_TABS = 30;
	
	protected JMenuBar menuBar;
	protected JMenu mnFile, mnTools, mnAbout;
	
	private JMenuItem mntmOpenProject, mntmSaveProject, mntmOpenData, mntmDeleteDataFile, mntmPrint, mntmExit; //File
	
	private JMenuItem mntmExportExcel, mntmAlertValues; //Tools

	protected JPanel pnMain, graphConc;
	protected JButton btnRemoveCalibration, btnCalibrate; //buttons second row panel
	protected JPanel pnFirstRow, pnSecondRow; // Container panels
	public CustomTable mainTable, calibrationTable; // Tables

	protected JScrollPane mainTablePane, scrollPaneCalibration,concentrationGraph, concentrationGraphR, scrollPaneCalibrationGraph; //scrollpane for tables
	private JPanel calibrationGraph; //panel tabs

	private JLabel lblPearson, lblIntercept, lblSlope; //labels tab 1
	private JLabel lblPearsonValue;
	private JLabel lblInterceptValue;
	private JLabel lblSlopeValue;
	private JMenu mnUsernameIngresado;
	
	protected JTabbedPane tabbedPane;
	
	protected ExcelAdapter excelAdapter;
	
	private String username;
	
	public MainWindow(String username) {
		controller = new Controller(this);
		this.username = username;
		
		setMinimumSize(new Dimension(1280, 720));
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(Preferences.IMG_ICON)));
		setTitle("MOLABS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		initComponents();
		setupKeyAdapters();
		
	}
	
	protected void initComponents(){
		setMenuBar();
		setFirstMenus();
		setMiddleMenus();
		setLastMenus();
		setFirstPanel();
		setMenuItems();
		setMainTable();
		setSecondPanel();
		setMainPanelLayout();
	}
	
	protected void setupKeyAdapters(){
		excelAdapter = new ExcelAdapter(mainTable);
	};
	
//----------------------initial setup of components section-------------------------------------------------------
	protected void setMainPanelLayout(){
		getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		pnMain = new JPanel();
		pnMain.setBackground(new Color(204, 204, 204));
		pnMain.setPreferredSize(new Dimension(10, 50));
		pnMain.setMinimumSize(new Dimension(10, 100));
		getContentPane().add(pnMain);
		
//--------Layout Main Panel--------------------------------------------------------------------------
		
		GroupLayout gl_pnMain = new GroupLayout(pnMain);
		gl_pnMain.setHorizontalGroup(
			gl_pnMain.createParallelGroup(Alignment.TRAILING)
				.addComponent(pnFirstRow, GroupLayout.DEFAULT_SIZE, 1264, Short.MAX_VALUE)
				.addGroup(gl_pnMain.createSequentialGroup()
					.addGap(21)
					.addGroup(gl_pnMain.createParallelGroup(Alignment.TRAILING)
						.addComponent(pnSecondRow, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1219, Short.MAX_VALUE)
						.addComponent(mainTablePane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1219, Short.MAX_VALUE))
					.addGap(24))
		);
		gl_pnMain.setVerticalGroup(
			gl_pnMain.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnMain.createSequentialGroup()
					.addComponent(pnFirstRow, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(mainTablePane, GroupLayout.PREFERRED_SIZE, 267, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(pnSecondRow, GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
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
	protected void setFirstMenus(){
		UIManager.put("Menu.selectionBackground",new Color(204,204,204));
		UIManager.put("Menu.selectionForeground", Color.white);

		//File
		
		mnFile = new JMenu("File");
		setMenuProperties(mnFile);
		
		//Tools
		
		mnTools = new JMenu("Tools");
		
		
	}
	protected void setMiddleMenus(){
		mnAbout = new JMenu("About");
		setMenuProperties(mnAbout);
		mnAbout.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				new About().setVisible(true);
				
			}
		});
	}
	protected void setLastMenus(){
		
		//Username registered
		
		mnUsernameIngresado = new JMenu("Current user: " + this.username);
		mnUsernameIngresado.setAlignmentX(Component.RIGHT_ALIGNMENT);
		mnUsernameIngresado.setOpaque(true);
		mnUsernameIngresado.setForeground(Color.WHITE);
		mnUsernameIngresado.setFont(new Font("Roboto Medium", Font.BOLD, 12));
		mnUsernameIngresado.setBackground(new Color(51, 51, 51));
		menuBar.add(Box.createHorizontalGlue()); 
		menuBar.add(mnUsernameIngresado);
	}
	protected void setMenuItems(){
		UIManager.put("MenuItem.selectionBackground",new Color(204,204,204));
		UIManager.put("MenuItem.selectionForeground", Color.white);
				
		//File
		
		mntmOpenProject = new JMenuItem("Open Project");
		setMenuItemProperties(mntmOpenProject, mnFile);
		mntmOpenProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openProgram();
			}
		});
		
		mntmExit = new JMenuItem("Exit");
		setMenuItemProperties(mntmExit, mnFile);
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		//Tools
		
		mntmAlertValues = new JMenuItem("Alert Values");
		setMenuItemProperties(mntmAlertValues, mnTools);
		mntmAlertValues.addActionListener(new java.awt.event.ActionListener() {
	        @Override
	        public void actionPerformed(java.awt.event.ActionEvent evt) {
	        	new AlertValues(controller).setVisible(true);
	        }
	    });
		
		mntmExportExcel = new JMenuItem("Export Excel");
		setMenuItemProperties(mntmExportExcel, mnTools);
		mntmExportExcel.addActionListener(new java.awt.event.ActionListener() {
	        @Override
	        public void actionPerformed(java.awt.event.ActionEvent evt) {
	        	export2Excel();
	        }
	    });
	}
	
	
	protected void setFirstPanel(){
		pnFirstRow = new JPanel();
		pnFirstRow.setMinimumSize(new Dimension(0, 0));
		pnFirstRow.setPreferredSize(new Dimension(0, 0));
		pnFirstRow.setBackground(new Color(204, 204, 204));
	};
	private void setMainTable(){
		mainTablePane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		mainTable = new MainTable(new SortableJTableModel(
				new String[] {
						"File", "Date", "Time", "Type","STD CONC"},0), controller);
		mainTable.setRowHeight(24);
		
		mainTable.getTableHeader().setFont(new Font("Roboto Medium", Font.BOLD, 12));
		mainTable.setColumnSelectionAllowed(true);
		
		mainTable.getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				tableHeaderClicked(arg0);
			}
		});
		
		mainTable.getTableHeader().setReorderingAllowed(false);
		mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		mainTable.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        calibrationTable.clearSelection();
		        }
		    });
		

		
		mainTablePane.setViewportView(mainTable);		
		
	}
	protected void setSecondPanel(){
		
		//Table
	
		scrollPaneCalibration = new JScrollPane(); //Pane for headers
		
		calibrationTable = new CalibrationTable(new SortableCalibrateModel( //has to be changed to calibrateTable
				new String[] {
						"Status", "Date", "Wavelength(nm)"
				},0), controller); // number of rows, should be 0 but for testing uses its 3
		calibrationTable.setRowHeight(24);
		
		calibrationTable.getTableHeader().setFont(new Font("Roboto Medium", Font.BOLD, 12));
		
		calibrationTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
        	public void valueChanged(ListSelectionEvent event) {
        		 if (!event.getValueIsAdjusting() && calibrationTable.getSelectedRow() != -1)
        			 setlblValues();
        	}
        });
		calibrationTable.getTableHeader().setReorderingAllowed(false);
		calibrationTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		calibrationTable.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        scrollPaneCalibration.repaint();
		        }
		    });
		
		scrollPaneCalibration.setViewportView(calibrationTable);
		
		controller.startPushGraph((CalibrationTable) calibrationTable);
		
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
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Roboto Medium", Font.PLAIN, 11));
		tabbedPane.setBackground(new Color(15,101,131));
		tabbedPane.setForeground(Color.white);
		

		
		// Tab 1
		
		calibrationGraph = new JPanel();
		calibrationGraph.setBackground(Color.WHITE);
		tabbedPane.addTab("Calibration", null, calibrationGraph, null);
		
		lblPearson = new JLabel("Pearson (R^2): ");
		
		lblIntercept = new JLabel("Intercept: ");
		
		lblSlope = new JLabel("Slope: ");
		
		lblPearsonValue = new JLabel("");
		
		lblInterceptValue = new JLabel("");
		
		lblSlopeValue = new JLabel("");
		
		scrollPaneCalibrationGraph = new JScrollPane();
		scrollPaneCalibrationGraph.setOpaque(false);
		scrollPaneCalibrationGraph.setBackground(Color.WHITE);
		
		scrollPaneCalibrationGraph.getViewport().setView(new CalibrationGraph(null));
		
		
//----------------------------------------layout tab 1 ------------------------------------
		
		GroupLayout gl_calibrationGraph = new GroupLayout(calibrationGraph);
		gl_calibrationGraph.setHorizontalGroup(
			gl_calibrationGraph.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_calibrationGraph.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_calibrationGraph.createParallelGroup(Alignment.LEADING)
						.addComponent(lblIntercept, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPearsonValue)
						.addComponent(lblInterceptValue)
						.addComponent(lblSlopeValue)
						.addComponent(lblPearson)
						.addComponent(lblSlope, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
					.addComponent(scrollPaneCalibrationGraph, GroupLayout.PREFERRED_SIZE, 488, GroupLayout.PREFERRED_SIZE))
		);
		gl_calibrationGraph.setVerticalGroup(
			gl_calibrationGraph.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_calibrationGraph.createSequentialGroup()
					.addGap(54)
					.addComponent(lblPearson)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblPearsonValue)
					.addGap(18)
					.addComponent(lblIntercept)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblInterceptValue)
					.addGap(18)
					.addComponent(lblSlope)
					.addGap(11)
					.addComponent(lblSlopeValue)
					.addContainerGap(106, Short.MAX_VALUE))
				.addComponent(scrollPaneCalibrationGraph, GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
		);
		calibrationGraph.setLayout(gl_calibrationGraph);
		
//----------------------------------------Ends layout tab 1 ------------------------------------
		
		JLabel lab = new JLabel(); //Label para modificar tama�o tabs
		lab.setText("Calibration");
		lab.setFont(new Font("Roboto Medium", Font.PLAIN, 11));
		lab.setForeground(Color.white);
	    lab.setPreferredSize(new Dimension(lab.getPreferredSize().width,HEIGTH_TABS)); //30 el height actual
	    tabbedPane.setTabComponentAt(0, lab);  // tab index, jLabel
	    
	    
	    
	    
	    // Tab 2
		
		concentrationGraph = new JScrollPane();
		concentrationGraph.setBackground(Color.WHITE);
		tabbedPane.addTab("Concentration", null, concentrationGraph, null);
		cleanGraph();
		
		
		//Panel Configuration
		
		pnSecondRow = new JPanel();
		pnSecondRow.setBackground(new Color(204, 204, 204));		
		
//----------------------------------------layout pnSecondRow ------------------------------------		
		GroupLayout gl_pnSecondRow = new GroupLayout(pnSecondRow);
		gl_pnSecondRow.setHorizontalGroup(
			gl_pnSecondRow.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnSecondRow.createSequentialGroup()
					.addGroup(gl_pnSecondRow.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPaneCalibration, GroupLayout.PREFERRED_SIZE, 504, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pnSecondRow.createSequentialGroup()
							.addGap(4)
							.addComponent(btnCalibrate, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnRemoveCalibration, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 625, GroupLayout.PREFERRED_SIZE))
		);
		gl_pnSecondRow.setVerticalGroup(
			gl_pnSecondRow.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pnSecondRow.createSequentialGroup()
					.addGap(51)
					.addComponent(scrollPaneCalibration, GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
					.addGap(18)
					.addGroup(gl_pnSecondRow.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCalibrate, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnRemoveCalibration, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26))
				.addGroup(gl_pnSecondRow.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))
		);
		
		pnSecondRow.setLayout(gl_pnSecondRow);
//---------------------------------------- Ends layout pnSecondRow ------------------------------------
		
	}
	
	
	
	
//----------------------------Properties and listeners setup section--------------------------------------------
	
	protected void setButtonProperties(JButton button, JPanel panel){
		button.setFont(new Font("Roboto Medium", Font.BOLD, 12));
		button.setBackground(new Color(15,101,131));
	}
	
	
	protected MouseAdapter setMenuListeners(JMenu menu){
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
	protected MouseAdapter setButtonsListeners(JButton button){
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
	protected void setMenuProperties(JMenu menu){
		menu.setOpaque(true);
		menu.addMouseListener(setMenuListeners(menu));
		menu.setBackground(new Color(51,51,51));
		menu.setForeground(Color.WHITE);
		menu.setFont(new Font("Roboto Medium", Font.BOLD, 12));
		menuBar.add(menu);
	}
	
	protected void setMenuItemProperties(JMenuItem menuItem, JMenu father){
		menuItem.setForeground(Color.WHITE);
		menuItem.setFont(new Font("Roboto Medium", Font.BOLD, 12));
		menuItem.setBackground(new Color(51,51,51));
		father.add(menuItem);
	}
	
// ----------------------------------------- Actions ------------------------------------------
	protected void saveProgram() {
		 JFileChooser chooser = new JFileChooser();
         FileNameExtensionFilter filter = new FileNameExtensionFilter(Strings.LABEL_MOLABS_FILE, "molabs");
         chooser.setFileFilter(filter);
         chooser.setDialogTitle(Strings.LABEL_SAVE_FILE);
         chooser.setAcceptAllFileFilterUsed(false);
         
         if (chooser.showSaveDialog(null) != 0) return;
         
         String file = chooser.getSelectedFile().toString();
         if (!file.contains(".molabs") )
         	file = file.concat(".molabs");
         
         controller.saveProgram(file);
	}
	
	protected void openProgram() {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(Strings.LABEL_MOLABS_FILE, "molabs");
		fileChooser.setFileFilter(filter);
		fileChooser.setMultiSelectionEnabled(false);
		
		if (fileChooser.showOpenDialog(null) != 0) return;
		
		String file = fileChooser.getSelectedFile().toString();
		
		controller.loadProgram(file);
	}
	
	protected void export2Excel() {
		try {
            if (this.mainTable.getRowCount() <= 0) return;
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(Strings.LABEL_EXCEL_FILE, "xls");
            chooser.setFileFilter(filter);
            chooser.setDialogTitle(Strings.LABEL_SAVE_FILE);
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showSaveDialog(null) != 0) return;
            ArrayList<JTable> tb = new ArrayList<JTable>();
            tb.add(this.mainTable);
            String file = chooser.getSelectedFile().toString();
            
            if (!file.contains(".xls") )
            	file = file.concat(".xls");
            ExcelExport excelExporter = new ExcelExport(tb, new File(file));
            if (!excelExporter.export()) return;
            JOptionPane.showMessageDialog(null, Strings.SUCCESS_TABLE_EXPORT,"Error",JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        catch (Exception chooser) {
            chooser.printStackTrace();
        }
	}
	
	
	private void tableHeaderClicked(MouseEvent evt){
		if(SwingUtilities.isLeftMouseButton(evt)){
			mainTable.leftClickAction(evt);
			mainTablePane.repaint();
		}else{
			mainTable.rightClickAction(evt);
		}
			
	}
	
	private void setlblValues(){
		int row = calibrationTable.getSelectedRow();
		Date key = (Date) calibrationTable.getValueAt(row, Strings.CALIBRATIONTABLE_COLUMN_DATE);
		
		Calibration calibration = controller.getCalibrationData(key);


		String intercept = new DecimalFormat("#.##########").format(calibration.getIntercept());
		String pearson = new DecimalFormat("#.##########").format(calibration.getPearson());
		String slope = new DecimalFormat("#.##########").format(calibration.getSlope());
		lblInterceptValue.setText(intercept);
		lblPearsonValue.setText(pearson);
		lblSlopeValue.setText(slope);
		
		scrollPaneCalibrationGraph.getViewport().setView(new CalibrationGraph(calibration));
	
		
		//highlight rows
		((MainTable) mainTable).highlightLightRowsRelatedToConcentration(calibration.getWavelength(),calibration.getFileKeys());
		mainTablePane.repaint();
	}


	
//--------------------------------------Methods from controller ------------------------------------------

	public void setNewCalibration(Calibration calibration){
		calibrationTable.addRow(calibration);
	}
	public void errorOnCalibration(){
		JOptionPane.showMessageDialog(null, Strings.ERROR_CALIBRATE,"Error",JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void errorOnOpenFile() {
		JOptionPane.showMessageDialog(null, Strings.ERROR_SELECT_FILE,"Error",JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void errorOpenSave() {
		JOptionPane.showMessageDialog(null, Strings.ERROR_SELECT_SAVE,"Error",JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void errorStartingObserver() {
		JOptionPane.showMessageDialog(null, Strings.ERROR_STARTING_OBSERVER,"Error",JOptionPane.INFORMATION_MESSAGE);
	}

	public void calculateConcentrations(Date key) {
		((MainTable) mainTable).calculateConcentrations(key);
	}
	public void deleteColumnMainTable(int index){
		((MainTable) mainTable).deleteColumn(index);
	}
	public void deleteExactColumnMainTable(int column){
		((MainTable) mainTable).deleteExactColumn(column);
	}
	public void delteAbsorbanceMainTable(int key, int numOfConcentrations) {
		for (int i=key+numOfConcentrations; i>=key; i--) {
			((MainTable) mainTable).deleteColumn(i);	
		}	
	}
	
	public void graphConcentration(int index){
		
	}
	
	public void initiateGraphConcentration(){
		((CalibrationTable)calibrationTable).graphCalibration();
	}
	public void cleanGraph(){
		concentrationGraph.getViewport().setView(new ConcentrationTimeGraph(null));
	}
	
	public ArrayList<String[]> getConcentrationPoints(){
		return ((MainTable)mainTable).getConcentrationPoints();
	}
	
	public void deleteCalibrationsByWavelength(String wavelength){
		((CalibrationTable)calibrationTable).deleteCalibrationsByWavelength(wavelength);
	}
	
	public void graphConcentrationSelected(int index){
		if(mainTable.getSelectedRows().length > 1){
			DataTable data =((MainTable)mainTable).getConcentrationsGraphSelected(index);
			concentrationGraph.getViewport().setView(new ConcentrationTimeGraph(data));
			
		}
		else{
			JOptionPane.showMessageDialog(null, "Please select more than one column", "Error", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	public Date getCalibrationDate(){
		return ((CalibrationTable)calibrationTable).getActiveCalibration();
	}
	
	public void graphConcentrationRealTime(DataTable data){
		concentrationGraphR.getViewport().setView(new ConcentrationTimeGraph(data));
	}
	
	public void graphConcentrationRealTime(){
		concentrationGraphR.getViewport().setView(new ConcentrationTimeGraph(((MainTable) mainTable).getGraphPointsRealTime()));
	}
	
	public String getWavelengthCalibration(){
		int row = calibrationTable.getSelectedRow();
		Date key = (Date) calibrationTable.getValueAt(row, Strings.CALIBRATIONTABLE_COLUMN_DATE);
		
		Calibration calibration = controller.getCalibrationData(key);
		
		return calibration.getWavelength();
	}
	
	public void changeUser(String user){
		mnUsernameIngresado.setText("Current user: " + user + " ("+DB.getInstance().getType() +")");
	}
	
}
