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
     * Creates a new Enemy of the given enemy type
     *
     * @param x             X-position of the enemy
     * @param y             Y-position of the enemy
     * @return A new instance of Enemy
     */
    public static Enemy createEnemy(float x, float y, EnemyType enemyType) {

        Vector2 pos = new Vector2(x, y);
        Vector2 size = new Vector2(enemyType.width, enemyType.height);
        Transform transform = new Transform(pos, size);

        return new Enemy(enemyType.health, enemyType.movementSpeed, enemyType.objectScore,
                enemyType.damage, transform);
    }
}
