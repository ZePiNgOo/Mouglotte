package com.mouglotte.genetics;
import java.util.Random;

public class Chromosome {

	// Longueur d'une s�quence
	private static int SEQUENCE_LENGTH = 200;

	// S�quence ADN du chromosome
	private String sequence;


	// Constructeur
	public Chromosome() {
		
	}

	// // D�finition de la s�quence du g�ne
	// public void setSequence(String sequence) {
	// this.sequence = sequence;
	// }

	// R�cup�ration de la s�quence ADN
	public String getSequence() {
		return this.sequence;
	}

	// Cr�ation de la s�quence ADN du chromosome
	public void createSequence() {

		Random r = new Random();

		this.sequence = new String();
		// Ajout de caract�res al�atoirement
		String alphabet = "ACGT";
		for (int i = 0; i < SEQUENCE_LENGTH; i++) {
			this.sequence = this.sequence
					+ alphabet.charAt(r.nextInt(alphabet.length()));
		}
	}
}
