package inf112.skeleton.view;
import inf112.skeleton.model.GameState;
import inf112.skeleton.model.gameobject.Position;
import com.badlogic.gdx.graphics.Texture;

import java.util.List;

public interface ViewableWorldModel {

    /**
     * Returns the sprite of the enemy
     *
     * @return an enemy's sprite-image
     */
    Texture getEnemySprite();

    /**
     * Returns the sprite of the player
     *
     * @return the players sprite-image
     */
    Texture getPlayerSprite();


    /**
     *Tell us where the fixed objects are
     *
     * @return positions
     */
    List<Position> getFixedObjectPosition();

    /**
     * Tells us where the enemy are
     *
     * @return the positions of the enemy
     */
    List<Position> getEnemysPosition();


    /**
     * Tells us where the player is
     *
     * @return the position of the player
     */
    Position getPlayerPosition();

    /**
     * Tells us the state of the game
     *
     * @return the state of the game
     */
    GameState getGameState();


    /**
     * Tells us the points the player has aquired
     *
     * @return the points scored
     */
    int getScore();


}

