package com.mouglotte.genetics;
import java.util.Hashtable;

public class Karyotype {

	// Liste des paires chromosomes
	private Hashtable<String,ChromosomePaire> chromosomeColl;

	// Constructeur
	public Karyotype() {

		// Instanciation de la liste des paires de chromosomes
		this.chromosomeColl = new Hashtable<String,ChromosomePaire>();
		
		// Cr�ation des chromosomes 1 � 10
		createChromosome("1");
		createChromosome("2");
		createChromosome("3");
		createChromosome("4");
		createChromosome("5");
		createChromosome("6");
		createChromosome("7");
		createChromosome("8");
		createChromosome("9");
		createChromosome("10");
		// Cr�ation des chromosomes sexuels
		createChromosome("XY");
	}

	// R�cup�ration de la paire de chromosome
	public ChromosomePaire getPaire(String name) {
		return this.chromosomeColl.get(name);
	}
	
	// Cr�ation de la paire de chromosomes
	private void createChromosome(String name) {

		ChromosomePaire paire = new ChromosomePaire();
		// Nom
		paire.setName(name);
		// S�quence pour chaque chromosome
		paire.createSequence();
		// Ajout de la paire de chromosomes
		addPaire(paire);
	}

	// Ajout du chromosome � la liste des chromosomes
	private void addPaire(ChromosomePaire paire) {
		this.chromosomeColl.put(paire.getName(),paire);
	}

}
