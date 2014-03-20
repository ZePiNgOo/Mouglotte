package com.mouglotte.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import com.mouglotte.game.GameState;
import com.mouglotte.map.Tile;

public class Tree extends WorkingEntity {

	/** Shape (TEST) */
	private Shape shape;

	/**
	 * Constructor
	 * 
	 * @param game
	 *            Game
	 * @param tile
	 *            Tile
	 */
	public Tree(GameState game, Tile tile) {

		// Working entity of type WOODCUTING
		super(game, tile, WorkType.WOODCUTING);

		// Shape (TESTS)
		// this.x and this.y indicate the center of the tile
		this.shape = new Rectangle(this.x - 25, this.y - 140, 50, 150);
	}
	
	@Override
	public boolean contains(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void eventMonth() {
		// Nothing
		// Eventually giving fruits ?
	}

	@Override
	public void eventSeason() {
		// Nothing
		// Eventually blossom/loose leaves ?
	}

	@Override
	public void eventYear() {
		// Nothing
		// Eventually dies ?
	}

//	@Override
//	protected void mouseMoved(int x, int y) {
//		// Nothing (hard to handle mouse over)
//	}
//
//	@Override
//	protected void mouseLeftClicked(int x, int y) {
//		// Nothing
//	}
//
//	@Override
//	protected void mouseRightClicked(int x, int y) {
//		// Nothing
//		// If giving fruits, could be a target for food
//	}

	@Override
	public void render(GameContainer container, Graphics g) {

		Color color = g.getColor();

		// Set color
		g.setColor(Color.green);

		g.fill(this.shape);

		// Back to initial color
		g.setColor(color);
	}

//	@Override
//	protected void mouseLeftClicked(int x, int y, int clickCount) {
//		// TODO Auto-generated method stub
//		super.mouseLeftClicked(x, y, clickCount);
//	}
//
//	@Override
//	protected void mouseRightClicked(int x, int y) {
//		// TODO Auto-generated method stub
//		super.mouseRightClicked(x, y);
//	}
//
//	@Override
//	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
//		// TODO Auto-generated method stub
//		super.mouseMoved(oldx, oldy, newx, newy);
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
