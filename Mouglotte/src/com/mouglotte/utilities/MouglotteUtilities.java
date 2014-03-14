package com.mouglotte.utilities;

import com.mouglotte.map.Tile;

public class MouglotteUtilities {

	/**
	 * Distance between two points
	 * 
	 * @param tile
	 *            From tile
	 * @param toTile
	 *            To tile
	 * @return
	 */
	public static double distance(Tile tile, Tile toTile) {
		return Math.hypot(toTile.getCenterY() - tile.getCenterY(),
				toTile.getCenterX() - tile.getCenterX());
	}

	/**
	 * Distance between two tiles
	 * 
	 * @param x
	 *            from x position
	 * @param y
	 *            from y position
	 * @param toX
	 *            to x position
	 * @param toY
	 *            to y position
	 * @return
	 */
	public static double distance(int x, int y, int toX, int toY) {
		return Math.hypot(toY - y, toX - x);
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
