package inf112.starhunt.model.gameobject;

/**
 * This interface is for game objects that can contribute to increase the player's score.
 * Implemented in classes like {@code Enemy} or {@code Coin} where it is necessary to know
 * how many points they are worth when collected or interacted with.
 */
public interface Scorable {

    /**
     * Get the current score value
     * @return score value
     */
    int getObjectScore();
}
