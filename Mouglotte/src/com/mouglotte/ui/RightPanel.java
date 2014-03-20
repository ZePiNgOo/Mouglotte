package com.mouglotte.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;

import com.mouglotte.game.Game;
import com.mouglotte.game.GameState;
import com.mouglotte.map.GameMap;
import com.mouglotte.specy.MemoryType;
import com.mouglotte.specy.Mouglotte;

public class RightPanel extends MouglotteGUI implements ComponentListener {

	/** Game */
	private GameState game;

	/** Needs progress bar */
	private Bar barNeedHunger;
	private Bar barNeedRest;
	private Bar barNeedSocial;
	private Bar barNeedFun;
	/** Desires progress bar */
	private Bar barDesireHunger;
	private Bar barDesireRest;
	private Bar barDesireSocial;
	private Bar barDesireFun;
	private Bar barDesireLove;
	private Bar barDesireFight;
	private Bar barDesireWork;

	/**
	 * Constructor
	 * 
	 * @param game
	 * @throws SlickException
	 */
	public RightPanel(GameState game) throws SlickException {

		super(game);

		// Game
		this.game = game;

		// Initialize progress bars
		// Needs
		this.barNeedHunger = new Bar(game.getContainer());
		this.barNeedHunger.setLocation(2, 10);
		this.barNeedRest = new Bar(game.getContainer());
		this.barNeedRest.setLocation(2, 35);
		this.barNeedSocial = new Bar(game.getContainer());
		this.barNeedSocial.setLocation(2, 60);
		this.barNeedFun = new Bar(game.getContainer());
		this.barNeedFun.setLocation(2, 85);
		// Desires
		this.barDesireHunger = new Bar(game.getContainer());
		this.barDesireHunger.setLocation(2, 105);
		this.barDesireRest = new Bar(game.getContainer());
		this.barDesireRest.setLocation(2, 130);
		this.barDesireSocial = new Bar(game.getContainer());
		this.barDesireSocial.setLocation(2, 155);
		this.barDesireFun = new Bar(game.getContainer());
		this.barDesireFun.setLocation(2, 180);
		this.barDesireLove = new Bar(game.getContainer());
		this.barDesireLove.setLocation(2, 205);
		this.barDesireFight = new Bar(game.getContainer());
		this.barDesireFight.setLocation(2, 230);
		this.barDesireWork = new Bar(game.getContainer());
		this.barDesireWork.setLocation(2, 255);
	}

	/**
	 * Update panel
	 * 
	 * @param container
	 *            Game container
	 * @param delta
	 *            Delta time since last call
	 */
	@Override
	public void update(GameContainer container, int delta) {

		// Get selected mouglotte
		Mouglotte mouglotte = this.game.getSelectedMouglotte();

		if (mouglotte != null)
			updateForMouglotte(mouglotte);
	}

	/**
	 * Update for the selected mouglotte
	 * 
	 * @param mouglotte
	 *            Selected mouglotte
	 */
	private void updateForMouglotte(Mouglotte mouglotte) {

		// Set needs and desires values
		this.barNeedHunger.setValue(mouglotte.getNeedHungerValue());
		this.barNeedRest.setValue(mouglotte.getNeedRestValue());
		this.barNeedSocial.setValue(mouglotte.getNeedSocialValue());
		this.barNeedFun.setValue(mouglotte.getNeedFunValue());
		this.barDesireHunger.setValue(mouglotte.getDesireHungerValue());
		this.barDesireRest.setValue(mouglotte.getDesireRestValue());
		this.barDesireSocial.setValue(mouglotte.getDesireSocialValue());
		this.barDesireFun.setValue(mouglotte.getDesireFunValue());
		this.barDesireLove.setValue(mouglotte.getDesireLoveValue());
		this.barDesireFight.setValue(mouglotte.getDesireFightValue());
		this.barDesireWork.setValue(mouglotte.getDesireWorkValue());

		// Highlight current need or desire
		highlightDecision(mouglotte);
	}

	/**
	 * Highlight decision in needs and desires
	 */
	private void highlightDecision(Mouglotte mouglotte) {

		// Reset highlighting
		this.barNeedHunger.highlight(false);
		this.barNeedRest.highlight(false);
		this.barNeedSocial.highlight(false);
		this.barNeedFun.highlight(false);
		this.barDesireHunger.highlight(false);
		this.barDesireRest.highlight(false);
		this.barDesireSocial.highlight(false);
		this.barDesireFun.highlight(false);
		this.barDesireLove.highlight(false);
		this.barDesireFight.highlight(false);
		this.barDesireWork.highlight(false);

		switch (mouglotte.getDecision()) {
		case NEED_HUNGER:
			this.barNeedHunger.highlight(true);
			break;
		case NEED_REST:
			this.barNeedRest.highlight(true);
			break;
		case NEED_SOCIAL:
			this.barNeedSocial.highlight(true);
			break;
		case NEED_FUN:
			this.barNeedFun.highlight(true);
			break;
		case DESIRE_HUNGER:
			this.barDesireHunger.highlight(true);
			break;
		case DESIRE_REST:
			this.barDesireRest.highlight(true);
			break;
		case DESIRE_SOCIAL:
			this.barDesireSocial.highlight(true);
			break;
		case DESIRE_FUN:
			this.barDesireFun.highlight(true);
			break;
		case DESIRE_LOVE:
			this.barDesireLove.highlight(true);
			break;
		case DESIRE_FIGHT:
			this.barDesireFight.highlight(true);
			break;
		case DESIRE_WORK:
			this.barDesireWork.highlight(true);
			break;
		default:
			break;
		}
	}

	/**
	 * Render panel
	 * 
	 * @param container
	 *            Game container
	 * @param g
	 *            Graphics
	 */
	@Override
	public void render(GameContainer container, Graphics g) {

		// Translate graphics to draw at the right place
		// Game map takes this width: GameMap.DISPLAYED_TILES *
		// GameMap.getTileSize()
		g.translate(GameMap.DISPLAYED_TILES * GameMap.TILE_SIZE + 1, 0);

		// Draw only the first time

		// Display panel border
		g.setColor(Color.red);
		g.drawRect(0, 0, Game.SCREEN_WIDTH - GameMap.TILE_SIZE
				* GameMap.DISPLAYED_TILES - 2, Game.SCREEN_HEIGHT - 1);

		// Get selected mouglotte
		Mouglotte mouglotte = this.game.getSelectedMouglotte();

		if (mouglotte != null)
			renderForMouglotte(container, g, mouglotte);

		// Initialize graphics position
		g.resetTransform();
	}

	/**
	 * Render panel for the selected mouglotte
	 * 
	 * @param container
	 *            Game container
	 * @param g
	 *            Graphics
	 * @param mouglotte
	 *            Selected mouglotte
	 */
	private void renderForMouglotte(GameContainer container, Graphics g,
			Mouglotte mouglotte) {

		g.drawString("Mouglotte", 2, 2);

		// Render needs and desires values
		try {
			this.barNeedHunger.render(container, g);
			this.barNeedRest.render(container, g);
			this.barNeedSocial.render(container, g);
			this.barNeedFun.render(container, g);
			this.barDesireHunger.render(container, g);
			this.barDesireRest.render(container, g);
			this.barDesireSocial.render(container, g);
			this.barDesireFun.render(container, g);
			this.barDesireLove.render(container, g);
			this.barDesireFight.render(container, g);
			this.barDesireWork.render(container, g);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void componentActivated(AbstractComponent arg0) {
		// TODO Auto-generated method stub

	}

}
