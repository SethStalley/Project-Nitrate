package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import controller.Controller;
import values.OptionErrorMessage;
import values.Strings;
import values.rightclickIdentifier;

public class PopUpMenu extends JPopupMenu {
    JMenuItem anItem;
    JMenuItem anItem2;
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
    	anItem2 = new JMenuItem("Graph Concentration");
    	anItem2.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent event) {
	    	  controller.graphConcentration(index);
	      }
    	});
        add(anItem2);
    }
	private boolean confirmDelete(String alert){
		int dialogResult = JOptionPane.showOptionDialog(null, alert, "Warning",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
				null, OptionErrorMessage.YES_NO_ENGLISH, OptionErrorMessage.YES_NO_ENGLISH[0]);
		if(dialogResult == JOptionPane.YES_OPTION){
		  return true;
		}
		return false;
	}
} 
