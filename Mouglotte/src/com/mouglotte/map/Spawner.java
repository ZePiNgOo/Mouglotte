package com.mouglotte.map;

import java.util.Random;

import com.mouglotte.entities.BeanTree;
import com.mouglotte.entities.Mushroom;
import com.mouglotte.utilities.TimeDependent;

public class Spawner extends TimeDependent {

	/** Number of food entities to spawn */
	private final static int FOOD_ENTITIES_NUMBER = 10;
	/** Probabilities */
	private final static int BEANTREE_PROBA = 50;
	private final static int MUSHROOM_PROBA = 50;

	/** Map */
	private GameMap map;

	/** Random */
	private Random r = new Random();

	/**
	 * Constructor
	 * 
	 * @param map
	 *            Map
	 */
	public Spawner(GameMap map) {

		this.map = map;
	}

	@Override
	protected void eventRealSecond() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void eventMinute() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void eventHour() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void eventDay() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void eventMonth() {

		// Spawn food entities
		spawnFoodEntities();
	}

	@Override
	protected void eventSeason() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void eventYear() {
		// TODO Auto-generated method stub

	}

	/**
	 * Spawn food entities
	 */
	private void spawnFoodEntities() {

		int spawned = 0;
		int i, j;
		int foodProba;

		while (spawned < FOOD_ENTITIES_NUMBER) {

			i = this.r.nextInt(this.map.getWidthInTiles());
			j = this.r.nextInt(this.map.getHeightInTiles());

			// Don't spawn on a blocked tile
			if (!this.map.getTile(i, j).isBlocked()) {

				foodProba = this.r.nextInt(BEANTREE_PROBA + MUSHROOM_PROBA);

				if (foodProba < BEANTREE_PROBA) {
					this.map.getTile(i, j).addEntity(
							new BeanTree(this.map.getGame(), i, j));
				} else if (foodProba < BEANTREE_PROBA + MUSHROOM_PROBA) {
					this.map.getTile(i, j).addEntity(
							new Mushroom(this.map.getGame(), i, j));
				}
				spawned++;
			}
		}
	}
}
