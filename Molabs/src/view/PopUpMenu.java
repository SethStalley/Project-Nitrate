package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import controller.Controller;
import values.Strings;
import values.rightclickIdentifier;

public class PopUpMenu extends JPopupMenu {
    JMenuItem anItem;
    public PopUpMenu(Controller controller, rightclickIdentifier type, int index){
        if(type == rightclickIdentifier.ABSORBANCE){
        		deleteAbsorbance(controller, index);
        }else{
        	if(type == rightclickIdentifier.CONCENTRATION){
        		deleteConcentration(controller, index);
        	}else{
        		deleteAbsorbance(controller, index);
        	}
        }
    }
    
    private void deleteAbsorbance(Controller controller, int index) {
    	anItem = new JMenuItem("Delete Absorbance");
    	anItem.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent event) {
				if(confirmDelete(Strings.ALERT_DELETE_COLUMN_ABSORBANCE)){
					controller.removeAbsorbanceColumn(index);
				}
	      }
    	});
        add(anItem);
	}

	private void deleteConcentration(Controller controller, int index){
    	anItem = new JMenuItem("Delete Concentration");
    	anItem.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent event) {
				if(confirmDelete(Strings.ALERT_DELETE_COLUMN_CONCENTRATION)){
					controller.removeConcentrationColumn(index);
				}
	      }
    	});
        add(anItem);
    }
    private void deleteAbsorbance(Controller controller, int index){
    	anItem = new JMenuItem("Delete Abosrbance");
    	anItem.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent event) {
				if(confirmDelete(Strings.ALERT_DELETE_COLUMN_ABSORBANCE)){
					controller.removeAbsorbance(index);
				}
	      }
    	});
        add(anItem);
    }
	private boolean confirmDelete(String alert){
		int dialogResult = JOptionPane.showConfirmDialog (null, alert);
		if(dialogResult == JOptionPane.YES_OPTION){
		  return true;
		}
		return false;
	}
} 
