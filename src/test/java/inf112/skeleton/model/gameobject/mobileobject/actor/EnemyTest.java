package inf112.skeleton.model.gameobject.mobileobject.actor;

import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.mobileobject.actor.enemy.Enemy;
import inf112.skeleton.model.gameobject.mobileobject.actor.enemy.EnemyFactory;
import inf112.skeleton.model.gameobject.mobileobject.actor.enemy.EnemyType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EnemyTest {

    @Test
    void testEnemyDeathSnail() {
        Enemy enemy = EnemyFactory.createSnail(0, 0, EnemyType.SNAIL);
        assertTrue(enemy.isAlive());
        enemy.receiveDamage(1);
        assertFalse( enemy.isAlive());
    }

    @Test
    void testEnemyDeathLeopard() {
        Enemy enemy = EnemyFactory.createLeopard(0, 0, EnemyType.LEOPARD);
        assertTrue(enemy.isAlive());
        enemy.receiveDamage(1);
        assertTrue( enemy.isAlive());
        enemy.receiveDamage(1);
        assertFalse(enemy.isAlive());
    }

    @Test
    void testMoveEnemySnail() {
        Enemy snailEnemy = EnemyFactory.createSnail(50, 0, EnemyType.SNAIL);
        float initialPosition = snailEnemy.getTransform().getPos().x;

        List<GameObject> objectList2 = new ArrayList<>();
        objectList2.add(snailEnemy);
        snailEnemy.moveEnemy(1/60f, objectList2);
        assertTrue(snailEnemy.getTransform().getPos().x > initialPosition, "Snail should move right.");
    }

    @Test
    void testMoveEnemyLeopard() {
        Enemy leopardEnemy = EnemyFactory.createLeopard(50, 0, EnemyType.LEOPARD);
        float initialPosition = leopardEnemy.getTransform().getPos().x;

        List<GameObject> objectList = new ArrayList<>();
        objectList.add(leopardEnemy);
        leopardEnemy.moveEnemy(1/60f, objectList);
        assertTrue(leopardEnemy.getTransform().getPos().x < initialPosition, "Leopard should move left.");
    }
}
