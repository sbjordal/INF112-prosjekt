package inf112.skeleton.model.gameobject.mobileobject.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.Transform;

/**
 * Represents the user-controlled actor in the game.
 * The user-controlled actor is unique and is defined as the only {@link GameObject}
 * that responds to user-input.
 */
final public class Player extends Actor {
    private static final int NORMAL_BOUNCE_FORCE = 35000;
    private static final int SMALL_BOUNCE_FORCE = 27000;
    private static final int NORMAL_JUMP_FORCE = 63000;
    private static final int BIG_JUMP_FORCE = 73000;
    private static final Vector2 STANDARD_PLAYER_SIZE = new Vector2(40, 80);
    private static final Vector2 LARGE_PLAYER_SIZE = new Vector2(65, 135);
    private int jumpForce;
    private boolean isJustRespawned;
    private boolean hasPowerUp;
    private long lastAttackTime;
    private long lastBounceTime;

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
    }

    public void jump(int jumpForce) {
        setVerticalVelocity(jumpForce);
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
    public void bananaCollision(){
        hasPowerUp = true;
        this.setSize(LARGE_PLAYER_SIZE);
        int middleOfPlayer = (int) (this.getTransform().getSize().x / 2);
        this.move(-middleOfPlayer, 0);
        jumpForce = BIG_JUMP_FORCE;
    }

    public void bounce(){
        final int bounceForce = this.getHasPowerUp() ? SMALL_BOUNCE_FORCE : NORMAL_BOUNCE_FORCE;
        final int distance = (int) (bounceForce * Gdx.graphics.getDeltaTime());
        this.jump(distance);
    }

    public int getJumpForce() {
        return jumpForce;
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
}
