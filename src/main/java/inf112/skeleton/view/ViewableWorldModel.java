package inf112.skeleton.view;
import inf112.skeleton.model.GameState;
import inf112.skeleton.model.gameobject.ViewableObject;

import java.util.List;

public interface ViewableWorldModel {

    /**
     * Returns the player as a ViewableObject
     *
     * @return ViewableObject of the player
     */
    public ViewableObject getViewablePlayer();


    /**
     * Returns all GameObjects except player as
     * ViewableObjects in a list
     *
     * @return list of ViewableObjects
     */
    public List<ViewableObject> getObjectList();


    /**
     * Returns the current speed of the player
     *
     * @return speed of player as int
     */
    public int getMovementSpeed();

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
    int getPlayerHealth();

    int getCountDown();

    /**
     * Get the direction the player is moving in.
     * -1 = left, 1 = right and 0 = idle
     * @return
     */
    int getMovementDirection();
}

