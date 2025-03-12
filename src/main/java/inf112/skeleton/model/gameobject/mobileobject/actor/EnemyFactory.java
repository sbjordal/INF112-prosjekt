package inf112.skeleton.model.gameobject.mobileobject.actor;

import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.gameobject.Transform;

/**
 * A factory class responsible for creating {@link Enemy} objects.
 * // TODO: Revisjon av factory (og bruk i create, egen todo på dette)
 * // TODO: Må oppdatere plassering i UML-diagram, flyttet EnemyFactory fra modellen inn hit (i actor)
 * // TODO: Legge til Enemy type i argument og konstruktør og legge til rette for å kunne ha
 *          ulike create-metoder for ulike enemy typer senere.
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
    public static Enemy createEnemy(float x, float y) {

        float width = 50;
        float height = 50;
        int health = 1;
        int movementSpeed = 1;
        int objectScore = 1;
        int damage = 1;

        Vector2 position = new Vector2(x, y);
        Vector2 size = new Vector2(width, height);
        Transform transform = new Transform(position, size);

        return new Enemy(health, movementSpeed, objectScore, damage, transform);
    }
}
