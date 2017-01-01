package view;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import javax.swing.table.DefaultTableModel;

public class MainTable extends CustomTable {
	
	public MainTable(DefaultTableModel model){
		super(model);
	}

	@Override
	public void addRow(File file) {
        DefaultTableModel model = (DefaultTableModel)this.getModel();
        String[] nombreArchivo = null;
  
        try {
            String cadena;
            String dia = null;
            String mes = null;
            String anno = null;
            String horas = null;
            String minutos = null;
            String segundos = null;
            FileReader archivo = new FileReader(file.getAbsolutePath());
            
            System.out.println(file.getAbsolutePath());
            LineNumberReader numeroLinea = new LineNumberReader(archivo);
            while ((cadena = numeroLinea.readLine()) != null) {
                if (numeroLinea.getLineNumber() != 3) continue;
                mes = cadena.substring(10, 13);
                dia = cadena.substring(14, 16);
                horas = cadena.substring(17, 19);
                minutos = cadena.substring(20, 22);
                segundos = cadena.substring(23, 25);
                anno = cadena.substring(30, 34);
            }
            nombreArchivo = file.getName().split("\\.");
            model.addRow(new Object[]{nombreArchivo[0], dia + '/' + mes + '/' + anno, horas + ':' + minutos + ':' + segundos});
            numeroLinea.close();
            
            addDropdowns();
            
            //find absorbance for new row
        }
        catch (IOException ex) {
        	System.out.println(ex.toString());
        }
		
	}
	

}
