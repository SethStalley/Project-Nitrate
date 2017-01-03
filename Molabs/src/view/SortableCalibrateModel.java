package view;

import values.Strings;

public class SortableCalibrateModel extends SortableJTableModel {

	public SortableCalibrateModel(String[] strings, int i) {
		super(strings, i);
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
        if(column == Strings.STATUS_COLUMN_INDEX){ //for dropdown
        	return true;
        }else return false;
    }

}
