package model;

import java.util.Date;
import java.util.Hashtable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainTable extends JSON_Exportable{
	
	private Hashtable<Date,TextFile> files;
	
	public MainTable() {
		this.files = new Hashtable<Date,TextFile>();
	}
	
	public Hashtable<Date,TextFile> getAllFiles() {
		return this.files;
	}
	
	public TextFile getFile(Date dateKey) {
		return this.files.get(dateKey);
	}
	
	public boolean addFile(String path) {
		TextFile txtData = new TextFile(path);
		
		if(txtData.getDate() != null) {
			this.files.put(txtData.getDate(), txtData);
			return true;
		}
		
		return false;
	}
	

}
