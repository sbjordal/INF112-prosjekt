package inf112.starhunt.model.gameobject.mobileobject.actor.enemy;

import com.badlogic.gdx.math.Vector2;
import inf112.starhunt.model.gameobject.Transform;
import inf112.starhunt.model.gameobject.fixedobject.item.Item;
import inf112.starhunt.model.gameobject.fixedobject.item.ItemFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * A factory class responsible for creating {@link Enemy} objects.
 */
public class EnemyFactory {


    static Map<String, BiFunction<Float, Float, Enemy>> registry = new HashMap<>();
    static {
        registry.put("snail", EnemyFactory::createSnail);
        registry.put("leopard", EnemyFactory::createLeopard);
    }

    public static Enemy createEnemy(String enemyName, float x, float y) {
        return registry.get(enemyName).apply(x, y);
    }
    /**
     * Creates a new Snail with the specific arguments defined by its enemy type.
     *
     * @param x             X-position of the enemy
     * @param y             Y-position of the enemy
     * @return A new instance of Snail
     */
    public static Snail createSnail(float x, float y) {

        Vector2 pos = new Vector2(x, y);
        Vector2 size = new Vector2(EnemyType.SNAIL.width, EnemyType.SNAIL.height);
        Transform transform = new Transform(pos, size);

        return new Snail(EnemyType.SNAIL.health, EnemyType.SNAIL.movementSpeed, EnemyType.SNAIL.objectScore,
                EnemyType.SNAIL.damage, transform);
    }

    /**
     * Creates a new Leopard with the specific arguments defined by its enemy type.
     *
     * @param x             X-position of the enemy
     * @param y             Y-position of the enemy
     * @return A new instance of Leopard
     */
    public static Leopard createLeopard(float x, float y) {

        Vector2 pos = new Vector2(x, y);
        Vector2 size = new Vector2(EnemyType.LEOPARD.width, EnemyType.LEOPARD.height);
        Transform transform = new Transform(pos, size);

        return new Leopard(EnemyType.LEOPARD.health, EnemyType.LEOPARD.movementSpeed, EnemyType.LEOPARD.objectScore,
                EnemyType.LEOPARD.damage, transform);
    }
}
