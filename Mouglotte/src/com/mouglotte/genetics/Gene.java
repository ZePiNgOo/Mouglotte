package com.mouglotte.genetics;
public class Gene {

	// Nom du gène
	private String name;
	// Type de gène
	protected GeneType geneType;
	// Chromosome concerné
	protected String chromosome;
	// Position du gène dans la séquence ADN du chromosome
	private int position;
	// Longueur de la séquence ADN du gène
	private int length;
	// Chromosome de référence
//	private Chromosome refChromosome;
	// // Séquence ADN du gène
	// protected String sequence;
	// Valeurs des combinaisons
	protected int AA;
	protected int AC;
	protected int AG;
	protected int AT;
	protected int CA;
	protected int CC;
	protected int CG;
	protected int CT;
	protected int GA;
	protected int GC;
	protected int GG;
	protected int GT;
	protected int TA;
	protected int TC;
	protected int TG;
	protected int TT;
	// Valeur minimale de combinaison
	private int minCombValue;
	// Valeur maximale de combinaison
	private int maxCombValue;

	// Constructeur
	public Gene() {
//		this.refChromosome = chromosome;
	}

	// public void constructor(
	// int AA, int AC, int AG, int AT,
	// int CA, int CC, int CG, int CT,
	// int GA, int GC, int GG, int GT,
	// int TA, int TC, int TG, int TT) {
	//
	// // Définition des valeurs des combinaisons
	// setCombinationValues(
	// AA, AC, AG, AT,
	// CA, CC, CG, CT,
	// GA, GC, GG, GT,
	// TA, TC, TG, TT);
	// }
	//
	// public void constructor(int AA, int AC, int AG, int AT, int CA, int CC,
	// int CG, int CT, int GA, int GC, int GG, int GT, int TA, int TC,
	// int TG, int TT, int minValue, int maxValue) {
	// // TODO Auto-generated method stub
	//
	// }

	// Définition du nom du gène
	public void setName(String name) {
		this.name = name;
	}
	
	// Définition du type de gène
	public void setType(GeneType geneType) {
		this.geneType = geneType;
	}

	// // Définition de la séquence du gène
	// public void setSequence(String sequence) {
	// this.sequence = sequence;
	// }

	// Définition de l'emplacement du gène dans la séquence ADN
	public void setPosition(String chromosome, int position, int length) {

		// Il n'y a pas de mécanisme de contrôle pour savoir si deux gènes se
		// chevauchent

		// Chromosome porteur
		this.chromosome = chromosome;
		// Position du gène dans la séquence ADN du chromosome
		this.position = position;
		// Longueur de la séquence ADN du gène
		this.length = length;
	}

	// Définition des valeurs des combinaisons
	public void setCombinationValues(int AA, int AC, int AG, int AT, int CA,
			int CC, int CG, int CT, int GA, int GC, int GG, int GT, int TA,
			int TC, int TG, int TT) {

		// Définition de la valeur de chaque combinaison
		// et des valeurs minimale et maximale de combinaison
		this.AA = AA;
		this.minCombValue = AA;
		this.maxCombValue = AA;
		this.AC = AC;
		if (AC < this.minCombValue)
			this.minCombValue = AC;
		if (AC > this.maxCombValue)
			this.maxCombValue = AC;
		this.AG = AG;
		if (AG < this.minCombValue)
			this.minCombValue = AG;
		if (AG > this.maxCombValue)
			this.maxCombValue = AG;
		this.AT = AT;
		if (AT < this.minCombValue)
			this.minCombValue = AT;
		if (AT > this.maxCombValue)
			this.maxCombValue = AT;
		this.CA = CA;
		if (CA < this.minCombValue)
			this.minCombValue = CA;
		if (CA > this.maxCombValue)
			this.maxCombValue = CA;
		this.CC = CC;
		if (CC < this.minCombValue)
			this.minCombValue = CC;
		if (CC > this.maxCombValue)
			this.maxCombValue = CC;
		this.CG = CG;
		if (CG < this.minCombValue)
			this.minCombValue = CG;
		if (CG > this.maxCombValue)
			this.maxCombValue = CG;
		this.CT = CT;
		if (CT < this.minCombValue)
			this.minCombValue = CT;
		if (CT > this.maxCombValue)
			this.maxCombValue = CT;
		this.GA = GA;
		if (GA < this.minCombValue)
			this.minCombValue = GA;
		if (GA > this.maxCombValue)
			this.maxCombValue = GA;
		this.GC = GC;
		if (GC < this.minCombValue)
			this.minCombValue = GC;
		if (GC > this.maxCombValue)
			this.maxCombValue = GC;
		this.GG = GG;
		if (GG < this.minCombValue)
			this.minCombValue = GG;
		if (GG > this.maxCombValue)
			this.maxCombValue = GG;
		this.GT = GT;
		if (GT < this.minCombValue)
			this.minCombValue = GT;
		if (GT > this.maxCombValue)
			this.maxCombValue = GT;
		this.TA = TA;
		if (TA < this.minCombValue)
			this.minCombValue = TA;
		if (TA > this.maxCombValue)
			this.maxCombValue = TA;
		this.TC = TC;
		if (TC < this.minCombValue)
			this.minCombValue = TC;
		if (TC > this.maxCombValue)
			this.maxCombValue = TC;
		this.TG = TG;
		if (TG < this.minCombValue)
			this.minCombValue = TG;
		if (TG > this.maxCombValue)
			this.maxCombValue = TG;
		this.TT = TT;
		if (TT < this.minCombValue)
			this.minCombValue = TT;
		if (TT > this.maxCombValue)
			this.maxCombValue = TT;
	}

	// Récupération du nom du gène
	public String getName() {
		return this.name;
	}
	
	// Récupération de la séquence du gène selon un chromosome
	protected String getSequence(Chromosome chromosome) {
		return chromosome.getSequence().substring(position-1,position-1+length);
	}
	
	// Récupération de la valeur de la séquence
	protected int getSequenceValue(String sequence) {

		int value = 0;

		// Cumul des valeurs de combinaisons sur la séquence
		for (int i = 0; i < this.length; i = i + 2) {
			switch (sequence.substring(i, i+2)) {
			case "AA":
				value += this.AA;
				break;
			case "AC":
				value += this.AC;
				break;
			case "AG":
				value += this.AG;
				break;
			case "AT":
				value += this.AT;
				break;
			case "CA":
				value += this.CA;
				break;
			case "CC":
				value += this.CC;
				break;
			case "CG":
				value += this.CG;
				break;
			case "CT":
				value += this.CT;
				break;
			case "GA":
				value += this.GA;
				break;
			case "GC":
				value += this.GC;
				break;
			case "GG":
				value += this.GG;
				break;
			case "GT":
				value += this.GT;
				break;
			case "TA":
				value += this.TA;
				break;
			case "TC":
				value += this.TC;
				break;
			case "TG":
				value += this.TG;
				break;
			case "TT":
				value += this.TT;
				break;
			default:
				break;
			}
		}
		return value;
	}
	
//	public int getValue(Karyotype karyotype) {
//		return 0;
//	}

	// Récupération de la valeur minimale de la séquence
	protected int getSequenceMinValue() {

		// Le calcul fait comme si la séquence n'était composée que de
		// la combinaison avec la valeur minimale
		int value = this.minCombValue * this.length / 2;
		return value;
	}

	// Récupération de la valeur maximale de la séquence
	protected int getSequenceMaxValue() {

		// Le calcul fait comme si la séquence n'était composée que de
		// la combinaison avec la valeur maximale
		int value = this.maxCombValue * this.length / 2;
		return value;
	}
}
