package inf112.starhunt.model.gameobject;

import inf112.starhunt.model.gameobject.fixedobject.FixedObjectFactory;
import inf112.starhunt.model.gameobject.fixedobject.Ground;
import inf112.starhunt.model.gameobject.fixedobject.item.Banana;
import inf112.starhunt.model.gameobject.fixedobject.item.Coin;
import inf112.starhunt.model.gameobject.fixedobject.item.Star;
import inf112.starhunt.model.gameobject.mobileobject.MobileObjectFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * A utility class for creating game objects with preset sizes and positions.
 * <p>
 * This factory centralizes the instantiation logic for objects like {@link Ground},
 * {@link Coin}, {@link Banana}, and {@link Star}, ensuring consistent sizing and
 * transform setup across the game world.
 * <p>
 * All methods are static and return fully initialized game objects ready for use in the game model.
 */
public class GameObjectFactory {

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
     * Creates a {@link GameObject} instance based on a string identifier and a position.
     *
     * @param gameObject the string identifier for the object to create
     * @param x the X-coordinate of the object's position
     * @param y the Y-coordinate of the object's position
     * @return a new {@link GameObject} instance positioned at (x, y)
     * @throws IllegalArgumentException if the identifier is not recognized in the factory registry
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
