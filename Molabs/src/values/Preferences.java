package values;

import java.awt.Color;

/*
 * Singleton class that stores user preferences during runtime and on load
 */
public class Preferences {

	public static final int WINDOW_NORMAL_RGB = new Color(204,204,204).getRGB();
	public static final int WINDOW_OBSERVER_RUNNING_RGB = new Color(160,157,157).getRGB();
	public static final int BTN_COLOR_RED = new Color(160, 0, 0).getRGB();
	public static final int BTN_COLOR_BLUE = new Color(6, 114, 155).getRGB();
	
	//Logos
	public static final String IMG_LOGO = "/resources/Logo.png";
	public static final String IMG_ICON = "/resources/Icon.png";
	
	//calibration table
	public static final int CALIBRATION_STATUS_WIDTH = 115;
	public static final int CALIBRATION_DATE_WIDTH = 260;
	public static final int CALIBRATION_WAVE_WIDTH = 125;
	
	//main¿table
	
	public static final int DATE_COLUMN_WIDTH = 200;
	public static final int DEFAULT_COLUMN_WIDTH = 80;
	public static final int ADDED_COLUMN_WIDTH = 145;
	
}
