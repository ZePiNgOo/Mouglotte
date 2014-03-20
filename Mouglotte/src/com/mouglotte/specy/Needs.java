package com.mouglotte.specy;

import java.util.Hashtable;
import java.util.Map.Entry;

public class Needs {

	// Mouglotte
	private Mouglotte mouglotte;
	
	// Liste des besoins
	private Hashtable<NeedType, Need> needs;
	// Besoin en cours
	private Need current;
	// Le besoin courant est en chemin pour s'accomplir
	private boolean searching;
	// Le besoin courant est en train de s'accomplir
	private boolean fulfilling;
	
	// Constructeur
	public Needs(Mouglotte mouglotte) {
		
		// Mouglotte concernée
		this.mouglotte = mouglotte;
		// Initialisation de la liste des besoins
		this.needs = new Hashtable<NeedType, Need>();
	}
	
	// Récupération de la mouglotte
	public Mouglotte getMouglotte() {
		return this.mouglotte;
	}
	
	// Récupération d'un besoin
	public Need get(NeedType type) {
		return this.needs.get(type);
	}

	// Récupération du besoin en cours
	public Need getCurrent() {
		return this.current;
	}

	// Définition du besoin le plus pressant
	private void setCurrent() {

		int value = 0;
		int maxValue = 0;

		// Parcours de tous les besoins
		for (final Entry<NeedType, Need> need : this.needs.entrySet()) {

			// Récupération de la valeur actuelle du besoin
			value = need.getValue().getValue();

			// Si le besoin est le plus pressant
			if (value > maxValue) {

				maxValue = value;
				// C'est ce besoin qui est le plus pressant
				this.current = need.getValue();
			}
		}
		// Fin du parcours des besoins
	}
	
	// Activation de la recherche du besoin
	public void setSearching(boolean searching) {
		this.searching = searching;
		if (searching)
			this.fulfilling = false;
	}

	// Activation de l'accomplissement du besoin
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
	
	// Ajout d'un besoin
	public void put(Need need) {
		this.needs.put(need.getType(), need);
	}
	
	/**
	 * Fulfill current need
	 */
	public void fulfill() {
		
		// Fulfill current need if it has to be
		if (this.current != null && this.fulfilling) {
			
			this.current.fulfill();
			
			// If the need is completely fulfilled
			if (this.current.getValue() <= 0)
				this.setFulfilling(false);
		}
	}
	

	/**
	 * Decide current need
	 */
	public void decide() {
	
		// Read through all needs
		for (final Entry<NeedType, Need> need : this.needs.entrySet()) {
			// Increase need
				need.getValue().increase();
		}
		
		// Needs always increase, so current need stays the same
		// If it is fulfilling then it can be exceeded by another one
		
		// If the is no current need
		// Or if the current need is fulfilling
		if ((this.current == null) || 
				this.fulfilling) {
			
			// Set current need
			setCurrent();
			// Initialization
			this.searching = false;
			this.fulfilling = false;
		}
	}
}
