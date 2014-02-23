package com.mouglotte.utilities;

public class MouglotteUtilities {

	// Distance entre deux coordonn�es
	public static int distance(int x, int y, int toX, int toY) {
		return (int) Math.sqrt(Math.pow(toY - y, 2) + Math.pow(toX - x, 2));
		//return (int) Math.hypot(arg0, arg1)
	}
	
	// Dur�e entre deux dates (long)
	public static long duration(long from, long to) {
		return to - from;
	}
	
	// La dur�e est elle une minute pile ?
	public static boolean isMinute(long duration) {
		
		// 1 min = 3 s r�elles
		return duration / 3000 == 0; 
	}
	
	// La dur�e est elle une heure pile ?
	public static boolean isHour(long duration) {
		
		// 1 h = 180 s r�elles
		return duration / 180000 == 0; 
	}
	
	// La dur�e est elle une journ�e pile ?
	public static boolean isDay(long duration) {
		
		// 1 j = 20 h = 3600 s r�elles
		return duration / 3600000 == 0; 
	}
	
	// La dur�e est elle un mois pile ?
	public static boolean isMonth(long duration) {
		
		// 1 mois = 12 j = 43200 s r�elles
		return duration / 43200000 == 0; 
	}
	
	// La dur�e est elle une saison pile ?
	public static boolean isSeason(long duration) {
		
		// 1 saison = 2 mois = 86400 s r�elles
		return duration / 86400000 == 0; 
	}
	
	// La dur�e est elle une ann�e pile ?
	public static boolean isYear(long duration) {
		
		// 1 ann�e = 3 saisons = 259200 s r�elles
		return duration / 259200000 == 0; 
	}
}
