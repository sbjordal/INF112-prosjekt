package inf112.skeleton.model;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.gameobject.fixedobject.item.Banana;
import inf112.skeleton.model.gameobject.fixedobject.item.Coin;
import inf112.skeleton.model.gameobject.fixedobject.item.ItemFactory;
import inf112.skeleton.model.gameobject.mobileobject.actor.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static inf112.skeleton.model.LevelManager.Level.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


public class CollisionHandlerTest {
    private CollisionHandler handler;

    @BeforeEach
    public void setUp() {
        handler= new CollisionHandler(500);
        Gdx.app = mock(Application.class);

    }

    @Test
    public void TestInitializationCollisionHandler() { // TODO: teste soundhandler her eller skulle den flyttes?
        int ceilingHeight= handler.getCeilingHeight();
        int attackCoolDown= handler.getATTACK_COOLDOWN();
        int bounceCoolDown= handler.getBOUNCE_COOLDOWN();
        assertNotNull(handler);
        assertEquals(500, ceilingHeight);
        assertEquals(800, attackCoolDown);
        assertEquals(64, bounceCoolDown);
    }

    @Test
    public void TestHandleStarCollision() {
        assertThrows(NullPointerException.class, () -> {handler.handleStarCollision(null);});
        assertEquals(LEVEL_1, handler.handleStarCollision(LEVEL_3));
        assertEquals(LEVEL_2, handler.handleStarCollision(LEVEL_1));
        assertEquals(LEVEL_3, handler.handleStarCollision(LEVEL_2));
    }

    @Test
    public void TestHandleBananaCollision() {
        Player player= new Player(3,50, new Transform(new Vector2(0,0), new Vector2(50,50)));
        Banana banana= ItemFactory.createBanana(0,0);
        handler.handleBananaCollision(player, banana);
        assertTrue(player.getHasPowerUp());
        assertEquals(banana.getLargePlayerSize(), player.getTransform().getSize());
        assertEquals(banana.getBigJumpForce(), player.getJumpForce());


    }

    @Test
    public void TestHandleCoinCollision() {
        Coin coin  = ItemFactory.createCoin(0,0);
        int totalScore=10;
        int newScore= handler.handleCoinCollision(coin, totalScore);
        assertEquals(15, newScore);


    }

}
