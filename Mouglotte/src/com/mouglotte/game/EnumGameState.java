package com.mouglotte.game;

public enum EnumGameState {

	// Menu
	MENU(0),
	// Jeu
	GAME(1);

	// Valeur en int
	private final int value;

	// Constructeur
	private EnumGameState(int value) {
		this.value = value;
	}

	// Récupération de la valeur en int
	public int getValue() {
		return this.value;
	}
}
