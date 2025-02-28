package inf112.skeleton.model.gameobject;

import inf112.skeleton.model.WorldBoard;

/**
 * Represents the position of a {@link GameObject}.
 * This is used to define the world coordinates of a game object.
 * World coordinates are absolute positions, confined within dimensions of the {@link WorldBoard}.
 *
 * @param x     The x-coordinate of the position.
 * @param y     The y-coordinate of the position.
 */
public record Position(int x, int y) { }
