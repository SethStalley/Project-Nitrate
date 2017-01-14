package view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EnterKey extends KeyAdapter {
	
	private MainWindowOwner window;
	
	public EnterKey(MainWindowOwner mainWindow) {
		this.window = mainWindow;
	}
	
    @Override 
    public void keyReleased(KeyEvent event) { 
        if (event.getKeyCode()==KeyEvent.VK_ENTER) {  
        	String wavelength = this.window.getWavelength();
        	((MainTable) window.mainTable).addAbsorbanceColumnFromWavelength(wavelength);
        }   
    }
}
