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
        // Opprette en transform med en posisjon og størrelse
        transform = new Transform(new Vector2(10, 20), new Vector2(30, 30));
        // Initialisere player med 3 liv og hastighet på 5
        player = new Player(3, 5, transform);
    }

    @Test
    void testPlayerCreation() {
        assertNotNull(player, "Player should not be null");
        assertEquals(3, player.getLives(), "Player should have 3 lives initially");
        assertEquals(5, player.getMovementSpeed(), "Player should have a movement speed of 5");
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


}
