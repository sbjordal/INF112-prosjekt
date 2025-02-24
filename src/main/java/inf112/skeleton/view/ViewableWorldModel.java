package inf112.skeleton.view;
import inf112.skeleton.model.GameState;
import inf112.skeleton.model.gameobject.Position;
import com.badlogic.gdx.graphics.Texture;

import java.util.List;

public interface ViewableWorldModel {

    /**
     * Returns the sprite image of the player
     *
     * @return sprite-image
     */
    Texture getPlayerTexture();


    /**
     * Tell us where the fixed positions are
     * and gives us their sprites
     *
     * @return position and sprite-images
     */
    //List<Object> getFixedObjectTextures();

    /**
     * Tell us where the enemy's positions are
     * and gives us their sprites
     *
     * @return position and sprite-images
     */
   // List<Object> getEnemyObjects();


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

