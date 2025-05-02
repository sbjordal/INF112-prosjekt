package inf112.starhunt.controller;

import inf112.starhunt.model.GameState;
import inf112.starhunt.model.LevelManager;

/**
 * TODO: javadoc
 */
public interface ControllableWorldModel {

    /**
     * Tells us the state of the game
     *
     * @return the state of the game
     */
    GameState getGameState();


    /**
     * Sets the state of the game
     *
     */
    void setGameState(GameState gameState);

    /**
     * InfoMode is a boolean value used to decide if the information of the game should be displayed or not.
     * @return a boolean, true if the information should be displayed, otherwise false.
     */
    boolean getInfoMode();


    /**
     * Modifies infoMode
     * @param infoMode the boolean value it should be set to.
     *                 True if you want to signal that infoMode should be displayed
     *                 False, if not.
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

    /**
     * A method for signalling whether the player is currently moving to the right, or not.
     * @param movingRight, True if the player is currently moving right, False if not.
     */
    void setMovingRight(boolean movingRight);

    /**
     * A method for signalling whether the player is currently moving to the left, or not.
     * @param movingLeft, True if the player is currently moving left, False if not.
     */
    void setMovingLeft(boolean movingLeft);

    /**
     * Sets whether the player should be jumping or not.
     *
     * @param isJumping Boolean determine the jumping state.
     */
    void setJumping(boolean isJumping);

    /**
     * Start the specified level.
     *
     * @param level The level to start
     */
    void startLevel(LevelManager.Level level);

    LevelManager.Level getCurrentLevel();
}

