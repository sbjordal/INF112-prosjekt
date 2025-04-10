package inf112.skeleton.model.gameobject.mobileobject;

import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.PositionValidator;
import inf112.skeleton.model.gameobject.CollisionBox;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.Movable;
import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.gameobject.fixedobject.item.Item;
import inf112.skeleton.model.gameobject.mobileobject.actor.Player;
import inf112.skeleton.model.gameobject.mobileobject.actor.enemy.Enemy;

import java.util.List;

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

    void setMovementDirection(Vector2 oldPos, Vector2 newPos){
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
        Vector2 oldPos = getTransform().getPos();
        Vector2 newPos = new Vector2(oldPos.x + deltaX, oldPos.y + deltaY);
        setMovementDirection(oldPos, newPos);
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

    @Override
    public boolean isTouchingGround(List<GameObject> objectList) {
        for (GameObject object : objectList) {
            if (!(object instanceof Enemy || object instanceof Item || object instanceof Player)) {
                CollisionBox objectCollisionBox = object.getCollisionBox();
                if (this.getCollisionBox().isCollidingFromBottom(objectCollisionBox)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Vector2 filterPosition(int deltaX, int deltaY, PositionValidator validator){
        Transform transform = this.getTransform();
        Vector2 position = transform.getPos();
        Vector2 size = transform.getSize();
        float filteredX = binarySearch(position.x, position.y, deltaX, size, true, validator);
        float filteredY = binarySearch(filteredX, position.y, deltaY, size, false, validator);

        return new Vector2(filteredX, filteredY);
    }

    private float binarySearch(float startX, float startY, int delta, Vector2 size, boolean isX, PositionValidator validator) {
        int low = 0;
        int high = Math.abs(delta);
        boolean isNegative = delta < 0;

        while (low < high) {
            int mid = (low + high + 1) / 2;
            int testDelta = isNegative ? -mid : mid;

            Vector2 newPosition = isX ? new Vector2(startX + testDelta, startY) : new Vector2(startX, startY + testDelta);
            Transform newTransform = new Transform(newPosition, size);
            CollisionBox newCollisionBox = new CollisionBox(newTransform);

            if (validator.isLegalMove(newCollisionBox)) {
                low = mid;
            } else {
                high = mid - 1;
            }
        }
        final float startCoordinate = isX ? startX : startY;
        final float endCoordinate = isNegative ? -low : low;

        return startCoordinate + endCoordinate;
    }
}
