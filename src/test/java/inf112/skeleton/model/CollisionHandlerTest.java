package inf112.skeleton.model;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.gameobject.CollisionBox;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.gameobject.fixedobject.FixedObject;
import inf112.skeleton.model.gameobject.fixedobject.item.Banana;
import inf112.skeleton.model.gameobject.fixedobject.item.Coin;
import inf112.skeleton.model.gameobject.fixedobject.item.Item;
import inf112.skeleton.model.gameobject.fixedobject.item.ItemFactory;
import inf112.skeleton.model.gameobject.mobileobject.actor.Player;
import inf112.skeleton.model.gameobject.mobileobject.actor.enemy.Enemy;
import inf112.skeleton.model.gameobject.mobileobject.actor.enemy.EnemyFactory;
import inf112.skeleton.model.gameobject.mobileobject.actor.enemy.EnemyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static inf112.skeleton.model.LevelManager.Level.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class CollisionHandlerTest {
    private CollisionHandler handler;
    private SoundHandler mockSoundHandler;

    @BeforeEach
    public void setUp() {
        handler= new CollisionHandler(500);
        mockSoundHandler = mock(SoundHandler.class);
        Gdx.app = mock(Application.class);

    }

    @Test
    public void testInitializationCollisionHandler() { // TODO: teste soundhandler her eller skulle den flyttes?
        int ceilingHeight= handler.getCeilingHeight();
        int attackCoolDown= handler.getATTACK_COOLDOWN();
        int bounceCoolDown= handler.getBOUNCE_COOLDOWN();
        assertNotNull(handler);
        assertEquals(500, ceilingHeight);
        assertEquals(800, attackCoolDown);
        assertEquals(64, bounceCoolDown);
    }

    @Test
    public void testCheckCollision() {
        //checks for player collision
        Player player= new Player(3,10, new Transform(new Vector2(0,0), new Vector2(2,2)));
        List<GameObject> objects1= new ArrayList<>();
        objects1.add(player);
        Pair<Boolean, GameObject> result1= handler.checkCollision(player, objects1, player.getCollisionBox());

        assertFalse(result1.first);
        assertNull(result1.second);

        //Checks for game objects = Item but not ground or ceiling
        List<GameObject> objects2= new ArrayList<>();
        Item item= ItemFactory.createCoin(0,0);
        objects2.add(item);
        Pair<Boolean, GameObject> result2= handler.checkCollision(player, objects2, player.getCollisionBox());

        assertTrue(result2.first);
        assertInstanceOf(GameObject.class, result2.second);

        //checks for gameobject= ceiling
        CollisionHandler handler =new CollisionHandler(19);
        List<GameObject> objects3= new ArrayList<>();
        FixedObject ceiling = new FixedObject(new Transform(new Vector2(0,1), new Vector2(20,20)));
        objects3.add(ceiling);
        Pair<Boolean, GameObject> result3= handler.checkCollision(player, objects3, player.getCollisionBox());
        assertTrue(result3.first);
        assertNull(result3.second);

        //checks for gameobject=ground #TODO gjør ferdig denne
        List<GameObject> objects4= new ArrayList<>();
        FixedObject ground = new FixedObject(new Transform(new Vector2(0,-18), new Vector2(20,20)));
        objects4.add(ground);
        Pair<Boolean, GameObject> result4= handler.checkCollision(player, objects4, player.getCollisionBox());
        assertTrue(result4.first);
        //assertNull(result4.second);


    }

    @Test
    public void testHandleEnemyCollsion(){
        Enemy enemy= EnemyFactory.createLeopard(1,0, EnemyType.SNAIL);
        Player player= new Player(3,10, new Transform(new Vector2(0,0), new Vector2(2,2)));
        int result1= handler.handleEnemyCollision(player, enemy, 13, new CollisionBox(new Transform(new Vector2(1,0), new Vector2(2,2))));
        assertEquals(9, result1);
        int result2= handler.handleEnemyCollision(player, enemy, 13, new CollisionBox(new Transform(new Vector2(40,0), new Vector2(2,2))));
        //System.out.println(result2);

    }



    @Test
    public void testHandleStarCollision() {
        assertThrows(NullPointerException.class, () -> {handler.handleStarCollision(null);});
        assertEquals(LEVEL_1, handler.handleStarCollision(LEVEL_3));
        assertEquals(LEVEL_2, handler.handleStarCollision(LEVEL_1));
        assertEquals(LEVEL_3, handler.handleStarCollision(LEVEL_2));
    }

    @Test
    public void testHandleBananaCollision() {
        Player player= new Player(3,50, new Transform(new Vector2(0,0), new Vector2(50,50)));
        Banana banana= ItemFactory.createBanana(0,0);
        handler.handleBananaCollision(player, banana);
        assertTrue(player.getHasPowerUp());
        assertEquals(banana.getLargePlayerSize(), player.getTransform().getSize());
        assertEquals(banana.getBigJumpForce(), player.getJumpForce());


    }

    @Test
    public void testHandleCoinCollision() {
        Coin coin  = ItemFactory.createCoin(0,0);
        int totalScore=10;
        int newScore= handler.handleCoinCollision(coin, totalScore);
        assertEquals(15, newScore);



    }

}
