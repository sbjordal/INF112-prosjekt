package inf112.skeleton.model.gameobject;

import com.badlogic.gdx.math.Vector2;

/**
 * Represents the collision area around a {@link GameObject}.
 * The collision area is squared and defined by two points: {@code botLeft} and {@code topRight}.
 */
public class CollisionBox {
    public Vector2 botLeft;
    public Vector2 topRight;
    private final int width;
    private final int height;

    /**
     * Creates a new collision box with the given transform.
     * The anchor is set to be the bottom left of the collision box.
     *
     * @param transform The position and size of the collision box.
     */
    public CollisionBox(Transform transform) {
        this.width = (int) transform.getSize().x;
        this.height = (int) transform.getSize().y;
        this.botLeft = transform.getPos();
        this.topRight = new Vector2(this.botLeft.x + width, this.botLeft.y + height);
    }

    /**
     * Sets the position of the points that make up the collision box based on absolute values.
     * Absolute values are values that overwrite already existing values.
     *
     * @param worldPosition A {@link Vector2} containing the absolute values of the new position.
     */
    public void setPosition(Vector2 worldPosition) {
        this.botLeft = worldPosition;
        this.topRight = new Vector2(this.botLeft.x + width, this.botLeft.y + height);
    }

    /**
     * Checks if the collision box is colliding with an external collision box.
     *
     * @param other     The external collision box.
     * @return          True if the collision box is colliding, else false.
     */
    public boolean isCollidingWith(CollisionBox other) {
        return this.botLeft.x < other.topRight.x &&
                this.topRight.x > other.botLeft.x &&
                this.botLeft.y < other.topRight.y &&
                this.topRight.y > other.botLeft.y;
    }

    /**
     * Checks if the collision box is on top of an external collision box.
     *
     * @param other     The external collision box.
     * @return          True if the collision box is on top, else false.
     */
    public boolean isCollidingFromBottom(CollisionBox other) {
        final int acceptanceRange = 5;

        return this.botLeft.y <= other.topRight.y &&
                this.topRight.y > other.botLeft.y &&
                this.botLeft.x < other.topRight.x - acceptanceRange &&
                this.topRight.x > other.botLeft.x + acceptanceRange;
    }

    /**
     * Checks if the collision box is on the bottom of an external collision box.
     *
     * @param other     The external collision box.
     * @return          True if the collision box is on the bottom, else false.
     */
    public boolean isCollidingFromTop(CollisionBox other) {
        final int acceptanceRange = (int) (other.height * 0.9f);

        return isCollidingWith(other) &&
                this.botLeft.y < other.topRight.y &&
                this.topRight.y < other.topRight.y - acceptanceRange;
    }


    // TODO: --------------- Disse trenger vi muligens ikke ---------------
    public boolean isCollidingFromLeft(CollisionBox other) {
        return isCollidingWith(other) &&
                this.topRight.x > other.botLeft.x &&
                this.botLeft.x < other.botLeft.x &&
                this.botLeft.y < other.topRight.y &&
                this.topRight.y > other.botLeft.y;
    }
    public boolean isCollidingFromRight(CollisionBox other) {
        return isCollidingWith(other) &&
                this.botLeft.x < other.topRight.x &&
                this.topRight.x > other.topRight.x &&
                this.botLeft.y < other.topRight.y &&
                this.topRight.y > other.botLeft.y;
    }
}
