package inf112.skeleton.model.gameobject.fixedobject.item;

import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.gameobject.Transform;

public class CoinFactory {
    // TODO: Revisjon av CoinFactory
    // TODO: Må oppdatere UML-diagram til å inneholde CoinFactory


    public static Coin createCoin(float x, float y){

        int COINDIAMETER = 30;
        Vector2 pos = new Vector2(x, y);
        Vector2 size = new Vector2(COINDIAMETER, COINDIAMETER);
        Transform transform = new Transform(pos, size);

        return new Coin(transform);
    }
}

