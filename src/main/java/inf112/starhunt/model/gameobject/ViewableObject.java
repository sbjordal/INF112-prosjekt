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
     * A getter for CollisionBox. CollisionBox is the bounding box of an object, used to
     * detect collisions.
     * @return the collisionBox of the GameObject
     */
    CollisionBox getCollisionBox();

    /**
     *
     * @param callback
     */
    void setOnCoinCollected(Runnable callback);

    /**
     *
     * @param callback
     */
    void setOnCollisionWithEnemy(Runnable callback);

    /**
     *
     * @param callback
     */
    void setOnCollisionWithEnemyDealDamage(Runnable callback);

    /**
     *
     * @param callback
     */
    void setOnBananaCollected(Runnable callback);

    /**
     *
     * @param callback
     */
    void setOnCollisionWithStar(Runnable callback);

    /**
     *
     */
    int getDirection();
}
