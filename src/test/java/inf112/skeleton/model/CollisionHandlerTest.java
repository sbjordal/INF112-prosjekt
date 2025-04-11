package inf112.skeleton.model;

import com.badlogic.gdx.*;
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
        Gdx.files = mock(Files.class);
        Gdx.app = mock(Application.class);
        Gdx.graphics = mock(Graphics.class);
        Gdx.audio = mock(Audio.class);
        try (var graphicsMock = mockStatic(Gdx.graphics.getClass())) {
            graphicsMock.when(Gdx.graphics::getDeltaTime).thenReturn(0.016f);
        }

    }
    @Test
    public void testInit(){
        handler.init();
        assertNotNull(handler.getSoundHandler());
    }

    @Test
    public void testInitializationCollisionHandler() {
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
        Triple<Boolean, GameObject> result1= handler.checkCollision(player, objects1, player.getCollisionBox());

        assertFalse(result1.first);
        assertNull(result1.second);

        //Checks for game objects = Item but not ground or ceiling
        List<GameObject> objects2= new ArrayList<>();
        Item item= ItemFactory.createCoin(0,0);
        objects2.add(item);
        player.setVerticalVelocity(10);
        Triple<Boolean, GameObject> result2= handler.checkCollision(player, objects2, player.getCollisionBox());

        assertTrue(result2.first);
        assertInstanceOf(GameObject.class, result2.second);
        assertEquals(10, player.getVerticalVelocity(), 0.01); //checks that velocity does not change when the object is not ceiling or ground and colliding with player

        //checks ceiling
        int ceilingHeight= 19;
        CollisionHandler handler =new CollisionHandler(ceilingHeight);
        List<GameObject> objects3= new ArrayList<>();
        Item star= ItemFactory.createStar(20,20);
        objects3.add(star);
        Player playerTest1= new Player(3,10, new Transform(new Vector2(0,0), new Vector2(20,20)));
        Triple<Boolean, GameObject> result3= handler.checkCollision(playerTest1, objects3, playerTest1.getCollisionBox());
        assertTrue(playerTest1.getCollisionBox().topRight.y >= ceilingHeight - 1);
        assertTrue(result3.first);
        assertNull(result3.second);


        //checks if VerticalVelocity is larger than 0 when collision with ceiling
        CollisionHandler handler2 =new CollisionHandler(19);
        List<GameObject> objects4= new ArrayList<>();
        Item banana = ItemFactory.createBanana(0,0);
        objects4.add(banana);
        Player playerTest= new Player(3,10, new Transform(new Vector2(0,0), new Vector2(20,20)));
        int verticalVelocityBeforeCheckCollision= 10;
        playerTest.setVerticalVelocity(verticalVelocityBeforeCheckCollision);

        assertTrue(playerTest.getVerticalVelocity()>0);
        Triple<Boolean, GameObject> result5= handler2.checkCollision(playerTest, objects4, playerTest.getCollisionBox());

        assertNotEquals(verticalVelocityBeforeCheckCollision, playerTest.getVerticalVelocity());
        assertEquals(-1.0f, playerTest.getVerticalVelocity(), 0.01);


        //checks for gameobject=ground og istopcollision= True
        List<GameObject> objects5= new ArrayList<>();
        FixedObject ground = new FixedObject(new Transform(new Vector2(0,-1), new Vector2(31,31)));
        objects5.add(ground);
        Triple<Boolean, GameObject> result4= handler.checkCollision(player, objects5, player.getCollisionBox());
        assertTrue(result4.first);
        assertNull(result4.second);
    }

    @Test
    public void testHandleEnemyCollision(){
        long currentTime = System.currentTimeMillis();
        int totalScore = 3;
        Player player= new Player(3,10, new Transform(new Vector2(0,39), new Vector2(30,30)));
        Enemy enemy = EnemyFactory.createSnail(0,0, EnemyType.SNAIL);

        //Checks what happens when none of the conditions are met. values should remain unchanged
        long recentTime = currentTime - 10;
        player.setLastBounceTime(recentTime);
        player.setLastAttackTime(recentTime);
        handler.handleEnemyCollision(player, enemy, totalScore, player.getCollisionBox());


        assertEquals(recentTime, player.getLastAttackTime());
        assertEquals(recentTime, player.getLastBounceTime());
        assertEquals(3, totalScore);
        assertTrue(enemy.isAlive());

        //Test damage to enemy when condition currentTime-lastBounceTime is larger or equal to BOUNCE_COOLDOWN is true
        long bounce = 70;
        player.setLastBounceTime(bounce-100);
        int newTotalScore= handler.handleEnemyCollision(player, enemy, totalScore, player.getCollisionBox());

        assertNotEquals(bounce-100, player.getLastBounceTime());
        assertFalse(enemy.isAlive());
        assertEquals(totalScore+enemy.getObjectScore(), newTotalScore);

        //Test when condition currentTime - player.getLastAttackTime() >= ATTACK_COOLDOWN is true
        long theCurrentTime = System.currentTimeMillis();
        Enemy newEnemy = EnemyFactory.createSnail(0,0, EnemyType.SNAIL);
        player.setLastBounceTime(theCurrentTime-10);
        player.setLastAttackTime(theCurrentTime-1000);

        int newTotalScore1= handler.handleEnemyCollision(player, newEnemy, newTotalScore, new CollisionBox(new Transform(new Vector2(0,0), new Vector2(2,2))));
        assertEquals(newTotalScore-4, newTotalScore1);
        assertNotEquals(theCurrentTime-1000,player.getLastAttackTime());
        assertTrue(player.getLastAttackTime() > (theCurrentTime - 1000));
        assertTrue(newEnemy.isAlive());

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
        //Test when soundhandler is not null
        mockSoundHandler= mock(SoundHandler.class);
        Coin coin  = ItemFactory.createCoin(0,0);
        int totalScore=10;
        handler.setSoundHandler(mockSoundHandler);
        int score= handler.handleCoinCollision(coin, totalScore);
        verify(mockSoundHandler, times(1)).playCoinSound();
        assertNotNull(coin);
        assertEquals(totalScore+ coin.getObjectScore(), score);

        //Test when soundhandler is null
        handler.setSoundHandler(null);
        int newScore= handler.handleCoinCollision(coin, totalScore);
        verify(Gdx.app, times(1)).error(eq("CollisionHandler"), eq("SoundHandler is null"));
        assertEquals(totalScore+ coin.getObjectScore(), newScore);






    }

}
