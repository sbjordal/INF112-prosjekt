package inf112.skeleton.model.gameobject;

import inf112.skeleton.model.gameobject.fixedobject.Ground;
import inf112.skeleton.model.gameobject.fixedobject.item.Banana;
import inf112.skeleton.model.gameobject.fixedobject.item.Coin;
import inf112.skeleton.model.gameobject.fixedobject.item.Star;
import inf112.skeleton.model.gameobject.mobileobject.actor.Player;
import inf112.skeleton.model.gameobject.mobileobject.actor.enemy.Enemy;

public interface Visitor {

    /**
     * TODO
     * @param coin
     */
    void visit(Coin coin);

    /**
     * TODO
     * @param star
     */
    void visit(Star star);

    /**
     * TODO
     * @param banana
     */
    void visit(Banana banana);

    /**
     * TODO
     * @param ground
     */
    void visit(Ground ground);

    /**
     * TODO
     * @param enemy
     */
    void visit(Enemy enemy);

    /**
     * TODO
     * @param player
     */
    void visit(Player player);

}
