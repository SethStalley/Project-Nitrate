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

public class AlertValues extends JFrame {
	private Controller controller;
	private JFormattedTextField txtYellow;
	private JFormattedTextField txtRed;
	
	public AlertValues( Controller controller) {
		this.controller = controller;
		setMinimumSize(new Dimension(500, 200));
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(Preferences.IMG_ICON)));
		setTitle("MOLABS Alert Values");
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(Preferences.WINDOW_NORMAL_RGB));
		initComponents();
	}
	
	private void initComponents(){
		
		JLabel lblAlertValues = new JLabel("Alert Values: ");
		
		NumberFormat format = NumberFormat.getInstance();
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Double.class);
	    formatter.setMaximum(Double.MAX_VALUE);
		
		txtYellow = new JFormattedTextField(formatter);
		txtYellow.setColumns(10);
		
		JLabel lblYellowValues = new JLabel("Yellow Values: ");
		
		JLabel lblRedValues = new JLabel("Red Values:");
		
		txtRed = new JFormattedTextField(formatter);
		txtRed.setColumns(10);
		
		setValues();
		
		GenericRoundedButton btnModifyValues = new GenericRoundedButton("Browse");
		btnModifyValues.setOpaque(false);
		setButtonProperties(btnModifyValues);
		btnModifyValues.addMouseListener(setButtonsListeners(btnModifyValues));
		btnModifyValues.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sendValues();
			}
		});
		btnModifyValues.setText("Modify");
		btnModifyValues.setFont(new Font("Roboto Medium", Font.BOLD, 12));
		btnModifyValues.setBackground(new Color(15, 101, 131));
	
		
//------------------------------Layout-----------------------------------------------------------------------
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblAlertValues, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(381, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(78)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblYellowValues)
						.addComponent(lblRedValues))
					.addGap(44)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(txtYellow)
						.addComponent(txtRed, GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE))
					.addContainerGap(178, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(368, Short.MAX_VALUE)
					.addComponent(btnModifyValues, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
					.addGap(37))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblAlertValues))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(36)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblRedValues)
								.addComponent(txtRed, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblYellowValues)
								.addComponent(txtYellow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addGap(5)
					.addComponent(btnModifyValues, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(37, Short.MAX_VALUE))
		);
		
//---------------------------------------Ends Layout ------------------------------------------------------------
		
		getContentPane().setLayout(groupLayout);
	}
	

	
	private void setButtonProperties(JButton button){
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
	
	private void setValues(){
		Double[] alertValues = DB.getInstance().getAlertValues();
		if(alertValues != null){
			txtYellow.setValue(alertValues[0]);
			txtRed.setValue(alertValues[1]);
		}else
			JOptionPane.showMessageDialog(null, Strings.ERROR_INTERNET,"Error",JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void sendValues(){
		try {
			txtRed.commitEdit();
			txtYellow.commitEdit();
			Double min = (Double)txtYellow.getValue();
			Double max = (Double)txtRed.getValue();
			System.out.println(min);
			if(min < max){
				String validate = DB.getInstance().updateAlertValues(min, max);
				if(validate != null){
					JOptionPane.showMessageDialog(null, Strings.ERROR_INTERNET_ALERT_VALUES,"Error",JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, Strings.SUCESS_ALERT_VALUES,"Info",JOptionPane.INFORMATION_MESSAGE);
				}
			}else{
				JOptionPane.showMessageDialog(null, Strings.ERROR_ALERT_VALUES,"Error",JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (ParseException e) {
			txtRed.setValue(txtRed.getValue());
			txtYellow.setValue(txtYellow.getValue());
		}
	}
}
