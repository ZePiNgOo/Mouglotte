package com.mouglotte.specy;

import java.util.Hashtable;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import com.mouglotte.game.GameState;
import com.mouglotte.genetics.Genetics;
import com.mouglotte.genetics.Karyotype;
import com.mouglotte.graphics.MouglotteGraph;
import com.mouglotte.graphics.MouglotteListener;
import com.mouglotte.map.GameMap;
import com.mouglotte.map.Path;
import com.mouglotte.map.Tile;
import com.mouglotte.map.UnitMover;
import com.mouglotte.utilities.MouglotteUtilities;

public class Mouglotte {

	// Distance de promenade au hasard
	private final int WALK_AROUND_DISTANCE = 10;

	// Jeu
	private GameState game;

	// Caryotype
	private Karyotype karyotype;
	// Mémoire
	private Memories memories;
	// Traits de caractères
	private Traits traits;
	// Besoins
	private Needs needs;
	// Envies
	private Desires desires;
	// Décision
	private DecisionType decision = DecisionType.NEED_HUNGER;
	// Graphismes
	private MouglotteGraph graphics;

	// Date de naissance
	private long birthDate;
	// Age
	private int age;

	// Chemin
	private Path path;
	// Action en cours
	private boolean actionInProgress;

	// Constructeur
	public Mouglotte(GameState game) {

		// Jeu
		this.game = game;
		// Caryotype
		this.karyotype = new Karyotype();
		// Mémoire
		this.memories = new Memories(getGeneValue("ZE"));
		// Traits de caractère
		this.traits = new Traits(this);
		// Besoins
		this.needs = new Needs(this);
		// Envies
		this.desires = new Desires(this);

		// Date de naissance
		this.birthDate = System.currentTimeMillis();

		// Graphismes
		this.graphics = new MouglotteGraph(this, this.game);

		// Initialisation des traits de caractères
		initTraits();
		// Initialisation des besoins
		initNeeds();
		// Initialisation des envies
		initDesires();
	}

	// Définition de la position
	public void setLocation(int x, int y) {
		this.graphics.setLocation(x, y);
	}

	// Définition du chemin
	public void setPath(Path path) {
		this.path = path;
	}

	// Récupération de l'âge
	public int getAge() {
		return this.age;
	}

	// Récupération la position
	public int getX() {
		return this.graphics.getX();
	}

	// Récupération la position
	public int getY() {
		return this.graphics.getY();
	}

	// Récupération de la décision
	public DecisionType getDecision() {
		return this.decision;
	}

	// La mouglotte est-elle sélectionnée
	public boolean isSelected() {
		return this.graphics.isSelected();
	}

	// Naissance
	// public static Mouglotte birth() {
	//
	// return new Mouglotte();
	// }

	// Initialisation des traits de caractères
	private void initTraits() {

		// Traits, Type, birthValue, babyGrowth, childGrowth, adultGrowth,
		// oldGrowth, exerciseGain, exerciceLoss

		// Force
		Trait trait = new Trait(this.traits, TraitType.STRENGTH,
				getGeneValue("AA"), getGeneValue("CF"), getGeneValue("GY"),
				getGeneValue("BS"), getGeneValue("RT"));
		this.traits.put(trait);

	}

	// Initialisation des besoins
	private void initNeeds() {

		// Needs, Type, babyHourGain, childHourGain, adultHourGain, oldHourGain,
		// fulfillLoss

		// Faim
		Need need = new Need(this.needs, NeedType.HUNGER, getGeneValue("MQ"),
				getGeneValue("JK"), getGeneValue("NS"), getGeneValue("IZ"),
				getGeneValue("VS"));
		this.needs.put(need);
		// Repos
		need = new Need(this.needs, NeedType.REST, getGeneValue("YU"),
				getGeneValue("ED"), getGeneValue("JI"), getGeneValue("MA"),
				getGeneValue("CS"));
		this.needs.put(need);
		// Social
		need = new Need(this.needs, NeedType.SOCIAL, getGeneValue("LM"),
				getGeneValue("YA"), getGeneValue("FD"), getGeneValue("OO"),
				getGeneValue("IT"));
		this.needs.put(need);
		// Divertissement
		need = new Need(this.needs, NeedType.FUN, getGeneValue("QN"),
				getGeneValue("UY"), getGeneValue("CV"), getGeneValue("TT"),
				getGeneValue("XS"));
		this.needs.put(need);
	}

