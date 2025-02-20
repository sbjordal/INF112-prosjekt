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
     * Moves player left if the move is legal (also calls isLegalMove)
     */
    public void movePlayerLeft();

    /**
     * Moves player left if the move is legal (also calls isLegalMove)
     */
    public void movePlayerRight();

    /**
     * Pauses the game and modifies GameState
     *
     */
    public void pause();

    /**
     * Resumes the game and modifies GameState
     */
    public void resume();

}
