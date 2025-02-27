package inf112.skeleton.model.gameobject.mobileobject;

import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.model.gameobject.Scorable;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.Transform;

/**
 * Represents all enemy types.
 * An enemy type is any {@link GameObject} that inflicts damage on the player.
 *
 * @author Eivind H. Naasen
 */
public class Enemy extends Actor implements Scorable {
    final private int objectScore;
    final private int damage;
    private Transform transform;
    private Texture sprite;

    /**
     * Creates a new Enemy with the specified health, movement speed, score value and damage.
     *
     * @param health        The initial health of the actor.
     * @param movementSpeed The movement speed of the actor.
     * @param objectScore    The score points obtained by defeating this enemy.
     * @param damage        The amount of damage this enemy can inflict.
     */
    public Enemy(int health, int movementSpeed, int objectScore, int damage, Transform transform, Texture sprite) {
        super(health, movementSpeed, transform, sprite);

        this.objectScore = objectScore;
        this.damage = damage;
    }

    int getDamage() { return damage; }

    /**
     * Moves this enemy in a predefined movement pattern.
     */
    public void moveEnemy() {
        // TODO: implement this when the enemy type is conceptually defined.
    }

    @Override
    public int getObjectScore() {
        return objectScore;
    }


}
