package inf112.starhunt.view;
import inf112.starhunt.model.GameState;
import inf112.starhunt.model.gameobject.ViewableObject;

import java.util.List;

public interface ViewableWorldModel {

    /**
     * returs the width of the board
     *
     * @return board width
     */
    int getBoardWidth();

    /**
     * Returns the player as a ViewableObject
     *
     * @return ViewableObject of the player
     */
    ViewableObject getViewablePlayer();


    /**
     * Returns all GameObjects except player as
     * ViewableObjects in a list
     *
     * @return list of ViewableObjects
     */
    List<ViewableObject> getObjectList();

    /**
     * Tells us the state of the game
     *
     * @return the state of the game
     */
    GameState getGameState();


    /**
     * Tells us the points the player has aquired so far
     *
     * @return the points scored
     */
    int getTotalScore();


    /**
     * Tells us how many coins the player has aquired so far
     *
     * @return the coins scored
     */
    int getCoinCounter();

    /**
     * Tells us the health of the player
     *
     * return an int representing the health
     */
    int getPlayerLives();

    /**
     * Get the current time countdown
     * @return int countDown
     */
    int getCountDown();

    /**
     * Get the direction the player is moving in.
     * -1 = left, 1 = right and 0 = idle
     * @return
     */
    int getMovementDirection();

    /**
     * Get world width
     */
    int getLevelWidth();

    /**
     * InfoMode is a boolean value used to decide if the information of the game should be displayed or not.
     * @return a boolean, true if the information should be displayed, otherwise false.
     */
    boolean getInfoMode();

    /**
     * Get the current game level.
     * @return int levelCounter
     */
    int getLevelCounter();

    /**
     * Updates where the left x of the viewport is
     */
    void updateViewportLeftX(float leftX);

    /**
     * Get the current player vertical velocity.
     * @return float verticalVelocity
     */
    float getVerticalVelocity();

    /**
     * Getter for finding out whether the player has moved since the last frame.
     * @return true if movement has happened, false otherwise
     */
    boolean getPlayerMovement();
}

