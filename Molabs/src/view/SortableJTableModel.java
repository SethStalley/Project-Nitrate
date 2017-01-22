package view;

import java.util.Date;
import java.util.Vector;

import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import controller.DB;
import values.Strings;

public class SortableJTableModel extends DefaultTableModel{
	
	public SortableJTableModel(String[] strings, int i) {
		super(strings, i);		
	}
	
	
   @Override
    public Class<?> getColumnClass(int column) {
	   if (column == 1)
		   return Date.class;
	   else {
		   return String.class;
	   }
    }
	
	/**
	 * Override of method to make the jtable cells non-editable unless its the
	 * concentration row with STD type. True means editable
	 * 
	 * @param int index of columns of selected cell
	 * @param int index of rows of selected cell
	 */
	@Override
	public boolean isCellEditable(int row, int column) {
		if(DB.getInstance().getType().equals("user")){
			return false;
		}else{
	        if(column == Strings.TYPE_COLUMN_INDEX){ //for dropdown
	        	return true;
	        }else if(column == Strings.CONCENTRATION_COLUMN_INDEX){
	        	return true;
	        }
	        else if(column >= Strings.CONCENTRATION_COLUMN_INDEX && getColumnName(column).startsWith("A")){
	        	Object valueCustom = this.getValueAt(row, 0);
	        	if(valueCustom.toString().startsWith("Custom")){
	        		return true;
	        	}
	        	return false;
	        }else return false;
		}
        
    }
			
	public void removeColumn(int column) {
        columnIdentifiers.remove(column);
        for (Object row: dataVector) {
            ((Vector) row).remove(column);
        }
        fireTableStructureChanged();
    }
	
}
