package inf112.skeleton.model.gameobject.mobileobject;

import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.Position;
import inf112.skeleton.model.gameobject.Transform;

/**
 * Represents all mobile GameObject types.
 * A mobile object is any {@link GameObject} that has a variable position.
 * Its position updates in real time for each rendered frame.
 *
 * @author Eivind H. Naasen
 */
public abstract class MobileObject extends GameObject {
    private final int movementSpeed;

    /**
     * Creates a new MobileObject with the specified movement speed.
     * The MobileObject is initially set to be standing still.
     *
     * @param movementSpeed The movement speed of the MobileObject.
     */
    protected MobileObject(int movementSpeed, Transform transform, Texture texture) {
        super(transform, texture);

        this.movementSpeed = movementSpeed;
    }

    /**
     * TODO: skriv kommentar.
     */
    public void move(Position newPosition) {
        this.getTransform().alterPosition(newPosition);
    }

    protected int getMovementSpeed() { return movementSpeed; }
}