package inf112.starhunt.model.gameobject.fixedobject;

import com.badlogic.gdx.math.Vector2;
import inf112.starhunt.model.gameobject.Transform;
import inf112.starhunt.model.gameobject.fixedobject.item.Banana;
import inf112.starhunt.model.gameobject.fixedobject.item.Coin;
import inf112.starhunt.model.gameobject.fixedobject.item.Star;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

// TODO: lag hjelpefunksjon på innholdet til Coin, Banana og Star for å redusere duplikat kode.
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

        Vector2 size = new Vector2(50, 50);
        Vector2 position = new Vector2(x, y);
        Transform transform = new Transform(position, size);

        return new Ground(transform);
    }

    public static Coin createCoin(float x, float y){

        int DIAMETER = 30;
        Vector2 pos = new Vector2(x, y);
        Vector2 size = new Vector2(DIAMETER, DIAMETER);
        Transform transform = new Transform(pos, size);

        return new Coin(transform);
    }

    public static Banana createBanana(float x, float y){

        int width = 50;
        int height = 53;
        Vector2 pos = new Vector2(x, y);
        Vector2 size = new Vector2(width, height);
        Transform transform = new Transform(pos, size);

        return new Banana(transform);
    }

    public static Star createStar(float x, float y){

        int width = 72;
        int height = 69;
        Vector2 pos = new Vector2(x, y);
        Vector2 size = new Vector2(width, height);
        Transform transform = new Transform(pos, size);

        return new Star(transform);
    }
}

