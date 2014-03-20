package com.mouglotte.map;

import java.util.Random;

import com.mouglotte.entities.BeanTree;
import com.mouglotte.entities.Mushroom;
import com.mouglotte.time.TimeDependent;
import com.mouglotte.utilities.Debug;

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
	public void eventRealSecond() {
		// TODO Auto-generated method stub

	}

	@Override
	public void eventMinute() {
		// TODO Auto-generated method stub

	}

	@Override
	public void eventHour() {
		// TODO Auto-generated method stub

	}

	@Override
	public void eventDay() {
		// TODO Auto-generated method stub

	}

	@Override
	public void eventMonth() {

		// Spawn food entities
		spawnFoodEntities();
	}

	@Override
	public void eventSeason() {
		// TODO Auto-generated method stub

	}

	@Override
	public void eventYear() {
		// TODO Auto-generated method stub

	}

	/**
	 * Spawn food entities
	 */
	private void spawnFoodEntities() {

		Debug.log("SPAWNER", "Spawner::SpawnFood");

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
					new BeanTree(this.map.getGame(), this.map.getTile(i, j));
				} else if (foodProba < BEANTREE_PROBA + MUSHROOM_PROBA) {
					new Mushroom(this.map.getGame(), this.map.getTile(i, j));
				}
				spawned++;
				Debug.log("SPAWNER", "Spawner::SpawnFood:Food spawn on " + i
						+ "," + j);
			}
		}

		Debug.log("SPAWNER", "Spawner::SpawnFood::End");
	}
}
