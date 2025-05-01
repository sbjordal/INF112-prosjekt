package inf112.starhunt.model.gameobject.mobileobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.Vector2;
import inf112.starhunt.model.PositionValidator;
import inf112.starhunt.model.gameobject.Transform;
import inf112.starhunt.model.gameobject.mobileobject.actor.Player;
import inf112.starhunt.model.gameobject.fixedobject.Ground;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

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
    public void testAddVerticalVelocity() {
        player.setVerticalVelocity(100);
        player.addVerticalVelocity(50);
        assertEquals(150, player.getVerticalVelocity(), "Vertical velocity should increase.");

        player.addVerticalVelocity(-100);
        assertEquals(50, player.getVerticalVelocity(), "Vertical velocity should decrease.");
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

    @Test
    public void testMoveUpdatesCollisionBox() {
        Vector2 before = new Vector2(player.getCollisionBox().getBotLeft().x, player.getCollisionBox().getTopRight().y);
        player.move(10, 15);
        Vector2 after = new Vector2(player.getCollisionBox().getBotLeft().x, player.getCollisionBox().getTopRight().y);

        assertTrue(before.x != after.x || before.y != after.y, "Collision box should update with movement.");
    }

    @Test
    public void testSwitchDirection() {
        player.setMovementDirection(1);
        player.switchDirection();
        assertEquals(-1, player.getMovementDirection());

        player.switchDirection();
        assertEquals(1, player.getMovementDirection());
    }

    @Test
    public void testIsTouchingGroundTrue() {
        Ground ground = new Ground(new Transform(new Vector2(100, -1), new Vector2(50, 1))); // Rett under
        assertTrue(player.isTouchingGround(List.of(ground)), "Should be touching the ground.");
    }

    @Test
    public void testIsTouchingGroundFalse() {
        Ground ground = new Ground(new Transform(new Vector2(100, -100), new Vector2(50, 1))); // Langt under
        assertTrue(!player.isTouchingGround(List.of(ground)), "Should not be touching the ground.");
    }

    @Test
    public void testGetDirection() {
        player.setMovementDirection(-1);
        assertEquals(-1, player.getDirection());
    }




}
