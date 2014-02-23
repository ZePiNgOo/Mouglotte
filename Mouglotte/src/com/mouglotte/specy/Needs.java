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
	
	// Evénement exécuté toutes les minutes
	public void eventMinute() {
		
		// Mise à jour du besoin courant s'il est en train de s'accomplir
		if (this.fulfilling) {
			this.current.fulfill();
		}
	}
	
	// Evénement exécuté toutes les heures
	public void eventHour() {
		
		// Augmentation des besoins
		// Parcours de tous les besoins
		for (final Entry<NeedType, Need> need : this.needs.entrySet()) {

			// Augmentation du besoin
			// Sauf le besoin courant s'il est en train de s'accomplir
			//if (!this.fulfilling || !need.getValue().equals(this.current)) {
				need.getValue().raise();
			//}
		}
		// Fin du parcours des besoins
		
		// Les besoins augmentent constamment, donc le besoin actuel le reste
		// S'il est en train de s'accomplir il peut se faire dépasser
		
		// S'il n'y a pas de besoin
		// Si le besoin est en train de s'accomplir
		if ((this.current == null) || 
				this.fulfilling) {
			
			// Décision du besoin le plus pressant
			decide();
		}
		
	}
	
	// Décision du besoin le plus pressant
	public void decide() {
	
		// Définition du besoin le plus pressant
		setCurrent();
		// Réinitialisation
		this.searching = false;
		this.fulfilling = false;
	}
}
