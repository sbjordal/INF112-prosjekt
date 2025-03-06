package inf112.skeleton.model.gameobject.mobileobject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.Transform;

/**
 * Represents all mobile object types.
 * A mobile object is any {@link GameObject} that has a variable position.
 * A variable position can be altered once instantiated.
 */
public abstract class MobileObject extends GameObject {
    private int movementSpeed;

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
     * @param newPosition   A {@link Vector2} containing the absolute values of the new position.
     */
    public void move(Vector2 newPosition) {
        getTransform().alterPosition(newPosition);
        updateCollisionBox();
    }

    /**
     * Moves the {@link GameObject} based on offset values.
     * Offset values are relative differences added to already existing values.
     *
     * @param deltaX    The horizontal offset value.
     * @param deltaY    The vertical offset value.
     */
    public void move(int deltaX, int deltaY) {
        getTransform().alterPosition(deltaX, deltaY);
        updateCollisionBox();
    }

    /**
     * Updates the transform of the collision box to match the current transform.
     */
    private void updateCollisionBox() {
        setCollisionBox(getTransform());
    }

    /**
     * Returns the MobileObject's movement speed.
     *
     * @return The movement speed as an integer.
     */
    public int getMovementSpeed() {
        return movementSpeed;
    }
    public void setMovementSpeed(int speed){
        this.movementSpeed = speed;
    }
}