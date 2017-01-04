package model;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import values.Strings;

public class TextFile extends JSON_Exportable{

	private String name;
	private Date date;
	private String concentration;
	private String type;
	
	private Hashtable<String, String> absorbances = new Hashtable<String, String>();
	
	public TextFile(String path) {
		this.parseTextFile(path);
	}
	
	public TextFile(String name, Date date) {
		this.name = name;
		this.date = date;
		this.type = Strings.SAMPLE;
	}
	
	/*
	 * returns null of absorbance was no found
	 */
	public String getAbsorbance(String wavelength) {
		return absorbances.get(wavelength);
	}
	
	private boolean parseTextFile(String path) {
		boolean success;
		
		success = loadMetaData(path);
		success &= loadAbsorbances(path);
		
		return success;
	}
	
	private boolean loadMetaData(String path) {
		try {
            String cadena;
            String dateStr = "";

            File file = new File(path);
            FileReader fileReader = new FileReader(path);
            
            LineNumberReader numeroLinea = new LineNumberReader(fileReader);
            while ((cadena = numeroLinea.readLine()) != null) {
                if (numeroLinea.getLineNumber() != 3) continue;
                dateStr = cadena.substring(10, 34);
            }
            numeroLinea.close();
            fileReader.close();
            
            this.name = file.getName();
 
            SimpleDateFormat format = new SimpleDateFormat(
            		"MMM dd HH:mm:ss zzz yyyy");
            this. date = format.parse(dateStr);
            
            this.type = Strings.SAMPLE;
            
        } catch (Exception e) {
        	System.out.println("ERROR parsing text file");
        
        	return false;
        }
		return true;
	}
	
	private boolean loadAbsorbances(String path) {
		File file;
		
        try {
            String cadena;
            
            file = new File(path);
            FileReader archivo = new FileReader(file.getAbsolutePath());
            LineNumberReader numeroLinea = new LineNumberReader(archivo);
            while ((cadena = numeroLinea.readLine()) != null) {
                if (numeroLinea.getLineNumber() <= 18) 
                	continue;
                String[] datos = cadena.split("\\s+");
                
                
                String waveLength = datos[0].substring(0, 5);
                String absorbance = datos[1];
                
                //add absorbance's to hash table
                this.absorbances.put(waveLength, absorbance);
            }   
            numeroLinea.close();
        }
        catch (IOException ex) {
        	ex.printStackTrace();
        	return false;
        }
        
        return true;
	}
	
	public String getName() {
		return name;
	}

	public Date getDate() {
		return date;
	}

	public String getConcentration() {
		return concentration;
	}

	public String getType() {
		return type;
	}
}
