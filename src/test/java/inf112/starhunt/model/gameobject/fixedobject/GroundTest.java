package inf112.starhunt.model.gameobject.fixedobject;

import com.badlogic.gdx.math.Vector2;
import inf112.starhunt.model.gameobject.Collidable;
import inf112.starhunt.model.gameobject.Transform;
import inf112.starhunt.model.gameobject.Visitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GroundTest {

    private Transform transform;
    private Ground ground;

    @BeforeEach
    void setUp() {
        transform = new Transform(new Vector2(5, 5), new Vector2(10, 10));
        ground = new Ground(transform, "0000");
    }

    @Test
    void testGroundCreationNotNull() {
        assertNotNull(ground, "Ground should not be null");
    }

    @Test
    void testGroundHasCorrectPositionAndSize() {
        assertEquals(5f, ground.getTransform().getPos().x, 0.001, "X position should match");
        assertEquals(5f, ground.getTransform().getPos().y, 0.001, "Y position should match");
        assertEquals(10f, ground.getTransform().getSize().x, "Width should match");
        assertEquals(10f, ground.getTransform().getSize().y, "Height should match");
    }

    @Test
    void testGroundAcceptsVisitor() {
        Visitor visitor = mock(Visitor.class);
        ground.accept(visitor);
        verify(visitor, times(1)).visitGround(ground);
    }

    @Test
    void testGroundImplementsCollidable() {
        assertTrue(ground instanceof Collidable,
                "Ground should implement Collidable");
    }
}
