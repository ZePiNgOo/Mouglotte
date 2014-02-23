package com.mouglotte.genetics;
public final class RateGene extends Gene {

	// Constructeur
	public RateGene() {
		super();
	}

	// R�cup�ration du taux donn� par le g�ne
	public float getRate(String sequence) {

		float rate = 1;
		int value = 0;
		int minValue = 0;
		int maxValue = 0;

		// R�cup�ration de la valeur de la s�quence
		value = getSequenceValue(sequence);
		// R�cup�ration de la valeur minimale de la s�quence
		minValue = getSequenceMinValue();
		// R�cup�ration de la valeur maximale de la s�quence
		maxValue = getSequenceMaxValue();

		// y = - (x - value)�
		rate = 1;

		return rate;
	}

}
