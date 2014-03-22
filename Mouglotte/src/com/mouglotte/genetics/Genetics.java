package com.mouglotte.genetics;

public class Genetics {

	/** genome */
	private static Genome genome;

	/**
	 * Initialize genetics
	 */
	public static void initialize() {
		Genetics.genome = new Genome();
	}

	/**
	 * Get gene value from karyotype
	 * 
	 * @param name
	 *            Name of the gene
	 * @param karyotype
	 *            Karyotype
	 * @return Gene value
	 */
	public static int getValue(String name, Karyotype karyotype) {
		return Genetics.genome.getValue(name, karyotype);
	}

}
