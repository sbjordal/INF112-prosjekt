package inf112.skeleton.model.gameobject.mobileobject.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.model.gameobject.Position;
import inf112.skeleton.model.gameobject.Scorable;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.Transform;

/**
 * Represents all enemy types.
 * An enemy type is any {@link GameObject} that inflicts damage on the player.
 */
public class Enemy extends Actor implements Scorable {
    private final static Texture ENEMY_TEXTURE = new Texture(Gdx.files.internal("enemy.png"));
    final private int objectScore;
    final private int damage;

    /**
     * Creates a new Enemy with the specified health, movement speed, object score, damage and transform.
     *
     * @param health        The initial health of the Enemy.
     * @param movementSpeed The rate of which the Enemy moves horizontally.
     * @param objectScore   The score points obtained by defeating the Enemy.
     * @param damage        The amount of damage the Enemy will inflict.
     * @param transform     The initial transform of the Enemy.
     */
    public Enemy(int health, int movementSpeed, int objectScore, int damage, Transform transform) {
        super(health, movementSpeed, transform, ENEMY_TEXTURE);

        this.objectScore = objectScore;
        this.damage = damage;
    }

    /**
     * Returns the amount of damage the Enemy can inflict.
     *
     * @return The damage value as an integer.
     */
    int getDamage() {
        return damage;
    }

    /**
     * Moves the Enemy in a predefined movement pattern.
     */
    @Override
    public void move(Position newPosition) {
        // TODO: implement me :)
    }

    @Override
    public int getObjectScore() {
        return objectScore;
    }


}
