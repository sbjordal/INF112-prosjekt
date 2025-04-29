package inf112.starhunt.model;

import inf112.starhunt.model.gameobject.GameObject;

/**
 * Represents the dimensions of the world board.
 * The world board defines the confinements of the playable area.
 * The playable area is wherever {@link GameObject} types are allowed to be positioned.
 *
 * @param width     The horizontal length of the world board.
 * @param height    The vertical length of the world board.
 */
public record WorldBoard(int width, int height) { }
