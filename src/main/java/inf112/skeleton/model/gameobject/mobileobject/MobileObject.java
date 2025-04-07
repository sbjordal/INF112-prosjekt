package inf112.skeleton.model.gameobject.mobileobject;

import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.Movable;
import inf112.skeleton.model.gameobject.Transform;

/**
 * Represents all mobile object types.
 * A mobile object is any {@link GameObject} that has a variable position.
 * A variable position can be altered once instantiated.
 */
public abstract class MobileObject extends GameObject implements Movable {
    private final int movementSpeed;
    private int verticalVelocity;
    private int movementDirection;
    private int gravityForce;

    /**
     * Creates a new MobileObject with the specified movement speed.
     * The MobileObject is initially set to be standing still.
     *
     * @param movementSpeed The rate of which the MobileObject moves horizontally.
     * @param transform     The initial position and size of the MobileObject.
     */
    protected MobileObject(int movementSpeed, Transform transform) {
        super(transform);

        this.movementSpeed = movementSpeed;
        this.verticalVelocity = 0;
        this.movementDirection = 0;
        this.gravityForce = -3200;
    }

    /**
     * Moves the {@link GameObject} based on absolute values.
     * Absolute values are values that overwrite already existing values.
     *
     * @param newPosition   A {@link Vector2} containing the absolute values of the new position.
     */
    public void move(Vector2 newPosition) {
        Vector2 oldPos = getTransform().getPos();
        setMovementDirection(oldPos, newPosition);
        getTransform().alterPosition(newPosition);
        updateCollisionBox();
    }

    private void setMovementDirection(Vector2 oldPos, Vector2 newPos){
        float deltaX = newPos.x - oldPos.x;
        if (deltaX > 0){
            movementDirection = 1;
        }
        else if (deltaX < 0){
            movementDirection = -1;
        }
        else {
            movementDirection = 0;
        }
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
    public int getMovementSpeed() { return movementSpeed; }

    public int getVerticalVelocity() {
        return verticalVelocity;
    }

    public void addVerticalVelocity(int velocity) {
        verticalVelocity += velocity;
    }

    public void setVerticalVelocity(int verticalVelocity) {
        this.verticalVelocity = verticalVelocity;
    }

    public int getMovementDirection(){
        return movementDirection;
    }

    @Override
    public void applyGravity(float deltaTime, boolean isOnGround) {
        if (isOnGround && verticalVelocity <= 0) {
            verticalVelocity = 0;
        } else {
            verticalVelocity += (int)(gravityForce * deltaTime);
        }
    }

    @Override
    public void moveHorizontally(float deltaTime, boolean moveLeft, boolean moveRight) {
        if (moveLeft == moveRight) return;

        int direction = moveRight ? 1 : -1;
        int delta = (int)(movementSpeed * deltaTime * direction);
        move(delta, 0);
    }

    @Override
    public void moveVertically(float deltaTime) {
        int delta = (int)(verticalVelocity * deltaTime);
        move(0, delta);
    }
}