package view;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

public class CopyToClipboard extends AbstractAction{
	
	public CopyToClipboard(MainTable mainTable) {
        putValue(NAME, "Copy");
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Copied");
	}
	
}
