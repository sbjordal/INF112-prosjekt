package inf112.skeleton.app.model.gameobject.fixedobject.item;

import inf112.skeleton.model.gameobject.fixedobject.item.Banana;
import inf112.skeleton.model.gameobject.fixedobject.item.Coin;
import inf112.skeleton.model.gameobject.fixedobject.item.ItemFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemFactoryTest {

    @Test
    void testCreateCoinNotNull() {
        Coin coin = ItemFactory.createCoin(10, 20);
        assertNotNull(coin, "Coin should not be null");
    }

    @Test
    void testCreateMushroomNotNull() {
        Banana banana = ItemFactory.createMushroom(15, 25);
        assertNotNull(banana, "Banana should not be null");
    }

    @Test
    void testCreateCoinHasCorrectPositionAndSize() {
        float x = 10f, y = 20f;
        Coin coin = ItemFactory.createCoin(x, y);

        assertEquals(x, coin.getTransform().getPos().x, 0.001, "X position should match");
        assertEquals(y, coin.getTransform().getPos().y, 0.001, "Y position should match");

        assertEquals(30, coin.getTransform().getSize().x, "Coin width should be 30");
        assertEquals(30, coin.getTransform().getSize().y, "Coin height should be 30");
    }

    @Test
    void testCreateMushroomHasCorrectPositionAndSize() {
        float x = 15f, y = 25f;
        Banana banana = ItemFactory.createMushroom(x, y);

        assertEquals(x, banana.getTransform().getPos().x, 0.001, "X position should match");
        assertEquals(y, banana.getTransform().getPos().y, 0.001, "Y position should match");

        assertEquals(50, banana.getTransform().getSize().x, "Banana width should be 50");
        assertEquals(53, banana.getTransform().getSize().y, 0.001, "Banana height should be 53");
    }

    @Test
    void testCreatedCoinsAreNotSameObject() {
        Coin coin1 = ItemFactory.createCoin(5, 5);
        Coin coin2 = ItemFactory.createCoin(5, 5);

        assertNotEquals(coin1, coin2, "Two created coins should be different objects");
    }

    @Test
    void testCreatedMushroomsAreNotSameObject() {
        Banana banana1 = ItemFactory.createMushroom(10, 10);
        Banana banana2 = ItemFactory.createMushroom(10, 10);

        assertNotEquals(banana1, banana2, "Two created mushrooms should be different objects");
    }

}
