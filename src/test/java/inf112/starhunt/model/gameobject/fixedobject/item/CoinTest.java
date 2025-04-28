package inf112.starhunt.model.gameobject.fixedobject.item;

import com.badlogic.gdx.math.Vector2;
import inf112.starhunt.model.gameobject.Scorable;
import inf112.starhunt.model.gameobject.Transform;
import inf112.starhunt.model.gameobject.Visitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CoinTest {

    private Coin coin;
    private Transform transform;

    @BeforeEach
    void setUp() {
        transform = new Transform(new Vector2(10, 20), new Vector2(30, 30));
        coin = new Coin(transform);
    }

    @Test
    void testCoinCreationNotNull() {
        assertNotNull(coin, "Coin should not be null");
    }

    @Test
    void testCoinHasCorrectPositionAndSize() {
        assertEquals(10f, coin.getTransform().getPos().x, 0.001, "X position should match");
        assertEquals(20f, coin.getTransform().getPos().y, 0.001, "Y position should match");
        assertEquals(30f, coin.getTransform().getSize().x, "Coin width should match");
        assertEquals(30f, coin.getTransform().getSize().y, "Coin height should match");
    }

    @Test
    void testCoinHasCorrectScore() {
        assertEquals(5, coin.getObjectScore(), "Coin should have an object score of 5");
    }

    @Test
    void testCoinImplementsScorable() {
        assertTrue(coin instanceof Scorable, "Coin should implement Scorable");
    }

    @Test
    void testCoinAcceptsVisitor() {
        Visitor visitor = mock(Visitor.class);
        coin.accept(visitor);

        verify(visitor, times(1)).visitCoin(coin);
    }
}
