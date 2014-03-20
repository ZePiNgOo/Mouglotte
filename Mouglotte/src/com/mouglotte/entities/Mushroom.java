package com.mouglotte.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.mouglotte.game.GameState;
import com.mouglotte.map.GameMap;
import com.mouglotte.map.Tile;

public class Mushroom extends FoodEntity {

	/** Image */
	private Image image;
	/** Real coordinates */
	private int realX;
	private int realY;

	/**
	 * Constructor
	 * 
	 * @param game
	 *            Game
	 * @param tile
	 *            Tile
	 */
	public Mushroom(GameState game, Tile tile) {

		super(game, tile);
		
		// Begins with food
		this.food = 100;

		// Set image
		try {
//			this.image = new Image("res/mushroom.png", new Color(255, 0, 255));
			this.image = new Image("res/mushroom.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}

		// Set real coordinates
		this.realX = this.x - this.image.getWidth() / 2;
		this.realY = this.y - this.image.getHeight() + GameMap.TILE_SIZE / 2
				- 10;
	}
	
	@Override
	public boolean contains(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void eventHealthChanged(int oldHealth, int newHealth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void eventLevelChanged(int oldLevel, int newLevel) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void eventFoodChanged(int oldFood, int newFood) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void calculateHealth() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void calculateHealthSurplus() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void calculateLevel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void calculateFood(int oldHealth, int newHealth) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	protected void mouseMoved(int x, int y) {
//
//		// Si l'image est généralisée dans FoodEntity (voire Entity) on peut
//		// gérer cela dans FoodEntity (voire Entity)
//
//		// Check if mouse is on the image (as a rectangle)
//		if (GameMap.convNoScrollX(x) >= this.realX
//				&& GameMap.convNoScrollX(x) <= this.realX
//						+ this.image.getWidth()
//				&& GameMap.convNoScrollY(y) >= this.realY
//				&& GameMap.convNoScrollY(y) <= this.realY
//						+ this.image.getHeight())
//			this.over = true;
//	}
//
//	@Override
//	protected void mouseRightClicked(int x, int y) {
//		// Nothing
//		// If giving fruits, could be a target for food
//	}

	@Override
	public void render(GameContainer container, Graphics g) {

		super.render(container, g);
		
		g.drawImage(this.image, realX, realY);
	}

//	@Override
//	protected void mouseLeftClicked(int x, int y, int clickCount) {
//		// TODO Auto-generated method stub
//		super.mouseLeftClicked(x, y, clickCount);
//	}

//	@Override
//	protected void mouseRightClicked(int x, int y) {
//		// TODO Auto-generated method stub
//		super.mouseRightClicked(x, y);
//		
//	}

//	@Override
//	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
//		// TODO Auto-generated method stub
//		super.mouseMoved(oldx, oldy, newx, newy);
//		
//		//toto
//		
//	}
	
	@Override
	protected void mouseLeftClicked(int x, int y) {
		// TODO Auto-generated method stub
		super.mouseLeftClicked(x, y);
	}

	@Override
	protected void mouseRightClicked(int x, int y) {
		// TODO Auto-generated method stub
		super.mouseRightClicked(x, y);
	}

	@Override
	public void mouseMoved(int x, int y) {
		// TODO Auto-generated method stub
		super.mouseMoved(x, y);
	}
}
