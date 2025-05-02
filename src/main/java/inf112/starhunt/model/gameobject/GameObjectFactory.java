package inf112.starhunt.model.gameobject;

import inf112.starhunt.model.gameobject.fixedobject.FixedObjectFactory;
import inf112.starhunt.model.gameobject.mobileobject.MobileObjectFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * A Factory that can create all types of {@link GameObject}s.
 */
public class GameObjectFactory {

    /**
     * TODO: javadoc
     */
    static Map<String, BiFunction<Float, Float, GameObject>> registry = new HashMap<>();
    static {
        registry.put("banana", FixedObjectFactory::createBanana);
        registry.put("star", FixedObjectFactory::createStar);
        registry.put("coin", FixedObjectFactory::createCoin);
        registry.put("player", MobileObjectFactory::createPlayer);
        registry.put("snail", MobileObjectFactory::createSnail);
        registry.put("leopard", MobileObjectFactory::createLeopard);
    }

    /**
     * TODO: javadoc
     *
     * @param gameObject
     * @param x
     * @param y
     * @return
     */
    public static GameObject createGameObject(String gameObject, float x, float y) {
        GameObject object;

        if (gameObject.startsWith("ground_")) {
            String alteration = gameObject.substring("ground_".length());
            object = FixedObjectFactory.createGround(x, y, alteration);
        } else {
            object = registry.get(gameObject).apply(x, y);
        }

        if (object == null) {
            throw new IllegalArgumentException("Unknown GameObject type: " + gameObject);
        }

        return object;
    }
}
