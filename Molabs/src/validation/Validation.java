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
				return "El n�mero debe tener al menos un d�cimal. Ejemplo: 123.1";
			}
			
			String text = Double.toString(Math.abs(number));
			int integerPlaces = text.indexOf('.');
			int decimalPlaces = text.length() - integerPlaces - 1;
			
			System.out.println(text);
			
			if (integerPlaces != 3){
				return "El n�mero ingresado debe ser de 3 d�gitos. Ejemplo: -123.1";
			}
			if (decimalPlaces != 1){
				return "El n�mero ingresado debe contener solamente un decimal. Ejemplo: 123.1";
			}
			return null;
		}
		catch(Exception e){
			return "El dato ingresado debe ser un n�mero";
		}
	}
}