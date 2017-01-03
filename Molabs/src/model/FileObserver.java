package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import controller.Controller;
import values.Preferences;

import static java.nio.file.StandardWatchEventKinds.*;;

public class FileObserver extends Thread{

	private Controller controller;
	private static FileObserver instance = null;
	private WatchService watcher;
	private WatchKey key = null;
	private boolean run = true;
	private Path dir = null;
	
	public FileObserver(Controller controller) {
		this.controller = controller;
	}
	
	public static FileObserver getInstance(Controller controller) {
		if (instance == null) {
			return new FileObserver(controller);
		} else {
			return instance;
		}
	}
	
	public void stopObserver() {
		this.run = false;
	}
	
	public void startObserver(String path) {
		this.run = true;
		this.interrupt();
		this.start();
	}
	
	public void run() {
		try {
			this.dir = Paths.get(Preferences.getInstance().getLiveFolderPath());
			
			while (this.run) {
				this.watcher = FileSystems.getDefault().newWatchService();
				
				dir.register(watcher, ENTRY_CREATE);
				this.key = watcher.take();
				
				Thread.sleep(500);
				
				//handle events
				List<WatchEvent<?>> events =  key.pollEvents();
				for (WatchEvent<?> event: events ) {
					
		             // Context for directory entry event is the file name of entry
					WatchEvent<Path> ev = (WatchEvent<Path>) event;
					
		            Path path = ev.context();
		            
		            controller.addFile(Paths.get(dir.toString(), path.toString()).toString());
		            
		        }

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}