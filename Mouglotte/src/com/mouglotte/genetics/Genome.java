package com.mouglotte.genetics;

import java.util.Hashtable;

public class Genome {

	// Liste des gènes
	private Hashtable<String, Gene> geneColl;

	// Constructeur
	public Genome() {

		// Initialisation de la liste de gènes
		this.geneColl = new Hashtable<String, Gene>();

		// Création du génome
		createGenome();
	}

	// Récupération d'un gène depuis son nom
	public Gene getGene(String name) {
		return this.geneColl.get(name);
	}

	// Création du génome
	private void createGenome() {

		// Traits de caractères
		createTraits();
		// Besoins
		createNeeds();
		// Envies
		createDesires();
	}

	// Création des gènes concernant les traits de caractères
	private void createTraits() {

		// Nom, Type, Chromosome, Début, Longueur, Min, Max, 16 valeurs
		
		// Ajout du gène AA (birthValue)
		createValueGene("AA", GeneType.AVERAGE, "1", 11, 10, 1, 10, 14, 6, 14,
				3, 16, 7, 9, 9, 20, 11, 11, 7, 18, 15, 11, 14);
		// Ajout du gène CF (babyGrowth)
		createValueGene("CF", GeneType.AVERAGE, "3", 131, 10, 15, 25, 14, 15,
				18, 1, 18, 8, 15, 20, 0, 11, 4, 3, 9, 15, 10, 8);
		// Ajout du gène GY (childGrowth)
		createValueGene("GY", GeneType.AVERAGE, "4", 81, 10, 25, 30, 2, 3, 13,
				20, 18, 0, 3, 4, 17, 1, 13, 6, 5, 0, 12, 6);
		// Ajout du gène BS (adultGrowth)
		createValueGene("BS", GeneType.AVERAGE, "XY", 1, 10, 10, 50, 8, 12, 19,
				7, 17, 16, 12, 12, 12, 3, 3, 16, 18, 4, 14, 0);
		// Ajout du gène RT (oldGrowth)
		createValueGene("RT", GeneType.AVERAGE, "5", 31, 10, -50, -10, 16, 0,
				16, 10, 10, 14, 4, 8, 11, 0, 1, 7, 7, 3, 0, 11);
		// Ajout du gène ZE (exerciseGain)
		createValueGene("ZE", GeneType.AVERAGE, "6", 11, 10, 0, 0, 19, 2, 7, 2,
				17, 13, 20, 6, 6, 16, 1, 18, 14, 18, 16, 13);
		// Ajout du gène TC (exerciseLoss)
		createValueGene("TC", GeneType.AVERAGE, "8", 1, 10, 0, 0, 19, 17, 6, 6,
				11, 1, 18, 4, 20, 17, 20, 17, 3, 19, 3, 11);
	}

