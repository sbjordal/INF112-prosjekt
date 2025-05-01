package inf112.starhunt.model.gameobject.mobileobject.actor;

import inf112.starhunt.model.gameobject.Transform;
import inf112.starhunt.model.gameobject.mobileobject.MobileObject;

/**
 * Represents all living mobile objects.
 * A living mobile object is any {@link MobileObject} that has lives.
 */
public abstract class Actor extends MobileObject {
    protected int damage;
    private boolean isAlive;
    protected int lives;

    /**
     * Creates a new Actor with the specified lives, movement speed, transform and texture.
     *
     * @param lives         The initial amount of lives of the Actor.
     * @param movementSpeed The rate of which the Actor moves horizontally.
     * @param transform     The initial position and size of the Actor.
     */
    protected Actor(int lives, int movementSpeed, Transform transform) {
        super(movementSpeed, transform);

        if (lives <= 0) {
            throw new IllegalArgumentException("Lives must be positive.");
        }

        this.isAlive = true;
        this.lives = lives;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    /**
     * Returns the current lives of the Actor.
     *
     * @return The current lives as an integer.
     */
    public int getLives() {
        return lives;
    }

    /**
     * Reduces the actor's lives by the specified damage amount.
     * If the damage reduces lives to zero or below, the actor dies.
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
    public void dealDamage(Actor target, int damage) {
        if (target == null) {
            throw new IllegalArgumentException("Target can not be null.");
        }
        target.receiveDamage(damage);
    }

    /**
     * Returns the amount of damage the actor can inflict.
     *
     * @return The damage value as an integer.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Sets the Actor's lives to zero and marks it as dead.
     * This method is private because death should only occur through receiving damage.
     *
     * @see #receiveDamage(int)
     */
    private void die() {
        lives = 0;
        isAlive = false;
    }

    protected void setLives(int lives){
        if (lives < 0){ throw new IllegalArgumentException("Lives can not be negative");}
        this.lives = lives;
    }
}
