package view;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTable;

class RadioButtonEditor extends DefaultCellEditor implements ItemListener {
	
	private JRadioButton button;
	private CalibrationTable table;

	  public RadioButtonEditor(JCheckBox checkBox, CalibrationTable table) {
	    super(checkBox);
	    this.table = table;
	  }

	  public Component getTableCellEditorComponent(JTable table, Object value,
	      boolean isSelected, int row, int column) {
	    if (value == null)
	      return null;
	    button = (JRadioButton) value;
	    button.addItemListener(this);
	    return (Component) value;
	  }

	  public Object getCellEditorValue() {
	    button.removeItemListener(this);
	    return button;
	  }

	  public void itemStateChanged(ItemEvent e) {
		if(button.isSelected())
			table.graphCalibration();
	    super.fireEditingStopped();
	    
	  }
}