	// Création des gènes concernant les besoins
	private void createNeeds() {

		// Nom, Type, Chromosome, Début, Longueur, Min, Max, 16 valeurs
		
		// Faim
		// Ajout du gène MQ (babyHourGain)
		createValueGene("MQ", GeneType.AVERAGE, "1", 191, 10, 100, 200, 9, 14,
				17, 10, 3, 1, 6, 6, 9, 11, 7, 5, 10, 8, 7, 7);
		// Ajout du gène JK (childHourGain)
		createValueGene("JK", GeneType.AVERAGE, "3", 181, 10, 50, 100, 5, 19,
				14, 6, 12, 6, 16, 13, 14, 14, 0, 1, 7, 0, 12, 4);
		// Ajout du gène NS (adultHourGain)
		createValueGene("NS", GeneType.AVERAGE, "4", 41, 10, 25, 75, 16, 10,
				16, 14, 3, 3, 9, 5, 16, 3, 6, 13, 2, 17, 8, 10);
		// Ajout du gène IZ (oldHourGain)
		createValueGene("IZ", GeneType.AVERAGE, "6", 111, 10, 25, 60, 3, 5, 8,
				13, 17, 12, 0, 5, 0, 9, 8, 6, 18, 12, 16, 11);
		// Ajout du gène VS (fulfillLoss)
		createValueGene("VS", GeneType.AVERAGE, "9", 171, 10, 8, 17, 11, 8, 7,
				17, 8, 12, 6, 0, 15, 12, 20, 15, 5, 9, 10, 8);
		// Ajout du gène JE (exerciseGain)
		createValueGene("JE", GeneType.AVERAGE, "1", 61, 10, 0, 0, 9, 9, 2, 19,
				18, 3, 3, 14, 10, 3, 18, 13, 17, 19, 17, 7);

		// Repos
		// Ajout du gène YU (babyHourGain)
		createValueGene("YU", GeneType.AVERAGE, "1", 51, 10, 100, 200, 14, 14,
				8, 18, 13, 7, 9, 11, 0, 2, 13, 10, 4, 8, 0, 18);
		// Ajout du gène ED (childHourGain)
		createValueGene("ED", GeneType.AVERAGE, "3", 161, 10, 40, 80, 6, 11,
				13, 1, 1, 2, 12, 4, 11, 16, 14, 3, 14, 15, 1, 17);
		// Ajout du gène JI (adultHourGain)
		createValueGene("JI", GeneType.AVERAGE, "4", 91, 10, 25, 75, 0, 0, 20,
				6, 5, 12, 18, 10, 12, 3, 8, 18, 16, 14, 6, 15);
		// Ajout du gène MA (oldHourGain)
		createValueGene("MA", GeneType.AVERAGE, "6", 141, 10, 40, 80, 20, 20,
				16, 14, 13, 10, 20, 5, 10, 8, 0, 15, 6, 13, 0, 1);
		// Ajout du gène CS (fulfillLoss)
		createValueGene("CS", GeneType.AVERAGE, "9", 41, 10, 3, 5, 8, 19, 11,
				0, 9, 7, 9, 12, 19, 15, 5, 7, 18, 16, 4, 7);
		// Ajout du gène TE (exerciseGain)
		createValueGene("TE", GeneType.AVERAGE, "1", 181, 10, 0, 0, 4, 0, 19,
				9, 20, 15, 1, 15, 10, 7, 20, 17, 12, 8, 20, 1);

		// Social
		// Ajout du gène LM (babyHourGain)
		createValueGene("LM", GeneType.AVERAGE, "4", 121, 10, 50, 100, 20, 19,
				18, 20, 0, 7, 11, 12, 16, 11, 12, 5, 5, 10, 16, 6);
		// Ajout du gène YA (childHourGain)
		createValueGene("YA", GeneType.AVERAGE, "6", 71, 10, 50, 100, 19, 4, 0,
				7, 4, 12, 11, 14, 5, 10, 7, 2, 0, 1, 18, 10);
		// Ajout du gène FD (adultHourGain)
		createValueGene("FD", GeneType.AVERAGE, "9", 181, 10, 25, 75, 15, 4, 8,
				17, 5, 7, 11, 15, 17, 13, 5, 18, 9, 7, 3, 15);
		// Ajout du gène OO (oldHourGain)
		createValueGene("OO", GeneType.AVERAGE, "10", 191, 10, 25, 50, 8, 3,
				13, 6, 1, 1, 4, 16, 5, 6, 20, 7, 18, 20, 16, 17);
		// Ajout du gène IT (fulfillLoss)
		createValueGene("IT", GeneType.AVERAGE, "8", 51, 10, 8, 17, 7, 3, 18,
				12, 18, 4, 15, 14, 1, 17, 11, 7, 20, 18, 18, 10);
		// Ajout du gène JS (exerciseGain)
		createValueGene("JS", GeneType.AVERAGE, "5", 121, 10, 0, 0, 8, 17, 8,
				16, 7, 18, 13, 19, 3, 17, 3, 12, 4, 14, 8, 13);

		// Divertissement
		// Ajout du gène QN (babyHourGain)
		createValueGene("QN", GeneType.AVERAGE, "3", 11, 10, 10, 25, 20, 13,
				17, 16, 6, 5, 9, 11, 8, 18, 7, 0, 13, 8, 10, 17);
		// Ajout du gène UY (childHourGain)
		createValueGene("UY", GeneType.AVERAGE, "8", 131, 10, 50, 100, 13, 13,
				19, 1, 20, 17, 12, 13, 16, 18, 6, 18, 5, 0, 16, 1);
		// Ajout du gène CV (adultHourGain)
		createValueGene("CV", GeneType.AVERAGE, "7", 21, 10, 25, 50, 12, 15,
				19, 6, 20, 14, 4, 3, 20, 7, 0, 13, 0, 8, 20, 11);
		// Ajout du gène TT (oldHourGain)
		createValueGene("TT", GeneType.AVERAGE, "5", 161, 10, 10, 25, 2, 2, 5,
				15, 20, 19, 5, 15, 16, 18, 18, 17, 0, 5, 17, 9);
		// Ajout du gène XS (fulfillLoss)
		createValueGene("XS", GeneType.AVERAGE, "2", 81, 10, 8, 17, 7, 5, 16,
				4, 20, 9, 2, 19, 12, 20, 18, 4, 16, 5, 5, 2);
		// Ajout du gène WD (exerciseGain)
		createValueGene("WD", GeneType.AVERAGE, "3", 141, 10, 0, 0, 3, 18, 9,
				9, 14, 0, 19, 12, 5, 1, 9, 2, 0, 1, 1, 12);
	}

