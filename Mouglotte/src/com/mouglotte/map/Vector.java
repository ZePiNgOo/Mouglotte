package com.mouglotte.map;

import java.awt.geom.Point2D;

/** A class that implements basic vector (as in mathematical vector) addition. */
public class Vector {

	/** The direction of this vector, measured in radians. */
	private float direction;
	/** The magnitude of this vector. */
	private float magnitude;
	/** The x component of this vector. */
	private float xComponent;
	/** The y component of this vector. */
	private float yComponent;

	/**
	 * Constructs a new DirectionalVector.
	 * 
	 * @param direction
	 *            the direction, measured in radians.
	 * @param magnitude
	 *            the magnitude
	 */
	public Vector(float direction, float magnitude) {
		this.direction = getNormalizedDirection(direction);
		this.magnitude = magnitude;
		this.xComponent = ((float) (Math.cos(direction) * magnitude));
		this.yComponent = ((float) (Math.sin(direction) * magnitude));
	}

	/**
	 * Gets this Vector's direction.
	 * 
	 * @return the direction of this vector, as measured in radians.
	 */
	public float getDirection() {
		return this.direction;
	}

	/**
	 * Sets the direction of this Vector to the one specified.
	 * 
	 * @param direction
	 *            the new direction for this Vector, measured in radians.
	 */
	public void setDirection(float direction) {
		this.direction = getNormalizedDirection(direction);
		this.xComponent = ((float) (Math.cos(direction) * this.magnitude));
		this.yComponent = ((float) (Math.sin(direction) * this.magnitude));
	}

	/**
	 * Gets this Vector's magnitude.
	 * 
	 * @return the magnitude of this Vector.
	 */
	public float getMagnitude() {
		return this.magnitude;
	}

	/** Sets this Vector's magnitude. */
	public void setMagnitude(float magnitude) {
		this.magnitude = magnitude;
		this.xComponent = ((float) (Math.cos(this.direction) * magnitude));
		this.yComponent = ((float) (Math.sin(this.direction) * magnitude));
	}

	public void setXComponent(float newXComponent) {
		Vector newVector = getVectorFromComponents(newXComponent,
				this.yComponent);

		this.xComponent = newVector.xComponent;
		this.yComponent = newVector.yComponent;
		this.magnitude = newVector.magnitude;
		this.direction = newVector.direction;
	}

	public void setYComponent(float newYComponent) {
		Vector newVector = getVectorFromComponents(this.xComponent,
				newYComponent);

		this.xComponent = newVector.xComponent;
		this.yComponent = newVector.yComponent;
		this.magnitude = newVector.magnitude;
		this.direction = newVector.direction;
	}

	/**
	 * Gets this Vector's x component.
	 * 
	 * @return the x component of this Vector.
	 */
	public float getXComponent() {
		return this.xComponent;
	}

	/**
	 * Gets this Vector's y component.
	 * 
	 * @return the y component of this Vector.
	 */
	public float getYComponent() {
		return this.yComponent;
	}

	/**
	 * Returns the x and y components from the specified direction.
	 * 
	 * @param direction
	 *            the direction, measured in radians.
	 * 
	 * @return a new Point, representing the (x, y) components.
	 */
	public static Point2D.Float getDeltasFromDirection(float direction) {
		return (new Point2D.Float((float) Math.cos(direction),
				(float) Math.sin(direction)));
	}

	public static float getDirectionFromDeltas(float xDelta, float yDelta) {
		float newDirection = (float) Math.atan(yDelta / xDelta);

		if (xDelta == 0 && yDelta == 0) {
			newDirection = 0;
		}

		if (xDelta < 0) // Second and third quadrants
			newDirection += Math.PI;
		else if (xDelta > 0 && yDelta < 0) // Fourth quadrant
			newDirection += Math.PI * 2.0;

		newDirection = getNormalizedDirection(newDirection);

		return newDirection;
	}

	/**
	 * Takes this Vector's direction, and changes it to an equivalent value
	 * between 0 and 2(PI)
	 */
	public static float getNormalizedDirection(float direction) {
		while ((direction < 0) || (direction > 2 * (Math.PI))) {
			if (direction < 0)
				direction += Math.PI * 2;
			if (direction > Math.PI * 2)
				direction -= Math.PI * 2;
		}

		return direction;
	}

	/**
	 * Creates a new vector from the specified x and y components.
	 * 
	 * @param xComponent
	 *            the x component of the new Vector
	 * @param yComponent
	 *            the y component of the new Vector
	 * @return a new Vector, built from the specified x and y components
	 */
	public static Vector getVectorFromComponents(float xComponent,
			float yComponent) {
		float newMagnitude = (float) Math.hypot(xComponent, yComponent);
		float newDirection = Vector.getDirectionFromDeltas(xComponent,
				yComponent);

		return (new Vector(newDirection, newMagnitude));
	}

	public void add(Vector additionalVector) {
		if (additionalVector != null) {
			setXComponent(this.xComponent + additionalVector.xComponent);
			setYComponent(this.yComponent + additionalVector.yComponent);
		}
	}

	/**
	 * Adds this vector to the specified vector, and returns the resulting
	 * vector.
	 * 
	 * @param additionalVector
	 *            the vector to add.
	 * 
	 * @return the resulting vector
	 */
	public static Vector addTwoVectors(Vector v1, Vector v2) {
		Vector newVector = new Vector(0, 0);
		newVector.add(v1);
		newVector.add(v2);

		return newVector;
	}

	/**
	 * Scales this vector by increasing the magnitude by the specified amount.
	 * The result is that the x and y components are also scaled.
	 * 
	 * @param percetageChange
	 *            he percentage by which to scale the magnitude, and resulting x
	 *            and y components
	 */
	public void scaleMagnitudeByPercentage(float percetageChange) {
		this.magnitude *= percetageChange;
		this.xComponent *= percetageChange;
		this.yComponent *= percetageChange;
	}
}
