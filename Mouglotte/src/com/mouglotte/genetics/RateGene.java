package com.mouglotte.genetics;
public final class RateGene extends Gene {

	// Constructeur
	public RateGene() {
		super();
	}

	// Récupération du taux donné par le gène
	public float getRate(String sequence) {

		float rate = 1;
		int value = 0;
		int minValue = 0;
		int maxValue = 0;

		// Récupération de la valeur de la séquence
		value = getSequenceValue(sequence);
		// Récupération de la valeur minimale de la séquence
		minValue = getSequenceMinValue();
		// Récupération de la valeur maximale de la séquence
		maxValue = getSequenceMaxValue();

		// y = - (x - value)²
		rate = 1;

		return rate;
	}

}
