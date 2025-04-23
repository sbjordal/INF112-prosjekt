package inf112.starhunt.model.gameobject.mobileobject.actor.enemy;

import inf112.starhunt.model.gameobject.Transform;

/**
 * Represents the Leopard enemy type.
 */
public final class Leopard extends Enemy {
    /**
     * Creates a new Enemy with the specified movement speed, object score, damage and transform.
     *
     * @param movementSpeed The rate of which the Enemy moves horizontally.
     * @param objectScore   The score points obtained by defeating the Enemy.
     * @param damage        The amount of damage the Enemy will inflict.
     * @param transform     The initial transform of the Enemy.
     */
    public Leopard(int lives, int movementSpeed, int objectScore, int damage, Transform transform) {
        super(lives, movementSpeed, objectScore, damage, transform);

        this.direction = Direction.LEFT;
    }
}
