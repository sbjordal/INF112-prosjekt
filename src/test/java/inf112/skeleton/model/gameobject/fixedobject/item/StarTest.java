package inf112.skeleton.model.gameobject.fixedobject.item;

import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.gameobject.Visitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StarTest {

    private Star star;
    private Transform transform;

    @BeforeEach
    void setUp() {
        transform = new Transform(new Vector2(5, 5), new Vector2(15, 15));
        star = new Star(transform);
    }

    @Test
    void testStarNotNull() {
        assertNotNull(star, "Star should not be null");
    }

    @Test
    void testStarHasCorrectPositionAndSize() {
        assertEquals(5f, star.getTransform().getPos().x, 0.001, "X position should match");
        assertEquals(5f, star.getTransform().getPos().y, 0.001, "Y position should match");
        assertEquals(15f, star.getTransform().getSize().x, "Width should match");
        assertEquals(15f, star.getTransform().getSize().y, "Height should match");
    }

    @Test
    void testStarAcceptsVisitor() {
        Visitor visitor = mock(Visitor.class);
        star.accept(visitor);

        verify(visitor, times(1)).visit(star);
    }
}
