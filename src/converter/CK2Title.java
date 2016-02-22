package converter;

public class CK2Title implements Cloneable{

	String name;
	String religion;
	String sLeige;
	CK2Title leige;
	int iHolder;
	CK2Char holder;
	

	
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
