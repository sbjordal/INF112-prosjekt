package inf112.starhunt.model.gameobject;

import inf112.starhunt.model.gameobject.fixedobject.Ground;
import inf112.starhunt.model.gameobject.fixedobject.item.Banana;
import inf112.starhunt.model.gameobject.fixedobject.item.Coin;
import inf112.starhunt.model.gameobject.fixedobject.item.Star;
import inf112.starhunt.model.gameobject.mobileobject.MobileObject;
import inf112.starhunt.model.gameobject.mobileobject.actor.Player;
import inf112.starhunt.model.gameobject.mobileobject.actor.enemy.Enemy;

import java.util.List;

/**
 * An interface to be used for objects that is {@link MobileObject}
 * and that can collide with other {@link Collidable} {@link GameObject}s.
 *
 */
public interface Visitor {

    /**
     * Handles visiting a Coin object.
     * Typically used for collecting or triggering coin-related effects.
     *
     * @param coin The Coin object being visited.
     */
    void visitCoin(Coin coin);

    /**
     * Handles colliding with a Star object.
     * Is used for handling logic for getting into the next level.
     *
     * @param star The Star object being collided with.
     */

    void visitStar(Star star);

    /**
     * Handles collision with Banana.
     *
     * @param banana The {@link Banana} object being collided with.
     */
    void visitBanana(Banana banana);

    /**
     * Handles collision with Ground.
     *
     * @param ground The {@link Ground} object being collided with.
     */
    void visitGround(Ground ground);

    /**
     *
     * @param enemy
     */
    void visitEnemy(Enemy enemy);

    /**
     * Handles collision with player.
     *
     * @param player The {@link Player} object being collided with.
     */
    void visitPlayer(Player player);

    /**
     * A getter for CollisionBox. CollisionBox is the bounding box of an object, used to
     * detect collisions.
     * @return the collisionBox of the GameObject
     */
    CollisionBox getCollisionBox();

    /**
     * Checks if the collisionBox of the current object is colliding with any other Collidable object.
     * @param collidables "all other GameObjects"
     * @param collisionBox collisionbox for the one object we want to check
     * @return True if colliding, false otherwise.
     */
    boolean isColliding(List<Collidable> collidables, CollisionBox collisionBox);
}
