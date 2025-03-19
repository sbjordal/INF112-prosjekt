package inf112.skeleton.app.model.gameobject.fixedobject.item;

import inf112.skeleton.model.gameobject.fixedobject.item.Coin;
import inf112.skeleton.model.gameobject.fixedobject.item.ItemFactory;
import inf112.skeleton.model.gameobject.fixedobject.item.Mushroom;
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
        Mushroom mushroom = ItemFactory.createMushroom(15, 25);
        assertNotNull(mushroom, "Mushroom should not be null");
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
        Mushroom mushroom = ItemFactory.createMushroom(x, y);

        assertEquals(x, mushroom.getTransform().getPos().x, 0.001, "X position should match");
        assertEquals(y, mushroom.getTransform().getPos().y, 0.001, "Y position should match");

        assertEquals(50, mushroom.getTransform().getSize().x, "Mushroom width should be 50");
        assertEquals(53, mushroom.getTransform().getSize().y, 0.001, "Mushroom height should be 53");
    }

    @Test
    void testCreatedCoinsAreNotSameObject() {
        Coin coin1 = ItemFactory.createCoin(5, 5);
        Coin coin2 = ItemFactory.createCoin(5, 5);

        assertNotEquals(coin1, coin2, "Two created coins should be different objects");
    }

    @Test
    void testCreatedMushroomsAreNotSameObject() {
        Mushroom mushroom1 = ItemFactory.createMushroom(10, 10);
        Mushroom mushroom2 = ItemFactory.createMushroom(10, 10);

        assertNotEquals(mushroom1, mushroom2, "Two created mushrooms should be different objects");
    }

}
