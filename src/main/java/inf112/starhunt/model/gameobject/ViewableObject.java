package inf112.starhunt.model.gameobject;

/**
 * An interface to be implemented by all objects in the game that should be visible.
 */
public interface ViewableObject {

    /**
     * Returns the objects position and size
     *
     * @return Transform of Position and Size
     */
    Transform getTransform();

    /**
     * A getter for {@link CollisionBox}. CollisionBox is the bounding box of an object, used to
     * detect collisions.
     * @return the collisionBox of the GameObject
     */
    CollisionBox getCollisionBox();

    /**
     * Sets the action to run when the player collects a coin.
     * Used for playing a specific sound effect.
     *
     * @param onCoinCollected a {@link Runnable} that will be executed when a coin is collected.
     *
     */
    void setOnCoinCollected(Runnable onCoinCollected);

    /**
     * Sets the action to run when the player collides with an enemy and takes damage.
     * Used for playing a specific sound effect.
     *
     * @param onCollisionWithEnemy a {@link Runnable} that will be executed on enemy collision.
     *
     */
    void setOnCollisionWithEnemy(Runnable onCollisionWithEnemy);

    /**
     * Sets the action to run when the player collides with an enemy and deals damage. Plays a specific sound effect.
     *
     * @param onCollisionWithEnemyDealDamage a {@link Runnable} that is triggered when the player damages an enemy.
     */
    void setOnCollisionWithEnemyDealDamage(Runnable onCollisionWithEnemyDealDamage);

    /**
     * Sets the action to run when the player collects a banana.
     * Used for playing a specific sound effect.
     *
     * @param onBananaCollected a {@link Runnable} that will be executed when a banana is collected.
     *                          e.g., getting a powerUp.
     *
     */
    void setOnBananaCollected(Runnable onBananaCollected);

    /**
     * Sets the action to run when the player collides with a star.
     * Used for playing a specific sound effect.
     *
     * @param onCollisionWithStar a {@link Runnable} to execute on star collision,
     *                            e.g., triggering level completion.
     */
    void setOnCollisionWithStar(Runnable onCollisionWithStar);

    /**
     * TODO, javadoc, handler det om texture retning?
     */
    int getDirection();

}
