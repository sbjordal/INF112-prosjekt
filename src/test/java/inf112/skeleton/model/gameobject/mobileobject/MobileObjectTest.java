package inf112.skeleton.model.gameobject.mobileobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.gameobject.CollisionBox;
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
    public void testApplyGravity() {
        player.applyGravity(1 / 60f, false);
        assertTrue(player.getVerticalVelocity() < 0, "Gravity should reduce the vertical velocity.");

        player.applyGravity(1 / 60f, true);
        assertEquals(0, player.getVerticalVelocity(), "On ground, gravity should not affect velocity.");
    }

    @Test
    public void testGetMovementDirection() {
        player.move(-1, 0);
        assertEquals(-1, player.getMovementDirection(), "Movement direction should be -1 when moving left.");

        player.move(1, 0);
        assertEquals(1, player.getMovementDirection(), "Movement direction should be 1 when moving right.");

    }

    @Test
    public void testAddVerticalVelocity() {
        player.setVerticalVelocity(100);
        player.addVerticalVelocity(50);
        assertEquals(150, player.getVerticalVelocity(), "Vertical velocity should increase.");

        player.addVerticalVelocity(-100);
        assertEquals(50, player.getVerticalVelocity(), "Vertical velocity should decrease.");
    }

    @Test
    public void testSetMovementDirectionUpdatesCorrectly() {
        Vector2 start = player.getTransform().getPos().cpy();

        // Move right
        Vector2 right = new Vector2(start.x + 10, start.y);
        player.move(right);
        assertEquals(1, player.getMovementDirection());

        // Move left
        Vector2 left = new Vector2(right.x - 20, right.y);
        player.move(left);
        assertEquals(-1, player.getMovementDirection());

        // No movement
        player.move(left); // move to same pos
        assertEquals(0, player.getMovementDirection());
    }

    @Test
    public void testMoveWithOffset() {
        Vector2 start = player.getTransform().getPos().cpy();
        player.move(20, 30);

        assertEquals(start.x + 20, player.getTransform().getPos().x);
        assertEquals(start.y + 30, player.getTransform().getPos().y);
    }

    @Test
    public void testApplyGravityStopsAtZeroOnGround() {
        player.setVerticalVelocity(0);
        player.applyGravity(1 / 60f, true);
        assertEquals(0, player.getVerticalVelocity(), "Velocity should remain zero on ground when already zero.");
    }

    @Test
    public void testApplyGravityAffectsVelocity() {
        player.setVerticalVelocity(0);
        player.applyGravity(1 / 60f, false);
        assertEquals(-53, player.getVerticalVelocity()); // 3200 / 60 = ~53
    }

    @Test
    public void testGetMovementSpeed() {
        assertEquals(350, player.getMovementSpeed());
    }


}
