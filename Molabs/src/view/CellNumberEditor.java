package view;

import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.math.RoundingMode;
import java.awt.Component;
import java.awt.Toolkit;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;

import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import values.OptionErrorMessage;
import values.Strings;

public class CellNumberEditor extends DefaultCellEditor {
    JFormattedTextField formattedTextField;
    NumberFormat integerFormat;

    public CellNumberEditor(){
        super(new JFormattedTextField());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        JFormattedTextField editor = (JFormattedTextField) super.getTableCellEditorComponent(table, value, isSelected, row, column);

        if (value instanceof String){
        	String valueStr = ((String)value).replace(',', '.');
        	float newValue = Float.parseFloat(valueStr);
            NumberFormat numberFormatB = NumberFormat.getInstance();

            editor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                            new NumberFormatter(numberFormatB)));

            editor.setHorizontalAlignment(SwingConstants.RIGHT);
            editor.setValue(newValue);
        }
        else if(value == null){
        	editor.setValue(null);
        }else{
        	JOptionPane.showMessageDialog(null, "invalid!!!","Error",JOptionPane.INFORMATION_MESSAGE);
        }
        return editor;
    }

    @Override
    public boolean stopCellEditing() {
        try {
            // try to get the value
            this.getCellEditorValue();
            return super.stopCellEditing();
        } catch (Exception ex) {
        	ex.printStackTrace();
            return false;
        }

    }

    @Override
    public Object getCellEditorValue() {
        // get content of textField
        String str = (String) super.getCellEditorValue();
        if (str == null) {
            return null;
        }

        if (str.length() == 0) {
            return null;
        }

        // try to parse a number
        try {
            ParsePosition pos = new ParsePosition(0);
            Number n = NumberFormat.getInstance().parse(str, pos);
            if (pos.getIndex() != str.length()) {
                throw new ParseException(
                        "parsing incomplete", pos.getIndex());
            }

            // return an instance of column class
            return new String(n.toString());

        } catch (ParseException pex) {
            return null;
        }
    }
}
