package inf112.skeleton.app.model.gameobject.fixedobject.item;

import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.gameobject.Scorable;
import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.gameobject.fixedobject.item.Coin;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CoinTest {

    @Test
    void testCoinCreationNotNull() {
        Transform transform = new Transform(new Vector2(10, 20), new Vector2(30, 30));
        Coin coin = new Coin(transform);

        assertNotNull(coin, "Coin should not be null");
    }

    @Test
    void testCoinHasCorrectPositionAndSize() {
        float x = 10f, y = 20f;
        float width = 30f, height = 30f;
        Transform transform = new Transform(new Vector2(x, y), new Vector2(width, height));
        Coin coin = new Coin(transform);

        assertEquals(x, coin.getTransform().getPos().x, 0.001, "X position should match");
        assertEquals(y, coin.getTransform().getPos().y, 0.001, "Y position should match");
        assertEquals(width, coin.getTransform().getSize().x, "Coin width should be 30");
        assertEquals(height, coin.getTransform().getSize().y, "Coin height should be 30");
    }

    @Test
    void testCoinHasCorrectScore() {
        Transform transform = new Transform(new Vector2(0, 0), new Vector2(30, 30));
        Coin coin = new Coin(transform);

        assertEquals(5, coin.getObjectScore(), "Coin should have an object score of 1");
    }

    @Test
    void testCoinImplementsScorable() {
        Transform transform = new Transform(new Vector2(0, 0), new Vector2(30, 30));
        Coin coin = new Coin(transform);

        assertTrue(coin instanceof Scorable, "Coin should implement Scorable");
    }

    @Test
    public void testGetObjectScore() {
        Transform transform = new Transform(new Vector2(0, 0), new Vector2(1, 1));
        Coin coin = new Coin(transform);

        int expectedScore = 5;
        assertEquals(expectedScore, coin.getObjectScore(),
                "getObjectScore() should return the correct coin value.");
    }


}
