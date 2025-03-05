package inf112.skeleton.view;
import inf112.skeleton.model.GameState;
import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.model.gameobject.Transform;

public interface ViewableWorldModel {

    /**
     * Returns the texture image of the player
     *
     * @return texture-image
     */
    Texture getPlayerTexture();

    /**
     * Returns the players position in x and y,
     * and its size in height and width
     *
     * @return Transform enum of Position and Size
     */
    Transform getPlayerTransform();

    /**
     * Returns the texture image of the enemy
     *
     * @return texture-image
     */
    Texture getEnemyTexture();

    /**
     * Returns the enemys position in x and y,
     * and its size in height and width
     *
     * @return Transform enum of Position and Size
     */
    Transform getEnemyTransform();

    /**
     * Returns the texture image of the coin
     *
     * @return texture-image
     */
    Texture getCoinTexture();

    /**
     * Returns the coins position in x and y,
     * and its size in height and width
     *
     * @return Transform enum of Position and Size
     */
    Transform getCoinTransform();

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
     * Tell us where the fixed positions are
     * and gives us their textures
     *
     * @return position and textures-images
     */
    //List<ObjectProperties> getFixedObjectProperties();

    /**
     * Tell us where the enemy's positions are
     * and gives us their sprites
     *
     * @return position and sprite-images
     */
    // List<Object> getEnemyObjects();

    /**
     * Tells us how many coins the player has aquired so far
     *
     * @return the coins scored
     */
    int getCoinScore();


}

