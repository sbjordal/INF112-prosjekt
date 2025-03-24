package inf112.skeleton.controller;

import inf112.skeleton.model.GameState;
import inf112.skeleton.model.LevelManager;

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
     * Modifies gamestate to GAME_INFO
     */
    public void setToInfoMode();

    /**
     * Modifies gamestate to GAME_MENU
     */
    public void backToGameMenu();

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
     * Sets whether the player should be jumping or not.
     *
     * @param isJumping Boolean determine the jumping state.
     */
    public void setJumping(boolean isJumping);

    /**
     * Gets the original speed of the object that was set when the object was initialized
     * // TODO : ?
     * @return the original speed of the object
     */
    public int getMovementSpeed();

    /**
     * Start the specified level.
     *
     * @param level The level to start
     */
    public void startLevel(LevelManager.Level level);
}

