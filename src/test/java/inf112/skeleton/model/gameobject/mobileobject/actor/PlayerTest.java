package inf112.skeleton.model.gameobject.mobileobject.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.gameobject.Transform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    void testInitiatePowerUp() {
        // Initial values
        Vector2 newSize = new Vector2(100, 200);
        int newJumpForce = 10;

        // Call the method
        player.initiatePowerUp(newSize, newJumpForce);

        // Assert that the power-up flag is set to true
        assertTrue(player.getHasPowerUp(), "Player should have power-up after initiation.");

        // Assert that the player's size is updated
        assertEquals(newSize, player.getTransform().getSize(), "Player size should match the new size.");

        // Assert that the jump force is updated
        assertEquals(newJumpForce, player.getJumpForce(), "Player's jump force should be updated.");
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
    public void testJumpWhenGrounded() {
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


}
