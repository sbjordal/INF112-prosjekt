package inf112.skeleton.model;

import com.badlogic.gdx.Gdx;
import inf112.skeleton.model.gameobject.Collidable;
import inf112.skeleton.model.gameobject.CollisionBox;
import inf112.skeleton.model.gameobject.Visitor;
import inf112.skeleton.model.gameobject.fixedobject.Ground;
import inf112.skeleton.model.gameobject.fixedobject.item.Coin;
import inf112.skeleton.model.gameobject.mobileobject.actor.Player;
import inf112.skeleton.model.gameobject.mobileobject.actor.enemy.Enemy;

import java.util.List;

import static inf112.skeleton.model.LevelManager.Level.*;

public class CollisionHandler {
    private SoundHandler soundHandler;
    private final int ceilingHeight;
    private final int ATTACK_COOLDOWN = 800;
    private final int BOUNCE_COOLDOWN = 64;

    public CollisionHandler(int ceilingHeight) {
        this.ceilingHeight = ceilingHeight;
    }

    void init() {
        this.soundHandler = new SoundHandler();
    }

    Triple<Boolean, Collidable> checkCollision(List<Collidable> collidables, List<Visitor> visitors) {
         for (Visitor visitor : visitors) {
             for (Collidable collided : collidables) {
                 if (visitor.getCollisionBox().isCollidingWith(collided.getCollisionBox())) {
                     collided.accept(visitor);
                 }
            }
         }


        /////////////////////////////////////////////////
        for (Collidable object : gameObjects) {
            if (object == player) continue;
            CollisionBox otherBox = object.getCollisionBox();
            boolean isTopCollision = collisionBox.isCollidingFromTop(otherBox);
            boolean isCeiling = collisionBox.topRight.y >= ceilingHeight - 1;
            boolean isGround = object instanceof Ground;

            if ((isTopCollision && isGround) || isCeiling) {
                if (player.getVerticalVelocity() > 0) {
                    float bumpForceLoss = 0.1f;
                    int bumpSpeed = (int) (-player.getVerticalVelocity() * bumpForceLoss);
                    player.setVerticalVelocity(bumpSpeed);
                }
                return new Triple<>(true, null);
            }
            if (collisionBox.isCollidingWith(otherBox)) {
                return new Triple<>(true, object);
            }
        }
        return new Triple<>(false, null);
    }
    Integer handleEnemyCollision(Player player, Enemy enemy, Integer totalScore, CollisionBox newPlayerCollisionBox){
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
                final int scorePenalty = 4;
                totalScore = Math.max(0, totalScore - scorePenalty);

                player.setLastAttackTime(currentTime);
            }
        }
        return totalScore;
    }
    int handleCoinCollision(Coin coin, Integer totalScore){
        int objectScore = coin.getObjectScore();
        int newScore = totalScore + objectScore;
        if (soundHandler == null) {
            Gdx.app.error("CollisionHandler", "SoundHandler is null");
            return newScore;
        }
        soundHandler.playCoinSound();
        return newScore;
    }

    LevelManager.Level handleStarCollision(LevelManager.Level currentLevel) {
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

    int getCeilingHeight(){
        return ceilingHeight;
    }

    int getATTACK_COOLDOWN(){
        return ATTACK_COOLDOWN;
    }

    int getBOUNCE_COOLDOWN(){
        return BOUNCE_COOLDOWN;
    }
    protected SoundHandler getSoundHandler() {
        return soundHandler;
    }
    protected void setSoundHandler(SoundHandler soundHandler) {
        this.soundHandler = soundHandler;
    }
}

