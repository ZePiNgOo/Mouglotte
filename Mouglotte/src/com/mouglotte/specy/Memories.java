package com.mouglotte.specy;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.mouglotte.entities.Entity;
import com.mouglotte.map.Tile;
import com.mouglotte.utilities.MouglotteUtilities;

public class Memories {

	/** Memories list */
	private LinkedHashMap<MemoryType, Memory> memories;
	/** Current memory */
	private Memory current;

	/**
	 * Constructor
	 * 
	 * @param maxMemories
	 */
	public Memories(final int maxMemories) {

		// Initialize list with a maximum number of memories
		this.memories = new LinkedHashMap<MemoryType, Memory>(maxMemories) {

			private static final long serialVersionUID = 1L;

			protected boolean removeEldestEntry(
					Map.Entry<MemoryType, Memory> eldest) {
				return size() > maxMemories;
			}
		};
	}

	/**
	 * Set current memory
	 * 
	 * @param current
	 *            Current memory
	 */
	public void setCurrent(Memory current) {
		this.current = current;
	}

	/**
	 * Get a memory of a type with an entity
	 * 
	 * @param type
	 *            Memory type
	 * @param entity
	 *            Entity
	 * @return Memory
	 */
	public Memory get(MemoryType type, Entity entity) {

		// Read through memories
		for (final Entry<MemoryType, Memory> memory : this.memories.entrySet()) {

			// Wanted memory type and entity
			if (memory.getValue().getType() == type
					&& memory.getValue().getEntity() == entity) {
				return memory.getValue();
			}
		}
		return null;
	}

	/**
	 * Get current memory
	 * 
	 * @return Current memory
	 */
	public Memory getCurrent() {
		return this.current;
	}

	/**
	 * Get the closest memory according to a decision type
	 * 
	 * @param type
	 *            Decision type
	 * @param tile
	 *            The closest to this tile
	 * @param excludeCurrent
	 *            True to exclude the current memory
	 * @return Closest memory
	 */
	public Memory getClosest(DecisionType decision, Tile tile,
			boolean excludeCurrent) {

		return getClosest(MemoryType.getMemoryType(decision), tile,
				excludeCurrent);
	}

	/**
	 * Get the closest memory according to a memory type
	 * 
	 * @param type
	 *            Memory type
	 * @param tile
	 *            The closest to this tile
	 * @param excludeCurrent
	 *            True to exclude the current memory
	 * @return Closest memory
	 */
	public Memory getClosest(MemoryType type, Tile tile, boolean excludeCurrent) {

		Memory retMemory = null;
		double points;
		double maxPoints = 0;

		// Read through memories
		for (final Entry<MemoryType, Memory> memory : this.memories.entrySet()) {

			// If the current memory is not included in the search
			if (memory.getValue() == this.current && excludeCurrent)
				continue;

			// Wanted memory type
			if (memory.getValue().getType() == type) {

				// Calculate points
				// Points of the memory - distance
				points = memory.getValue().getPoints()
						- MouglotteUtilities.distance(tile, memory.getValue()
								.getTile());

				// Minimum distance
				if (points >= maxPoints || maxPoints == 0) {
					maxPoints = points;
					retMemory = memory.getValue();
				}
			}
		}

		return retMemory;
	}

	/**
	 * Is there a current memory ?
	 * 
	 * @return True if there is a current memory
	 */
	public boolean hasCurrent() {
		return this.current != null;
	}

	/**
	 * Add a memory
	 * 
	 * @param memory
	 *            Memory to add
	 */
	public void put(Memory memory) {

		// Add only if the memory doesn't already exist
		if (!exists(memory))
			this.memories.put(memory.getType(), memory);
	}

	/**
	 * Remove a memory
	 * 
	 * @param memory
	 *            Memory to remove
	 */
	public void remove(Memory memory) {
		this.memories.remove(memory);
	}

	/**
	 * Refresh memory
	 */
	public void refresh() {
		this.current = null;
	}

	/**
	 * Check if the memory already exists
	 * 
	 * @param memory
	 *            Memory to check
	 * @return True if the memory already exists
	 */
	private boolean exists(Memory memory) {

		// Read through memories
		for (final Entry<MemoryType, Memory> m : this.memories.entrySet()) {

			// If the entity of the memory already exists for the same type
			if (m.getValue().getType() == memory.getType()
					&& m.getValue().getEntity() == memory.getEntity())
				return true;
		}

		return false;
	}

	/**
	 * Reward points to current memory
	 * 
	 * @param points
	 *            Points to reward
	 */
	public void rewardCurrent(int points) {
		if (this.current != null)
			this.current.reward(points);
	}

	/**
	 * Penalize points to current memory
	 * 
	 * @param points
	 *            Points to penalize
	 */
	public void penalizeCurrent(int points) {

		if (this.current != null) {

			this.current.penalize(points);

			// If there is no more points then delete current
			if (!this.current.hasPoints()) {
				remove(this.current);
				this.current = null;
			}
		}
	}
}
