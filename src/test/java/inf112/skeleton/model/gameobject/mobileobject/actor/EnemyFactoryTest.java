package inf112.skeleton.app.model.gameobject.mobileobject.actor;

import inf112.skeleton.model.gameobject.mobileobject.actor.enemy.Enemy;
import inf112.skeleton.model.gameobject.mobileobject.actor.enemy.EnemyFactory;
import inf112.skeleton.model.gameobject.mobileobject.actor.enemy.EnemyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyFactoryTest {

    @BeforeEach
    void setUp() {
        Enemy enemy = EnemyFactory.createSnail(5, 10, EnemyType.SNAIL);
        assertNotNull(enemy, "Enemy should not be null");
    }

    @Test
    void testCreateEnemyHasCorrectPosition() {
        float x = 5f, y = 10f;
        Enemy enemy = EnemyFactory.createSnail(x, y, EnemyType.SNAIL);

        assertEquals(x, enemy.getTransform().getPos().x, 0.001, "X position should match");
        assertEquals(y, enemy.getTransform().getPos().y, 0.001, "Y position should match");
    }

    @Test
    void testCreateEnemyHasCorrectSize() {
        Enemy enemy = EnemyFactory.createSnail(5, 10, EnemyType.SNAIL);

        assertEquals(EnemyType.SNAIL.width, enemy.getTransform().getSize().x, 0.001, "Width should match");
        assertEquals(EnemyType.SNAIL.height, enemy.getTransform().getSize().y, 0.001, "Height should match");
    }

    @Test
    void testCreateEnemyHasCorrectAttributes() {
        Enemy enemy = EnemyFactory.createSnail(5, 10, EnemyType.SNAIL);

        assertEquals(EnemyType.SNAIL.movementSpeed, enemy.getMovementSpeed(), "Movement speed should match");
        assertEquals(EnemyType.SNAIL.objectScore, enemy.getObjectScore(), "Object score should match");
        assertEquals(EnemyType.SNAIL.damage, enemy.getDamage(), "Damage should match");
    }

    @Test
    void testCreateEnemyAtNegativePosition() {
        float x = -10f, y = -20f;
        Enemy enemy = EnemyFactory.createSnail(x, y, EnemyType.SNAIL);

        assertEquals(x, enemy.getTransform().getPos().x, 0.001, "X position should match negative value");
        assertEquals(y, enemy.getTransform().getPos().y, 0.001, "Y position should match negative value");
    }

    @Test
    void testEnemiesAreNotEqualButHaveSameAttributes() {
        Enemy enemy1 = EnemyFactory.createSnail(5, 10, EnemyType.SNAIL);
        Enemy enemy2 = EnemyFactory.createSnail(5, 10, EnemyType.SNAIL);

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
