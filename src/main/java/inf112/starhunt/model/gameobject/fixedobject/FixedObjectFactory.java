package inf112.starhunt.model.gameobject.fixedobject;

import inf112.starhunt.model.gameobject.Transform;
import inf112.starhunt.model.gameobject.TransformUtils;
import inf112.starhunt.model.gameobject.fixedobject.item.Banana;
import inf112.starhunt.model.gameobject.fixedobject.item.Coin;
import inf112.starhunt.model.gameobject.fixedobject.item.Star;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Factory that creates {@link FixedObject}s so that only x, y position is needed as argument for creating these objects.
 */
public class FixedObjectFactory {
    static Map<String, BiFunction<Float, Float, FixedObject>> registry = new HashMap<>();
    static {
        registry.put("banana", FixedObjectFactory::createBanana);
        registry.put("star", FixedObjectFactory::createStar);
        registry.put("coin", FixedObjectFactory::createCoin);
    }

    /**
     * Creates a new {@link Ground} object at the specified position with a given alteration type.
     *
     * @param x the X-coordinate of the ground object
     * @param y the Y-coordinate of the ground object
     * @param alteration a string representing a visual or functional variation of the ground (e.g., texture type)
     * @return a new {@link Ground} instance positioned at (x, y)
     */
    public static Ground createGround(float x, float y, String alteration){
        Transform transform = TransformUtils.createTransformForObjects(x, y, 50, 50);

        return new Ground(transform, alteration);
    }

    /**
     * Creates a new {@link Coin} object at the specified position.
     *
     * @param x the X-coordinate of the coin
     * @param y the Y-coordinate of the coin
     * @return a new {@link Coin} instance positioned at (x, y)
     */
    public static Coin createCoin(float x, float y){
        int DIAMETER = 30;
        Transform transform = TransformUtils.createTransformForObjects(x, y, DIAMETER, DIAMETER);

        return new Coin(transform);
    }

    /**
     * Creates a new {@link Banana} power-up object at the specified position.
     *
     * @param x the X-coordinate of the banana
     * @param y the Y-coordinate of the banana
     * @return a new {@link Banana} instance positioned at (x, y)
     */
    public static Banana createBanana(float x, float y){
        Transform transform = TransformUtils.createTransformForObjects(x, y, 50, 53);

        return new Banana(transform);
    }

    /**
     * Creates a new {@link Star} collectible or objective object at the specified position.
     *
     * @param x the X-coordinate of the star
     * @param y the Y-coordinate of the star
     * @return a new {@link Star} instance positioned at (x, y)
     */
    public static Star createStar(float x, float y){
        Transform transform = TransformUtils.createTransformForObjects(x, y, 72, 69);

        return new Star(transform);
    }

}
