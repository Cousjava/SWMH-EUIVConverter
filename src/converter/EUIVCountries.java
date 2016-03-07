package converter;

import java.util.HashMap;

public class EUIVCountries extends HashMap<String, EUIVCountry> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int counteri;
	private int letters;
	
	public EUIVCountries(){
		super();
		counteri = 0;
		letters = 59;
	}
	
	
	public String put(EUIVCountry V) throws CountryTagFullException{
		String tag = "";
		if (letters < 34){
			throw new CountryTagFullException();
		}
		char l = (char) letters;
		tag += l;
		if (counteri < 10){
			tag += "0";
			tag += counteri;
		} else {
			tag += counteri;
		}
		super.put(tag, V);
		counteri++;
		if (counteri == 100){
			counteri = 0;
			letters --;
		}
		return tag;
	}
	
	
}
