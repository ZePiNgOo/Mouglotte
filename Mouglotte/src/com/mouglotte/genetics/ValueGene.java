package com.mouglotte.genetics;

public final class ValueGene extends Gene {

	// Valeur minimale renvoyée par le gène
	private int minValue = 0;
	// Valeur maximale renvoyée par le gène
	private int maxValue = 0;
	// Valeur moyenne renvoyée par le gène
	private int midValue = 0;

	// Définition des valeurs minimale et maximale renvoyées par le gène
	public void setBorders(int minValue, int maxValue) {
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	// Récupération de la valeur données par le gène depuis le caryotype
	public int getValue(Karyotype karyotype) {
		return getValue(karyotype.getPaire(this.chromosome));
	}

	// Récupération de la valeur données par le gène depuis la paire
	private int getValue(ChromosomePaire paire) {

		// Valeur du gène pour le premier chromosome
		int valueA = getValue(paire.getChromosomeA());
		// Valeur du gène pour le deuxième chromosome
		int valueB = getValue(paire.getChromosomeB());

		switch (this.geneType) {
		case DOMINANT:
			return valueA == valueB ? valueA : 0;
		case RECESSIVE:
			return valueA == valueB ? valueA : 0;
		case AVERAGE:
			return (valueA + valueB) / 2;
		case BEST:
			return valueA > valueB ? valueA : valueB;
		case WORST:
			return valueA < valueB ? valueA : valueB;
		default:
			return 0;
		}
	}

	// Récupération de la valeur données par le gène depuis le chromosome
	private int getValue(Chromosome chromosome) {
		return getValue(getSequence(chromosome));
	}

	// Récupération de la valeur donnée par le gène depuis la séquence
	private int getValue(String sequence) {

		int retValue = 0;
		int value = 0;
		int minValue = 0;
		int maxValue = 0;

		// Récupération de la valeur de la séquence
		value = getSequenceValue(sequence);
		// Récupération de la valeur minimale de la séquence
		minValue = getSequenceMinValue();
		// Récupération de la valeur maximale de la séquence
		maxValue = getSequenceMaxValue();

		// Calcul de la valeur donnée par le gène
		if (value > 0)
			if ((maxValue - minValue) / value > 0)
				retValue = this.minValue + (this.maxValue - this.minValue)
						/ ((maxValue - minValue) / value);
			else
				retValue = this.minValue;
		else
			retValue = this.minValue;

		return retValue;
	}
}
