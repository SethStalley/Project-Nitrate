package view;

import java.util.Date;

import javax.swing.table.DefaultTableModel;

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

			if (newDate.before(date)) {
				this.moveRow(rowCount-1, rowCount-1, i);
				break;
			}
		}
	}
}
