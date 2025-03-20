package inf112.skeleton.model.gameobject.mobileobject.actor.enemy;

import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.gameobject.Scorable;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.gameobject.mobileobject.actor.Actor;

/**
 * Represents all enemy types.
 * An enemy type is any {@link GameObject} that inflicts damage on the player.
 */
public abstract class Enemy extends Actor implements Scorable {
    final private int objectScore;
    final private int damage;

    /**
     * Creates a new Enemy with the specified movement speed, object score, damage and transform.
     *
     * @param movementSpeed The rate of which the Enemy moves horizontally.
     * @param objectScore   The score points obtained by defeating the Enemy.
     * @param damage        The amount of damage the Enemy will inflict.
     * @param transform     The initial transform of the Enemy.
     */
    public Enemy(int movementSpeed, int objectScore, int damage, Transform transform) {
        super(1, movementSpeed, transform);

        this.objectScore = objectScore;
        this.damage = damage;
    }

    /**
     * Returns the amount of damage the Enemy can inflict.
     *
     * @return The damage value as an integer.
     */
    public int getDamage() {
        return damage;
    }

    @Override
    public int getObjectScore() {
        return objectScore;
    }

    /**
     * Moves the Enemy in a predefined movement pattern.
     */
    abstract public void moveEnemy();
}
