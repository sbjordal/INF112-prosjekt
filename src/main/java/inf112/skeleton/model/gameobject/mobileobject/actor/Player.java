package inf112.skeleton.model.gameobject.mobileobject.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.gameobject.CollisionBox;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.utility.Utility;


/**
 * Represents the user-controlled actor in the game.
 * The user-controlled actor is unique and is defined as the only {@link GameObject}
 * that responds to user-input.
 */
final public class Player extends Actor {
    private static final int NORMAL_BOUNCE_FORCE = 35000;
    private static final int SMALL_BOUNCE_FORCE = 27000;
    private boolean hasPowerUp;
    private boolean isJustRespawned;
    private int jumpForce;


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
        this.isJustRespawned = false;
    }

    @Override
    public void move(int deltaX, int deltaY) {
        Vector2 newPlayerPosition = filterPlayerPosition(deltaX, deltaY);

        if (!isJustRespawned) move(newPlayerPosition);
        isJustRespawned = false;

        // Player falls to his death
        final int belowLevel = -200;
        if (newPlayerPosition.y <= belowLevel) {
            receiveDamage(getLives());
        }
    }

    /**
     * Filters player's position to be valid.
     * A valid position is a position that does not overlap with any other {@link GameObject} types.
     * The filter-algorithm will favor the desired distances.
     *
     * @param deltaX    the desired distance in the horizontal direction.
     * @param deltaY    the desired distance in the vertical direction.
     * @return          filtered player position.
     */
    private Vector2 filterPlayerPosition(int deltaX, int deltaY) {
        Transform transform = getTransform();
        Vector2 position = transform.getPos();
        Vector2 size = transform.getSize();

        float filteredX = Utility.binarySearch(position.x, position.y, deltaX, size, true);
        float filteredY = binarySearch(filteredX, position.y, deltaY, size, false);

        return new Vector2(filteredX, filteredY);
    }


    public void jump() {
        if (isTouchingGround()) {
            final int distance = (int) (jumpForce * Gdx.graphics.getDeltaTime());
            setVerticalVelocity(distance);
        }
    }

    /**
     * Makes the player bounce.
     * A bounce is a lower altitude jump.
     */
    private void bounce(float deltaTime) {
        final int bounceForce = getHasPowerUp() ? SMALL_BOUNCE_FORCE : NORMAL_BOUNCE_FORCE;
        final int distance = (int) (bounceForce * deltaTime);
        jump(distance);
    }

    private void jump(int distance) {

    }


    public void setIsJustRespawned(boolean isJustRespawned) {
        this.isJustRespawned = isJustRespawned;
    }

    public void setHasPowerUp(boolean hasPowerUp) {
        this.hasPowerUp = hasPowerUp;
    }

    public boolean getHasPowerUp() {
        return hasPowerUp;
    }
}
