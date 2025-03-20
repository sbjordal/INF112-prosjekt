package inf112.skeleton.model.gameobject;

import com.badlogic.gdx.math.Vector2;

public class CollisionBox { // TODO: legg til kommentarer
    public Vector2 botLeft;
    public Vector2 topRight;
    private int width;
    private int height;

    // Anchor is at the bottom left of the box
    public CollisionBox(Transform transform) {
        this.width = (int) transform.getSize().x;
        this.height = (int) transform.getSize().y;
        this.botLeft = transform.getPos();
        this.topRight = new Vector2(this.botLeft.x + width, this.botLeft.y + height);
    }

    public void setPosition(Vector2 worldPosition) {
        this.botLeft = worldPosition;
        this.topRight = new Vector2(this.botLeft.x + width, this.botLeft.y + height);
    }

    public boolean isCollidingWith(CollisionBox other) {
        return this.botLeft.x < other.topRight.x &&
                this.topRight.x > other.botLeft.x &&
                this.botLeft.y < other.topRight.y &&
                this.topRight.y > other.botLeft.y;
    }

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

    public boolean isCollidingFromBottom(CollisionBox other) {
        final int acceptanceRange = 5;

        return this.botLeft.y <= other.topRight.y &&
                this.topRight.y >= other.botLeft.y &&
                this.botLeft.x < other.topRight.x - acceptanceRange &&
                this.topRight.x > other.botLeft.x + acceptanceRange;
    }

    public boolean isCollidingFromTop(CollisionBox other) {
        return isCollidingWith(other) && this.botLeft.y < other.topRight.y && this.topRight.y > other.topRight.y;
    }
}
