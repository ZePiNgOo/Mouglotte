package com.mouglotte.specy;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Desires {

	// Historique des envies pr�c�dentes
	private static int MAX_PREVIOUS_DESIRES = 5;
	// Perte de valeur lorsque l'envie est dans l'historique
	// Valeur calcul�e = Valeur * cette constante
	private static double PREVIOUS_DESIRE_PENALTY = 0.5;
	// Perte de valeur lorsque l'envie est accomplie dans l'historique
	// Valeur calcul�e = Valeur * cette constante
	private static double FULFILLED_DESIRE_PENALTY = 0.25;
	// Temps maximum de recherche de l'envie (en heures)
	private static int MAX_SEARCHING_TIME = 2;

	// Mouglotte
	private Mouglotte mouglotte;
	
	// Liste des envies
	private Hashtable<DesireType, Desire> desires;
	// Liste des envies pr�c�dentes
	private LinkedHashMap<DesireType, Boolean> previousDesires;
	// Envie en cours
	private Desire current;
	// L'envie courante est en chemin pour s'accomplir
	private boolean searching;
	// L'envie courante est en train de s'accomplir
	private boolean fulfilling;
	// Temps pass� � chercher � accomplir l'envie (en heures)
	private int searchingTime;

	// Constructeur
	public Desires(Mouglotte mouglotte) {
		
		// Mouglotte concern�e
		this.mouglotte = mouglotte;
		// Initialisation de la liste des envies
		this.desires = new Hashtable<DesireType, Desire>();
		// Initialisation de la liste des envies pr�c�dentes avec une limite du nombre
		this.previousDesires = new LinkedHashMap<DesireType, Boolean>(MAX_PREVIOUS_DESIRES) {

			private static final long serialVersionUID = 1L;

			protected boolean removeEldestEntry(
					Map.Entry<DesireType, Boolean> eldest) {
				return size() > MAX_PREVIOUS_DESIRES;
			}
		};
	}
	
	// R�cup�ration de la mouglotte
	public Mouglotte getMouglotte() {
		return this.mouglotte;
	}

	// R�cup�ration d'une envie
	public Desire get(DesireType type) {
		return this.desires.get(type);
	}

	// R�cup�ration de l'envie en cours
	public Desire getCurrent() {
		return this.current;
	}

	// D�finition de l'envie la plus pressante
	public void setCurrent() {

		int value = 0;
		int maxValue = 0;

		// Parcours de toutes les envies
		for (final Entry<DesireType, Desire> desire : this.desires.entrySet()) {
			
			// D�finition de la valeur actuelle de l'envie
			// Sauf l'envie courante si elle est en train de s'accomplir
			if (!this.fulfilling || !desire.getValue().equals(this.current)) {
				desire.getValue().setValue();
			}
			
			// R�cup�ration de la valeur actuelle de l'envie
			value = desire.getValue().getValue();

			// Si l'envie est d�j� dans les envies pr�c�dentes
			if (this.previousDesires.containsKey(desire.getValue().getType())) {

				// Si l'envie a d�j� �t� accomplie
				if (this.previousDesires.get(desire.getValue().getType()) == true)
					value *= FULFILLED_DESIRE_PENALTY;
				// Si l'envie a d�j� �t� suivie mais non accomplie
				else
					value *= PREVIOUS_DESIRE_PENALTY;
			}

			// Si l'envie est la plus pressante
			if (value > maxValue) {

				maxValue = value;
				// C'est cette envie qui est la plus pressante
				this.current = desire.getValue();
			}
		}
		// Fin du parcours des envies

		// Mise � jour de la liste des envies pr�c�dentes
		updatePreviousDesires(this.current.getType(), false);
	}
	
	// Activation de la recherche de l'envie
	public void setSearching(boolean searching) {
		this.searching = searching;
		if (searching)
			this.fulfilling = false;
	}
	
	// Activation de l'accomplissement de l'envie
	public void setFulfilling(boolean fulfilling) {
		this.fulfilling = fulfilling;
		if (fulfilling)
			this.searching = false;
	}
	
	// En recherche ?
	public boolean isSearching() {
		return this.searching;
	}

	// En accomplissement ?
	public boolean isFulfilling() {
		return this.fulfilling;
	}
	
	// Ajout d'une envie
	public void put(Desire desire) {
		this.desires.put(desire.getType(), desire);
	}

	// Ev�nement ex�cut� toutes les minutes
	public void eventMinute() {
		
		// Mise � jour de l'envie courante si elle est en train de s'accomplir
		if (this.fulfilling) {
			this.current.fulfill();
		}
	}
	
	// Ev�nement ex�cut� toutes les heures
	public void eventHour() {
		
		// Mise � jour du temps pass� � chercher � accomplir l'envie
		if (this.searching) this.searchingTime++;
		
		// S'il n'y a pas d'envie
		// Si le temps maximum de recherche de l'envie est d�pass�
		// Si l'envie est en train de s'accomplir
		if ((this.current == null) || 
				(this.searching && this.searchingTime > MAX_SEARCHING_TIME) ||
				this.fulfilling) {
			
			// D�cision de l'envie la plus pressante
			decide();
		}
	}
	
	// D�cision de l'envie la plus pressante
	public void decide() {
			
		// D�finition de l'envie la plus pressante
		setCurrent();
		// R�initialisation
		this.searchingTime = 0;
		this.searching = false;
		this.fulfilling = false;
	}
	
	// Mise � jour de la liste des envies pr�c�dentes
	public void updatePreviousDesires(DesireType type, Boolean fulfilled) {
		this.previousDesires.put(type, fulfilled);
	}
}
