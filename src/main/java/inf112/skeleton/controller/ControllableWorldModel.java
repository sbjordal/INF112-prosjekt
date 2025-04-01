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
     * TODO skriv!
     * @return
     */
    boolean getInfoMode();


    /**
     * Modifies infoMode
     */
    void setInfoMode(boolean infoMode);

    /**
     * Modifies gamestate to GAME_MENU
     */
    void backToGameMenu();

    /**
     * Pauses the game and modifies GameState
     */
    void pause();

    /**
     * Resumes the game and modifies GameState
     */
    void resume();

    /***
     * //TODO
     * @param movingRight
     */
    void setMovingRight(boolean movingRight);

    /**
     * // TODO
     * @param movingLeft
     */
    void setMovingLeft(boolean movingLeft);

    /**
     * Sets whether the player should be jumping or not.
     *
     * @param isJumping Boolean determine the jumping state.
     */
    void setJumping(boolean isJumping);

    /**
     * Gets the original speed of the object that was set when the object was initialized
     * // TODO : ?
     * @return the original speed of the object
     */
    int getMovementSpeed();

    /**
     * Start the specified level.
     *
     * @param level The level to start
     */
    void startLevel(LevelManager.Level level);
}

