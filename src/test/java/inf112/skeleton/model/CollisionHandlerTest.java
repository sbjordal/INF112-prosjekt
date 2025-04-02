package inf112.skeleton.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CollisionHandlerTest {
    private CollisionHandler handler;

    @BeforeEach
    public void setUp() {
        CollisionHandler handler= new CollisionHandler(500);
    }

    @Test
    public void intitializeCollisionHandler() { // TODO: teste soundhandler her eller skulle den flyttes?
        int ceilingHeight= handler.getCeilingHeight();
        int attackCoolDown= handler.getATTACK_COOLDOWN();
        int bounceCoolDown= handler.getBOUNCE_COOLDOWN();
        handler.init();
        assertNotNull(handler);
        assertTrue(ceilingHeight > 0);
        assertEquals(500, ceilingHeight);
        assertEquals(800, attackCoolDown);
        assertEquals(64, bounceCoolDown);


    }
}
