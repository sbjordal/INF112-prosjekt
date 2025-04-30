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
        registry.put("ground", FixedObjectFactory::createGround);
        registry.put("banana", FixedObjectFactory::createBanana);
        registry.put("star", FixedObjectFactory::createStar);
        registry.put("coin", FixedObjectFactory::createCoin);
    }

    public static Ground createGround(float x, float y){

        Transform transform = TransformUtils.createTransformForObjects(x, y, 50, 50);

        return new Ground(transform);
    }

    public static Coin createCoin(float x, float y){

        int DIAMETER = 30;
        Transform transform = TransformUtils.createTransformForObjects(x, y, DIAMETER, DIAMETER);

        return new Coin(transform);
    }

    public static Banana createBanana(float x, float y){
        Transform transform = TransformUtils.createTransformForObjects(x, y, 50, 53);

        return new Banana(transform);
    }

    public static Star createStar(float x, float y){
        Transform transform = TransformUtils.createTransformForObjects(x, y, 72, 69);

        return new Star(transform);
    }

}

