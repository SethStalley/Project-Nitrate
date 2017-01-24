package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class MainWindowUser extends MainWindow {

	public MainWindowUser(String username) {
		super(username);
	}
	
	@Override
	protected void setMiddleMenus() {
		setMenuProperties(mnTools);
		super.setMiddleMenus();
	}
	
	protected void setSecondPanel(){
		//The buttons of calibrate and remove calibration muts be in the groupLayout of the whole second panel, so its easier to setvisible false on MianWindowUsers
		super.setSecondPanel();
		btnRemoveCalibration.setVisible(false);
		btnCalibrate.setVisible(false);
	}

	@Override
	protected void setFirstPanel() {
		pnFirstRow = new JPanel();
		pnFirstRow.setMinimumSize(new Dimension(0, 0));
		pnFirstRow.setPreferredSize(new Dimension(0, 0));
		pnFirstRow.setBackground(new Color(204, 204, 204));
		
	}
	
	@Override
	protected void setMainPanelLayout(){
		getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		pnMain = new JPanel();
		pnMain.setBackground(new Color(204, 204, 204));
		pnMain.setPreferredSize(new Dimension(10, 50));
		pnMain.setMinimumSize(new Dimension(10, 100));
		getContentPane().add(pnMain);
		
		GroupLayout gl_pnMain = new GroupLayout(pnMain);
		gl_pnMain.setHorizontalGroup(
			gl_pnMain.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnMain.createSequentialGroup()
					.addGap(21)
					.addGroup(gl_pnMain.createParallelGroup(Alignment.TRAILING)
						.addComponent(mainTablePane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1219, Short.MAX_VALUE)
						.addComponent(pnSecondRow, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1219, Short.MAX_VALUE))
					.addGap(24))
		);
		gl_pnMain.setVerticalGroup(
			gl_pnMain.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnMain.createSequentialGroup()
					.addContainerGap()
					.addComponent(mainTablePane, GroupLayout.PREFERRED_SIZE, 325, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(pnSecondRow, GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		pnMain.setLayout(gl_pnMain);
	}

}
