package inf112.skeleton.model;

import inf112.skeleton.model.gameobject.CollisionBox;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.fixedobject.FixedObject;
import inf112.skeleton.model.gameobject.fixedobject.item.Coin;
import inf112.skeleton.model.gameobject.fixedobject.item.Item;
import inf112.skeleton.model.gameobject.mobileobject.actor.Player;
import inf112.skeleton.model.gameobject.mobileobject.actor.enemy.Enemy;

import java.util.List;

import static inf112.skeleton.model.LevelManager.Level.*;

public class CollisionHandler {
    private final int ceilingHeight;
    private final int ATTACK_COOLDOWN = 800;
    private final int BOUNCE_COOLDOWN = 64;

    public CollisionHandler(int ceilingHeight) {
        this.ceilingHeight = ceilingHeight;
    }

    public Pair<Boolean, GameObject> checkCollision(Player player, List<GameObject> gameObjects, CollisionBox collisionBox) {
        CollisionBox playerBox = player.getCollisionBox();
        for (GameObject object : gameObjects) {
            if (object == player) continue;
            CollisionBox otherBox = object.getCollisionBox();
            boolean isTopCollision = playerBox.isCollidingFromTop(otherBox);
            boolean isCeiling = playerBox.topRight.y >= ceilingHeight - 1;
            boolean isGround = object instanceof FixedObject && !(object instanceof Item);

            if ((isTopCollision && isGround) || isCeiling) {
                if (player.getVerticalVelocity() > 0) {
                    float bumpForceLoss = 0.1f;
                    int bumpSpeed = (int) (-player.getVerticalVelocity() * bumpForceLoss);
                    player.setVerticalVelocity(bumpSpeed);
                }
                return new Pair<>(true, null);
            }
            if (collisionBox.isCollidingWith(otherBox)) {
                return new Pair<>(true, object);
            }
        }
        return new Pair<>(false, null);
    }
    public Integer handleEnemyCollision(Player player, Enemy enemy, Integer totalScore, CollisionBox newPlayerCollisionBox){
        long currentTime = System.currentTimeMillis();

        if (newPlayerCollisionBox.isCollidingFromBottom(enemy.getCollisionBox())){
            if (currentTime - player.getLastBounceTime() >= BOUNCE_COOLDOWN) {

                player.bounce();
                player.dealDamage(enemy, player.getDamage());

                if (!enemy.isAlive()) {
                    totalScore += enemy.getObjectScore();
                }

                player.setLastBounceTime(currentTime);
            }
        } else {
            if (currentTime - player.getLastAttackTime() >= ATTACK_COOLDOWN) {

                // TODO...
                // Enemy dealing damage to the player is moved into Enemy.moveEnemy()
                // - This is to make sure that the enemy doesn't deal damage twice.
                // - The logic needs to be inside Enemy class. If not, the enemy won't deal damage
                //   when it collides with the player.
                // - As of right now, ATTACK_COOLDOWN only affects totalScore. It does NOT affect the frequency of attacks.

                // Reduce total score
                final int scorePenalty = 4;
                totalScore = Math.max(0, totalScore - scorePenalty);

                player.setLastAttackTime(currentTime);
            }
        }
        return totalScore;
    }
    public int handleCoinCollision(Coin coin, SoundHandler handler, Integer totalScore){
        int objectScore = coin.getObjectScore();
        handler.playCoinSound();
        int newScore = totalScore + objectScore;
        return newScore;
    }
    public void handleBananaCollision(Player player){
        player.initiatePowerUp();
    }
    public LevelManager.Level handleStarCollision(LevelManager.Level currentLevel) {
        switch (currentLevel) {
            case LEVEL_1:
                return (LEVEL_2);
            case LEVEL_2:
                return (LEVEL_3);
            case LEVEL_3:
                return (LEVEL_1);
            default: throw new IllegalArgumentException("No such level exists");
        }
    }
}

