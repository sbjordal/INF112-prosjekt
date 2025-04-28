package inf112.starhunt.model.gameobject.fixedobject;

import com.badlogic.gdx.math.Vector2;
import inf112.starhunt.model.gameobject.Transform;
import inf112.starhunt.model.gameobject.TransformUtils;
import inf112.starhunt.model.gameobject.fixedobject.item.Banana;
import inf112.starhunt.model.gameobject.fixedobject.item.Coin;
import inf112.starhunt.model.gameobject.fixedobject.item.Star;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class FixedObjectFactory {
    static Map<String, BiFunction<Float, Float, FixedObject>> registry = new HashMap<>();
    static {
        registry.put("ground", FixedObjectFactory::createGround);
        registry.put("banana", FixedObjectFactory::createBanana);
        registry.put("star", FixedObjectFactory::createStar);
        registry.put("coin", FixedObjectFactory::createCoin);
    }

    public static FixedObject createFixedObject(String fixedObject, float x, float y) {
        return registry.get(fixedObject).apply(x, y);
    }

    public static Ground createGround(float x, float y){

        Transform transform = TransformUtils.createTransformForObjects(50, 50, x, y);

        return new Ground(transform);
    }

    public static Coin createCoin(float x, float y){

        int DIAMETER = 30;
        Transform transform = TransformUtils.createTransformForObjects(DIAMETER, DIAMETER, x, y);

        return new Coin(transform);
    }

    public static Banana createBanana(float x, float y){
        Transform transform = TransformUtils.createTransformForObjects(50, 53, x, y);

        return new Banana(transform);
    }

    public static Star createStar(float x, float y){
        Transform transform = TransformUtils.createTransformForObjects(72, 69, x, y);

        return new Star(transform);
    }

}

