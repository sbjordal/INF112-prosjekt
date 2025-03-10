package inf112.skeleton.controller;

import inf112.skeleton.model.GameState;

public interface ControllableWorldModel {

    /**
     * Tells us the state of the game
     *
     * @return the state of the game
     */
    GameState getGameState();

    /**
     * Moves player left if the move is legal (also calls isLegalMove) TODO: finskriv
     */
    public void move(int deltaX, int deltaY);

    /**
     * Makes player jump. TODO: finskriv
     */
    public void jump();

    /**
     * Pauses the game and modifies GameState
     */
    public void pause();

    /**
     * Resumes the game and modifies GameState
     */
    public void resume();

    /***
     * //TODO
     * @param movingRight
     */
    public void setMovingRight(boolean movingRight);

    /**
     * // TODO
     * @param movingLeft
     */
    public void setMovingLeft(boolean movingLeft);

    /**
     * Gets the original speed of the object that was set when the object was initialized
     * // TODO : ?
     * @return the original speed of the object
     */
    public int getMovementSpeed();
}

