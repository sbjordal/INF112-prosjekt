package inf112.skeleton.model.gameobject.mobileobject;

/**
 * Represents all living MobileObject types.
 * A living object is any {@link MobileObject} that has health.
 *
 * @author Eivind H. Naasen
 */
abstract class Actor extends MobileObject {
    private boolean isAlive;
    private int health;
    private final int movementSpeed;

    /**
     * Creates a new Actor with the specified health and movement speed.
     *
     * @param health        The initial health of the actor.
     * @param movementSpeed The movement speed of the actor.
     */
    protected Actor(int health, int movementSpeed) {
        if (health <= 0) {
            throw new IllegalArgumentException("Health must be positive.");
        }

        this.isAlive = true;
        this.health = health;
        this.movementSpeed = movementSpeed;
    }

    protected boolean isAlive() { return isAlive; }
    protected int getHealth() { return health; }
    protected int getMovementSpeed() { return movementSpeed; }

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