	// Initialisation des envies
	private void initDesires() {

		// Desires, Type, babyValue, childValue, adultValue, oldValue,
		// fulfillLoss

		// Faim
		Desire desire = new Desire(this.desires, DesireType.HUNGER,
				getGeneValue("AD"), getGeneValue("FF"), getGeneValue("BG"),
				getGeneValue("CX"), getGeneValue("UR"));
		desire.setRelatedNeed(this.needs.get(NeedType.HUNGER));
		this.desires.put(desire);

		// Repos
		desire = new Desire(this.desires, DesireType.REST, getGeneValue("EZ"),
				getGeneValue("FC"), getGeneValue("HE"), getGeneValue("NZ"),
				getGeneValue("PS"));
		desire.setRelatedNeed(this.needs.get(NeedType.REST));
		this.desires.put(desire);

		// Social
		desire = new Desire(this.desires, DesireType.SOCIAL,
				getGeneValue("JU"), getGeneValue("LS"), getGeneValue("MD"),
				getGeneValue("NC"), getGeneValue("ID"));
		desire.setRelatedNeed(this.needs.get(NeedType.SOCIAL));
		this.desires.put(desire);

		// Divertissement
		desire = new Desire(this.desires, DesireType.FUN, getGeneValue("KZ"),
				getGeneValue("DP"), getGeneValue("JA"), getGeneValue("SO"),
				getGeneValue("BQ"));
		desire.setRelatedNeed(this.needs.get(NeedType.FUN));
		this.desires.put(desire);

		// Amour
		desire = new Desire(this.desires, DesireType.LOVE, getGeneValue("IS"),
				getGeneValue("GZ"), getGeneValue("DA"), getGeneValue("DF"),
				getGeneValue("FZ"));
		this.desires.put(desire);

		// Combat
		desire = new Desire(this.desires, DesireType.FIGHT, getGeneValue("JL"),
				getGeneValue("NE"), getGeneValue("SC"), getGeneValue("YE"),
				getGeneValue("KS"));
		this.desires.put(desire);

		// Travail
		desire = new Desire(this.desires, DesireType.WORK, getGeneValue("ZJ"),
				getGeneValue("US"), getGeneValue("FQ"), getGeneValue("WJ"),
				getGeneValue("VT"));
		this.desires.put(desire);

	}

	// Récupération de la valeur du gène
	private int getGeneValue(String name) {
		return Genetics.getValue(name, this.karyotype);
	}

	// // Evénement exécuté par le timer
	// public void event(long time) {
	//
	// if (MouglotteUtilities.isMinute(time))
	// eventMinute();
	// else if (MouglotteUtilities.isHour(time))
	// eventHour();
	// else if (MouglotteUtilities.isDay(time))
	// eventDay();
	// else if (MouglotteUtilities.isYear(time))
	// eventYear();
	// }

	// Evénement exécuté toutes les minutes
	public void eventMinute() {

		// Pour les besoins
		this.needs.eventMinute();
		// Pour les envies
		this.desires.eventMinute();

		// Effectue une action
		action();
	}

	// Evénement exécuté toutes les heures
	public void eventHour() {

		// Pour les besoins
		this.needs.eventHour();
		// Pour les envies
		this.desires.eventHour();

		// Prise de décision
		decide();
	}

	// Evénement exécuté tous les jours
	public void eventDay() {

	}

	// Evénement exécuté tous les ans
	public void eventYear() {

		// Joyeux anniversaire
		this.age++;

		// Pour les traits de caractères
		this.traits.eventYear();
	}

