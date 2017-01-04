package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import controller.Controller;

import static java.nio.file.StandardWatchEventKinds.*;;

public class FileObserver extends Thread{
	private view.MainTable mainTable;
	private Controller controller;
	private WatchService watcher;
	private WatchKey key = null;
	private boolean run = true;
	private Path dir = null;
	
	public FileObserver(Controller controller, view.MainTable mainTable) {
		this.controller = controller;
		this.mainTable = mainTable;
	}
	
	
	public void stopObserver() {
		this.run = false;
		
		if(key != null)
			this.key.cancel();
	}
	
	public boolean startObserver(String path) {
		try {
			this.dir = Paths.get(path);
			this.run = true;
			this.start();
			return true;
		} catch (InvalidPathException | NullPointerException ex) {
			return false;
		}
		
	}
	
	public void run() {
		
		while (this.run) {
			try {
				this.watcher = FileSystems.getDefault().newWatchService();
				
				dir.register(watcher, ENTRY_CREATE);
				this.key = watcher.take();
				
				Thread.sleep(500);
				
				//handle events
				List<WatchEvent<?>> events =  key.pollEvents();
				for (WatchEvent<?> event: events) {
					
					//if the observer has since been stopped
					if (!this.run)
						break;
					
		             // Context for directory entry event is the file name of entry
					WatchEvent<Path> ev = (WatchEvent<Path>) event;
					
		            Path path = ev.context();
		            String completePath = Paths.get(dir.toString(), path.toString()).toString();
		           
		            this.mainTable.addRow(new File(completePath));
				}
				
				this.watcher.close();
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		
		}
	}
	
	
}