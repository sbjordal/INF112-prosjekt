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
    private int lives;

    /**
     * Creates a new Actor with the specified health, movement speed, transform and texture.
     *
     * @param lives        The initial health of the Actor.
     * @param movementSpeed The rate of which the Actor moves horizontally.
     * @param transform     The initial position and size of the Actor.
     * @param texture       The visual representation of the Actor.
     */
    protected Actor(int lives, int movementSpeed, Transform transform, Texture texture) {
        super(movementSpeed, transform, texture);

        if (lives <= 0) {
            throw new IllegalArgumentException("Health must be positive.");
        }

        this.isAlive = true;
        this.lives = lives;
    }

    /**
     * Checks if the Actor is currently alive.
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Returns the current health of the Actor.
     *
     * @return The current health as an integer.
     */
    public int getLives() {
        return lives;
    }

    /**
     * Reduces the Actor's health by the specified damage amount.
     * If the damage reduces health to zero or below, the Actor dies.
     *
     * @param damage The amount of damage to inflict. Must be non-negative.
     * @throws IllegalArgumentException if {@code damage} is negative.
     */
    public void receiveDamage(int damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Damage can not be negative.");
        }

        if (damage >= lives) {
            die();
        } else {
            lives -= damage;
        }
    }

    /**
     * Inflicts damage on another Actor.
     *
     * @param target The Actor receiving the damage.
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
     * Sets the Actor's health to zero and marks it as dead.
     * This method is private because death should only occur through receiving damage.
     *
     * @see #receiveDamage(int)
     */
    private void die() {
        lives = 0;
        isAlive = false;
    }

}
