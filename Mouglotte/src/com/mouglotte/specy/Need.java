package com.mouglotte.specy;

public class Need {

	// Liste des besoins
	private Needs needs;

	// Type
	private NeedType type;
	// Valeur du besoin (d�finie par la g�n�tique)
	private int geneValue;
	// Valeur actuelle du besoin
	private int value;
	// Envie associ�e
	private Desire relatedDesire;
	
	// Evolution par heure selon les ages
	private int babyHourGain;
	private int childHourGain;
	private int adultHourGain;
	private int oldHourGain;
	// Diminution pendant la satisfaction
	private int fulfillLoss;

	// Constructeur
	public Need(Needs needs, NeedType type, int babyHourGain,
			int childHourGain, int adultHourGain, int oldHourGain,
			int fulfillLoss) {

		this.needs = needs;
		this.type = type;
		this.babyHourGain = babyHourGain;
		this.childHourGain = childHourGain;
		this.adultHourGain = adultHourGain;
		this.oldHourGain = oldHourGain;
		this.fulfillLoss = fulfillLoss;
	}

	// R�cup�ration du type
	public NeedType getType() {
		return this.type;
	}

	// R�cup�ration de la valeur r�elle du besoin
	public int getGeneValue() {
		return this.geneValue;
	}

	// R�cup�ration de la valeur actuelle du besoin
	public int getValue() {
		return this.value;
	}

	// D�finition de la valeur
	public void setGeneValue(int value) {
		// Cette m�thode ne doit pas exister car la valeur est d�finie
		// g�n�tiquement
		this.geneValue = value;
	}

	// D�finition de l'envie correspondante
	public void setRelatedDesire(Desire desire) {
		this.relatedDesire = desire;
	}
	
	// Augmentation du besoin
	public void increase() {

		// B�b�
		if (this.needs.getMouglotte().getAge() < 1)
			this.value += this.babyHourGain;
		// Enfant
		else if (this.needs.getMouglotte().getAge() < 2)
			this.value += this.childHourGain;
		// Adulte
		else if (this.needs.getMouglotte().getAge() < 26)
			this.value += this.adultHourGain;
		// Vieux
		else
			this.value += this.oldHourGain;
	}

	// Accomplissement du besoin
	public void fulfill() {
		
		// Evolution de la valeur du besoin
		this.value -= this.fulfillLoss;
		if (this.value < 0)
			this.value = 0;
		
		// Accomplissement de l'envie correspondante
		if (this.relatedDesire != null)
			this.relatedDesire.fulfillFromNeed();
	}
	
	// Accomplissement du besoin depuis l'envie
	public void fulfillFromDesire() {
		
		// Evolution de la valeur du besoin
		this.value -= this.fulfillLoss;
		if (this.value < 0)
			this.value = 0;
	}
}
