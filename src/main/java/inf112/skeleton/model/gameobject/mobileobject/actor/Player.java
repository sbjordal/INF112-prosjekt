package inf112.skeleton.model.gameobject.mobileobject.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.LevelManager;
import inf112.skeleton.model.gameobject.CollisionBox;
import inf112.skeleton.model.gameobject.CollisionVisitor;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.gameobject.fixedobject.Ground;
import inf112.skeleton.model.gameobject.fixedobject.item.Banana;
import inf112.skeleton.model.gameobject.fixedobject.item.Coin;
import inf112.skeleton.model.gameobject.fixedobject.item.Star;
import inf112.skeleton.model.gameobject.mobileobject.actor.enemy.Enemy;

/**
 * Represents the user-controlled actor in the game.
 * The user-controlled actor is unique and is defined as the only {@link GameObject}
 * that responds to user-input.
 */
final public class Player extends Actor implements CollisionVisitor {
    private static final int NORMAL_BOUNCE_FORCE = 35000;
    private static final int SMALL_BOUNCE_FORCE = 27000;
    private static final int NORMAL_JUMP_FORCE = 63000;
    private int jumpForce;
    private boolean isJustRespawned;
    private boolean hasPowerUp;
    private boolean goToNextLevel;
    private long lastAttackTime;
    private long lastBounceTime;
    private Integer coinCounter;
    private Integer totalScore;


    /**
     * Creates a new Player with the specified lives, movement speed and transform.
     *
     * @param lives         The initial amount of lives of the Player.
     * @param movementSpeed The rate of which the Player moves horizontally.
     * @param transform     The initial transform of the Player.
     */
    public Player(int lives, int movementSpeed, Transform transform) {
        super(lives, movementSpeed, transform);
        this.hasPowerUp = false;
        this.damage = 1;
        this.lastAttackTime = 0;
        this.lastBounceTime = 0;
        this.isJustRespawned = false;
        this.jumpForce = NORMAL_JUMP_FORCE;
        this.coinCounter = 0;
        this.totalScore = 0;
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
    public void collideWithCoin(Coin coin) {
        coinCounter++;
        totalScore += coin.getObjectScore();
    }

    @Override
    public void collideWithStar(Star star) {
        goToNextLevel = true; // TODO, husk å sette til false i modellen etter denne er gettet
    }

    @Override
    public void collideWithBanana(Banana banana) {
        hasPowerUp = true;
        setSize(banana.getLargePlayerSize());
        int middleOfPlayer = (int) (getTransform().getSize().x / 2);
        move(-middleOfPlayer, 0);
        jumpForce = banana.getBigJumpForce();
    }

    @Override
    public void collideWithGround(Ground ground) {
        //TODO, implement me
    }

    @Override
    public void collideWithEnemy(Enemy enemy) {
        //TODO, nmå bli fikset med hva som kom fra CollisionHandler (kopiert derfa)
        // Originale metode parameter fra CollisionHandler:
//        (Enemy enemy, Integer totalScore, CollisionBox newPlayerCollisionBox){
            long currentTime = System.currentTimeMillis();

            if (newPlayerCollisionBox.isCollidingFromBottom(enemy.getCollisionBox())) {
                if (currentTime - getLastBounceTime() >= BOUNCE_COOLDOWN) {

                    bounce();
                    dealDamage(enemy, getDamage());

                    if (!enemy.isAlive()) {
                        totalScore += enemy.getObjectScore();
                    }

                    setLastBounceTime(currentTime);
                }
            } else {
                if (currentTime - getLastAttackTime() >= ATTACK_COOLDOWN) {

                    // TODO...
                    // Enemy dealing damage to the player is moved into Enemy.moveEnemy()
                    // - This is to make sure that the enemy doesn't deal damage twice.
                    // - The logic needs to be inside Enemy class. If not, the enemy won't deal damage
                    //   when it collides with the player.
                    // - As of right now, ATTACK_COOLDOWN only affects totalScore. It does NOT affect the frequency of attacks.

                    // Reduce total score
                    final int scorePenalty = 4;
                    totalScore = Math.max(0, totalScore - scorePenalty);

                    setLastAttackTime(currentTime);
                }
            }
//        }
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
        return goToNextLevel;
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
}
