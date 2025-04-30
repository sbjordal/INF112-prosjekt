package inf112.starhunt.model.gameobject.mobileobject.actor.enemy;

import com.badlogic.gdx.math.Vector2;
import inf112.starhunt.model.gameobject.Transform;

/**
 * Represents the Leopard enemy type.
 */
public final class Leopard extends Enemy {
    private final static int LIVES = 2;
    private final static int MOVEMENT_SPEED = 140;
    private final static int OBJECT_SCORE = 30;
    private final static int DAMAGE = 1;
    private final static float DIAMETER = 70;


    /**
     * Creates a new Enemy with the specified movement speed, object score, damage and transform.
     *
     * @param position     The initial position of the Enemy.
     */
    public Leopard(Vector2 position) {
        super(LIVES, MOVEMENT_SPEED, OBJECT_SCORE, DAMAGE, new Transform(position, new Vector2(DIAMETER, DIAMETER)));

        setMovementDirection(-1);
    }

}
