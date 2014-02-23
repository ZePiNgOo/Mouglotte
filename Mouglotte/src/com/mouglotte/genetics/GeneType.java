package com.mouglotte.genetics;

public enum GeneType {

	 DOMINANT ("Allèle dominant"),
	 RECESSIVE ("Allèle récéssif"),
	 AVERAGE ("Moyenne entre les deux allèles"),
	 BEST ("Meilleure valeur des deux allèles"),
	 WORST ("Pire valeur des deux allèles");
	 
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
