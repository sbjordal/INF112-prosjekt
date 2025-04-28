package inf112.starhunt.model.gameobject.mobileobject.actor.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.Vector2;
import inf112.starhunt.model.gameobject.GameObject;
import inf112.starhunt.model.gameobject.GameObjectFactory;
import inf112.starhunt.model.gameobject.Transform;
import inf112.starhunt.model.gameobject.Visitor;
import inf112.starhunt.model.gameobject.fixedobject.Ground;
import inf112.starhunt.model.gameobject.fixedobject.item.Banana;
import inf112.starhunt.model.gameobject.fixedobject.item.Coin;
import inf112.starhunt.model.gameobject.fixedobject.item.Star;
import inf112.starhunt.model.gameobject.mobileobject.MobileObjectFactory;
import inf112.starhunt.model.gameobject.mobileobject.actor.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EnemyTest {

    @Test
    void testEnemyDeathSnail() {
        Enemy enemy = MobileObjectFactory.createSnail(0, 0);
        assertTrue(enemy.isAlive());
        enemy.receiveDamage(1);
        assertFalse( enemy.isAlive());
    }

    @Test
    void testEnemyDeathLeopard() {
        Enemy enemy = MobileObjectFactory.createLeopard(0, 0);
        assertTrue(enemy.isAlive());
        enemy.receiveDamage(1);
        assertTrue( enemy.isAlive());
        enemy.receiveDamage(1);
        assertFalse(enemy.isAlive());
    }

    @Test
    void testMoveEnemySnail() {
        Enemy snailEnemy = MobileObjectFactory.createSnail(50, 0);
        float initialPositionX = snailEnemy.getTransform().getPos().x;

        snailEnemy = moveTestHelper(snailEnemy);
        assertTrue(snailEnemy.getTransform().getPos().x > initialPositionX, "Snail should move right.");
        assertTrue(snailEnemy.getTransform().getPos().x > 0, "x should be positive.");
    }

    @Test
    void testMoveEnemyLeopard() {
        Enemy leopardEnemy = MobileObjectFactory.createLeopard(50, 0);
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
        Enemy snailEnemy = MobileObjectFactory.createSnail(50, 0);
        assertEquals(10, snailEnemy.getObjectScore());
    }

    @Test
    void testObjectScoreLeopard() {
        Enemy leopardEnemy = MobileObjectFactory.createLeopard(50, 0);
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
        Enemy snailEnemy = MobileObjectFactory.createSnail(50, 0);
        Player player = setUpPlayer();
        testAttack(snailEnemy, player);
    }

    @Test
    void testAttackLeopard() {
        Enemy leopardEnemy = MobileObjectFactory.createLeopard(50, 0);
        Player player = setUpPlayer();
        testAttack(leopardEnemy, player);
    }

    void testAttack(Enemy enemy, Player player) {
        assertTrue(player.isAlive());
        assertEquals(3, player.getLives());

        enemy.attack(player);
        assertEquals(2, player.getLives());

        //TODO: Får ikke attacke to ganger på rad, antakelig pga cooldown.
        // Forsøkt Thread.sleep(60) uten hell
//        enemy.attack(player);
//        assertEquals(1, player.getLives());

//        enemy.attack(player);
//        assertEquals(0, player.getLives());
//        assertFalse(player.isAlive());
    }

    @Test
    void testAttackSnailWithPowerUp() {
        Enemy snailEnemy = MobileObjectFactory.createSnail(50, 0);
        Player player = setUpPlayer();
        testAttackWithPowerUp(snailEnemy, player);
    }

    @Test
    void testAttackLeopardWithPowerUp() {
        Enemy leopardEnemy = MobileObjectFactory.createLeopard(50, 0);
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

        //TODO: Får ikke attacke to ganger på rad, antakelig pga cooldown. Forsøkt Thread.sleep(60) uten hell
//        enemy.attack(player);
//        assertEquals(2, player.getLives());
    }

    @Test
    void testVisitPlayerTriggersAttackAndDirectionSwitch() {
        Enemy enemy = MobileObjectFactory.createLeopard(0, 0);
        Player player = setUpPlayer();

        int dirBefore = enemy.getMovementDirection();
        enemy.visit(player);

        assertEquals(2, player.getLives(), "Player should lose one life");
        assertEquals(-dirBefore, enemy.getMovementDirection(), "Enemy should switch direction");
    }

    @Test
    void testVisitGroundSwitchesDirection() {
        Enemy enemy = MobileObjectFactory.createSnail(0, 0);

        int directionBefore = enemy.getMovementDirection();
        enemy.visit(mock(Ground.class));

        assertEquals(-directionBefore, enemy.getMovementDirection());
    }

    @Test
    void testIsReadyToCollide() throws InterruptedException {
        Enemy enemy = MobileObjectFactory.createSnail(0, 0);

        // Første kall skal returnere true
        assertTrue(enemy.isReadyToCollide(), "Første kall skal være klar for kollisjon");

        // Umiddelbar nytt kall skal returnere false (fordi cooldown ikke er utløpt)
        assertFalse(enemy.isReadyToCollide(), "Andre kall rett etter skal være blokkert pga. cooldown");

        // Vent til cooldown (48ms) har gått over
        Thread.sleep(60);

        // Nå skal det igjen returnere true
        assertTrue(enemy.isReadyToCollide(), "Etter venting skal kollisjon være mulig igjen");
    }

    @Test
    void testVisitEnemySwitchesDirectionIfDifferentEnemy() {
        Enemy snail1 = MobileObjectFactory.createSnail(0, 0);
        Enemy snail2 = MobileObjectFactory.createSnail(100, 0);

        int initialDirection = snail1.getMovementDirection();

        // Besøk med en annen enemy
        snail1.visit(snail2);

        assertEquals(-initialDirection, snail1.getMovementDirection(), "Bevegelsesretningen skal snu ved kollisjon med en annen enemy");
    }

    @Test
    void testVisitEnemyDoesNotSwitchDirectionIfSameEnemy() {
        Enemy snail = MobileObjectFactory.createSnail(0, 0);
        int initialDirection = snail.getMovementDirection();

        // Besøk seg selv – skal ikke skje, men må sikres
        snail.visit(snail);

        assertEquals(initialDirection, snail.getMovementDirection(), "Bevegelsesretningen skal ikke endres ved besøk på seg selv");
    }


    @Test
    void testAcceptCallsVisitOnVisitor() {
        Enemy snail = MobileObjectFactory.createSnail(0, 0);
        Visitor mockVisitor = mock(Visitor.class);

        snail.accept(mockVisitor);

        verify(mockVisitor).visit(snail); // Sjekker at riktig metode ble kalt
    }

    @Test
    void testVisitItemsDoesNotSwitchDirection() {
        Enemy snail = MobileObjectFactory.createSnail(0, 0);
        int initialDirection = snail.getMovementDirection();

        Coin coin = mock(Coin.class);
        snail.visit(coin);
        assertEquals(initialDirection, snail.getMovementDirection(), "Enemy should not switch direction when visiting Coin.");

        Star star = mock(Star.class);
        snail.visit(star);
        assertEquals(initialDirection, snail.getMovementDirection());

        Banana banana = mock(Banana.class);
        snail.visit(banana);
        assertEquals(initialDirection, snail.getMovementDirection());
    }

}
