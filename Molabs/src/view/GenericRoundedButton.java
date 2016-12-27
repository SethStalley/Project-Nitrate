package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;

public class GenericRoundedButton extends JButton {

	 public GenericRoundedButton(String title) {
	   super(title);
	   init();
	 }

	 public GenericRoundedButton() {
	   super();
	   init();
	 }

	 private void init() {
	   setFocusable(false);
	 }

	 public void paint(Graphics g) {
	   // Don't need to set these to get transparent button --
	   // we'll simply not draw it!
//	        setBackground(getParent().getBackground());
//	        setBorder(Styles.BORDER_NONE);

	   // Don't draw the button or border
	   this.setContentAreaFilled(false);
	   this.setBorderPainted(false);

	   Graphics2D g2d = (Graphics2D)g;
	      
	   // Anti-aliased lines and text
	   g2d.setRenderingHint(
	        RenderingHints.KEY_ANTIALIASING,
	        RenderingHints.VALUE_ANTIALIAS_ON);
	   g2d.setRenderingHint(
	        RenderingHints.KEY_TEXT_ANTIALIASING,
	        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    
	   // This is needed on non-Mac so text
	   // is repainted correctly!
	   //super.paint(g);

	   // Make it grey #DDDDDD, and make it round with 1px
	   // black border
	   g2d.setColor(getBackground());
	   g2d.fillRoundRect(0,0,getWidth(),getHeight(),18,18);
	   g2d.setColor(Color.BLACK);
	   g2d.drawRoundRect(0,0,getWidth()-1,getHeight()-1,18,18);

	   // Determine the label size so can center it
	   FontRenderContext frc = new FontRenderContext(null, false, false);
	   Rectangle2D r = getFont().getStringBounds(getText(), frc);

	   float xMargin = (float)(getWidth()-r.getWidth())/2;
	   float yMargin = (float)(getHeight()-getFont().getSize())/2;

	   // Draw the text in the center
	   g2d.setColor(Color.WHITE); //changes color of turtle
	   g2d.drawString(getText(),xMargin,
	     (float)getFont().getSize()+yMargin);
	 }
	}
