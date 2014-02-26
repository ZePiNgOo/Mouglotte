package com.mouglotte.ui;

import javax.swing.JPanel;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;

import com.mouglotte.game.Game;
import com.mouglotte.game.GameState;
import com.mouglotte.map.GameMap;

public class RightPanel extends MouglotteGUI implements ComponentListener {

	private MouseOverArea play;

	public RightPanel(GameState game) throws SlickException {

		super(game);

		// panel = new JPanel();
		// panel.setBackground((java.awt.Color) Color.yellow);

		// Création du bouton
		this.play = new MouseOverArea(game.getContainer(), new Image(
				"res/bouton.png"), 100, 100, this);
		this.play.setNormalColor(new Color(0.7f, 0.7f, 0.7f, 1f));
		this.play.setMouseOverColor(new Color(0.9f, 0.9f, 0.9f, 1f));
	}

	@Override
	public void update(GameContainer container, int delta) {

	}

	@Override
	public void render(GameContainer container, Graphics g) {

		// Décalage pour bien se placer dans le panel
		// La carte prend GameMap.DISPLAYED_TILES * GameMap.getTileSize()
		g.translate(GameMap.DISPLAYED_TILES * this.game.getMap().getTileSize()
				+ 1, 0);

		// Affichage d'un cadre
		g.setColor(Color.red);
		g.drawRect(0, 0, Game.SCREEN_WIDTH - this.game.getMap().getTileSize()
				* GameMap.DISPLAYED_TILES - 2, Game.SCREEN_HEIGHT - 1);

		g.drawString("Mouglotte", 500, 50);
		play.render(container, g);

		// Réinitialisation de la position du Graphics
		g.resetTransform();
	}

	@Override
	public void componentActivated(AbstractComponent arg0) {
		// TODO Auto-generated method stub

	}

}
