package inf112.skeleton.model.gameobject;

public class CollisionBox { // TODO: legg til kommentarer
    public Position botLeft;
    public Position topRight;
    private int width;
    private int height;

    // Anchor is at the bottom left of the box
    public CollisionBox(Transform transform) {
        this.width = transform.getSize().width();
        this.height = transform.getSize().height();
        this.botLeft = transform.getPos();
        this.topRight = new Position(this.botLeft.x() + width, this.botLeft.y() + height);
    }

    public void setPosition(Position worldPosition) {
        this.botLeft = worldPosition;
        this.topRight = new Position(this.botLeft.x() + width, this.botLeft.y() + height);
    }

    public boolean isCollidingWith(CollisionBox other) {
        return this.botLeft.x() < other.topRight.x() &&
                this.topRight.x() > other.botLeft.x() &&
                this.botLeft.y() < other.topRight.y() &&
                this.topRight.y() > other.botLeft.y();
    }

    public boolean isCollidingFromLeft(CollisionBox other) {
        return isCollidingWith(other) && this.botLeft.x() < other.botLeft.x() && this.topRight.x() > other.botLeft.x();
    }

    public boolean isCollidingFromRight(CollisionBox other) {
        return isCollidingWith(other) && this.botLeft.x() < other.topRight.x() && this.topRight.x() > other.topRight.x();
    }

    public boolean isCollidingFromBottom(CollisionBox other) {
        if (this.botLeft.y() <= other.topRight.y() &&
                this.topRight.y() >= other.botLeft.y() &&
                this.botLeft.x() <= other.topRight.x() &&
                this.topRight.x() >= other.botLeft.x()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isCollidingFromTop(CollisionBox other) {
        return isCollidingWith(other) && this.botLeft.y() < other.topRight.y() && this.topRight.y() > other.topRight.y();
    }
}
