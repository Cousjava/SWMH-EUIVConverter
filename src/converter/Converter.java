package converter;

import java.io.*;
import java.util.*;

public class Converter {

	BufferedReader savein;
	HashMap dynastys;
	String in;
	String date;
	String version;
	
	public Converter (String savegame) throws FileNotFoundException{
		System.out.println("Opening file " + savegame);
		savein = new BufferedReader(new FileReader(savegame));
		dynastys = new HashMap<Integer, String>();
	}
	
	public void Run() throws IOException{		
		do {
			in = savein.readLine();
			//TODO: check flags
			if (in.contains("dynasties=")){
				dynastyIn();
			}
			if (in.contains("character=")){
				charactersIn();
			}
			
		} while (in != null);
		
	}
	
	private void dynastyIn() throws IOException{
		System.out.println("Reading in dynasties...");
		int dynid = 0;
		String dynName;
		do {
			in = savein.readLine();
			if (in.contains("=")){
				
				String[] split = in.split("=");
				try {
					dynid = Integer.parseInt(split[0]);
				} catch (NumberFormatException e) {
					if (in.contains("name")){
						dynName = in.split("\"")[2];
						dynastys.put(dynid, dynName);
						dynName = null;
						dynid = 0;
					}
				}
			}
			
		} while (in.contains("character=")==false && in != null);		
		
	}

	private void charactersIn(){
		System.out.println("Reading in characters");
		
	
	}
}
