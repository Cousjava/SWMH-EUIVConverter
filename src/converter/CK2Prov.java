package converter;

public class CK2Prov {

	int id;
	String name;
	String culture;
	String religion;
	boolean fort= false;
	
	//Building levels - Castles
	int ca_wall = 0;			//Castle Walls
	int ca_wall_q = 0;			//Castle Fortifications
	int ca_keep = 9;			//Keep
	int ca_militia_barraks=0;	//Militia training Ground
	int ca_training_grounds=0;	//Training Grounds
	int ca_barracks=0;			//Barracks
	int ca_stable=0;			//Stables
	int ca_town=0;				//Castle Town
	int ca_shipyard=0;			//Castle Shipyard
	int ca_cultural=0;			//Cultural building
	//Building levels - Cities
	int ct_wall_q=0;			//City Fortifications
	int ct_wall = 0;			//City Walls
	int ct_arsenal = 0;			//Merchant Republic Arsenal
	int ct_training_grounds = 0;//Training Grounds
	int ct_barracks = 0;		//City Barracks
	int ct_guard = 0;			//City Guard
	int ct_marketplace = 0;		//Marketplace
	int ct_port = 0;			//Port
	int ct_shipyard = 0;		//Shipyard
	int ct_university = 0;		//University
	//Building levels - Temples
	int tp_wall_q = 0;			//Temple Fortifications
	int tp_wall = 0;			//Temple Walls
	int tp_monastery = 0;		//Monastery
	int tp_barracks = 0;		//Barracks or Militia Quarters
	int tp_elite_barracks = 0;	//Barracks
	int tp_town = 0;			//Church town
	int tp_shipyard = 0;		//Shipyard
	int school = 0;				//School
	//Building levels - Tribal
	int tb_hillfort = 0;		//Tribal Hillfort
	int tb_market_town = 0;		//Tribal Town
	int tb_shipyard = 0;		//Shipyard
	int tb_traingground = 0;	//Tribal Training Ground
	int tb_war_camp = 0;		//Tribal War Camp or Steppe Barracks
	int tb_practice_range=0;	//Tribal Practice Range or Steppe Stable
	int tb_arsenal = 0;//Tribal training grounds/arsenal
	
	
}
