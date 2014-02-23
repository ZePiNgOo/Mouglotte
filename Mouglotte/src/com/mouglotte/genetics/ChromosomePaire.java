package com.mouglotte.genetics;

public class ChromosomePaire {

	// 1er chromosome
	private Chromosome chromosomeA;
	// 2�me chromosome
	private Chromosome chromosomeB;
	// Nom du chromosome (le m�me pour les deux)
	private String name;
	
	// Constructeur
	public ChromosomePaire() {
		this.chromosomeA = new Chromosome();
		this.chromosomeB = new Chromosome();
	}

	// D�finition du nom du chromosome
	public void setName(String name) {
		this.name = name;
	}
	
	// R�cup�ration du 1er chromosome
	public Chromosome getChromosomeA() {
		return this.chromosomeA;
	}
	
	// R�cup�ration du 2�me chromosome
	public Chromosome getChromosomeB() {
		return this.chromosomeB;
	}
	
	// R�cup�ration du nom du chromosome
	public String getName() {
		return this.name;
	}
	
	// Cr�ation de la s�quence ADN pour chaque chromosome
	public void createSequence() {
		this.chromosomeA.createSequence();
		this.chromosomeB.createSequence();
	}
}
