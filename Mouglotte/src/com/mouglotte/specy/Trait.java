package com.mouglotte.specy;

public class Trait {

	// Liste des traits de caractères
	private Traits traits;

	// Type
	private TraitType type;
	// Valeur de la caractéristique
	private int value;
	// Valeur à la naissance
	private int birthValue;
	// Evolution selon les ages
	private int babyGrowth;
	private int childGrowth;
	private int adultGrowth;
	private int oldGrowth;
	// Gain et perte lors de l'exercice
	private int exerciseGain;
	private int exerciseLoss;

	// Constructeur
	public Trait(Traits traits, TraitType type, int birthValue, int babyGrowth,
			int childGrowth, int adultGrowth, int oldGrowth) {

		this.traits      = traits;
		this.type        = type;
		this.birthValue  = birthValue;
		this.babyGrowth  = babyGrowth;
		this.childGrowth = childGrowth;
		this.adultGrowth = adultGrowth;
		this.oldGrowth   = oldGrowth;

		// Valeur initiale du trait de caractère
		this.value = this.birthValue;
	}

	// Récupération du type
	public TraitType getType() {
		return this.type;
	}

	// Récupération de la valeur
	public int getValue() {
		return this.value;
	}

	// Définition de la valeur
	public void setValue(int value) {
		// Ne devrait pas exister, pour les tests
		this.value = value;
	}

	// Ajout d'une année pour le calcul de la valeur du trait de caractère
	public void addYear() {
	
		// Bébé
		if (this.traits.getMouglotte().getAge() < 1)
			this.value += this.babyGrowth;
		// Enfant
		else if (this.traits.getMouglotte().getAge() < 2)
			this.value += this.childGrowth;
		// Adulte
		else if (this.traits.getMouglotte().getAge() < 26)
			this.value += this.adultGrowth;
		// Vieux
		else this.value += this.oldGrowth;

		if (this.value < 0)
			this.value = 0;
	}
}
