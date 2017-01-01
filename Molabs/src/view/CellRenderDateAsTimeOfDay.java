package view;

import java.text.SimpleDateFormat;

import javax.swing.table.DefaultTableCellRenderer;

public class CellRenderDateAsTimeOfDay extends DefaultTableCellRenderer {
    public CellRenderDateAsTimeOfDay() { super(); }

    @Override
    public void setValue(Object value) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        setText((value == null) ? "" : sdf.format(value));
    }
}