package inf112.skeleton.model.gameobject.mobileobject.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.gameobject.Collidable;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.Transform;

/**
 * Represents the user-controlled actor in the game.
 * The user-controlled actor is unique and is defined as the only {@link GameObject}
 * that responds to user-input.
 */
final public class Player extends Actor implements Collidable {
    private static final int NORMAL_BOUNCE_FORCE = 35000;
    private static final int SMALL_BOUNCE_FORCE = 27000;
    private static final int NORMAL_JUMP_FORCE = 63000;
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
    public void initiatePowerUp(Vector2 newPlayerSize, int newJumpForce){
        hasPowerUp = true;
        setSize(newPlayerSize);
        int middleOfPlayer = (int) (getTransform().getSize().x / 2);
        move(-middleOfPlayer, 0);
        jumpForce = newJumpForce;
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

    public int getJumpForce() { return jumpForce; }

    @Override
    public void onCollide(Collidable gameObject) {

    }
}
