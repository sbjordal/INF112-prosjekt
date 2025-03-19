package inf112.skeleton.app.model.gameobject.mobileobject.actor;

import inf112.skeleton.model.gameobject.mobileobject.actor.Enemy;
import inf112.skeleton.model.gameobject.mobileobject.actor.EnemyFactory;
import inf112.skeleton.model.gameobject.mobileobject.actor.EnemyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyFactoryTest {

    @BeforeEach
    void setUp() {
        Enemy enemy = EnemyFactory.createEnemy(5, 10, EnemyType.SNEGL);
        assertNotNull(enemy, "Enemy should not be null");
    }

    @Test
    void testCreateEnemyHasCorrectPosition() {
        float x = 5f, y = 10f;
        Enemy enemy = EnemyFactory.createEnemy(x, y, EnemyType.SNEGL);

        assertEquals(x, enemy.getTransform().getPos().x, 0.001, "X position should match");
        assertEquals(y, enemy.getTransform().getPos().y, 0.001, "Y position should match");
    }

    @Test
    void testCreateEnemyHasCorrectSize() {
        Enemy enemy = EnemyFactory.createEnemy(5, 10, EnemyType.SNEGL);

        assertEquals(EnemyType.SNEGL.width, enemy.getTransform().getSize().x, 0.001, "Width should match");
        assertEquals(EnemyType.SNEGL.height, enemy.getTransform().getSize().y, 0.001, "Height should match");
    }

    @Test
    void testCreateEnemyHasCorrectAttributes() {
        Enemy enemy = EnemyFactory.createEnemy(5, 10, EnemyType.SNEGL);

        assertEquals(EnemyType.SNEGL.movementSpeed, enemy.getMovementSpeed(), "Movement speed should match");
        assertEquals(EnemyType.SNEGL.objectScore, enemy.getObjectScore(), "Object score should match");
        assertEquals(EnemyType.SNEGL.damage, enemy.getDamage(), "Damage should match");
    }

    @Test
    void testCreateEnemyAtNegativePosition() {
        float x = -10f, y = -20f;
        Enemy enemy = EnemyFactory.createEnemy(x, y, EnemyType.SNEGL);

        assertEquals(x, enemy.getTransform().getPos().x, 0.001, "X position should match negative value");
        assertEquals(y, enemy.getTransform().getPos().y, 0.001, "Y position should match negative value");
    }

    @Test
    void testEnemiesAreNotEqualButHaveSameAttributes() {
        Enemy enemy1 = EnemyFactory.createEnemy(5, 10, EnemyType.SNEGL);
        Enemy enemy2 = EnemyFactory.createEnemy(5, 10, EnemyType.SNEGL);

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
