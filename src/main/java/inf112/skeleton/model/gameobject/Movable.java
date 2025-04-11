package inf112.skeleton.model.gameobject;

import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.PositionValidator;

import java.util.List;

public interface Movable {

    /**
     * A method for checking whether a Gameobject is standing on the ground, or not
     * @param objectList, the list of objects that the object potentially is standing on.
     * @return true if the Gameobject is touching the ground, false if not.
     */
    boolean isTouchingGround(List<GameObject> objectList);

    /**
     * Applies gravity to the object's vertical velocity.
     * If the object is on the ground and not moving upward, vertical velocity is set to 0.
     * Otherwise, gravity is applied based on the elapsed time since the last frame.
     *
     * @param deltaTime   The time in seconds since the last update. Used to ensure frame rate–independent physics.
     * @param isOnGround  Whether the object is currently standing on a solid surface.
     */
    void applyGravity(float deltaTime, boolean isOnGround);

    /**
     * Moves the object horizontally based on input and elapsed time.
     * If both or neither movement directions are active, no movement occurs.
     * Otherwise, the object is moved left or right depending on the input.
     *
     * @param deltaTime  The time in seconds since the last update.
     * @param moveLeft   Whether the object should move left.
     * @param moveRight  Whether the object should move right.
     */
    void moveHorizontally(float deltaTime, boolean moveLeft, boolean moveRight);

    /**
     * Moves the object vertically based on its current vertical velocity and elapsed time.
     * The distance moved is calculated by multiplying the vertical velocity with deltaTime,
     * ensuring consistent movement regardless of frame rate.
     *
     * @param deltaTime  The time in seconds since the last update.
     */
    void moveVertically(float deltaTime);

    /**
     * Filters object's position to be valid.
     * A valid position is a position that does not overlap with any other {@link GameObject} types.
     * The filter-algorithm will favor the desired distances.
     *
     * @param deltaX    the desired distance in the horizontal direction.
     * @param deltaY    the desired distance in the vertical direction.
     * @param validator A way to validate the new position for the object.
     * @return          filtered player position.
     */
    Vector2 filterPosition(int deltaX, int deltaY, PositionValidator validator);
}
