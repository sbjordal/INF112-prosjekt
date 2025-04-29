package inf112.starhunt.model.gameobject;

import com.badlogic.gdx.math.Vector2;

/**
 * Represents the collision area around a {@link GameObject}.
 * The collision area is squared and defined by two points: {@code botLeft} and {@code topRight}.
 */
public class CollisionBox {
    private final static float ACCEPTANCE_RANGE = 3.0f;
    public Vector2 botLeft; // TODO: lage get-ere for denne?
    public Vector2 topRight; // TODO: samme her
    private final float width;
    private final float height;

    /**
     * Creates a new collision box with the given transform.
     * The anchor is set to be the bottom left of the collision box.
     *
     * @param transform The position and size of the collision box.
     */
    public CollisionBox(Transform transform) {
        this.width = transform.getSize().x;
        this.height = transform.getSize().y;
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
        botLeft = worldPosition;
        topRight = new Vector2(botLeft.x + width, botLeft.y + height);
    }

    /**
     * Checks if the collision box is colliding with an external collision box.
     *
     * @param other     The external collision box.
     * @return          True if the collision box is colliding, else false.
     */
    public boolean isCollidingWith(CollisionBox other) {
        return botLeft.x < other.topRight.x &&
                topRight.x > other.botLeft.x &&
                botLeft.y < other.topRight.y &&
                topRight.y > other.botLeft.y;
    }

    /**
     * Checks if the collision box is on top of an external collision box.
     *
     * @param other     The external collision box.
     * @return          True if the collision box is on top, else false.
     */
    public boolean isCollidingFromBottom(CollisionBox other) {
        return botLeft.y <= other.topRight.y &&
                topRight.y > other.botLeft.y &&
                botLeft.x < other.topRight.x - ACCEPTANCE_RANGE &&
                topRight.x > other.botLeft.x + ACCEPTANCE_RANGE;
    }

    /**
     * Checks if the collision box is on the bottom of an external collision box.
     *
     * @param other     The external collision box.
     * @return          True if the collision box is on the bottom, else false.
     */
    public boolean isCollidingFromTop(CollisionBox other) {
        final float acceptanceRange = other.height * 0.9f;

        return isWithinBounds(other) &&
                botLeft.y < other.topRight.y &&
                topRight.y < other.topRight.y - acceptanceRange;
    }

    /**
     * Checks if the collision box is to the right of an external collision box.
     *
     * @param other     The external collision box.
     * @return          True if the collision box is to the left, else false.
     */
    public boolean isCollidingFromLeft(CollisionBox other) {
        return botLeft.x < other.topRight.x + ACCEPTANCE_RANGE &&
                topRight.x > other.topRight.x &&
                botLeft.y < other.topRight.y &&
                topRight.y > other.botLeft.y;
    }

    /**
     * Checks if the collision box is to the left of an external collision box.
     *
     * @param other     The external collision box.
     * @return          True if the collision box is to the right, else false.
     */
    public boolean isCollidingFromRight(CollisionBox other) {
        return topRight.x > other.botLeft.x - ACCEPTANCE_RANGE &&
                botLeft.x < other.botLeft.x &&
                botLeft.y < other.topRight.y &&
                topRight.y > other.botLeft.y;
    }

    /**
     * Checks if the collision box is located inside an external collision box.
     *
     * @param other     The external collision box.
     * @return          True if the collision box is inside, else false.
     */
    private boolean isWithinBounds(CollisionBox other) {
        return botLeft.x <= other.topRight.x &&
                topRight.x >= other.botLeft.x &&
                botLeft.y <= other.topRight.y &&
                topRight.y >= other.botLeft.y;
    }

    public Vector2 getBotLeft(){
        return botLeft;
    }

    public Vector2 getTopRight(){
        return topRight;
    }
}
