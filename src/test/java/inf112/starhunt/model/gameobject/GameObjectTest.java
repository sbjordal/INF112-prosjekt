package inf112.starhunt.model.gameobject;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class GameObjectTest {
    GameObject gameObject;

    @BeforeEach
    public void setUp() {
        this.gameObject = new GameObject(TransformUtils.createTransformForObjects(0,0,10,10));
    }

    @Test
    void testDefaultDirection(){
        assertEquals(0, gameObject.getDirection());
    }

    @Test
    void testSetSize(){
        Vector2 newSize = new Vector2(20, 20);
        assertFalse(newSize.epsilonEquals(gameObject.getTransform().getSize()));

        gameObject.setSize(newSize);
        assertTrue(newSize.epsilonEquals(gameObject.getTransform().getSize()));
    }
}
