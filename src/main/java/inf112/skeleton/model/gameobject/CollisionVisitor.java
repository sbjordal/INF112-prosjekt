package inf112.skeleton.model.gameobject;

import inf112.skeleton.model.gameobject.fixedobject.Ground;
import inf112.skeleton.model.gameobject.fixedobject.item.Banana;
import inf112.skeleton.model.gameobject.fixedobject.item.Coin;
import inf112.skeleton.model.gameobject.fixedobject.item.Star;
import inf112.skeleton.model.gameobject.mobileobject.actor.enemy.Enemy;

public interface CollisionVisitor {

    void collideWithCoin(Coin coin);
    void collideWithStar(Star star);
    void collideWithBanana(Banana banana);
    void collideWithGround(Ground ground);
    void collideWithEnemy(Enemy enemy);

}