	// Création des gènes concernant les envies
	private void createDesires() {

		// Nom, Type, Chromosome, Début, Longueur, Min, Max, 16 valeurs
		
		// Ajout du gène AD (babyValue)
		createValueGene("AD", GeneType.AVERAGE, "2", 31, 10, 400, 800, 16, 10,
				11, 16, 9, 1, 16, 5, 1, 2, 0, 15, 9, 4, 17, 4);
		// Ajout du gène FF (childValue)
		createValueGene("FF", GeneType.AVERAGE, "6", 101, 10, 200, 400, 11, 19,
				12, 5, 19, 10, 7, 16, 12, 7, 13, 8, 11, 10, 17, 5);
		// Ajout du gène BG (adultValue)
		createValueGene("BG", GeneType.AVERAGE, "7", 141, 10, 300, 600, 12, 20,
				9, 0, 14, 1, 2, 6, 19, 18, 15, 6, 8, 8, 14, 13);
		// Ajout du gène CX (oldValue)
		createValueGene("CX", GeneType.AVERAGE, "9", 11, 10, 200, 400, 7, 8,
				16, 17, 18, 19, 11, 13, 4, 6, 6, 17, 14, 9, 16, 4);
		// Ajout du gène UR (fulfillLoss)
		createValueGene("UR", GeneType.AVERAGE, "10", 121, 10, 8, 17, 6, 7, 15,
				15, 4, 1, 13, 19, 14, 5, 1, 17, 16, 13, 15, 11);
		// Ajout du gène EZ (babyValue)
		createValueGene("EZ", GeneType.AVERAGE, "2", 61, 10, 50, 100, 10, 17,
				3, 5, 5, 10, 6, 13, 16, 18, 7, 12, 7, 2, 12, 6);
		// Ajout du gène FC (childValue)
		createValueGene("FC", GeneType.AVERAGE, "10", 151, 10, 50, 100, 7, 2,
				0, 0, 20, 5, 2, 13, 14, 11, 20, 18, 6, 4, 11, 6);
		// Ajout du gène HE (adultValue)
		createValueGene("HE", GeneType.AVERAGE, "7", 91, 10, 200, 400, 18, 5,
				17, 7, 12, 15, 2, 15, 8, 20, 15, 8, 6, 10, 12, 17);
		// Ajout du gène NZ (oldValue)
		createValueGene("NZ", GeneType.AVERAGE, "8", 141, 10, 400, 800, 20, 0,
				5, 10, 9, 12, 18, 6, 4, 16, 11, 1, 14, 16, 14, 13);
		// Ajout du gène PS (fulfillLoss)
		createValueGene("PS", GeneType.AVERAGE, "9", 21, 10, 3, 5, 8, 0, 15, 3,
				19, 19, 2, 3, 4, 14, 12, 3, 0, 10, 10, 7);
		// Ajout du gène JU (babyValue)
		createValueGene("JU", GeneType.AVERAGE, "5", 41, 10, 50, 100, 1, 2, 7,
				11, 18, 16, 10, 11, 15, 11, 14, 8, 18, 4, 7, 3);
		// Ajout du gène LS (childValue)
		createValueGene("LS", GeneType.AVERAGE, "7", 71, 10, 400, 600, 18, 6,
				3, 6, 6, 18, 1, 3, 2, 7, 17, 0, 2, 15, 14, 10);
		// Ajout du gène MD (adultValue)
		createValueGene("MD", GeneType.AVERAGE, "XY", 151, 10, 400, 600, 14,
				20, 6, 1, 0, 15, 14, 16, 14, 0, 0, 2, 1, 7, 8, 20);
		// Ajout du gène NC (oldValue)
		createValueGene("NC", GeneType.AVERAGE, "9", 121, 10, 200, 400, 18, 18,
				2, 10, 19, 12, 20, 9, 13, 0, 5, 16, 2, 10, 2, 4);
		// Ajout du gène ID (fulfillLoss)
		createValueGene("ID", GeneType.AVERAGE, "3", 171, 10, 5, 10, 3, 12, 1,
				15, 8, 17, 5, 9, 18, 13, 1, 14, 7, 3, 6, 0);
		// Ajout du gène KZ (babyValue)
		createValueGene("KZ", GeneType.AVERAGE, "2", 51, 10, 200, 500, 6, 12,
				7, 15, 9, 20, 18, 2, 12, 6, 14, 17, 9, 18, 3, 18);
		// Ajout du gène DP (childValue)
		createValueGene("DP", GeneType.AVERAGE, "8", 71, 10, 400, 800, 13, 6,
				20, 8, 18, 11, 6, 1, 9, 18, 6, 14, 5, 5, 18, 20);
		// Ajout du gène JA (adultValue)
		createValueGene("JA", GeneType.AVERAGE, "XY", 51, 10, 300, 600, 17, 19,
				20, 1, 5, 14, 16, 12, 19, 16, 18, 4, 16, 11, 4, 8);
		// Ajout du gène SO (oldValue)
		createValueGene("SO", GeneType.AVERAGE, "3", 111, 10, 200, 400, 1, 1,
				13, 2, 11, 0, 19, 9, 14, 8, 9, 17, 5, 13, 16, 5);
		// Ajout du gène BQ (fulfillLoss)
		createValueGene("BQ", GeneType.AVERAGE, "1", 131, 10, 8, 17, 3, 15, 10,
				0, 8, 16, 1, 14, 4, 18, 1, 3, 5, 2, 14, 12);
		// Ajout du gène IS (babyValue)
		createValueGene("IS", GeneType.AVERAGE, "4", 141, 10, 0, 0, 10, 3, 8,
				7, 11, 11, 9, 2, 20, 14, 15, 20, 20, 5, 10, 13);
		// Ajout du gène GZ (childValue)
		createValueGene("GZ", GeneType.AVERAGE, "1", 41, 10, 50, 100, 14, 6, 9,
				16, 8, 16, 10, 5, 14, 15, 18, 20, 5, 15, 18, 19);
		// Ajout du gène DA (adultValue)
		createValueGene("DA", GeneType.AVERAGE, "8", 61, 10, 200, 500, 13, 11,
				3, 15, 15, 3, 12, 5, 2, 7, 9, 18, 17, 0, 17, 9);
		// Ajout du gène DF (oldValue)
		createValueGene("DF", GeneType.AVERAGE, "10", 51, 10, 100, 200, 5, 14,
				20, 5, 7, 3, 12, 5, 8, 7, 8, 14, 7, 5, 17, 19);
		// Ajout du gène FZ (fulfillLoss)
		createValueGene("FZ", GeneType.AVERAGE, "9", 151, 10, 8, 17, 15, 5, 7,
				16, 11, 12, 20, 7, 9, 18, 18, 15, 5, 20, 13, 1);
		// Ajout du gène JL (babyValue)
		createValueGene("JL", GeneType.AVERAGE, "4", 101, 10, 0, 0, 11, 11, 11,
				17, 7, 8, 11, 6, 16, 7, 20, 17, 9, 15, 6, 14);
		// Ajout du gène NE (childValue)
		createValueGene("NE", GeneType.AVERAGE, "XY", 11, 10, 100, 300, 8, 14,
				9, 14, 16, 11, 4, 8, 20, 9, 14, 7, 12, 15, 4, 20);
		// Ajout du gène SC (adultValue)
		createValueGene("SC", GeneType.AVERAGE, "XY", 71, 10, 100, 500, 14, 2,
				3, 2, 2, 7, 15, 20, 1, 0, 10, 14, 8, 9, 8, 5);
		// Ajout du gène YE (oldValue)
		createValueGene("YE", GeneType.AVERAGE, "7", 31, 10, 50, 100, 1, 10, 8,
				15, 16, 19, 10, 20, 8, 2, 2, 13, 6, 9, 20, 8);
		// Ajout du gène KS (fulfillLoss)
		createValueGene("KS", GeneType.AVERAGE, "2", 161, 10, 8, 17, 14, 14,
				13, 0, 9, 12, 8, 18, 19, 9, 19, 1, 10, 9, 18, 10);
		// Ajout du gène ZJ (babyValue)
		createValueGene("ZJ", GeneType.AVERAGE, "6", 21, 10, 0, 0, 9, 10, 13,
				13, 10, 8, 7, 1, 15, 0, 14, 2, 0, 0, 2, 12);
		// Ajout du gène US (childValue)
		createValueGene("US", GeneType.AVERAGE, "7", 81, 10, 50, 100, 3, 5, 0,
				11, 19, 17, 2, 20, 14, 6, 11, 8, 12, 12, 19, 20);
		// Ajout du gène FQ (adultValue)
		createValueGene("FQ", GeneType.AVERAGE, "5", 1, 10, 100, 700, 13, 8,
				17, 20, 5, 11, 8, 2, 14, 11, 13, 1, 13, 9, 12, 3);
		// Ajout du gène WJ (oldValue)
		createValueGene("WJ", GeneType.AVERAGE, "5", 91, 10, 100, 400, 18, 9,
				18, 7, 7, 6, 14, 19, 14, 12, 17, 12, 12, 8, 3, 20);
		// Ajout du gène VT (fulfillLoss)
		createValueGene("VT", GeneType.AVERAGE, "6", 121, 10, 3, 5, 11, 12, 8,
				6, 7, 12, 14, 15, 6, 4, 5, 12, 16, 20, 13, 1);

	}

