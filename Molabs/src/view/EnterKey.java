package view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EnterKey extends KeyAdapter {
	
	private MainWindow window;
	
	public EnterKey(MainWindow mainWindow) {
		this.window = mainWindow;
	}
	
    @Override 
    public void keyReleased(KeyEvent event) { 
        if (event.getKeyCode()==KeyEvent.VK_ENTER) {  
        	String wavelength = this.window.txtWavelength.getText();
        	((MainTable) window.mainTable).addAbsorbanceColumnFromWavelength(wavelength);
        }   
    }
}
