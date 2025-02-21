package inf112.skeleton.model.gameobject.mobileobject;

import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.Position;

/**
 * Represents all mobile GameObject types.
 * A mobile object is any {@link GameObject} that has a variable position.
 * Its position updates in real time for each rendered frame.
 *
 * @author Eivind H. Naasen
 */
abstract class MobileObject extends GameObject {
    private boolean isMovingLeft;
    private boolean isMovingRight;
    private final int movementSpeed;
    private Position pos;
    private Texture sprite;

    /**
     * Creates a new MobileObject with the specified movement speed.
     * The MobileObject is initially set to be standing still.
     *
     * @param movementSpeed The movement speed of the MobileObject.
     */
    protected MobileObject(int movementSpeed, Position pos, Texture sprite) {
        super(pos,sprite);
        this.isMovingLeft = false;
        this.isMovingRight = false;
        this.movementSpeed = movementSpeed;
    }

    public void setMoveLeft(boolean isMovingLeft) { this.isMovingLeft = isMovingLeft; }
    public void setMoveRight(boolean isMovingRight) { this.isMovingRight = isMovingRight; }
    protected int getMovementSpeed() { return movementSpeed; }
}