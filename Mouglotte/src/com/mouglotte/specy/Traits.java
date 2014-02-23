package com.mouglotte.specy;

import java.util.Hashtable;
import java.util.Map.Entry;

public class Traits {

	// Mouglotte
	private Mouglotte mouglotte;
	
	// Liste des traits de caractères
	private Hashtable<TraitType, Trait> traits;
	
	// Constructeur
	public Traits(Mouglotte mouglotte) {
		
		this.mouglotte = mouglotte;
		this.traits = new Hashtable<TraitType, Trait>();
	}
	
	// Récupération de la mouglotte
	public Mouglotte getMouglotte() {
		return this.mouglotte;
	}
	
	// Récupération d'un trait de caractère
	public Trait get(TraitType type) {
		return this.traits.get(type);
	}
	
	// Ajout d'un trait de caractère
	public void put(Trait trait) {
		this.traits.put(trait.getType(), trait);
	}
	
	// Evénement exécuté tous les ans
	public void eventYear() {
		
		// Parcours de tous les traits de caractères
		for (final Entry<TraitType, Trait> trait : this.traits.entrySet()) {

			// Recalcul de la valeur du trait de caractère
			trait.getValue().addYear();
		}
		// Fin du parcours des traits de caractères
	}
}
