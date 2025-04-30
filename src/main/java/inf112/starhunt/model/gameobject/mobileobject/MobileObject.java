package inf112.starhunt.model.gameobject.mobileobject;

import com.badlogic.gdx.math.Vector2;
import inf112.starhunt.model.PositionValidator;
import inf112.starhunt.model.gameobject.*;
import inf112.starhunt.model.gameobject.fixedobject.Ground;
import inf112.starhunt.model.gameobject.mobileobject.actor.Player;

import java.util.List;

/**
 * Represents all mobile object types.
 * A mobile object is any {@link GameObject} that has a variable position.
 * A variable position can be altered once instantiated.
 */
public abstract class MobileObject extends GameObject implements Movable {
    private final static int GRAVITY_FORCE = -3200;
    private final int movementSpeed;
    private int verticalVelocity;
    private int movementDirection;

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
    }

    @Override
    public void move(Vector2 newPos) {
        getTransform().alterPosition(newPos);
        updateCollisionBox();
    }

    @Override
    public void move(float deltaX, float deltaY) {
        getTransform().alterPosition(deltaX, deltaY);
        updateCollisionBox();
    }

    public void setMovementDirection(int movementDirection) {
        this.movementDirection = movementDirection;
    }

    public void switchDirection() {
        movementDirection *= -1;
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
            verticalVelocity += (int)(GRAVITY_FORCE * deltaTime);
        }
    }

    /**
     * A method for checking whether the mobile object is standing on the ground, or not
     * @param objectList, the list of objects that the mobile object potentially is standing on.
     * @return true if the MobileObject is touching the ground, false if not.
     */
    protected boolean isTouchingGround(List<Collidable> objectList) {
        for (Collidable object : objectList) {
            if (object instanceof Ground) {
                CollisionBox objectCollisionBox = object.getCollisionBox();
                if (this.getCollisionBox().isCollidingFromBottom(objectCollisionBox)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Vector2 filterPosition(float deltaX, float deltaY, PositionValidator validator, Visitor visitor){
        Transform transform = getTransform();
        Vector2 position = transform.getPos();
        Vector2 size = transform.getSize();
        float filteredX = binarySearch(position.x, position.y, deltaX, size, true, validator, visitor);
        float filteredY = binarySearch(filteredX, position.y, deltaY, size, false, validator, visitor);

        return new Vector2(filteredX, filteredY);
    }

    private float binarySearch(float startX, float startY, float delta, Vector2 size, boolean isX, PositionValidator validator, Visitor visitor) {
        int low = 0;
        int high = (int) Math.abs(delta);
        boolean isNegative = delta < 0;

        while (low < high) {
            int mid = (low + high + 1) / 2;
            int testDelta = isNegative ? -mid : mid;

            Vector2 newPosition = isX ? new Vector2(startX + testDelta, startY) : new Vector2(startX, startY + testDelta);
            Transform newTransform = new Transform(newPosition, size);
            CollisionBox newCollisionBox = new CollisionBox(newTransform);

            if (validator.isLegalMove(visitor, newCollisionBox)) {
                low = mid;
            } else {
                high = mid - 1;
            }
        }
        final float startCoordinate = isX ? startX : startY;
        final float endCoordinate = isNegative ? -low : low;

        return startCoordinate + endCoordinate;
    }

    @Override
    public int getDirection(){
        return movementDirection;
    }
}
