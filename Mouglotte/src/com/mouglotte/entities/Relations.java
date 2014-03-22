package com.mouglotte.entities;

import java.util.Hashtable;

public class Relations {

	/** Relations */
	private static Hashtable<EntityCouple, Relation> relations;

	/**
	 * Initialize relations between entities
	 */
	public static void initialize() {
		Relations.relations = new Hashtable<EntityCouple, Relation>();
	}

	/**
	 * Get relation between an entity and another
	 * 
	 * @param origin
	 *            Entity (origin of the relation)
	 * @param target
	 *            Other entity (target of the relation)
	 * @return
	 */
	public static Relation get(Entity origin, Entity target) {
		return Relations.relations.get(new EntityCouple(origin, target));
	}

	/**
	 * Create a relation (and create the opposite relation)
	 * 
	 * @param origin
	 *            Origin
	 * @param target
	 *            Target
	 */
	public static void create(Entity origin, Entity target) {
		put(new Relation(origin, target));
		put(new Relation(target, origin));
	}

	/**
	 * Add relation
	 * 
	 * @param relation
	 *            Relation to add
	 */
	private static void put(Relation relation) {
		Relations.relations.put(relation.getEntityCouple(), relation);
	}
}
