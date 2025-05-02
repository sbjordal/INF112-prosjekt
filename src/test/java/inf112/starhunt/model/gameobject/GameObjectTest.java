package inf112.starhunt.model.gameobject;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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


        Vector2 zeroSize = new Vector2(0, 0);
        gameObject.setSize(zeroSize);
        assertTrue(zeroSize.epsilonEquals(gameObject.getTransform().getSize()));
    }

    @Test
    void testCallbackSettersDoNotThrow() {
        assertDoesNotThrow(() -> {
            gameObject.setOnCoinCollected(() -> {});
            gameObject.setOnCollisionWithEnemy(() -> {});
            gameObject.setOnCollisionWithEnemyDealDamage(() -> {});
            gameObject.setOnBananaCollected(() -> {});
            gameObject.setOnCollisionWithStar(() -> {});
        });
    }
}
