package inf112.skeleton.model.gameobject.fixedobject.item;

import inf112.skeleton.model.gameobject.Scorable;
import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.gameobject.mobileobject.actor.Player;

/**
 * Represents an object that increases the total score when picked up by {@link Player}.
 */
public class Coin extends Item implements Scorable {

    private final static int COIN_VALUE = 1;
    private final int objectScore;

    /**
     * Creates a new Coin with the specified transform.
     *
     * @param transform The fixed position and size of the Coin.
     */
    public Coin(Transform transform) {
        super(transform);
        objectScore = COIN_VALUE;
    }

    @Override
    public int getObjectScore() {
        return objectScore;
    }
}
