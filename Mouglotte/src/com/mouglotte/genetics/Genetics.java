package com.mouglotte.genetics;

public class Genetics {

	private static Genome genome;
	
//	// R�cup�ration du g�nome
//	private static Genome getGenome() {
//		return Genetics.genome;
//	}
//	
//	// R�cup�ration du g�ne
//	private static Gene getGene(String name) {
//		return Genetics.genome.getGene(name);
//	}
	// R�cup�ration de l'instance de moi-m�me
	public static void initialize() {

		Genetics.genome = new Genome();
	}
	
	// R�cup�ration de la valeur du g�ne selon le karyotype
	public static int getValue(String name, Karyotype karyotype) {
		return Genetics.genome.getValue(name, karyotype);
	}
	
}
