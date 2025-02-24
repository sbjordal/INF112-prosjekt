package inf112.skeleton.model.gameobject.mobileobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.model.gameobject.ViewableObject;

/**
 * Represents the user-controlled actor in the game.
 *
 * @author Eivind H. Naasen
 */
final public class Player extends Actor implements ViewableObject {
    /**
     * Creates a new Player with the specified health and movement speed.
     *
     * @param health        The initial health of the actor.
     * @param movementSpeed The movement speed of the actor.
     */
    public Player(int health, int movementSpeed) {
        super(health, movementSpeed);
    }

    public void jump() {
        // TODO: implement when Box2D is integrated.
    }

    @Override
    public Texture getTexture() {
        return new Texture(Gdx.files.internal("sprite.png"));
    }
}
