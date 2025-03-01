package inf112.skeleton.model.gameobject;

import inf112.skeleton.model.WorldBoard;

/**
 * Represents the world coordinates of a {@link GameObject}.
 * World coordinates are used to define the position of a game object.
 * World coordinates are absolute positions, confined within dimensions of the {@link WorldBoard}.
 *
 * @param x     The x-coordinate of the position.
 * @param y     The y-coordinate of the position.
 */
public record Position(int x, int y) { }
