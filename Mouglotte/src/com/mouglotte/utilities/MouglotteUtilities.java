package com.mouglotte.utilities;

public class MouglotteUtilities {

	// Distance entre deux coordonnées
	public static int distance(int x, int y, int toX, int toY) {
		return (int) Math.sqrt(Math.pow(toY - y, 2) + Math.pow(toX - x, 2));
		//return (int) Math.hypot(arg0, arg1)
	}
	
	// Durée entre deux dates (long)
	public static long duration(long from, long to) {
		return to - from;
	}
	
	// La durée est elle une minute pile ?
	public static boolean isMinute(long duration) {
		
		// 1 min = 3 s réelles
		return duration / 3000 == 0; 
	}
	
	// La durée est elle une heure pile ?
	public static boolean isHour(long duration) {
		
		// 1 h = 180 s réelles
		return duration / 180000 == 0; 
	}
	
	// La durée est elle une journée pile ?
	public static boolean isDay(long duration) {
		
		// 1 j = 20 h = 3600 s réelles
		return duration / 3600000 == 0; 
	}
	
	// La durée est elle un mois pile ?
	public static boolean isMonth(long duration) {
		
		// 1 mois = 12 j = 43200 s réelles
		return duration / 43200000 == 0; 
	}
	
	// La durée est elle une saison pile ?
	public static boolean isSeason(long duration) {
		
		// 1 saison = 2 mois = 86400 s réelles
		return duration / 86400000 == 0; 
	}
	
	// La durée est elle une année pile ?
	public static boolean isYear(long duration) {
		
		// 1 année = 3 saisons = 259200 s réelles
		return duration / 259200000 == 0; 
	}
}
