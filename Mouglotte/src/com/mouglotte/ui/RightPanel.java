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

//		// Création du bouton
//		this.play = new MouseOverArea(game.getContainer(), new Image(
//				"res/bouton.png"), 100, 100, this);
//		this.play.setNormalColor(new Color(0.7f, 0.7f, 0.7f, 1f));
//		this.play.setMouseOverColor(new Color(0.9f, 0.9f, 0.9f, 1f));
		
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

	@Override
	public void update(GameContainer container, int delta) {

		// Get selected mouglotte
		Mouglotte mouglotte = this.game.getSelectedMouglotte();
		
		if (mouglotte != null) {
			
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
			switch (mouglotte.getDecision()) {
			case NEED_HUNGER:
				this.barNeedHunger.setLineColor(Color.green);
				break;
			case NEED_REST:
				this.barNeedRest.setLineColor(Color.green);
				break;
			case NEED_SOCIAL:
				this.barNeedSocial.setLineColor(Color.green);
				break;
			case NEED_FUN:
				this.barNeedFun.setLineColor(Color.green);
				break;
			case DESIRE_HUNGER:
				this.barDesireHunger.setLineColor(Color.green);
				break;
			case DESIRE_REST:
				this.barDesireRest.setLineColor(Color.green);
				break;
			case DESIRE_SOCIAL:
				this.barDesireSocial.setLineColor(Color.green);
				break;
			case DESIRE_FUN:
				this.barDesireFun.setLineColor(Color.green);
				break;
			case DESIRE_LOVE:
				this.barDesireLove.setLineColor(Color.green);
				break;
			case DESIRE_FIGHT:
				this.barDesireFight.setLineColor(Color.green);
				break;
			case DESIRE_WORK:
				this.barDesireWork.setLineColor(Color.green);
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void render(GameContainer container, Graphics g) {

		// Décalage pour bien se placer dans le panel
		// La carte prend GameMap.DISPLAYED_TILES * GameMap.getTileSize()
		g.translate(GameMap.DISPLAYED_TILES * GameMap.TILE_SIZE + 1,
				0);

		// Affichage d'un cadre
		g.setColor(Color.red);
		g.drawRect(0, 0, Game.SCREEN_WIDTH - GameMap.TILE_SIZE
				* GameMap.DISPLAYED_TILES - 2, Game.SCREEN_HEIGHT - 1);

		g.drawString("Mouglotte", 2, 2);

		// Réinitialisation de la position du Graphics
		g.resetTransform();
	}

	@Override
	public void componentActivated(AbstractComponent arg0) {
		// TODO Auto-generated method stub

	}

}
