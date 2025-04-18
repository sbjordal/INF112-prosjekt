package inf112.skeleton.model;

import inf112.skeleton.model.gameobject.GameObject;

// TODO, revisjon, denne klassen trengs ikke lengre, er erstattet av en instans av Rectangle i worldModel.setUpmodel
/**
 * Represents the dimensions of the world board.
 * The world board defines the confinements of the playable area.
 * The playable area is wherever {@link GameObject} types are allowed to be positioned.
 *
 * @param width     The horizontal length of the world board.
 * @param height    The vertical length of the world board.
 */
public record WorldBoard(int width, int height) { }
