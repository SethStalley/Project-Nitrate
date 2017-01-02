package validation;

public class Validation {
	/**
	 * Class only for data validation. Returns significant messages.
	 */
	
	/**
	 * 
	 * @param pWavelength used to search for the absorbance in a txt file.
	 * @return descriptive message weather the absorbance is correct or not.
	 */
	
	public static String validWavelength(String wavelength){
		try{
			Double number = Double.parseDouble(wavelength);
			
			if (number % 1 == 0){
				return "Wavelength must have at least one decimal space. Ex: 123.1";
			}
			
			String text = Double.toString(Math.abs(number));
			int integerPlaces = text.indexOf('.');
			int decimalPlaces = text.length() - integerPlaces - 1;
			
			
			if (integerPlaces != 3){
				return "Wavelength must be a three digit number. Ex: -123.1";
			}
			if (decimalPlaces != 1){
				return "Wavelength must only contain one decimal space. Ex: 123.1";
			}
			return null;
		}
		catch(Exception e){
			return "Wavelength must be a number.";
		}
	}
}