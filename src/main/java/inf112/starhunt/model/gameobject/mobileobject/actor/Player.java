package inf112.starhunt.model.gameobject.mobileobject.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import inf112.starhunt.model.PositionValidator;
import inf112.starhunt.model.gameobject.*;
import inf112.starhunt.model.gameobject.fixedobject.Ground;
import inf112.starhunt.model.gameobject.fixedobject.item.Banana;
import inf112.starhunt.model.gameobject.fixedobject.item.Coin;
import inf112.starhunt.model.gameobject.fixedobject.item.Star;
import inf112.starhunt.model.gameobject.mobileobject.actor.enemy.Enemy;


import java.util.ArrayList;
import java.util.List;

/**
 * Represents the user-controlled actor in the game.
 * The user-controlled actor is unique and is defined as the only {@link GameObject}
 * that responds to user-input.
 */
final public class Player extends Actor implements Visitor, Collidable {
    private final static int NORMAL_BOUNCE_FORCE = 35000;
    private final static int SMALL_BOUNCE_FORCE = 27000;
    private final static int NORMAL_JUMP_FORCE = 63000;
    private final static int ATTACK_COOLDOWN = 800;
    private final static int BOUNCE_COOLDOWN = 64;
    private final static Vector2 STANDARD_PLAYER_SIZE = new Vector2(40, 80);
    private int jumpForce;
    private boolean isJustRespawned;
    private boolean hasPowerUp;
    private boolean goToNextLevel;
    private long lastAttackTime;
    private long lastBounceTime;
    private Integer coinCounter;
    private Integer totalScore;
    private List<Collidable> objectsToRemove;
    private Runnable coinCollected;
    private Runnable takingDamage;
    private int initialLives;


    /**
     * Creates a new Player with the specified lives, movement speed and transform.
     *
     * @param lives         The initial amount of lives of the Player.
     * @param movementSpeed The rate of which the Player moves horizontally.
     * @param transform     The initial transform of the Player.
     */
    public Player(int lives, int movementSpeed, Transform transform) {
        super(lives, movementSpeed, transform);
        this.initialLives = lives;
        this.hasPowerUp = false;
        this.damage = 1;
        this.lastAttackTime = 0;
        this.lastBounceTime = 0;
        this.isJustRespawned = false;
        this.jumpForce = NORMAL_JUMP_FORCE;
        this.coinCounter = 0;
        this.totalScore = 0;
        this.objectsToRemove = new ArrayList<>();
    }

    /**
     * Constructor for LevelManager
     */
    public Player() {
        super(1,0,TransformUtils.createTransformForObjects(0, 0, 0,0));
    }

    @Override
    public void setOnCoinCollected(Runnable callback) {
        coinCollected = callback;
    }

    @Override
    public void setOnCollisionWithEnemy(Runnable callback) {
        takingDamage = callback;
    }

    public void jump(boolean isGrounded) {
        if (isGrounded) {
            jump(jumpForce);
        }
    }

    public void jump(int force){
        int velocity = (int)(force * Gdx.graphics.getDeltaTime());
        setVerticalVelocity(velocity);
    }

    public void bounce(){
        int bounceForce = hasPowerUp ? SMALL_BOUNCE_FORCE : NORMAL_BOUNCE_FORCE;
        jump(bounceForce);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitPlayer(this);
    }

    @Override
    public void visitCoin(Coin coin) {
        if (!coin.isCollected()) {
            coin.collect();
            coinCounter++;
            totalScore += coin.getObjectScore();
            objectsToRemove.add(coin);
            if (coinCollected != null) {
                coinCollected.run();
            }
        }
    }

    @Override
    public void visitStar(Star star) {
        goToNextLevel = true;
        objectsToRemove.add(star);
    }

    @Override
    public void visitBanana(Banana banana) {
        final int middleOfPlayer = (int) (getTransform().getSize().x / 2);
        hasPowerUp = true;
        setSize(banana.getLargePlayerSize());
        move(-middleOfPlayer, 0);
        jumpForce = banana.getBigJumpForce();
        objectsToRemove.add(banana);
    }

    @Override
    public void visitGround(Ground ground) {
        boolean isBumpingHead = getCollisionBox().isCollidingFromTop(ground.getCollisionBox());

        if (isBumpingHead && getVerticalVelocity() > 0) {
            float bumpForceLoss = 0.1f;
            int bumpSpeed = (int) (-getVerticalVelocity() * bumpForceLoss);
            setVerticalVelocity(bumpSpeed);
        }
    }

