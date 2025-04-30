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
     *
     * @param coin
     */
    void visitCoin(Coin coin);

    /**
     *
     * @param star
     */
    void visitStar(Star star);

    /**
     *
     * @param banana
     */
    void visitBanana(Banana banana);

    /**
     *
     * @param ground
     */
    void visitGround(Ground ground);

    /**
     *
     * @param enemy
     */
    void visitEnemy(Enemy enemy);

    /**
     *
     * @param player
     */
    void visitPlayer(Player player);

    /**
     * A getter for CollisionBox. CollisionBox is the bounding box of an object, used to
     * detect collisions.
     * @return the collisionBox of the GameObject
     */
    CollisionBox getCollisionBox();

    /**
     *
     */
    boolean isColliding(List<Collidable> collidables, CollisionBox collisionBox);
}
