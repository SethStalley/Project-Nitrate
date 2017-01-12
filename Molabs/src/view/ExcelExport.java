
package view;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.swing.JTable;
import jxl.CellView;
import jxl.Workbook;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelExport {
    private File archi;
    private List<JTable> tabla;
   
    private WritableCellFormat fomato_fila;
    private WritableCellFormat fomato_columna;

    public ExcelExport(List<JTable> tab, File ar) throws Exception {
        this.archi = ar;
        this.tabla = tab;
        if (tab.size() >= 0) return;
        throw new Exception("ERROR");
    }

    public boolean export() {
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(this.archi));
            WritableWorkbook w = Workbook.createWorkbook(out);
            w.createSheet("Nitratos", 0);
            int index = 0;
            do {
                if (index >= this.tabla.size()) {
                    w.write();
                    w.close();
                    out.close();
                    return true;
                }
                JTable table = this.tabla.get(index);
                WritableSheet s = w.getSheet(0);
         
                addHeader(s,table);
                
                for (int i = 0; i < table.getColumnCount(); ++i) {
                    for (int j = 0; j < table.getRowCount(); ++j) {
                        Object objeto = table.getValueAt(j, i);
                        if(objeto == null) {
                        	this.createFilas(s, i, j+1, "");
                        } else {
                        	String value = String.valueOf(objeto);       
                        	this.createFilas(s, i, j+1, value);
                        }
                    }
                    
                    resizeColumn(s, table.getColumnCount());
                }
                ++index;
            } while (true);
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        catch (WriteException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    private void resizeColumn(WritableSheet s, int columnCount) {
    	for(int x=0;x<columnCount;x++) {
    	    CellView cell=s.getColumnView(x);
    	    cell.setAutosize(true);
    	    s.setColumnView(x, cell);
    	}
	}

	private void addHeader(WritableSheet sheet, JTable table) throws WriteException {
    	for(int i = 0; i<table.getColumnCount(); i++ )
    		this.createColumna(sheet, table.getColumnName(i), i);
    }

	private void createColumna(WritableSheet sheet, String columna, int number_columna) throws WriteException {
        WritableFont times10pt = new WritableFont(WritableFont.TAHOMA, 14);
        WritableCellFormat times = new WritableCellFormat(times10pt);
        WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TAHOMA, 11, WritableFont.BOLD, false, UnderlineStyle.SINGLE);
        this.fomato_columna = new WritableCellFormat(times10ptBoldUnderline);
        CellView cevell = new CellView();
     
        cevell.setFormat(times);
        cevell.setFormat(this.fomato_columna);
        this.addColumna(sheet, number_columna, 0, columna, this.fomato_columna);
    }

    private void createFilas(WritableSheet sheet, int number_columna, int filas, String name_filas) throws WriteException {
        WritableFont times10pt = new WritableFont(WritableFont.ARIAL, 12);
        times10pt.setColour(Colour.GOLD);
        WritableCellFormat times = new WritableCellFormat(times10pt);
        times.setBorder(jxl.write.Border.TOP, BorderLineStyle.MEDIUM, Colour.GOLD);
        WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);
        this.fomato_fila = new WritableCellFormat(times10ptBoldUnderline);
        CellView cevell = new CellView();
        cevell.setFormat(times);
        cevell.setFormat(this.fomato_fila);
        this.addFilas(sheet, number_columna, filas, name_filas, this.fomato_fila);
    }
    

    private void addColumna(WritableSheet sheet, int column, int row, String s, WritableCellFormat format) throws RowsExceededException, WriteException {
        Label label = new Label(column, row, s, format);
        sheet.addCell(label);
    }

    private void addFilas(WritableSheet sheet, int column, int row, String value, WritableCellFormat format) throws WriteException, RowsExceededException {  	
    	try {
    		Double doubleValue = Double.parseDouble(value);
    		Number label = new Number(column, row, doubleValue);
    		sheet.addCell(label);
    	} catch (Exception e) {
    		Label label = new Label(column, row, value, format);
    		sheet.addCell(label);
    	} 	
        
    }
    
}

