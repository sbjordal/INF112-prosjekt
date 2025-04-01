package inf112.skeleton.model.gameobject.mobileobject.actor.enemy;

import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.gameobject.Transform;

/**
 * A factory class responsible for creating {@link Enemy} objects.
 * // TODO: Legge til Enemy type i argument og konstruktør og legge til rette for å kunne ha
 *          ulike create-metoder for ulike enemy typer senere.
 */
public class EnemyFactory {

    /**
     * Creates a new Snail with the specific arguments defined by its enemy type.
     *
     * @param x             X-position of the enemy
     * @param y             Y-position of the enemy
     * @param enemyType     the specific enemy type
     * @return A new instance of Snail
     */
    public static Snail createSnail(float x, float y, EnemyType enemyType) {

        Vector2 pos = new Vector2(x, y);
        Vector2 size = new Vector2(enemyType.width, enemyType.height);
        Transform transform = new Transform(pos, size);

        return new Snail(enemyType.health, enemyType.movementSpeed, enemyType.objectScore,
                enemyType.damage, transform);
    }

    /**
     * Creates a new Leopard with the specific arguments defined by its enemy type.
     *
     * @param x             X-position of the enemy
     * @param y             Y-position of the enemy
     * @param enemyType     the specific enemy type
     * @return A new instance of Leopard
     */
    public static Leopard createLeopard(float x, float y, EnemyType enemyType) {

        Vector2 pos = new Vector2(x, y);
        Vector2 size = new Vector2(enemyType.width, enemyType.height);
        Transform transform = new Transform(pos, size);

        return new Leopard(enemyType.health, enemyType.movementSpeed, enemyType.objectScore,
                enemyType.damage, transform);
    }
}
