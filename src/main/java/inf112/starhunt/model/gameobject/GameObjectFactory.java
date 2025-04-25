package inf112.starhunt.model.gameobject;

import com.badlogic.gdx.math.Vector2;
import inf112.starhunt.model.gameobject.fixedobject.FixedObjectFactory;
import inf112.starhunt.model.gameobject.fixedobject.item.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class GameObjectFactory {


    // TODO: lag hjelpefunksjon på innholdet til Coin, Banana og Star for å redusere duplikat kode.
        static Map<String, BiFunction<Float, Float, Item>> registry = new HashMap<>();
        static {
            registry.put("banana", FixedObjectFactory::createBanana);
            registry.put("star", FixedObjectFactory::createStar);
            registry.put("coin", FixedObjectFactory::createCoin);
        }

        public static Item createItem(String item, float x, float y) {
            return registry.get(item).apply(x, y);
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
