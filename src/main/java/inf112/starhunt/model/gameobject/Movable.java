package inf112.starhunt.model.gameobject;

import com.badlogic.gdx.math.Vector2;
import inf112.starhunt.model.PositionValidator;

import java.util.List;

/**
 * Represents an object in the game world that can move and be affected by gravity and/or collisions.
 * <p>
 * This interface defines essential behavior for dynamic objects such as players or enemies,
 * including movement handling, gravity simulation, collision validation, and ground detection.
 * <p>
 * Classes implementing {@code Movable} are expected to interact with physics and collision systems
 * to ensure smooth and legal movement within the game world.
 */
public interface Movable {

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
     * A method for checking whether the mobile object is standing on the ground, or not
     * @param objectList, the list of objects that the mobile object potentially is standing on.
     * @return true if the MobileObject is touching the ground, false if not.
     */
    boolean isTouchingGround(List<Collidable> objectList);

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

    /**
     * Resolves a proposed movement of the movable object.
     * It filters the movement based on the provided validator.
     *
     * @param deltaX      The proposed change in the x-coordinate.
     * @param deltaY      The proposed change in the y-coordinate.
     * @param validator   The {@link PositionValidator} used to filter the movement.
     */
    void resolveMovement(float deltaX, float deltaY, PositionValidator validator);

}
