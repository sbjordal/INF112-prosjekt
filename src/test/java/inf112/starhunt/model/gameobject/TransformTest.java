package inf112.starhunt.model.gameobject;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransformTest {

    Transform transform;

    @BeforeEach
    public void setup() {
        Vector2 size = new Vector2(0, 0);
        Vector2 position = new Vector2(0, 0);
        transform = new Transform(position, size);
    }

    @Test
    public void testAlterSize() {

        // Check that the size is altered in either absolute- or relative terms
        GameObject relativeObject = new GameObject(transform);
        relativeObject.getTransform().alterSize(1, 1);
        assertEquals(new Vector2(1, 1), relativeObject.getTransform().getSize());

        // Check that the size is altered ONLY in absolute terms
        GameObject absoluteObject = new GameObject(transform);
        absoluteObject.getTransform().alterSize(1, 1);
        absoluteObject.getTransform().alterSize(2, 2);
        assertEquals(new Vector2(2, 2), absoluteObject.getTransform().getSize());
    }
}
