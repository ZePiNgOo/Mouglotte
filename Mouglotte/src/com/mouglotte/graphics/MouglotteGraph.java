package com.mouglotte.graphics;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.GUIContext;

import com.mouglotte.game.GameState;
import com.mouglotte.map.GameMap;
import com.mouglotte.map.UnitMover;
import com.mouglotte.specy.Mouglotte;

public class MouglotteGraph extends AbstractComponent {

	// Mouglotte
	private Mouglotte mouglotte;
	// Listener
	private MouglotteListener listener;
	// Jeu
	private GameState game;

	// Position
	int x = -1;
	int y = -1;
	int lastX = -1;
	int lastY = -1;

	// Forme
	private Shape shape;
	private Shape halo;
	private Graphics g;
	private Color color;

	// Que fais-je
	private Shape doing;

	// Survol souris
	private boolean over = false;
	// Mouglotte s�lectionn�e
	private boolean selected = false;
	// Bouton souris press�
	private boolean mouseDown = false;
	private boolean mouseLeft = false;
	private boolean mouseRight = false;

	// Constructeur
	public MouglotteGraph(Mouglotte mouglotte, GameState game) {

		super(game.getContainer());

		this.game = game;
		this.mouglotte = mouglotte;
		this.listener = new MouglotteListener(this.game.getContainer());
		this.container = container;

		addListener(this.listener);
		// /**** les coordonn�es de la case dans le jeu !! *******/
		// this.posEcran = posEcran;
		// this.nomCase = name;

		Input input = this.game.getContainer().getInput();
		this.shape = new Circle(this.x, this.y, 10);
		this.halo = new Circle(this.x, this.y, 20);
		this.over = shape.contains(input.getMouseX(), input.getMouseY());
		// this.mouseDown = input.isMouseButtonDown(0);
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	// R�cup�ration de la position
	public int getX() {
		return this.x;
	}

	@Override
	// R�cup�ration de la position
	public int getY() {
		return this.y;
	}
	
	// R�cup�ration de la position pr�c�dente
	public int getLastX() {
		return this.lastX;
	}

	// R�cup�ration de la position pr�c�dente
	public int getLastY() {
		return this.lastY;
	}

	// La mouglotte est-elle s�lectionn�e
	public boolean isSelected() {
		return this.selected;
	}

	@Override
	// D�finition de la position
	public void setLocation(int x, int y) {

		// Ancienne position
		this.lastX = this.x;
		this.lastY = this.y;
		// Position
		this.x = x;
		this.y = y;
		
		// D�placement de la forme
		if (this.shape != null) {

			this.shape.setX(this.x - this.shape.getWidth() / 2);
			this.shape.setY(this.y - this.shape.getHeight() / 2);

			// Si la mouglotte a boug� elle n'est peut �tre plus survol�e par la
			// souris
			this.over = this.shape.contains(x, y);
		}

		// D�placement du halo
		if (this.halo != null) {
			
			this.halo.setX(this.x - this.halo.getWidth()/2);
			this.halo.setY(this.y - this.halo.getHeight()/2);
		}
	}

	@Override
	// Affichage
	public void render(GUIContext c, Graphics g) throws SlickException {

		switch (this.mouglotte.getDecision()) {
		case NEED_HUNGER:
			g.drawString("NH", 120, 90);
			break;
		case NEED_REST:
			g.drawString("NR", 120, 90);
			break;
		case NEED_SOCIAL:
			g.drawString("NS", 120, 90);
			break;
		case NEED_FUN:
			g.drawString("NF", 120, 90);
			break;
		case DESIRE_HUNGER:
			g.drawString("DH", 120, 90);
			break;
		case DESIRE_REST:
			g.drawString("DR", 120, 90);
			break;
		case DESIRE_SOCIAL:
			g.drawString("DS", 120, 90);
			break;
		case DESIRE_FUN:
			g.drawString("DF", 120, 90);
			break;
		case DESIRE_LOVE:
			g.drawString("DL", 120, 90);
			break;
		case DESIRE_FIGHT:
			g.drawString("DF", 120, 90);
			break;
		case DESIRE_WORK:
			g.drawString("DW", 120, 90);
			break;
		default:
		}

		Color color = g.getColor();
		
		if (this.selected) {
			this.color = Color.blue;
			notifyListeners(); // Active le MouglotteListener
		} else if (this.over) {
			this.color = Color.green;
		} else  {
			this.color = Color.red;
		}
		
		g.setColor(this.color);
		g.fill(this.shape);

		// Affichage du halo de s�lection
		if (this.selected)
			g.draw(this.halo);

		// Retour � la couleur d'origine
		g.setColor(color);
	}

	// @Override
	// // Bouton souris relach�
	// public void mouseReleased(int button, int x, int y) {
	//
	// this.over = this.shape.contains(x, y);
	// if (button == 0) {
	// this.mouseDown = false;
	// }
	// }

	@Override
	// Souris boug�e
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		this.over = this.shape.contains(newx, newy);
	}

	// @Override
	// // Bouton souris press�
	// public void mousePressed(int button, int x, int y) {
	//
	// this.over = this.shape.contains(x, y);
	// if (button == 0) {
	// this.mouseDown = true;
	// }
	// }

	@Override
	// Bouton souris cliqu�
	public void mouseClicked(int button, int x, int y, int clickCount) {

		// Si la souris est sortie de la carte on ne fait rien
		if (!this.game.getMap().contains(x,y)) return;
		
		if (button == 0)
			mouseLeftClicked(x, y, clickCount);
		else if (button == 1)
			mouseRightClicked(x, y);
	}

	// Bouton souris cliqu� bouton gauche
	public void mouseLeftClicked(int x, int y, int clickCount) {

		// Si la souris est dans la forme
		if (this.shape.contains(x, y))
			this.selected = true;
		else
			this.selected = false;

		// Mais il faut aussi d�selectionner ce qui l'�tait
	}

	// Bouton souris cliqu� bouton droit
	public void mouseRightClicked(int x, int y) {

		// Si la mouglotte est s�lectionn�e
		if (this.selected) {

			// Aller � l'endroit cliqu�
			this.mouglotte.goTo(x, y);
		}
	}

}
