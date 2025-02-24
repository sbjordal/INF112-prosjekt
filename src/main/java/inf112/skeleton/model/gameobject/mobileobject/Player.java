package inf112.skeleton.model.gameobject.mobileobject;

import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.model.gameobject.Position;

/**
 * Represents the user-controlled actor in the game.
 *
 * @author Eivind H. Naasen
 */
final public class Player extends Actor {
    private Position pos;
    private Texture sprite;
    /**
     * Creates a new Player with the specified health and movement speed.
     *
     * @param health        The initial health of the actor.
     * @param movementSpeed The movement speed of the actor.
     */
    public Player(int health, int movementSpeed, Position pos, Texture sprite) {
        super(health, movementSpeed, pos, sprite);
    }

    public void jump() {
        // TODO: implement when Box2D is integrated.
    }
}
