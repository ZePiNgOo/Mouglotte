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

import com.mouglotte.game.GameState;

public class RightPanel extends MouglotteGUI implements ComponentListener {

	private JPanel panel;
	private int state;
	private MouseOverArea play;

	public RightPanel(GameContainer c, String name, int state)
			throws SlickException {

		super(c, name);
		this.state = state;
		
//		panel = new JPanel();
//		panel.setBackground((java.awt.Color) Color.yellow);

		// Création du bouton
		this.play = new MouseOverArea(c, new Image("res/bouton.png"), 600, 300,
				this);
		this.play.setNormalColor(new Color(0.7f, 0.7f, 0.7f, 1f));
		this.play.setMouseOverColor(new Color(0.9f, 0.9f, 0.9f, 1f));
	}

	@Override
	public void drawGUI(GameContainer container, Graphics g) {

		g.drawString("Mouglotte", 50, 50);
		g.drawString("--> Etat :" + this.state, 200, 50);

		play.render(container, g);

	}

	@Override
	public void update(GameContainer container, int state) {
		this.state = state;
	}

	@Override
	public void componentActivated(AbstractComponent arg0) {
		// TODO Auto-generated method stub
		
	}

}
