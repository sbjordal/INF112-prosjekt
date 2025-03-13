package inf112.skeleton.model.gameobject.fixedobject.item;

import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.gameobject.Transform;

public class ItemFactory {
    // TODO: Revisjon av CoinFactory
    // TODO: Må oppdatere UML-diagram til å inneholde CoinFactory


    public static Coin createCoin(float x, float y){

        int DIAMETER = 30;
        Vector2 pos = new Vector2(x, y);
        Vector2 size = new Vector2(DIAMETER, DIAMETER);
        Transform transform = new Transform(pos, size);

        return new Coin(transform);
    }

    public static Mushroom createMushroom(float x, float y){

        int width = 50;
        int height = (int) (width * 1.0625f);
        Vector2 pos = new Vector2(x, y);
        Vector2 size = new Vector2(width, height);
        Transform transform = new Transform(pos, size);

        return new Mushroom(transform);
    }
}

