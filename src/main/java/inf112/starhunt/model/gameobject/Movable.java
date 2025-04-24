package inf112.starhunt.model.gameobject;

import com.badlogic.gdx.math.Vector2;
import inf112.starhunt.model.PositionValidator;

import java.util.List;

public interface Movable {

    /**
     * A method for checking whether a Gameobject is standing on the ground, or not
     * @param objectList, the list of objects that the object potentially is standing on.
     * @return true if the Gameobject is touching the ground, false if not.
     */
    boolean isTouchingGround(List<Collidable> objectList);

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
     * Filters object's position to be valid.
     * A valid position is a position that does not overlap with any other {@link GameObject} types.
     * The filter-algorithm will favor the desired distances.
     *
     * @param deltaX    the desired distance in the horizontal direction.
     * @param deltaY    the desired distance in the vertical direction.
     * @param validator A way to validate the new position for the object.
     * @return          filtered player position.
     */
    Vector2 filterPosition(float deltaX, float deltaY, PositionValidator validator, Visitor visitor);

    /**
     * Moves the {@link GameObject} based on offset values.
     * Offset values are relative differences added to already existing values.
     *
     * @param deltaX    The horizontal offset value.
     * @param deltaY    The vertical offset value.
     */
     void move(float deltaX, float deltaY);

    /**
     * Moves the {@link GameObject} based on absolute values.
     * Absolute values are values that overwrite already existing values.
     *
     * @param newPos   A {@link Vector2} containing the absolute values of the new position.
     */
    void move(Vector2 newPos);

    // TODO: comment
    void resolveMovement(float deltaX, float deltaY, PositionValidator validator);
}
