package inf112.starhunt.model.gameobject.mobileobject.actor.enemy;

import inf112.starhunt.model.gameobject.mobileobject.MobileObjectFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MobileObjectFactoryTest {

    @BeforeEach
    void setUp() {
        Enemy enemy = MobileObjectFactory.createSnail(5, 10);
        assertNotNull(enemy, "Enemy should not be null");
    }

    @Test
    void testCreateEnemyHasCorrectPosition() {
        float x = 5f, y = 10f;
        Enemy enemy = MobileObjectFactory.createSnail(x, y);

        assertEquals(x, enemy.getTransform().getPos().x, 0.001, "X position should match");
        assertEquals(y, enemy.getTransform().getPos().y, 0.001, "Y position should match");
    }

    @Test
    void testCreateEnemyAtNegativePosition() {
        float x = -10f, y = -20f;
        Enemy enemy = MobileObjectFactory.createSnail(x, y);

        assertEquals(x, enemy.getTransform().getPos().x, 0.001, "X position should match negative value");
        assertEquals(y, enemy.getTransform().getPos().y, 0.001, "Y position should match negative value");
    }

    @Test
    void testEnemiesAreNotEqualButHaveSameAttributes() {
        Enemy enemy1 = MobileObjectFactory.createSnail(5, 10);
        Enemy enemy2 = MobileObjectFactory.createSnail(5, 10);

        assertNotEquals(enemy1, enemy2, "Enemies should not be the same object");

        // Sjekker at de har samme attributter
        assertEquals(enemy1.getMovementSpeed(), enemy2.getMovementSpeed(), "Movement speed should be equal");
        assertEquals(enemy1.getObjectScore(), enemy2.getObjectScore(), "Object score should be equal");
        assertEquals(enemy1.getDamage(), enemy2.getDamage(), "Damage should be equal");
        assertEquals(enemy1.getTransform().getPos().x, enemy2.getTransform().getPos().x, 0.001, "X position should be equal");
        assertEquals(enemy1.getTransform().getPos().y, enemy2.getTransform().getPos().y, 0.001, "Y position should be equal");
        assertEquals(enemy1.getTransform().getSize().x, enemy2.getTransform().getSize().x, 0.001, "Width should be equal");
        assertEquals(enemy1.getTransform().getSize().y, enemy2.getTransform().getSize().y, 0.001, "Height should be equal");
    }

}
