package inf112.starhunt.model.gameobject.mobileobject;

import com.badlogic.gdx.math.Vector2;
import inf112.starhunt.model.gameobject.Transform;
import inf112.starhunt.model.gameobject.TransformUtils;
import inf112.starhunt.model.gameobject.mobileobject.actor.Player;
import inf112.starhunt.model.gameobject.mobileobject.actor.enemy.Enemy;
import inf112.starhunt.model.gameobject.mobileobject.actor.enemy.Leopard;
import inf112.starhunt.model.gameobject.mobileobject.actor.enemy.Snail;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * A factory class responsible for creating {@link MobileObject} objects.
 */
public class MobileObjectFactory {
    static Map<String, BiFunction<Float, Float, MobileObject>> registry = new HashMap<>();
    static {
        registry.put("player", MobileObjectFactory::createPlayer);
        registry.put("snail", MobileObjectFactory::createSnail);
        registry.put("leopard", MobileObjectFactory::createLeopard);
    }

    public static MobileObject createPlayer(Float x, Float y) {
        Transform transform = TransformUtils.createTransformForObjects(x, y, 40, 80);
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
        return new Snail(new Vector2(x,y));
    }

    /**
     * Creates a new Leopard with the specific arguments defined by its enemy type.
     *
     * @param x             X-position of the enemy
     * @param y             Y-position of the enemy
     * @return A new instance of Leopard
     */
    public static Leopard createLeopard(float x, float y) {
        return new Leopard(new Vector2(x,y));
    }
}
