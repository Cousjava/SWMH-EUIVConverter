package converter;

public class CK2Char implements Cloneable {

	int id;
	String name;
	int dynasty;
	String birthdate; //As string because the Clauswitz engine uses this format
	String religion;
	String culture;
	int wealth;
	String attributes;
	String traits;
	String government;
	String capital;
	
	protected Object clone(){
		 try {
	            return super.clone();
	        }
	        catch (CloneNotSupportedException e) {
	            // This should never happen
	            throw new InternalError(e.toString());
	        }
	}
}
