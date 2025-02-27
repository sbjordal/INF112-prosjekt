package inf112.skeleton.model.gameobject;

/**
 * Simple class that describes where an object is on the screen
 * and its size
 */

public class Transform{
    Position pos;
    Size size;

    public Transform(Position pos, Size size) {
        this.pos = pos;
        this.size = size;
    }

    public Position getPos() {
        return pos;
    }

    public Size getSize() {
        return size;
    }

    public void alterPosition(int deltaX, int deltaY) {
        this.pos = new Position(pos.x() + deltaX, pos.y() + deltaY);
    }

    public void alterSize(int width, int height) {
        this.size = new Size(width, height);
    }

}
