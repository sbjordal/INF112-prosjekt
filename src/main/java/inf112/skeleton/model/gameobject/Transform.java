package inf112.skeleton.model.gameobject;

/**
 * Simple record class that describes where an object is on the screen
 * and its size
 *
 * @param pos Position object
 * @param size Size object
 */

public record Transform(Position pos, Size size) {
}
