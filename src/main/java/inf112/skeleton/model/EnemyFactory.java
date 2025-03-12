package inf112.skeleton.model;

import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.gameobject.mobileobject.actor.Enemy;

/**
 * A factory class responsible for creating {@link Enemy} objects.
 */
public class EnemyFactory {

    /**
     * Creates a new Enemy
     *
     * @param x             X-position of the enemy
     * @param y             Y-position of the enemy
     * @param width         Enemy width
     * @param height        Enemy height
     * @param health        Health of the enemy: Number of lives
     * @param movementSpeed Enemy movement speed
     * @param objectScore   The points you gain by defeating the enemy
     * @param damage        The damage the enemy gives
     * @return A new instance of Enemy
     */
    public static Enemy createEnemy(float x, float y, float width, float height,
                                    int health, int movementSpeed, int objectScore, int damage) {
        Vector2 position = new Vector2(x, y);
        Vector2 size = new Vector2(width, height);
        Transform transform = new Transform(position, size);

        return new Enemy(health, movementSpeed, objectScore, damage, transform);
    }
}
