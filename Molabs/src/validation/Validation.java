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
				return "El número debe tener al menos un décimal. Ejemplo: 123.1";
			}
			
			String text = Double.toString(Math.abs(number));
			int integerPlaces = text.indexOf('.');
			int decimalPlaces = text.length() - integerPlaces - 1;
			
			System.out.println(text);
			
			if (integerPlaces != 3){
				return "El número ingresado debe ser de 3 dígitos. Ejemplo: -123.1";
			}
			if (decimalPlaces != 1){
				return "El número ingresado debe contener solamente un decimal. Ejemplo: 123.1";
			}
			return null;
		}
		catch(Exception e){
			return "El dato ingresado debe ser un número";
		}
	}
}