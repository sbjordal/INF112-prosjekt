package inf112.skeleton.model.gameobject.mobileobject.actor;

import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.gameobject.mobileobject.MobileObject;

/**
 * Represents all living mobile objects.
 * A living mobile object is any {@link MobileObject} that has health.
 */
abstract class Actor extends MobileObject {
    private boolean isAlive;
    private int health;

    /**
     * Creates a new Actor with the specified health, movement speed, transform and texture.
     *
     * @param health        The initial health of this actor.
     * @param movementSpeed The movement speed of this actor.
     * @param transform     The transform of this actor.
     * @param texture       The texture of this actor.
     */
    protected Actor(int health, int movementSpeed, Transform transform, Texture texture) {
        super(movementSpeed, transform, texture);

        if (health <= 0) {
            throw new IllegalArgumentException("Health must be positive.");
        }

        this.isAlive = true;
        this.health = health;
    }

    /**
     * Checks if this actor is currently alive.
     */
    protected boolean isAlive() {
        return isAlive;
    }

    /**
     * Returns the current health of this actor.
     *
     * @return The current health as an integer.
     */
    protected int getHealth() {
        return health;
    }

    /**
     * Reduces the actor's health by the specified damage amount.
     * If the damage reduces health to zero or below, the actor dies.
     *
     * @param damage The amount of damage to apply. Must be non-negative.
     * @throws IllegalArgumentException if {@code damage} is negative.
     */
    protected void receiveDamage(int damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Damage can not be negative.");
        }

        if (damage >= health) {
            die();
        } else {
            health -= damage;
        }
    }

    /**
     * Inflicts damage on another actor.
     *
     * @param target The actor receiving the damage.
     * @param damage The amount of damage to inflict.
     * @throws NullPointerException if {@code target} is null.
     */
    protected void dealDamage(Actor target, int damage) {
        if (target == null) {
            throw new IllegalArgumentException("Target can not be null.");
        }
        target.receiveDamage(damage);
    }

    /**
     * Sets the actor's health to zero and marks it as dead.
     * This method is private because death should only occur through receiving damage.
     *
     * @see #receiveDamage(int)
     */
    private void die() {
        health = 0;
        isAlive = false;
    }
}
