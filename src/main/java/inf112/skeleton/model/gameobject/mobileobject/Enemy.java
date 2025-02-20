package inf112.skeleton.model.gameobject.mobileobject;

import inf112.skeleton.model.gameobject.Scorable;
import inf112.skeleton.model.gameobject.GameObject;

/**
 * Represents all enemy types.
 * An enemy type is any {@link GameObject} that inflicts damage on the player.
 *
 * @author Eivind H. Naasen
 */
public class Enemy extends Actor implements Scorable {
    final private int scoreValue;
    final private int damage;

    /**
     * Creates a new Enemy with the specified health, movement speed, score value and damage.
     *
     * @param health        The initial health of the actor.
     * @param movementSpeed The movement speed of the actor.
     * @param scoreValue    The score points obtained by defeating this enemy.
     * @param damage        The amount of damage this enemy can inflict.
     */
    public Enemy(int health, int movementSpeed, int scoreValue, int damage) {
        super(health, movementSpeed);

        this.scoreValue = scoreValue;
        this.damage = damage;
    }

    int getDamage() { return damage; }

    /**
     * Moves this enemy in a predefined movement pattern.
     */
    public void moveEnemy() {
        // TODO: implement this when the enemy type is conceptually defined.
    }

/* TODO: uncomment when Scorable interface is integrated.
    @override
    public int getScoreValue() {
        return scoreValue;
    }
*/

}
