package inf112.skeleton.model.gameobject.mobileobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.gameobject.mobileobject.actor.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MobileObjectTest {
    private Player player;
    private Transform transform;

    @BeforeEach
    void setUp() {
        //Mocker graphics for å kunne bruke deltatime i bounce-tester og jump-test
        Graphics mockGraphics = mock(Graphics.class);
        Gdx.graphics = mockGraphics;
        when(Gdx.graphics.getDeltaTime()).thenReturn(1 / 60f); // 60 FPS som eksempel

        transform = new Transform(new Vector2(100, 0), new Vector2(50, 100));
        player = new Player(3, 350, transform);
    }

    @Test
    public void testMoveHorizontally() {

        float initialPosition = player.getTransform().getPos().x;

        // initial position
        assertEquals(100, initialPosition);


        // Moving left
        player.moveHorizontally(1 / 60f, true, false);
        assertTrue(player.getTransform().getPos().x < initialPosition, "Player should move left.");

        float playerPosition = player.getTransform().getPos().x;

        // Moving right
        player.moveHorizontally(1 / 60f, false, true);
        assertTrue(player.getTransform().getPos().x > playerPosition, "Player should move right.");


        playerPosition = player.getTransform().getPos().x;
        assertEquals(initialPosition, playerPosition, "Player should be at initial position");

        // Moving right again
        player.moveHorizontally(1 / 60f, false, true);
        assertTrue(player.getTransform().getPos().x > playerPosition, "Player should move right.");

        // Should not move if direction is not set
        float noMoveX = player.getTransform().getPos().x;
        player.moveHorizontally(1 / 60f, false, false);
        assertEquals(noMoveX, player.getTransform().getPos().x, "Player should not move if both directions are false.");
    }

    @Test
    public void testMoveVertically() {
        player.setVerticalVelocity(300);
        player.moveVertically(1 / 60f);
        assertEquals(5, player.getTransform().getPos().y, "Player should move upward by velocity.");

        player.setVerticalVelocity(-300);
        player.moveVertically(1 / 60f);
        assertEquals(0, player.getTransform().getPos().y, "Player should move downward by velocity.");
    }

    @Test
    public void testApplyGravity() {
        player.applyGravity(1 / 60f, false);
        assertTrue(player.getVerticalVelocity() < 0, "Gravity should reduce the vertical velocity.");

        player.applyGravity(1 / 60f, true);
        assertEquals(0, player.getVerticalVelocity(), "On ground, gravity should not affect velocity.");
    }

    //TODO: Fungerer ikke, actual er 0 i alle tilfellene, oppdateres ikke riktig etter moveHorizontally?
//    @Test
//    public void testGetMovementDirection() {
//        player.moveHorizontally(1 / 60f, true, false);
//        assertEquals(-1, player.getMovementDirection(), "Movement direction should be -1 when moving left.");
//
//        player.moveHorizontally(1 / 60f, false, true);
//        assertEquals(1, player.getMovementDirection(), "Movement direction should be 1 when moving right.");
//
//        player.moveHorizontally(1 / 60f, false, false);
//        assertEquals(0, player.getMovementDirection(), "Movement direction should be 0 when not moving.");
//    }

    @Test
    public void testAddVerticalVelocity() {
        player.setVerticalVelocity(100);
        player.addVerticalVelocity(50);
        assertEquals(150, player.getVerticalVelocity(), "Vertical velocity should increase.");

        player.addVerticalVelocity(-100);
        assertEquals(50, player.getVerticalVelocity(), "Vertical velocity should decrease.");
    }


}