	// Decision
	private void decide() {

		System.out.println("Mouglotte::Decide");
		
		// Une nouvelle action va commencer ensuite
		this.actionInProgress = false;

		// Je veux suivre un besoin
		if (this.needs.getCurrent().getValue() > this.desires.getCurrent()
				.getValue()) {

			this.needs.setSearching(true);
			this.desires.setSearching(false);
			this.desires.setFulfilling(false);

			switch (this.needs.getCurrent().getType()) {
			case HUNGER:
				this.decision = DecisionType.NEED_HUNGER;
				break;
			case REST:
				this.decision = DecisionType.NEED_REST;
				break;
			case SOCIAL:
				this.decision = DecisionType.NEED_SOCIAL;
				break;
			case FUN:
				this.decision = DecisionType.NEED_FUN;
				break;
			}

			// Je veux suivre une envie
		} else {
			this.desires.setSearching(true);
			this.needs.setSearching(false);
			this.needs.setFulfilling(false);

			switch (this.desires.getCurrent().getType()) {
			case HUNGER:
				this.decision = DecisionType.DESIRE_HUNGER;
				break;
			case REST:
				this.decision = DecisionType.DESIRE_REST;
				break;
			case SOCIAL:
				this.decision = DecisionType.DESIRE_SOCIAL;
				break;
			case FUN:
				this.decision = DecisionType.DESIRE_FUN;
				break;
			case LOVE:
				this.decision = DecisionType.DESIRE_LOVE;
				break;
			case FIGHT:
				this.decision = DecisionType.DESIRE_FIGHT;
				break;
			case WORK:
				this.decision = DecisionType.DESIRE_WORK;
				break;
			}
		}

		System.out.println("Mouglotte::Decide:Decision=" + this.decision);
	}

	// Effectuer une action
	private void action() {

		System.out.println("Mouglotte::Action");

		// Rechercher si l'action peut être conclue
		// (nourriture à proximité,...)
		actionFulfilled();

		// Une action va à son terme sauf si un événement arrive
		if (this.actionInProgress)
			continueAction();
		else
			newAction();
	}

	// Rechercher si l'action peut être conclue
	private void actionFulfilled() {

		System.out.println("Mouglotte::ActionFulFilled");
		
		MemoryType memoryType = null;

		// Qu'est ce que la mouglotte recherche ?
		switch (this.decision) {
		case NEED_HUNGER:
		case DESIRE_HUNGER:
			memoryType = MemoryType.FOOD;
			break;
		case NEED_SOCIAL:
		case DESIRE_SOCIAL:
			memoryType = MemoryType.FRIEND;
			break;
		case DESIRE_LOVE:
			memoryType = MemoryType.LOVER;
			break;
		case DESIRE_FIGHT:
			memoryType = MemoryType.ENEMY;
			break;
		case DESIRE_WORK:
			memoryType = MemoryType.WORK;
			break;
		default:
			break;
		}

		// Recherche à côté
		Tile tile = this.game.getMap().searchNear(memoryType,
				this.graphics.getX(), this.graphics.getY());

		// La mouglotte a trouvé ce qu'elle cherche
		if (tile != null) {

			// Inutile de continuer à marcher
			this.path = null;

			// La mouglotte est à côté de l'endroit
			if (tile.getX() != this.graphics.getX()
					|| tile.getY() != this.graphics.getY())
				// Allons-y
				goTo(tile.getX(), tile.getY());

			// La mouglotte est au bon endroit, accomplissons
			else
				fulfill();

			// La mouglotte n'a pas trouvé ce qu'elle cherche
		} else {

			// Si la mouglotte est à destination
			if (this.path == null)
				// L'action est terminée, on n'a rien trouvé
				this.actionInProgress = false;
		}
	}

	// Nouvelle action
	private void newAction() {

		System.out.println("Mouglotte::NewAction");

		MemoryType memoryType = null;
		Memory memory = null;

		// Action en cours
		this.actionInProgress = true;

		// Recherche d'un souvenir concernant la décision en cours
		if (this.decision != null)
			switch (this.decision) {
			case NEED_HUNGER:
			case DESIRE_HUNGER:
				memoryType = MemoryType.FOOD;
				break;
			case NEED_SOCIAL:
			case DESIRE_SOCIAL:
				memoryType = MemoryType.FRIEND;
				break;
			case DESIRE_LOVE:
				memoryType = MemoryType.LOVER;
				break;
			case DESIRE_FIGHT:
				memoryType = MemoryType.ENEMY;
				break;
			case DESIRE_WORK:
				memoryType = MemoryType.WORK;
				break;
			default:
				break;
			}

		// Récupération du souvenir le plus proche
		if (memoryType != null)
			memory = this.memories.getCloser(memoryType, this.graphics.getX(),
					this.graphics.getY());

		// Aller à l'endroit du souvenir
		if (memory != null)
			goTo(memory.getX(), memory.getY());
		// Ou se promener au hasard
		else
			walkAround();
	}

	// Continuer l'action
	private void continueAction() {

		System.out.println("Mouglotte::ContinueAction");

		// Marcher
		walk();
	}

