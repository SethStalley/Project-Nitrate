package view;

import java.util.Date;
import java.util.Vector;

import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import values.Strings;

public class SortableJTableModel extends DefaultTableModel{
	
	
	public SortableJTableModel(String[] strings, int i) {
		super(strings, i);		
	}
	
	/**
	 * This is an efficient N time ordering, as it only places the new row in the correct position.
	 * It does not sort all standing rows!
	 * 
	 * @param int index of columns which holds date object used to sort this model
	 */
	public void sortAddedRowByDate(int columnIndex) {
		int rowCount = this.getRowCount();
		
		Date newDate = (Date) this.getValueAt(rowCount-1, columnIndex);
		
		for (int i=0; i<rowCount; i++) {
			Date date = (Date) this.getValueAt(i, columnIndex);

			if (date.before(newDate)) {
				this.moveRow(rowCount-1, rowCount-1, i);
				break;
			}
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
			
	public void removeColumn(int column) {
        columnIdentifiers.remove(column);
        for (Object row: dataVector) {
            ((Vector) row).remove(column);
        }
        fireTableStructureChanged();
    }
	
}
