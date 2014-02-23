package com.mouglotte.genetics;

public class Genetics {

	private static Genome genome;
	
//	// Récupération du génome
//	private static Genome getGenome() {
//		return Genetics.genome;
//	}
//	
//	// Récupération du gène
//	private static Gene getGene(String name) {
//		return Genetics.genome.getGene(name);
//	}
	// Récupération de l'instance de moi-même
	public static void initialize() {

		Genetics.genome = new Genome();
	}
	
	// Récupération de la valeur du gène selon le karyotype
	public static int getValue(String name, Karyotype karyotype) {
		return Genetics.genome.getValue(name, karyotype);
	}
	
}
