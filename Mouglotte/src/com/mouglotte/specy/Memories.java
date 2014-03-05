package com.mouglotte.specy;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.mouglotte.utilities.MouglotteUtilities;

public class Memories {

	// Liste des souvenirs
	private LinkedHashMap<MemoryType, Memory> memories;

	// Constructeur
	public Memories(final int maxMemories) {

		// Initialisation de la liste des souvenirs avec une limite du nombre
		this.memories = new LinkedHashMap<MemoryType, Memory>(maxMemories) {

			private static final long serialVersionUID = 1L;

			protected boolean removeEldestEntry(
					Map.Entry<MemoryType, Memory> eldest) {
				return size() > maxMemories;
			}
		};
	}

	// Récupération d'un souvenir
	public Memory get(MemoryType type) {
		return this.memories.get(type);
	}

	// Récupération du souvenir le plus proche
	public Memory getCloser(MemoryType type, int x, int y) {

		Memory retMemory = null;
		int distance;
		int minDistance = 0;

		// Parcours de tous les souvenirs
		for (final Entry<MemoryType, Memory> memory : this.memories.entrySet()) {

			// Si le souvenir est du type souhaité
			if (memory.getValue().getType() == type) {

				// Calcul de la distance
				distance = MouglotteUtilities.distance(x, y, memory.getValue()
						.getTile().getCenterX(), memory.getValue().getTile()
						.getCenterY());

				// S'il s'agit de la distance la plus courte
				if (distance < minDistance || minDistance == 0) {
					minDistance = distance;
					retMemory = memory.getValue();
				}
			}
		}
		// Fin du parcours des souvenirs

		return retMemory;
	}

	// Ajout d'un souvenir
	public void put(Memory memory) {
		this.memories.put(memory.getType(), memory);
	}
}
