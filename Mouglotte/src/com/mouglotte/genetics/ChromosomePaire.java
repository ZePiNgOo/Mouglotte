package com.mouglotte.genetics;

public class ChromosomePaire {

	// 1er chromosome
	private Chromosome chromosomeA;
	// 2ème chromosome
	private Chromosome chromosomeB;
	// Nom du chromosome (le même pour les deux)
	private String name;
	
	// Constructeur
	public ChromosomePaire() {
		this.chromosomeA = new Chromosome();
		this.chromosomeB = new Chromosome();
	}

	// Définition du nom du chromosome
	public void setName(String name) {
		this.name = name;
	}
	
	// Récupération du 1er chromosome
	public Chromosome getChromosomeA() {
		return this.chromosomeA;
	}
	
	// Récupération du 2ème chromosome
	public Chromosome getChromosomeB() {
		return this.chromosomeB;
	}
	
	// Récupération du nom du chromosome
	public String getName() {
		return this.name;
	}
	
	// Création de la séquence ADN pour chaque chromosome
	public void createSequence() {
		this.chromosomeA.createSequence();
		this.chromosomeB.createSequence();
	}
}
