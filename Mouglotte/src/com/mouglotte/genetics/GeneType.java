package com.mouglotte.genetics;

public enum GeneType {

	 DOMINANT ("All�le dominant"),
	 RECESSIVE ("All�le r�c�ssif"),
	 AVERAGE ("Moyenne entre les deux all�les"),
	 BEST ("Meilleure valeur des deux all�les"),
	 WORST ("Pire valeur des deux all�les");
	 
	// Nom du style
	private String name = "";
	
	//Constructeur
	GeneType(String name){
	  this.name = name;
	}
	
	// Conversion en String
	public String toString(){
	  return name;
	}
}