	// Création d'un gène de valeur
	private void createValueGene(String name, GeneType geneType,
			String chromosome, int position, int length, int minValue,
			int maxValue, int AA, int AC, int AG, int AT, int CA, int CC,
			int CG, int CT, int GA, int GC, int GG, int GT, int TA, int TC,
			int TG, int TT) {

		ValueGene valueGene = new ValueGene();
		// Nom
		valueGene.setName(name);
		// Type
		valueGene.setType(geneType);
		// Position dans le génome
		valueGene.setPosition(chromosome, position, length);
		// Valeurs minimales et maximales
		valueGene.setBorders(minValue, maxValue);
		// Valeur des combinaisons
		valueGene.setCombinationValues(AA, AC, AG, AT, CA, CC, CG, CT, GA, GC,
				GG, GT, TA, TC, TG, TT);
		// Ajout du gène au chromosome
		addGene(valueGene);
	}

	// Création d'un gène de taux
	private void createRateGene(String name, GeneType geneType,
			String chromosome, int position, int length, int minValue,
			int maxValue, int AA, int AC, int AG, int AT, int CA, int CC,
			int CG, int CT, int GA, int GC, int GG, int GT, int TA, int TC,
			int TG, int TT) {

		RateGene rateGene = new RateGene();
		// Nom
		rateGene.setName(name);
		// Type
		rateGene.setType(geneType);
		// Position dans le génome
		rateGene.setPosition(chromosome, position, length);
		// Valeur des combinaisons
		rateGene.setCombinationValues(10, 2, 3, 1, 0, 11, 6, 5, 5, 13, 2, 1, 0,
				8, 3, 1);
		// Ajout du gène au chromosome
		addGene(rateGene);
	}

	// Ajout du gène à la liste des gènes
	public void addGene(Gene gene) {
		this.geneColl.put(gene.getName(), gene);
	}

	// Récupération de la valeur du gène selon le karyotype
	public int getValue(String name, Karyotype karyotype) {

		ValueGene gene = (ValueGene) getGene(name);

		return gene.getValue(karyotype);
	}
}
