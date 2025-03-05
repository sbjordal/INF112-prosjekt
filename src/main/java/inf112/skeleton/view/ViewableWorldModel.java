package inf112.skeleton.view;
import inf112.skeleton.model.GameState;
import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.model.gameobject.Transform;
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
     * // TODO: revisjon - Vil vi ha det slik?
     * Returns all GameObjects except player as
     * ViewableObjects in a list
     *
     * @return list of ViewableObjects
     */
    public List<ViewableObject> getObjectList();


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
    int getCoinScore();


}

