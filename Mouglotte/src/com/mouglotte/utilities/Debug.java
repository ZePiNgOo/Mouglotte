package com.mouglotte.utilities;

public class Debug {

	public static final boolean MOUGLOTTE = true;
	
	// Affichage dans la console
	public static void log(String type, String msg) {
		//if (Debug.class.getField(type).)
		if (MOUGLOTTE)
			System.out.println(msg);
	}
}
