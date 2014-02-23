package com.mouglotte.specy;

import java.util.Hashtable;
import java.util.Map.Entry;

public class Traits {

	// Mouglotte
	private Mouglotte mouglotte;
	
	// Liste des traits de caract�res
	private Hashtable<TraitType, Trait> traits;
	
	// Constructeur
	public Traits(Mouglotte mouglotte) {
		
		this.mouglotte = mouglotte;
		this.traits = new Hashtable<TraitType, Trait>();
	}
	
	// R�cup�ration de la mouglotte
	public Mouglotte getMouglotte() {
		return this.mouglotte;
	}
	
	// R�cup�ration d'un trait de caract�re
	public Trait get(TraitType type) {
		return this.traits.get(type);
	}
	
	// Ajout d'un trait de caract�re
	public void put(Trait trait) {
		this.traits.put(trait.getType(), trait);
	}
	
	// Ev�nement ex�cut� tous les ans
	public void eventYear() {
		
		// Parcours de tous les traits de caract�res
		for (final Entry<TraitType, Trait> trait : this.traits.entrySet()) {

			// Recalcul de la valeur du trait de caract�re
			trait.getValue().addYear();
		}
		// Fin du parcours des traits de caract�res
	}
}
