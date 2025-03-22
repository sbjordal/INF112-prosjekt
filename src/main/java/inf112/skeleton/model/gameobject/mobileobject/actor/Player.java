package inf112.skeleton.model.gameobject.mobileobject.actor;

import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.Transform;

/**
 * Represents the user-controlled actor in the game.
 * The user-controlled actor is unique and is defined as the only {@link GameObject}
 * that responds to user-input.
 */
final public class Player extends Actor {
    private boolean hasPowerUp;

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
}
