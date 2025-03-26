package inf112.skeleton.model;

import inf112.skeleton.model.gameobject.CollisionBox;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.fixedobject.FixedObject;
import inf112.skeleton.model.gameobject.fixedobject.item.Banana;
import inf112.skeleton.model.gameobject.fixedobject.item.Coin;
import inf112.skeleton.model.gameobject.fixedobject.item.Item;
import inf112.skeleton.model.gameobject.fixedobject.item.Star;
import inf112.skeleton.model.gameobject.mobileobject.actor.Player;
import inf112.skeleton.model.gameobject.mobileobject.actor.enemy.Enemy;

import java.util.List;

public class CollisionHandler {
    private final int ceilingHeight;
    private final int ATTACK_COOLDOWN = 800;
    private final int BOUNCE_COOLDOWN = 64;

    public CollisionHandler(int ceilingHeight) {
        this.ceilingHeight = ceilingHeight;
    }

    public GameObject checkCollision(Player player, List<GameObject> gameObjects) {
        CollisionBox playerBox = player.getCollisionBox();
        for (GameObject object : gameObjects) {
            if (object == player) continue;
            CollisionBox otherBox = object.getCollisionBox();
            boolean isTopCollision = playerBox.isCollidingFromTop(otherBox);
            boolean isCeiling = playerBox.topRight.y >= ceilingHeight - 1;
            boolean isGround = object instanceof FixedObject && !(object instanceof Item);

            if ((isTopCollision && isGround) || isCeiling) {
                if (player.getVerticalVelocity() > 0) {
                    float loss = 0.1f;
                    int bump = (int) (-player.getVerticalVelocity() * loss);
                    player.setVerticalVelocity(bump);
                }
            }
            if (playerBox.isCollidingWith(otherBox)) {
                if (object instanceof Enemy enemy) {
                    return enemy;
                } else if (object instanceof Coin coin) {
                    return coin;
                } else if (object instanceof Banana banana) {
                    return banana;
                } else if (object instanceof Star star) {
                    return star;
                }
            }
        }
        return null;
    }

    public void handleEnemyCollsion(){

    }
    public void handleCoinCollision(){

    }
    public void handleBananaCollision(){

    }
    public void handleStarCollision(){

    }

}

