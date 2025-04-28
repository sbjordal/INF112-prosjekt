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
     * TODO
     * @param coin
     */
    void visitCoin(Coin coin);

    /**
     * TODO
     * @param star
     */
    void visitStar(Star star);

    /**
     * TODO
     * @param banana
     */
    void visitBanana(Banana banana);

    /**
     * TODO
     * @param ground
     */
    void visitGround(Ground ground);

    /**
     * TODO
     * @param enemy
     */
    void visitEnemy(Enemy enemy);

    /**
     * TODO
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
     * TODO
     */
    boolean isColliding(List<Collidable> collidables, CollisionBox collisionBox);
}
