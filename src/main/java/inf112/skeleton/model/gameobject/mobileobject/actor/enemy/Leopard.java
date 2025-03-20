package inf112.skeleton.model.gameobject.mobileobject.actor.enemy;

import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.gameobject.Transform;

public class Leopard extends Enemy {
    /**
     * Creates a new Enemy with the specified movement speed, object score, damage and transform.
     *
     * @param movementSpeed The rate of which the Enemy moves horizontally.
     * @param objectScore   The score points obtained by defeating the Enemy.
     * @param damage        The amount of damage the Enemy will inflict.
     * @param transform     The initial transform of the Enemy.
     */
    public Leopard(int movementSpeed, int objectScore, int damage, Transform transform) {
        super(movementSpeed, objectScore, damage, transform);
    }

    /**
     * Moves the Enemy in a predefined movement pattern.
     */
    @Override
    public void move(Vector2 newPosition) { // TODO: Trenger tester for enemy movement

    }
}
