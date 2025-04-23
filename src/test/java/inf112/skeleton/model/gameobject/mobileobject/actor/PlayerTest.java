package inf112.skeleton.model.gameobject.mobileobject.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.PositionValidator;
import inf112.skeleton.model.gameobject.Collidable;
import inf112.skeleton.model.gameobject.CollisionBox;
import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.gameobject.Visitor;
import inf112.skeleton.model.gameobject.fixedobject.item.Banana;
import inf112.skeleton.model.gameobject.fixedobject.item.Coin;
import inf112.skeleton.model.gameobject.fixedobject.item.Star;
import inf112.skeleton.model.gameobject.mobileobject.actor.enemy.Enemy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PlayerTest {

    private Player player;
    private Transform transform;

    @BeforeEach
    void setUp() {
        //Mocker graphics for å kunne bruke deltatime i testene som krever det
        Graphics mockGraphics = mock(Graphics.class);
        Gdx.graphics = mockGraphics;
        when(Gdx.graphics.getDeltaTime()).thenReturn(1 / 60f); // 60 FPS som eksempel

        transform = new Transform(new Vector2(0, 0), new Vector2(50, 100));
        player = new Player(3, 5, transform);
    }

    @Test
    void testPlayerInitialization() {
        assertNotNull(player);
        assertEquals(3, player.getLives());
        assertTrue(player.isAlive());

        assertFalse(player.getHasPowerUp(), "Player should not have a power-up initially.");
        assertFalse(player.getRespawned(), "Player should not be respawned initially.");

        assertEquals(0, player.getLastAttackTime(), "Last attack time should be initialized to 0.");
        assertEquals(0, player.getLastBounceTime(), "Last bounce time should be initialized to 0.");
        assertEquals(5, player.getMovementSpeed(), "Player should have a movement speed of 5");
    }


    @Test
    void testReceiveDamageReducesLives() {
        player.receiveDamage(1);
        assertEquals(2, player.getLives());
        assertTrue(player.isAlive());
    }

    @Test
    void testReceiveDamageKillsPlayer() {
        // player has 3 lives initially
        assertEquals(3, player.getLives());

        // player loses 3 lives and should die
        player.receiveDamage(3);
        assertEquals(0, player.getLives());
        assertFalse(player.isAlive());
    }

    @Test
    void testReceiveNegativeDamage() {
        assertThrows(IllegalArgumentException.class, () -> player.receiveDamage(-1), "Damage can not be negative.");
        assertThrows(IllegalArgumentException.class, () -> player.dealDamage(player, -1), "Damage can not be negative.");
    }

    @Test
    void testSetAndGetHasPowerUp() {
        player.setHasPowerUp(true);
        assertTrue(player.getHasPowerUp());

        player.setHasPowerUp(false);
        assertFalse(player.getHasPowerUp());
    }

    @Test
    void testTakeDamageWithPowerUp() {
        player.setHasPowerUp(true);
        player.takeDamage(1);
        assertFalse(player.getHasPowerUp());
    }


    @Test
    void testVisitStarSetsNextLevel() {
        Star star = mock(Star.class);

        player.visit(star);
        assertTrue(player.getGoToNextLevel());
        assertFalse(player.getGoToNextLevel()); // reset
    }

    @Test
    void testTakeDamageWithoutPowerUp() {
        player.setHasPowerUp(false);
        int livesBefore = player.getLives();
        player.takeDamage(1);
        assertEquals(livesBefore - 1, player.getLives());
    }


    @Test
    void testPlayerNonPositiveLives() {

        assertThrows(IllegalArgumentException.class, () -> new Player(0, 5, transform));
        assertThrows(IllegalArgumentException.class, () -> new Player(-1, 5, transform));
    }

    @Test
    void testDealDamageThrowsExceptionForNullTarget() {
        assertThrows(IllegalArgumentException.class, () -> player.dealDamage(null, 1));
    }

    @Test
    void testJumpWhenGrounded() {
        // Spilleren hopper med grounded = true
        player.jump(true);

        // Forventet beregning av vertikal hastighet (velocity)
        float deltaTime = Gdx.graphics.getDeltaTime();
        int expectedVelocity = (int) (player.getJumpForce() * deltaTime);  // JumpForce * deltaTime

        // Sjekk om vertikal hastighet er satt korrekt
        assertNotEquals(0, player.getVerticalVelocity(), "Player should have a vertical velocity after jump.");
        assertEquals(expectedVelocity, player.getVerticalVelocity(), "Player's velocity should be correctly calculated based on jump force and deltaTime.");
    }

    @Test
    public void testJumpWhenNotGrounded() {
        player.jump(false);
        assertEquals(0, player.getVerticalVelocity(), "Player should not jump when not grounded.");

    }

    @Test
    public void testBounceWithoutPowerUp() {
        player.setHasPowerUp(false);
        player.bounce();
        assertTrue(player.getVerticalVelocity() > 0, "Player should bounce with normal force.");
    }

    @Test
    public void testBounceWithPowerUp() {
        player.setHasPowerUp(true);
        player.bounce();
        assertTrue(player.getVerticalVelocity() > 0, "Player should bounce with small force when powered up.");
    }

    @Test
    public void testSetAndGetRespawned() {
        player.setRespawned(true);
        assertTrue(player.getRespawned(), "Player should be marked as respawned.");
        player.setRespawned(false);
        assertFalse(player.getRespawned(), "Player should not be marked as respawned.");
    }

    @Test
    public void testLastAttackTime() {
        long currentTime = System.currentTimeMillis();
        player.setLastAttackTime(currentTime);
        assertEquals(currentTime, player.getLastAttackTime(), "Last attack time should be updated.");
    }

    @Test
    public void testLastBounceTime() {
        long currentTime = System.currentTimeMillis();
        player.setLastBounceTime(currentTime);
        assertEquals(currentTime, player.getLastBounceTime(), "Last bounce time should be updated.");
    }

    //TODO: Må justeres, fungerer ikke
//    @Test
//    void testEnemyCollisionFromSideTakesDamage() {
//        Enemy enemy = mock(Enemy.class);
//        when(enemy.getCollisionBox()).thenReturn(player.getCollisionBox());
//        when(player.getCollisionBox().isCollidingFromBottom(any())).thenReturn(false);
//        when(enemy.getDamage()).thenReturn(1);
//
//        int livesBefore = player.getLives();
//        player.visit(enemy);
//        assertTrue(player.getLives() < livesBefore);
//    }

    @Test
    public void testResolvePlayerMovementBelowLevelKillsPlayer() {
        PositionValidator validator = mock(PositionValidator.class);
        when(validator.isLegalMove(any())).thenReturn(true);

        Vector2 farDown = new Vector2(0, -300);
        Transform transformMock = mock(Transform.class);
        when(transformMock.getPos()).thenReturn(farDown);
        when(transformMock.getSize()).thenReturn(new Vector2(50, 100));
        player = new Player(3, 5, transformMock);

        player.resolvePlayerMovement(0, 0, validator);
        assertEquals(0, player.getLives());
        assertFalse(player.isAlive());
    }

    @Test
    public void testIsCollidingSkipsPlayer() {
        Collidable otherPlayer = mock(Player.class);
        when(otherPlayer.getCollisionBox()).thenReturn(mock(CollisionBox.class));
        when(otherPlayer.getCollisionBox().isCollidingWith(any())).thenReturn(true);

        List<Collidable> list = List.of(otherPlayer);
        assertFalse(player.isColliding(list, mock(CollisionBox.class)));
    }

    @Test
    void testUpdateAndGetTotalScore() {
        assertEquals(0, player.getTotalScore());
        player.updateTotalScore(5);
        assertEquals(5, player.getTotalScore());
    }

    @Test
    void testUpdateAndGetCoinCounter() {
        assertEquals(0, player.getCoinCounter());
        player.updateCoinCounter(3);
        assertEquals(3, player.getCoinCounter());
    }

    @Test
    void testAcceptCallsVisitor() {
        Visitor visitor = mock(Visitor.class);
        player.accept(visitor);
        verify(visitor, times(1)).visit(player);
    }

    @Test
    void testVisitCoin() {
        // Mock Coin-objektet
        Coin mockCoin = mock(Coin.class);
        int mockScore = 10;
        when(mockCoin.getObjectScore()).thenReturn(mockScore);

        // Simuler besøk
        player.visit(mockCoin);

        // Sjekk at coinCounter har økt med 1
        assertEquals(1, player.getCoinCounter());

        // Sjekk at totalScore har økt med coinens score
        assertEquals(mockScore, player.getTotalScore());

        // Sjekk at objectsToRemove inneholder mockCoin
        assertTrue(player.getObjectsToRemove().contains(mockCoin));
    }

    //TODO: Fungerer ikke
//    @Test
//    void testVisitBanana() {
//        // Mock Banana-objektet
//        Banana mockBanana = mock(Banana.class);
//        int oldJumpForce = player.getJumpForce();
//
//        when(mockBanana.getBigJumpForce()).thenReturn(73000);
//
//        Transform mockTransform = mock(Transform.class);
//        when(mockTransform.getSize()).thenReturn(new Vector2(50, 100)); // Mock størrelse
//
//        // Bruk mock Transform når du lager Player
//        Player player = new Player(3, 5, mockTransform);
//
//
//        player.visit(mockBanana);
//
//        // Sjekk at spilleren fikk power-up
//        assertTrue(player.getHasPowerUp(), "Player should have power-up after visiting Banana.");
//
//        // Sjekk at jumpForce ble oppdatert
//        //assertTrue(oldJumpForce < player.getJumpForce(), "Player's jump force should be updated.");
//
//        // Sjekk at banana er lagt til objectsToRemove
//        //assertTrue(player.getObjectsToRemove().contains(mockBanana), "Player should add Banana to objectsToRemove.");
//    }



}
