package inf112.skeleton.model.gameobject;

/**
 * Represents the size of a {@link GameObject}.
 * A size is used to define the dimensions of both the object's texture and its collision box.
 *
 * @param width     The horizontal length of the size.
 * @param height    The vertical length of the size.
 */
public record Size(int width, int height) { }
