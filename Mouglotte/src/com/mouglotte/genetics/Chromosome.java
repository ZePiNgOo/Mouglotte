package com.mouglotte.genetics;
import java.util.Random;

public class Chromosome {

	// Longueur d'une séquence
	private static int SEQUENCE_LENGTH = 200;

	// Séquence ADN du chromosome
	private String sequence;


	// Constructeur
	public Chromosome() {
		
	}

	// // Définition de la séquence du gène
	// public void setSequence(String sequence) {
	// this.sequence = sequence;
	// }

	// Récupération de la séquence ADN
	public String getSequence() {
		return this.sequence;
	}

	// Création de la séquence ADN du chromosome
	public void createSequence() {

		Random r = new Random();

		this.sequence = new String();
		// Ajout de caractères aléatoirement
		String alphabet = "ACGT";
		for (int i = 0; i < SEQUENCE_LENGTH; i++) {
			this.sequence = this.sequence
					+ alphabet.charAt(r.nextInt(alphabet.length()));
		}
	}
}