	// Aller à
	public void goTo(int x, int y) {

		System.out.println("Mouglotte::GoTo " + x + "," + y);

		// Si la souris est sortie de la carte on ne fait rien
		if (!this.game.getMap().contains(x, y))
			return;

		// Réinitialisation des zones visitées
		this.game.getMap().clearVisited();

		// Recherche du chemin
		setPath(this.game.getMap().findPath(new UnitMover(3), getX(), getY(),
				x, y));
	}

	// Se promener au hasard
	private void walkAround() {

		System.out.println("Mouglotte::WalkAround");

		Random r = new Random();
		int destX = 0, destY = 0;

		// Direction actuelle
		int dirX = this.graphics.getX() - this.graphics.getLastX();
		int dirY = this.graphics.getY() - this.graphics.getLastY();

		// Une chance sur 2 de continuer dans la même direction
		if (r.nextBoolean() == true) {

			destX = this.graphics.getX() + WALK_AROUND_DISTANCE * dirX;
			destY = this.graphics.getY() + WALK_AROUND_DISTANCE * dirY;

			// On va dans une direction au hasard
			// (y compris la direction actuelle pour simplifier)
		} else {

			switch (r.nextInt(8)) {
			case 0:
				destX = this.graphics.getX() + WALK_AROUND_DISTANCE * 0;
				destY = this.graphics.getY() + WALK_AROUND_DISTANCE * 1;
				break;
			case 1:
				destX = this.graphics.getX() + WALK_AROUND_DISTANCE * 1;
				destY = this.graphics.getY() + WALK_AROUND_DISTANCE * 1;
				break;
			case 2:
				destX = this.graphics.getX() + WALK_AROUND_DISTANCE * 1;
				destY = this.graphics.getY() + WALK_AROUND_DISTANCE * 0;
				break;
			case 3:
				destX = this.graphics.getX() + WALK_AROUND_DISTANCE * 1;
				destY = this.graphics.getY() + WALK_AROUND_DISTANCE * -1;
				break;
			case 4:
				destX = this.graphics.getX() + WALK_AROUND_DISTANCE * 0;
				destY = this.graphics.getY() + WALK_AROUND_DISTANCE * -1;
				break;
			case 5:
				destX = this.graphics.getX() + WALK_AROUND_DISTANCE * -1;
				destY = this.graphics.getY() + WALK_AROUND_DISTANCE * -1;
				break;
			case 6:
				destX = this.graphics.getX() + WALK_AROUND_DISTANCE * -1;
				destY = this.graphics.getY() + WALK_AROUND_DISTANCE * 0;
				break;
			case 7:
				destX = this.graphics.getX() + WALK_AROUND_DISTANCE * -1;
				destY = this.graphics.getY() + WALK_AROUND_DISTANCE * 1;
				break;
			}
		}

		// Aller à la destination trouvée
		goTo(destX, destY);
	}

	// Marche
	private void walk() {

		System.out.println("Mouglotte::Walk");

		if (this.path != null) {

			// Le step renvoie une position en zone
			// setLocation(this.path.getStep(0).getX(),this.path.getStep(0).getY());
			// On se place au milieu de la prochaine zone
			setLocation(this.path.getStep(0).getX() * GameMap.TILE_SIZE
					+ GameMap.TILE_SIZE / 2, this.path.getStep(0).getY()
					* GameMap.TILE_SIZE + GameMap.TILE_SIZE / 2);
			this.path.removeStep(0);
			if (this.path.getLength() == 0)
				this.path = null;
		}
	}

	// Accomplissement
	private void fulfill() {
		
		System.out.println("Mouglotte::Fulfill");

		// A décliner en véritable actions
		// eat(), talk(), fuck(),...
		
		if (this.decision != null)
			switch (this.decision) {
			case NEED_HUNGER:
			case NEED_REST:
			case NEED_SOCIAL:
			case NEED_FUN:
				this.needs.setFulfilling(true);
				break;
			case DESIRE_HUNGER:
			case DESIRE_REST:
			case DESIRE_SOCIAL:
			case DESIRE_FUN:
			case DESIRE_LOVE:
			case DESIRE_FIGHT:
			case DESIRE_WORK:
				this.desires.setFulfilling(true);
				break;
			default:
				break;
			}
	}

	// Affichage
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		this.graphics.render(container, g);
	}
}
