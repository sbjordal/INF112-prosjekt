package inf112.skeleton.model.gameobject.mobileobject.actor.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.gameobject.mobileobject.actor.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        float initialPositionX = snailEnemy.getTransform().getPos().x;

        snailEnemy = moveTestHelper(snailEnemy);
        assertTrue(snailEnemy.getTransform().getPos().x > initialPositionX, "Snail should move right.");
        assertTrue(snailEnemy.getTransform().getPos().x > 0, "x should be positive.");
    }

    @Test
    void testMoveEnemyLeopard() {
        Enemy leopardEnemy = EnemyFactory.createLeopard(50, 0, EnemyType.LEOPARD);
        float initialPositionX = leopardEnemy.getTransform().getPos().x;

        leopardEnemy = moveTestHelper(leopardEnemy);
        assertTrue(leopardEnemy.getTransform().getPos().x < initialPositionX, "Leopard should move left.");
        assertTrue(leopardEnemy.getTransform().getPos().x > 0, "x should be positive.");
    }

    Enemy moveTestHelper(Enemy enemy) {
        assertEquals(new Vector2(50, 0),enemy.getTransform().getPos(), "Leopards start position should be 50x 0y");
        enemy.moveEnemy(1/60f);
        return enemy;
    }

    @Test
    void testObjectScoreSnail() {
        Enemy snailEnemy = EnemyFactory.createSnail(50, 0, EnemyType.SNAIL);
        assertEquals(10, snailEnemy.getObjectScore());
    }

    @Test
    void testObjectScoreLeopard() {
        Enemy leopardEnemy = EnemyFactory.createLeopard(50, 0, EnemyType.LEOPARD);
        assertEquals(30, leopardEnemy.getObjectScore());
    }


    private Player setUpPlayer() {
        Gdx.graphics = mock(Graphics.class);;
        when(Gdx.graphics.getDeltaTime()).thenReturn(1 / 60f);

        Transform transform = new Transform(new Vector2(0, 0), new Vector2(50, 100));
        return new Player(3, 5, transform);
    }

    @Test
    void testAttackSnail() {
        Enemy snailEnemy = EnemyFactory.createSnail(50, 0, EnemyType.SNAIL);
        Player player = setUpPlayer();
        testAttack(snailEnemy, player);
    }

    @Test
    void testAttackLeopard() {
        Enemy leopardEnemy = EnemyFactory.createLeopard(50, 0, EnemyType.LEOPARD);
        Player player = setUpPlayer();
        testAttack(leopardEnemy, player);
    }

    void testAttack(Enemy enemy, Player player) {
        assertTrue(player.isAlive());
        assertEquals(3, player.getLives());

        enemy.attack(player);
        assertEquals(2, player.getLives());

        enemy.attack(player);
        assertEquals(1, player.getLives());

        enemy.attack(player);
        assertEquals(0, player.getLives());
        assertFalse(player.isAlive());
    }

    @Test
    void testAttackSnailWithPowerUp() {
        Enemy snailEnemy = EnemyFactory.createSnail(50, 0, EnemyType.SNAIL);
        Player player = setUpPlayer();
        testAttackWithPowerUp(snailEnemy, player);
    }

    @Test
    void testAttackLeopardWithPowerUp() {
        Enemy leopardEnemy = EnemyFactory.createLeopard(50, 0, EnemyType.LEOPARD);
        Player player = setUpPlayer();
        testAttackWithPowerUp(leopardEnemy, player);
    }

    void testAttackWithPowerUp(Enemy enemy, Player player) {
        player.setHasPowerUp(true);

        assertTrue(player.getHasPowerUp());
        assertTrue(player.isAlive());
        assertEquals(3, player.getLives());

        enemy.attack(player);
        assertFalse(player.getHasPowerUp());
        assertTrue(player.isAlive());
        assertEquals(3, player.getLives());

        enemy.attack(player);
        assertEquals(2, player.getLives());
    }

}
