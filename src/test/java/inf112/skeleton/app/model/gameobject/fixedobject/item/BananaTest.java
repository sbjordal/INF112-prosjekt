package inf112.skeleton.app.model.gameobject.fixedobject.item;

import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.gameobject.fixedobject.item.Banana;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BananaTest {

    @Test
    public void testGetBigJumpForce() {
        Transform transform = new Transform(new Vector2(0, 0), new Vector2(1, 1));
        Banana banana = new Banana(transform);

        int expectedForce = 73000;
        assertEquals(expectedForce, banana.getBigJumpForce(),
                "getBigJumpForce() should return the correct jump force.");
    }

    @Test
    public void testGetLargePlayerSize() {
        Transform transform = new Transform(new Vector2(0, 0), new Vector2(1, 1));
        Banana banana = new Banana(transform);

        Vector2 expectedSize = new Vector2(65, 135);
        assertEquals(expectedSize, banana.getLargePlayerSize(),
                "getLargePlayerSize() should return the correct player size.");
    }
}
