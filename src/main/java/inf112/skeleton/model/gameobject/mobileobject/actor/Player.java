package inf112.skeleton.model.gameobject.mobileobject.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.Transform;

/**
 * Represents the user-controlled actor in the game.
 * The user-controlled actor is unique and is defined as the only {@link GameObject}
 * that responds to user-input.
 */
final public class Player extends Actor {
    private final static Texture PLAYER_TEXTURE = new Texture(Gdx.files.internal("player/idle/i1.png"));
    private final static Vector2 START_POSITION = new Vector2(380, 102);
    private final static Vector2 SIZE = new Vector2(40, 80);
    private final static Transform PLAYER_TRANSFORM = new Transform(START_POSITION, SIZE);

    /**
     * Creates a new Player with the specified health and movement speed.
     *
     * @param health        The initial health of the Player.
     * @param movementSpeed The rate of which the Player moves horizontally.
     */
    public Player(int health, int movementSpeed) {
        super(health, movementSpeed, PLAYER_TRANSFORM, PLAYER_TEXTURE);
    }

    public void jump() {
        // TODO: implement me :)
    }

    @Override
    public int getMovementSpeed() {
        return super.getMovementSpeed();
    }
}
