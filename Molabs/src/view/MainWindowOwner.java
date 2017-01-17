package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import controller.DB;
import values.Preferences;

public class MainWindowOwner extends MainWindow {
	
	private JMenu mnUsers, mnObserver, mnEdit;
	
	private JMenuItem mntmOpenProject, mntmSaveProject, mntmOpenData, mntmDeleteDataFile, mntmPrint, mntmExit; //File
	
	private JMenuItem mntmAddRow, mntmCopyRow, mntmPasteRow, mntmDeleteRow; //Edit
	
	private JMenuItem mntmFindObsorbance, mntmCalibrate, mntmCalibrationGraph, 
					  mntmConcentrationGraph, mntmObserver, mntmOpenObserver, mntmCloseObserver, 
					  mntmAlertValues, mntmExportExcel; //Tools
	
	private JMenuItem mntmAddUser, mntmListUser; //Users
	
	
	private JButton btnOpenFile, btnAddRow, btnDeleteRow, btnSaveProject, btnConcentration, btnAbsorbance; // first row buttons
	private JTextField txtWavelength; //single textfield

	public MainWindowOwner(String username) {
		super(username);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setupKeyAdapters() {
		super.setupKeyAdapters();
		txtWavelength.addKeyListener(new EnterKey(this));
	}
	
	protected void setMiddleMenus(){
		
		mnEdit = new JMenu("Edit"){
			private KeyStroke accelerator;

            @Override
            public KeyStroke getAccelerator() {
                return accelerator;
            }

            @Override
            public void setAccelerator(KeyStroke keyStroke) {
                KeyStroke oldAccelerator = accelerator;
                this.accelerator = keyStroke;
                repaint();
                revalidate();
                firePropertyChange("accelerator", oldAccelerator, accelerator);
            }
		};
		setMenuProperties(mnEdit);
		
		//Users
		
		mnUsers = new JMenu("Users");
		setMenuProperties(mnUsers);
		
		//Invisible observer
		mnObserver = new JMenu("                                   Observer running");
		mnObserver.setBackground(new Color(51,51,51));
		mnObserver.setForeground(Color.WHITE);
		mnObserver.setFont(new Font("Roboto Medium", Font.BOLD, 12));
		menuBar.add(mnObserver);
		mnObserver.setEnabled(false);
		mnObserver.setVisible(false);
	}
	
	protected void setMenuItems(){
		//File
		
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
		
		//File - this is copy paste from MainWindow, dont know how to do this, my idea is to separate each menu items by methods, but it will take long and alot of refactor
		
		mntmOpenProject = new JMenuItem("Open Project");
		setMenuItemProperties(mntmOpenProject, mnFile);
		mntmOpenProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openProgram();
			}
		});
		
		mntmSaveProject = new JMenuItem("Save Project");
		setMenuItemProperties(mntmSaveProject, mnFile);
		mntmSaveProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveProgram();
			}
		});
		
		mntmExit = new JMenuItem("Exit");
		setMenuItemProperties(mntmExit, mnFile);
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		//Edit
		
		mntmCopyRow = new JMenuItem("Copy");
		setMenuItemProperties(mntmCopyRow, mnEdit);
		mntmCopyRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				excelAdapter.pasteFromClipboard();
			}
		});
			
		mntmPasteRow = new JMenuItem("Paste");
		setMenuItemProperties(mntmPasteRow, mnEdit);
		mntmPasteRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				excelAdapter.pasteFromClipboard();
			}
		});
		
		mntmAddRow = new JMenuItem("Add Row");
		setMenuItemProperties(mntmAddRow, mnEdit);
		mntmAddRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((MainTable) mainTable).addBlankRow();
			}
		});
		
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
		
		mntmCalibrationGraph = new JMenuItem("Calibration");
		setMenuItemProperties(mntmCalibrationGraph, mnTools);
		
		mntmConcentrationGraph = new JMenuItem("Concentration");
		setMenuItemProperties(mntmConcentrationGraph, mnTools);
		
		mntmObserver = new JMenuItem("Observer");
		setMenuItemProperties(mntmObserver, mnTools);
		mntmObserver.addActionListener(new java.awt.event.ActionListener() {
	        @Override
	        public void actionPerformed(java.awt.event.ActionEvent evt) {
	           Observer.getInstance(controller).setVisible(true);
	        }
	    });
		
		mntmOpenObserver = new JMenuItem("Start Observer");
		setMenuItemProperties(mntmOpenObserver, mnTools);
		mntmOpenObserver.addActionListener(new java.awt.event.ActionListener() {
	        @Override
	        public void actionPerformed(java.awt.event.ActionEvent evt) {
	           Observer.getInstance(controller).observerAction();
	        }
	    });
		
		mntmCloseObserver = new JMenuItem("Stop Observer");
		setMenuItemProperties(mntmCloseObserver, mnTools);
		mntmCloseObserver.addActionListener(new java.awt.event.ActionListener() {
	        @Override
	        public void actionPerformed(java.awt.event.ActionEvent evt) {
	           Observer.getInstance(controller).observerAction();
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
		
		//Users
		mntmAddUser = new JMenuItem("Add User");
		setMenuItemProperties(mntmAddUser, mnUsers);
		mntmAddUser.addActionListener(new java.awt.event.ActionListener() {
	        @Override
	        public void actionPerformed(java.awt.event.ActionEvent evt) {	        	
	        	new CreateUser(controller,isAdmin(),false, null).setVisible(true); 
	        }
	    });
		
		mntmListUser = new JMenuItem("List Users");
		setMenuItemProperties(mntmListUser, mnUsers);
		mntmListUser.addActionListener(new java.awt.event.ActionListener() {
	        @Override
	        public void actionPerformed(java.awt.event.ActionEvent evt) {
	           new ListUsers(controller,isAdmin()).setVisible(true); 
	        }
	    });
		
	}
	
	protected void setFirstPanel(){
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
				((MainTable) mainTable).addBlankRow();
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
					.addGap(22)
					.addComponent(btnOpenFile, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnAddRow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnDeleteRow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnSaveProject, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 420, Short.MAX_VALUE)
					.addComponent(lblWavelengthnm)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txtWavelength, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnAbsorbance, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnConcentration, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(24))
		);
		gl_pnFirstRow.setVerticalGroup(
			gl_pnFirstRow.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnFirstRow.createSequentialGroup()
					.addContainerGap(27, Short.MAX_VALUE)
					.addGroup(gl_pnFirstRow.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnFirstRow.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnConcentration, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnAbsorbance, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtWavelength, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblWavelengthnm))
						.addGroup(gl_pnFirstRow.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnOpenFile, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnAddRow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnDeleteRow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnSaveProject, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		
//--------Ends Layout Four butons to the left and 2 and a textfield to the right--------------------------------	
		
		pnFirstRow.setLayout(gl_pnFirstRow);		
	}
	
	protected void setSecondPanel(){
		super.setSecondPanel();
		// Tab 3
		
		concentrationGraphR = new JScrollPane();
		concentrationGraphR.setBackground(Color.WHITE);
		tabbedPane.addTab("Concentration (Real Time)", null, concentrationGraphR, null);
		concentrationGraphR.getViewport().setView(new ConcentrationTimeGraph(null));
		
		//The buttons of calibrate and remove calibration muts be in the groupLayout of the whole second panel, so its easier to setvisible false on MianWindowUsers
	}	
	
	private boolean isAdmin(){
		if(DB.getInstance().getType().equals("owner"))
    		return false;
    	else
    		return true;
	}
	
	public void observerRunningColor() {
		pnMain.setBackground(new Color(Preferences.WINDOW_OBSERVER_RUNNING_RGB));
		pnFirstRow.setBackground(new Color(Preferences.WINDOW_OBSERVER_RUNNING_RGB));
		pnSecondRow.setBackground(new Color(Preferences.WINDOW_OBSERVER_RUNNING_RGB));
		mnObserver.setVisible(true);
		
	}
	
	public void observerStoppedColor() {
		pnMain.setBackground(new Color(Preferences.WINDOW_NORMAL_RGB));
		pnFirstRow.setBackground(new Color(Preferences.WINDOW_NORMAL_RGB));
		pnSecondRow.setBackground(new Color(Preferences.WINDOW_NORMAL_RGB));
		mnObserver.setVisible(false);
	}
	
	//-----------------------------------Actions----------------------------------------
	
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
	
	public String getWavelength(){
		return txtWavelength.getText();
	}
}
