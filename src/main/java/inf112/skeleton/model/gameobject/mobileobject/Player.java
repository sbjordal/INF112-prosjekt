package inf112.skeleton.model.gameobject.mobileobject;

import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.model.gameobject.Transform;

/**
 * Represents the user-controlled actor in the game.
 *
 * @author Eivind H. Naasen
 */
final public class Player extends Actor {
    private Transform transform;
    private Texture texture;
    /**
     * Creates a new Player with the specified health and movement speed.
     *
     * @param health        The initial health of the actor.
     * @param movementSpeed The movement speed of the actor.
     */
    public Player(int health, int movementSpeed, Transform transform, Texture texture) {
        super(health, movementSpeed, transform, texture);
    }

    public void jump() {
        // TODO: implement when Box2D is integrated.
    }
}
