package converter;

import java.io.*;
import java.util.*;

public class Converter {

	BufferedReader savein;
	HashMap<Integer, String> dynastys;
	ArrayList<CK2Prov> CK2Provs; 
	String in;
	String date;
	String version;
	
	public static void main(String[] args) {
		Converter convert;
		try {
			convert = new Converter(args[0]);
			convert.Run();
		//} catch (ArrayIndexOutOfBoundsException e){
			//System.out.println("Error: No file specified");
		} catch (FileNotFoundException e) {
			System.out.println("Error: File not found");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

	}
	
	public Converter (String savegame) throws FileNotFoundException{
		System.out.println("Opening file " + savegame);
		savein = new BufferedReader(new FileReader(savegame));
		dynastys = new HashMap<Integer, String>();
		CK2Provs = new ArrayList<CK2Prov>();
	}
	
	public void Run() throws IOException{		
		do {
			in = savein.readLine();
			if (in.contains("version")) {
				version = in.split("=")[1];
				version = version.replaceAll("\\s","");
				continue;
			}
			if (in.contains("date") && date ==null){
				date = in.split("=")[1];
				date = version.replaceAll("\\s","");
			}
			//TODO: check flags
			if (in.contains("dynasties=")){
				dynastyIn();
			}
			if (in.contains("character=")){
				charactersIn();
			}
			//Ignore delayed events
			//Ignore relations
			//Ignore ambitions
			//Ignore religion
			if (in.contains("provinces=")){
				provincesIn();
			}
			if (in.contains("titles=")){
				titlesIn();
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
						dynName = in.split("\"")[1];
						dynastys.put(dynid, dynName);
						dynName = null;
						dynid = 0;
					}
				}
			}
			
		} while (in.contains("character=")==false && in != null);		
		
	}

	private void charactersIn() throws IOException{
		System.out.println("Reading in characters...");
		do {
			in = savein.readLine();
			
		}while (in.contains("delayed_event=")==false && in != null);
	
	}
	
	private void provincesIn() throws IOException{
		System.out.println("Reading in provinces...");
		int brackets = 0;
		boolean techlevel = false;
		CK2Prov prov = new CK2Prov();
		do {
			in = savein.readLine();
			if (in.contains("{")) {
				brackets++;continue;
			}
			if (in.contains("tech_levels")){
				techlevel = true;
			}
			if (in.contains("}") ==true && techlevel==true){
				String[] splitter = in.split(" ");				
				for(int i = 0; i < splitter.length - 1; i++){
					prov.techTotal += Float.parseFloat(splitter[i]);
				}
				brackets--;continue;
			}
			if (in.contains("}")) {
				brackets--;continue;
			}
			if (brackets==1){
				if (prov.id != 0){
					CK2Provs.add(prov.id, prov);
				}
				prov = new CK2Prov();
				prov.id = Integer.parseInt(in.split("=")[0]);
			}
			if (in.contains("religion")){
				prov.religion = in.split("=")[1];
			} else if (in.contains("culture")){
				prov.culture = in.split("=")[1];
			} else if (in.contains("ca")){
				prov.ca_builds++;
			} else if (in.contains("ct")){
				prov.ct_builds++;
			} else if (in.contains("tp")){
				prov.tp_builds++;
			} else if (in.contains("tb_hillfort")){
				prov.tb_hillfort++;
			} else if (in.contains("tb_market_town")){
				prov.tb_market_town++;
			} else if (in.contains("tb_shipyard")){
				prov.tb_shipyard++;
			} else if (in.contains("tb_training_ground")){
				prov.tb_trainingground++;
			}else if (in.contains("tb_war_camp") || in.contains("tb_steppe_barr")){
				prov.tb_war_camp++;
			} else if (in.contains("tb_practice_range") || in.contains("tb_stable")){
				prov.tb_practice_range++;
			} else if (in.contains("arsenal")){
				prov.tb_arsenal++;
			}
			
		} while (in.contains("title=")==false && in != null);
	}

	private void titlesIn(){
		System.out.println("Reading in titles...");
		
	}
}
