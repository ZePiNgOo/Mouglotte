package com.mouglotte.specy;

import java.util.Random;

public class Desire {

	/** Max value */
	private final static int MAX_VALUE = 1000;
	// Facteur al�atoire appliqu� � l'envie actuelle
	// Valeur actuelle = Valeur +/- Valeur * cette constante
	private final static double RANDOM_EFFECT = 0.25;

	// Liste des envies
	private Desires desires;

	// Type
	private DesireType type;
	// Valeur de l'envie (d�finie par la g�n�tique)
	private int geneValue;
	// Valeur actuelle de l'envie (avec un facteur al�atoire)
	private int value;
	// Besoin associ�
	private Need relatedNeed;

	// Valeur selon les ages
	private int babyValue;
	private int childValue;
	private int adultValue;
	private int oldValue;
	// Diminution pendant la satisfaction
	private int fulfillLoss;
	
	// Facteur al�atoire pour le calcul de l'envie
	private Random random = new Random();

	// Constructeur
	public Desire(Desires desires, DesireType type, int babyValue,
			int childValue, int adultValue, int oldValue, int fulfillLoss) {

		this.desires = desires;
		this.type = type;
		this.babyValue = babyValue;
		this.childValue = childValue;
		this.adultValue = adultValue;
		this.oldValue = oldValue;
		this.fulfillLoss = fulfillLoss;

		// Valeur g�n�tique initiale de l'envie
		this.geneValue = this.babyValue;
	}

	// R�cup�ration du type
	public DesireType getType() {
		return this.type;
	}

	// R�cup�ration de la valeur r�elle de l'envie
	public int getGeneValue() {
		return this.geneValue;
	}

	// R�cup�ration de la valeur actuelle de l'envie
	public int getValue() {
		return this.value;
	}

	// Calcul de la valeur actuelle de l'envie
	// Avec un facteur al�atoire
	public void setValue() {

		// L'envie reste � 0 si la valeur g�n�tique est 0
		if (this.geneValue == 0) {
			this.value = 0;
			return;
		}
		
		// Retrancher ou ajouter...
		boolean operator = this.random.nextBoolean();
		// ...un pourcentage al�atoire � la valeur (d�finit par RANDOM_EFFECT)
		if (operator == true)
			this.value = this.geneValue
					+ this.random.nextInt((int) (this.geneValue * RANDOM_EFFECT));
		else
			this.value = this.geneValue
					- this.random.nextInt((int) (this.geneValue * RANDOM_EFFECT));
		
		if (this.value > MAX_VALUE) this.value = MAX_VALUE;
	}

	// D�finition du besoin correspondant
	public void setRelatedNeed(Need need) {
		this.relatedNeed = need;
	}

	// D�finition de la valeur
	public void setGeneValue(int value) {
		// Cette m�thode ne doit pas exister car la valeur est d�finie
		// g�n�tiquement
		this.geneValue = value;
	}

	// Accomplissement de l'envie
	public void fulfill() {

		// Evolution de la valeur de l'envie
		this.value -= this.fulfillLoss;
		if (this.value < 0)
			this.value = 0;

		// Accomplissement du besoin correspondant
		if (this.relatedNeed != null)
			this.relatedNeed.fulfillFromDesire();
	}
	
	// Accomplissement de l'envie depuis le besoin
	public void fulfillFromNeed() {

		// Evolution de la valeur de l'envie
		this.value -= this.fulfillLoss;
		if (this.value < 0)
			this.value = 0;
	}

	// Ajout d'une ann�e pour le calcul de la valeur du trait de caract�re
	public void addYear() {

		// B�b�
		if (this.desires.getMouglotte().getAge() < 1)
			this.geneValue = this.babyValue;
		// Enfant
		else if (this.desires.getMouglotte().getAge() < 2)
			this.geneValue = this.childValue;
		// Adulte
		else if (this.desires.getMouglotte().getAge() < 26)
			this.geneValue = this.adultValue;
		// Vieux
		else
			this.geneValue = this.oldValue;
	}
}