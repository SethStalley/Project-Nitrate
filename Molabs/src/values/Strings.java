package values;

public class Strings {
	/*
	 * Errors
	 */
	public static final String ERROR_SELECT_FILE = "Selected file has an unexpected format or already exists.";
	public static final String ERROR_WAVELENGTH_INPUT = "Wavelength must be a 3 digit number and one decimal.";
	public static final String ERROR_SELECT_COLUMN = "No column selected.";
	public static final String ERROR_SELECT_ABSORBANCE_COLUMN = "Select an Absorbance Column.";
	public static final String ERROR_NEW_CALIBRATION = "Select more than one concentrations for the new calibration.";
	public static final String ERROR_CALIBRATE = "There is an error with the calibration values.";
	public static final String ERROR_NO_ACTIVE_CALIBRATION = "Please select one calibration as type Active."; //unkown
	public static final String ERROR_NO_ABSORBANCE_SELECTED = "Please select one absorbance column";
	public static final String ALERT_DELETE_ROW = "Are you sure you want to delete this row (s)?";
	public static final String ALERT_DELETE_COLUMN_CONCENTRATION = "Are you sure you want to delete this concentration?";
	public static final String ALERT_DELETE_COLUMN_ABSORBANCE = "Are you sure you want to delete this absorbance, it will delete all concentrations and calibrations that used this absorbance?";
	public static final String ERROR_CONCENTRATION_COLUMN = "The current value is invalid, it should be a number and can include floating point decimals with a '.'";
	public static final String ERROR_LOG_IN = "Combination of username and password incorrect.";
	public static final String ACCEPT_ERROR_BUTTON = "Okay";
	public static final String ERROR_NO_ABSORBANCE = "No absorbance value was found for that wavelength. Please choose different wavelength.";

	
	/*
	 * GUI Labels
	 */
	public static final String LABEL_SAVE_FILE = "Save File";
	public static final String LABEL_EXCEL_FILE = "Excel file type";
	public static final String LABEL_MOLABS_FILE = "Molabs file type";
	public static final String STD = "STD";
	public static final String SAMPLE = "Sample";
	public static final String CALIBRATE = "Calibrate";
	
	/*
	 * Events
	 */
	public static final String SUCCESS_TABLE_EXPORT = "Table exported successfully.";
	
	/*
	 * Table 
	 */
	public static final int CALIBRATIONTABLE_COLUMN_DATE = 1;
	public static final int CALIBRATIONTABLE_COLUMN_WAVELENGTH = 2;
	public static final int TYPE_COLUMN_INDEX = 3;	
	public static final int MAINTABLE_COLUMN_DATE = 1;
	public static final int CONCENTRATION_COLUMN_INDEX = 4;
	public static final int STATUS_COLUMN_INDEX = 0;
	public static final String NO_ROW_SELECTED = "No row selected";
	public static final String ACTIVE = "Active";
	public static final String ERROR_STARTING_OBSERVER = "Could not start observer.\nSpecified path is probably incorrect";
	public static final int NUMBER_DEFAULT_COLUMNS = 5;
	public static final String ERROR_ABSORBANCE_COLUMN_MISSING = "The absorbance column for that wavelength does not exist";
	public static final String ERROR_COCENTRATION_EXISTS = "That concentration column already exists.";
	
	/*
	 * users
	 */
	public static final String ERROR_PASSWORD_CONFIRMATION = "The provided passwords does not match.";
	public static final String ERROR_PASSWORD_FORMAT = "Password must be at least 5 digits long.";
	public static final String ERROR_NO_USER_SELECTED = "Please select an user.";
	public static final String ERROR_NO_INTERNET = "Error. Please make sure you have Internet connection.";
	public static final String ERROR_USER_REPEATED = "There is already an user with that username. Please select an other one.";
	
	/*
	 * graphs 
	 */
	
	public static final double XVALUEMAX = 100;
	public static final double XVALUEMIN = -100;
	public static final double EXTRA_PORCENTAGE = 0.5;
	public static final int PUSH_GRAPH_SLEEP = 3; //3 seconds
	
	

}
