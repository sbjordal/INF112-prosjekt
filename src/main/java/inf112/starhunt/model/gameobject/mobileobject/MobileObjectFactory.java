package inf112.starhunt.model.gameobject.mobileobject;

import com.badlogic.gdx.math.Vector2;
import inf112.starhunt.model.gameobject.Transform;
import inf112.starhunt.model.gameobject.mobileobject.actor.Player;
import inf112.starhunt.model.gameobject.mobileobject.actor.enemy.Enemy;
import inf112.starhunt.model.gameobject.mobileobject.actor.enemy.EnemyType;
import inf112.starhunt.model.gameobject.mobileobject.actor.enemy.Leopard;
import inf112.starhunt.model.gameobject.mobileobject.actor.enemy.Snail;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * A factory class responsible for creating {@link Enemy} objects.
 */
public class MobileObjectFactory {


    static Map<String, BiFunction<Float, Float, MobileObject>> registry = new HashMap<>();
    static {
        registry.put("player", MobileObjectFactory::createPlayer);
        registry.put("snail", MobileObjectFactory::createSnail);
        registry.put("leopard", MobileObjectFactory::createLeopard);
    }


    public static MobileObject createMobileObject(String mobileObject, float x, float y) {
        return registry.get(mobileObject).apply(x, y);
    }

    public static MobileObject createPlayer(Float x, Float y) {
        Vector2 size = new Vector2(40, 80);
        Vector2 position = new Vector2(x, y);
        Transform transform = new Transform(position, size);
        return new Player(3, 350, transform);
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
