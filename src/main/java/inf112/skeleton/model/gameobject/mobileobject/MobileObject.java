package inf112.skeleton.model.gameobject.mobileobject;

import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.Position;
import inf112.skeleton.model.gameobject.Transform;

/**
 * Represents all mobile object types.
 * A mobile object is any {@link GameObject} that has a variable position.
 * A variable position can be altered once instantiated.
 */
public abstract class MobileObject extends GameObject {
    private final int movementSpeed;

    /**
     * Creates a new MobileObject with the specified movement speed.
     * The MobileObject is initially set to be standing still.
     *
     * @param movementSpeed The rate of which the MobileObject moves horizontally.
     * @param transform     The initial position and size of the MobileObject.
     * @param texture       The visual representation of the MobileObject.
     */
    protected MobileObject(int movementSpeed, Transform transform, Texture texture) {
        super(transform, texture);

        this.movementSpeed = movementSpeed;
    }

    /**
     * Moves the {@link GameObject} based on absolute values.
     * Absolute values are values that overwrite already existing values.
     *
     * @param newPosition   A {@link Position} containing the absolute values of the new position.
     */
    public void move(Position newPosition) {
        this.getTransform().alterPosition(newPosition);
    }

    /**
     * Moves the {@link GameObject} based on offset values.
     * Offset values are relative differences added to already existing values.
     *
     * @param deltaX    The horizontal offset value.
     * @param deltaY    The vertical offset value.
     */
    public void move(int deltaX, int deltaY) {
        this.getTransform().alterPosition(deltaX, deltaY);
    }

    /**
     * Returns the MobileObject's movement speed.
     *
     * @return The movement speed as an integer.
     */
    protected int getMovementSpeed() {
        return movementSpeed;
    }
}