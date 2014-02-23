package com.mouglotte.genetics;

public final class ValueGene extends Gene {

	// Valeur minimale renvoy�e par le g�ne
	private int minValue = 0;
	// Valeur maximale renvoy�e par le g�ne
	private int maxValue = 0;
	// Valeur moyenne renvoy�e par le g�ne
	private int midValue = 0;

	// D�finition des valeurs minimale et maximale renvoy�es par le g�ne
	public void setBorders(int minValue, int maxValue) {
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	// R�cup�ration de la valeur donn�es par le g�ne depuis le caryotype
	public int getValue(Karyotype karyotype) {
		return getValue(karyotype.getPaire(this.chromosome));
	}

	// R�cup�ration de la valeur donn�es par le g�ne depuis la paire
	private int getValue(ChromosomePaire paire) {

		// Valeur du g�ne pour le premier chromosome
		int valueA = getValue(paire.getChromosomeA());
		// Valeur du g�ne pour le deuxi�me chromosome
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

	// R�cup�ration de la valeur donn�es par le g�ne depuis le chromosome
	private int getValue(Chromosome chromosome) {
		return getValue(getSequence(chromosome));
	}

	// R�cup�ration de la valeur donn�e par le g�ne depuis la s�quence
	private int getValue(String sequence) {

		int retValue = 0;
		int value = 0;
		int minValue = 0;
		int maxValue = 0;

		// R�cup�ration de la valeur de la s�quence
		value = getSequenceValue(sequence);
		// R�cup�ration de la valeur minimale de la s�quence
		minValue = getSequenceMinValue();
		// R�cup�ration de la valeur maximale de la s�quence
		maxValue = getSequenceMaxValue();

		// Calcul de la valeur donn�e par le g�ne
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
