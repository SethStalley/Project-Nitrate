package view;

import java.text.SimpleDateFormat;

import javax.swing.table.DefaultTableCellRenderer;

public class CellRenderDateAsYYMMDD extends DefaultTableCellRenderer {
    public CellRenderDateAsYYMMDD() { super(); }

    @Override
    public void setValue(Object value) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        setText((value == null) ? "" : sdf.format(value));
    }
}