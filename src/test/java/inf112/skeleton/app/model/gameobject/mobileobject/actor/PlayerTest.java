package inf112.skeleton.app.model.gameobject.mobileobject.actor;

import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.gameobject.mobileobject.actor.Enemy;
import inf112.skeleton.model.gameobject.mobileobject.actor.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;
    private Transform transform;

    @BeforeEach
    void setUp() {
        transform = new Transform(new Vector2(10, 20), new Vector2(30, 30));
        player = new Player(3, 5, transform);
    }

    @Test
    void testPlayerInitialization() {
        assertNotNull(player);
        assertEquals(3, player.getLives());
        assertTrue(player.isAlive());
        assertFalse(player.getHasPowerUp());
    }

    @Test
    void testPlayerCreation() {
        assertNotNull(player, "Player should not be null");
        assertEquals(3, player.getLives(), "Player should have 3 lives initially");
        assertEquals(5, player.getMovementSpeed(), "Player should have a movement speed of 5");
    }

    @Test
    void testReceiveDamageReducesLives() {
        player.receiveDamage(1);
        assertEquals(2, player.getLives());
        assertTrue(player.isAlive());
    }

    @Test
    void testReceiveDamageKillsPlayerWhenLivesAreZero() {
        player.receiveDamage(3);
        assertEquals(0, player.getLives());
        assertFalse(player.isAlive());
    }

    @Test
    void testReceiveDamageThrowsExceptionForNegativeDamage() {
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
    void testJumpSetsVerticalVelocity() {
        int jumpForce = 10;
        player.jump(jumpForce);

        assertEquals(jumpForce, player.getVerticalVelocity(), "Vertical velocity should match the jump force");
    }

    @Test
    void testHasPowerUp() {
        assertFalse(player.getHasPowerUp(), "Player should not have power-up initially");

        player.setHasPowerUp(true);
        assertTrue(player.getHasPowerUp(), "Player should have power-up after setting it to true");

        player.setHasPowerUp(false);
        assertFalse(player.getHasPowerUp(), "Player should not have power-up after setting it to false");
    }

    @Test
    void testPlayerLives() {
        assertEquals(3, player.getLives(), "Player should have 3 lives initially");

    }

    @Test
    void testPlayerThrowsExceptionForNonPositiveLives() {

        assertThrows(IllegalArgumentException.class, () -> new Player(0, 5, transform));
        assertThrows(IllegalArgumentException.class, () -> new Player(-1, 5, transform));
    }

    @Test
    void testDealDamageThrowsExceptionForNullTarget() {
        assertThrows(IllegalArgumentException.class, () -> player.dealDamage(null, 1));
    }



}
