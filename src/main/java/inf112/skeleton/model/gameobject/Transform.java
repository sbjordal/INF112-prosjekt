package inf112.skeleton.model.gameobject;

/**
 * Defines the position and size of a {@link GameObject}.
 * The position is represented by a {@link Position} object.
 * The size is represented by a {@link Size} object.
 */
public class Transform{
    Position position;
    Size size;

    /**
     * Creates a new Transform object with the specified position and size.
     *
     * @param position  The initial position of the transform.
     * @param size      The initial size of the transform.
     */
    public Transform(Position position, Size size) {
        this.position = position;
        this.size = size;
    }

    public Position getPos() { return position; }
    public Size getSize() { return size; }

    /**
     * Sets the transform's position based on offset values.
     * Offset values are relative differences added to already existing values.
     *
     * @param deltaX    The horizontal offset value.
     * @param deltaY    The vertical offset value.
     */
    public void alterPosition(int deltaX, int deltaY) {
        this.position = new Position(position.x() + deltaX, position.y() + deltaY);
    }

    /**
     * Sets the transform's position based on absolute values.
     * Absolute values are values that overwrite already existing values.
     *
     * @param newPosition   A {@link Position} containing the absolute values of the new position.
     */
    public void alterPosition(Position newPosition) {
        this.position = newPosition;
    }

    /**
     * Sets the transform's size based on absolute values.
     * Absolute values are values that overwrite already existing values.
     *
     * @param width     The absolute value of the size's horizontal length.
     * @param height    The absolute value of the size's vertical length.
     */
    public void alterSize(int width, int height) {
        this.size = new Size(width, height);
    }
}
