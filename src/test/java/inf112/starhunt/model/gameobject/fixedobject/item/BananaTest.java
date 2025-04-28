package inf112.starhunt.model.gameobject.fixedobject.item;

import com.badlogic.gdx.math.Vector2;
import inf112.starhunt.model.gameobject.Transform;
import inf112.starhunt.model.gameobject.Visitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BananaTest {

    private Banana banana;

    @BeforeEach
    public void setUp() {
        Transform transform = new Transform(new Vector2(0, 0), new Vector2(1, 1));
        banana = new Banana(transform);
    }

    @Test
    public void testGetBigJumpForce() {
        int expectedForce = 73000;
        assertEquals(expectedForce, banana.getBigJumpForce(),
                "getBigJumpForce() should return the correct jump force.");
    }

    @Test
    public void testGetLargePlayerSize() {
        Vector2 expectedSize = new Vector2(65, 135);
        assertEquals(expectedSize, banana.getLargePlayerSize(),
                "getLargePlayerSize() should return the correct player size.");
    }

    @Test
    public void testBananaAcceptsVisitor() {
        Visitor visitor = mock(Visitor.class);
        banana.accept(visitor);

        verify(visitor, times(1)).visitBanana(banana);
    }
}
