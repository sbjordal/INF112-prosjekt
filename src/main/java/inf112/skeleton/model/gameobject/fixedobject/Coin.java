package inf112.skeleton.model.gameobject.fixedobject;

import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.model.gameobject.Scorable;
import inf112.skeleton.model.gameobject.Transform;

/**
 * Class that increases the score each time a player collides with an object
 *
 */

public class Coin extends Item implements Scorable { // TODO: implementer logikken her
    private Transform transform;
    private Texture texture;
    private int scoreValue;

    public Coin(Transform transform, Texture texture) {
        super(transform, texture);

    }

    @Override
    public int getScoreValue() {
        return scoreValue;
    }
}
