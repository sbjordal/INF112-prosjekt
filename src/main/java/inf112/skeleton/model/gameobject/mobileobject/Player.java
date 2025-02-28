package inf112.skeleton.model.gameobject.mobileobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.model.gameobject.Position;
import inf112.skeleton.model.gameobject.Size;
import inf112.skeleton.model.gameobject.Transform;

/**
 * Represents the user-controlled actor in the game.
 *
 * @author Eivind H. Naasen
 */
final public class Player extends Actor {
    // TODO: revisjon - bestemmer nå bilde, plassering, størrelse på player her inne i player istedenfor i WorldModel
    private final static Texture PLAYER_TEXTURE = new Texture(Gdx.files.internal("sprite.png"));
    private final static Position START_POSITION = new Position(380, 70);
    private final static Size SIZE = new Size(50, 100);
    private final static Transform PLAYER_TRANSFORM = new Transform(START_POSITION, SIZE);
    /**
     * Creates a new Player with the specified health and movement speed.
     *
     * @param health        The initial health of the actor.
     * @param movementSpeed The movement speed of the actor.
     */
    public Player(int health, int movementSpeed) {
        super(health, movementSpeed, PLAYER_TRANSFORM, PLAYER_TEXTURE);
    }

    public void jump() {
        // TODO: implement when Box2D is integrated.
    }
}
