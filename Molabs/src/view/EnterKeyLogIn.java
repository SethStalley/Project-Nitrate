package view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EnterKeyLogIn extends KeyAdapter {
	
	private LoginScreen window;
	
	public EnterKeyLogIn(LoginScreen log) {
		this.window = log;
	}
	
    @Override 
    public void keyReleased(KeyEvent event) {
    	
        if (event.getKeyCode()==KeyEvent.VK_ENTER) {
        	
        	window.validateEntry();
        }   
    }
}