    @Override
    public void visitEnemy(Enemy enemy) {
        final long currentTime = System.currentTimeMillis();
        final boolean isReadyToBounce = currentTime - getLastBounceTime() >= BOUNCE_COOLDOWN;
        final boolean isOnTopOfEnemy = getCollisionBox().isCollidingFromBottom(enemy.getCollisionBox());
        final boolean isCollidingFromLeft = getCollisionBox().isCollidingFromLeft(enemy.getCollisionBox());
        final boolean isCollidingFromRight = getCollisionBox().isCollidingFromRight(enemy.getCollisionBox());
        final boolean isWalkingIntoEnemy = enemy.getMovementDirection() == this.getMovementDirection() && (isCollidingFromLeft || isCollidingFromRight);

        if(isWalkingIntoEnemy) {
            takeDamage(enemy.getDamage());
        }

        if (isOnTopOfEnemy && isReadyToBounce) {
            bounce();
            dealDamage(enemy, getDamage());

            if (!enemy.isAlive()) {
                totalScore += enemy.getObjectScore();
                objectsToRemove.add(enemy);
            }

            setLastBounceTime(currentTime);
        }
    }

    @Override
    public void visitPlayer(Player player) {}

    @Override
    public boolean isColliding(List<Collidable> collidables, CollisionBox collisionBox) {
        for (Collidable collided : collidables) {
            if (collisionBox.isCollidingWith(collided.getCollisionBox())) {

                if (collided instanceof Player) {
                    continue;
                }

                collided.accept(this);
                return true;
            }
        }

        return false;
    }

    public List<Collidable> getObjectsToRemove() {
        return objectsToRemove;
    }

    @Override
    public void resolveMovement(float deltaX, float deltaY, PositionValidator validator) {
        Vector2 newActorPosition = filterPosition(deltaX, deltaY, validator, this);
        if (!getRespawned()) {
            move(newActorPosition);
        }

        setRespawned(false);
        final int belowLevel = -200;
        if (newActorPosition.y <= belowLevel) {
            receiveDamage(getLives());
        }
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void updateTotalScore(int deltaScore) {
        totalScore += deltaScore;
    }

    public  void updateCoinCounter(int deltaCoin) {
        coinCounter += deltaCoin;
    }

    public int getCoinCounter() {
        return coinCounter;
    }

    public boolean getGoToNextLevel() {
        boolean currentValue = goToNextLevel;
        goToNextLevel = false;
        return currentValue;
    }

    public void resetScores() {
        totalScore = 0;
        coinCounter = 0;
    }

    public void setHasPowerUp(boolean hasPowerUp) {
        this.hasPowerUp = hasPowerUp;
    }

    public boolean getHasPowerUp() {
        return hasPowerUp;
    }

    public void setRespawned(boolean bool){
        isJustRespawned = bool;
    }

    public boolean getRespawned(){
        return isJustRespawned;
    }

    public long getLastAttackTime() {
        return lastAttackTime;
    }

    public void setLastAttackTime(long lastAttackTime) {
        this.lastAttackTime = lastAttackTime;
    }

    public long getLastBounceTime() {
        return lastBounceTime;
    }

    public void setLastBounceTime(long lastBounceTime) {
        this.lastBounceTime = lastBounceTime;
    }

    public int getJumpForce() {
        return jumpForce;
    }

    public void takeDamage(int damage){
        final long currentTime = System.currentTimeMillis();
        final boolean playerReadyToTakeDamage = currentTime - getLastAttackTime() >= ATTACK_COOLDOWN;
        final int scorePenalty = 4;

        if (playerReadyToTakeDamage) {
            reduceTotalScore(scorePenalty);

            if (hasPowerUp) {
                losePowerUp();
            } else {
                receiveDamage(damage);
            }

            if (takingDamage != null) {
                takingDamage.run();
            }

            setLastAttackTime(currentTime);
        }
    }

    public void resetForNewLevel(Vector2 spawnPoint) {
        move(spawnPoint);
        setLives(initialLives);
        setVerticalVelocity(0);
        setRespawned(true);
    }

    private void reduceTotalScore(int scorePenalty) {
        totalScore = Math.max(0, totalScore - scorePenalty);
    }

    private void losePowerUp() {
        final int middleOfPlayer = (int) (STANDARD_PLAYER_SIZE.x / 2);
        hasPowerUp = false;
        jumpForce = NORMAL_JUMP_FORCE;
        setSize(STANDARD_PLAYER_SIZE);
        move(middleOfPlayer, 0);
    }
}
