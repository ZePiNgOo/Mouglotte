package com.mouglotte.test;

import com.mouglotte.map.GameMap;
import com.mouglotte.map.Tile;

public class TestTile {

	public TestTile() {
		
		GameMap.tileOffsetX = 2;
		GameMap.tileOffsetY = 2;
		GameMap.offsetX = -14;
		GameMap.offsetY = -24;
		GameMap.TILE_SIZE = 64;
		
		Tile tile = Tile.create(150, 100);
		
		int x = tile.getX();
		int y = tile.getY();
		
		int i = tile.getColumn();
		int j = tile.getRow();
		
		x = tile.getCenterX();
		y = tile.getCenterY();
		
//		boolean toto = tile.contains(150, 100);
//		toto = tile.contains(142, 88);
//		toto = tile.contains(174, 120);
//		toto = tile.contains(206, 152);
//		toto = tile.contains(207, 152);
//		toto = tile.contains(142, 87);
	}
	
    public static void main(String args[]) {
    	
    	new TestTile();
    }
}
