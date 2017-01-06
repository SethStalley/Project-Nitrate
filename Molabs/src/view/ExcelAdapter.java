package view;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import values.Strings;

public class ExcelAdapter extends KeyAdapter {

    private static final String LINE_BREAK = "\r"; 
    private static final String CELL_BREAK = "\t"; 
    private static final Clipboard CLIPBOARD = Toolkit.getDefaultToolkit().getSystemClipboard(); 

    private final JTable table; 

    public ExcelAdapter(JTable table) { 
            this.table = table; 
    } 

    @Override 
    public void keyReleased(KeyEvent event) { 
            if (event.isControlDown() || event.isMetaDown()) { 
                    if (event.getKeyCode()==KeyEvent.VK_C) { // Copy                         
                            copyToClipboard(false); 
                    } else if (event.getKeyCode()==KeyEvent.VK_X) { // Cut 
                            copyToClipboard(true); 
                    } else if (event.getKeyCode()==KeyEvent.VK_V) { // Paste 
                            pasteFromClipboard();           
                    } 
            } 
    } 

    public void copyToClipboard(boolean isCut) { 
    		cancelEditing();
            int numCols=table.getSelectedColumnCount(); 
            int numRows=table.getSelectedRowCount(); 
            int[] rowsSelected=table.getSelectedRows(); 
            int[] colsSelected=table.getSelectedColumns(); 
            if (numRows!=rowsSelected[rowsSelected.length-1]-rowsSelected[0]+1 || numRows!=rowsSelected.length || 
                            numCols!=colsSelected[colsSelected.length-1]-colsSelected[0]+1 || numCols!=colsSelected.length) {

                    JOptionPane.showMessageDialog(null, "Invalid Copy Selection", "Invalid Copy Selection", JOptionPane.ERROR_MESSAGE);
                    return; 
            } 

            StringBuffer excelStr=new StringBuffer(); 
            for (int i=0; i<numRows; i++) { 
                    for (int j=0; j<numCols; j++) { 
                            excelStr.append(escape(table.getValueAt(rowsSelected[i], colsSelected[j]))); 
                            if (isCut) { 
                                    table.setValueAt(null, rowsSelected[i], colsSelected[j]); 
                            } 
                            if (j<numCols-1) { 
                                    excelStr.append(CELL_BREAK); 
                            } 
                    } 
                    excelStr.append(LINE_BREAK); 
            } 

            StringSelection sel  = new StringSelection(excelStr.toString()); 
            CLIPBOARD.setContents(sel, sel); 
    } 

    public void pasteFromClipboard() { 
    		cancelEditing();
            int startRow=table.getSelectedRows()[0]; 
            int startCol=table.getSelectedColumns()[0];

            String pasteString = ""; 
            try { 
                    pasteString = (String)(CLIPBOARD.getContents(this).getTransferData(DataFlavor.stringFlavor)); 
            } catch (Exception e) { 
                    JOptionPane.showMessageDialog(null, "Invalid Paste Type", "Invalid Paste Type", JOptionPane.ERROR_MESSAGE);
                    return; 
            }

            String[] lines = pasteString.split(LINE_BREAK);
            String[] lines2 = pasteString.split("\n");
            
            if(lines2.length > lines.length)
            	lines = lines2;
            
            for (int i=0 ; i<lines.length; i++) { 
                    String[] cells = lines[i].split(CELL_BREAK); 
                    for (int j=0 ; j<cells.length; j++) { 
                            if (table.getRowCount()>startRow+i && table.getColumnCount()>startCol+j) { 
                                    table.setValueAt(cells[j], startRow+i, startCol+j); 
                            } 
                    } 
            }
            
            if(startCol == Strings.TYPE_COLUMN_INDEX && table.getSelectedColumns().length == 1) {
            	int numRows=table.getSelectedRowCount(); 
                int[] rowsSelected=table.getSelectedRows();
            	for (int i=0; i<numRows; i++) {
            		table.setValueAt(lines[0], rowsSelected[i], startCol);
            	}
            }
    } 

    private void cancelEditing() { 
            if (table.getCellEditor() != null) { 
                    table.getCellEditor().cancelCellEditing(); 
        } 
    } 

    private String escape(Object cell) { 
    	if(cell != null) {
    		return cell.toString().replace(LINE_BREAK, " ").replace(CELL_BREAK, " "); 
    	}
        return "";
    } 
}