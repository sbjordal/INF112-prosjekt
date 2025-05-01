package inf112.starhunt.model;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import inf112.starhunt.model.gameobject.CollisionBox;
import inf112.starhunt.model.gameobject.Transform;
import inf112.starhunt.model.gameobject.Visitor;
import inf112.starhunt.model.gameobject.fixedobject.item.Coin;
import inf112.starhunt.model.gameobject.mobileobject.actor.Player;
import inf112.starhunt.model.gameobject.mobileobject.actor.enemy.Enemy;
import inf112.starhunt.model.gameobject.mobileobject.MobileObjectFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WorldModelTest {

    private WorldModel model;
    private Transform transform;
    private Coin coin;
    private List<Enemy> enemies;
    private GameState gameState;

    @BeforeAll
    static void setUpBeforeALl() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        ApplicationListener listener = new ApplicationAdapter() {
        };
        new HeadlessApplication(listener, config);
    }

    @BeforeEach
    void setUp() {
        model = new WorldModel(1000, 500);

        Graphics mockGraphics = mock(Graphics.class);
        Gdx.graphics = mockGraphics;
        when(Gdx.graphics.getDeltaTime()).thenReturn(1 / 60f);

        transform = new Transform(new Vector2(0, 0), new Vector2(50, 100));
        model.player = new Player(3, 5, transform);
        model.setCollidables(new ArrayList<>());
        this.enemies = new ArrayList<>();

        Transform coinTransform = new Transform(new Vector2(10, 20), new Vector2(30, 30));
        coin = new Coin(coinTransform);

    }

    @Test
    void testWorldModelInitialization() {
        assertNotNull(model);
        assertEquals(0, model.getTotalScore());
        assertEquals(0, model.getCoinCounter());
        assertEquals(GameState.GAME_MENU, model.getGameState());
        assertNotNull(model.getObjectList());
        assertNotNull(model.getViewablePlayer());
        assertEquals(1, model.getLevelCounter());
    }


    @Test
    public void testSetMovement_rightDirection() {
        assertFalse(model.getIsMovingRight());
        model.setMovingRight(true);
        assertTrue(model.getIsMovingRight());

        model.setMovingRight(false);
        assertFalse(model.getIsMovingRight());
    }

    @Test
    public void testSetMovement_leftDirection() {
        assertFalse(model.getIsMovingLeft());
        model.setMovingLeft(true);
        assertTrue(model.getIsMovingLeft());

        model.setMovingLeft(false);
        assertFalse(model.getIsMovingLeft());
    }

    @Test
    void testIsJumping() {
        assertFalse(model.getIsJumping());
        model.setJumping(true);
        assertTrue(model.getIsJumping());

        model.setJumping(false);
        assertFalse(model.getIsJumping());
    }

    @Test
    void testInfoMode() {
        assertFalse(model.getInfoMode());
        model.setInfoMode(true);
        assertTrue(model.getInfoMode());
    }

    @Test
    void testGamestate() {
        //Upon starting the game, the state should be GAME_MENU
        assertTrue(model.getGameState().equals(GameState.GAME_MENU));
        model.resume();
        assertTrue(model.getGameState().equals(GameState.GAME_ACTIVE));
        model.pause();
        model.getGameState().equals(GameState.GAME_PAUSED);
        model.backToGameMenu();
        model.getGameState().equals(GameState.GAME_MENU);
        model.resume();
    }

    @Test
    void testCheckForGameOver() {
        model.setGameState(GameState.GAME_ACTIVE);
        model.player.receiveDamage(3);
        model.checkForGameOver();
        assertTrue(model.getGameState() == (GameState.GAME_OVER));
    }

    @Test
    void testShouldUpdateCountDownReturnsTrueWhenValid() {
        model.setCountDown(10);
        model.setLastScoreUpdate(System.currentTimeMillis() - 1500);
        model.resume();
        assertTrue(model.shouldUpdateCountDown());
    }

    @Test
    void testScoreUpdate() {
        model.setCountDown(10);
        model.updateScore(true);
        assertTrue(model.getCountDown() == 9);
        model.updateScore(false);
        assertTrue(model.getCountDown() == 9);
    }

    @Test
    void testEnemiesMovable() {
        Enemy en1 = MobileObjectFactory.createSnail(10, 10);
        Enemy en2 = MobileObjectFactory.createLeopard(15, 10);
        enemies.add(en1);
        enemies.add(en2);

        model.setEnemies(enemies);
        model.moveEnemies(1 / 60f);
        assertTrue(model.getEnemies().get(0).getTransform().getPos().x == 11);
        assertTrue(model.getEnemies().get(1).getTransform().getPos().x == 13);
    }

    @Test
    void testPauseAndResume() {
        model.resume();
        assertEquals(GameState.GAME_ACTIVE, model.getGameState());

        model.pause();
        assertEquals(GameState.GAME_PAUSED, model.getGameState());

        model.backToGameMenu();
        assertEquals(GameState.GAME_MENU, model.getGameState());
    }


    @Test
    void testGetCountDown() {
        // Standardverdi fra konstruktør/setUpModel
        assertEquals(150, model.getCountDown());
    }

    @Test
    void testLevelCounter() {
        assertEquals(1, model.getLevelCounter());
    }

    @Test
    void testGetLevelWidthReturnsCorrectValue() {
        WorldModel worldModel = new WorldModel(4500, 900);
        assertEquals(4500, worldModel.getLevelWidth());
    }

    @Test
    void testGetBoardWidthReturnsCorrectValue() {
        WorldModel worldModel = new WorldModel(4500, 900);
        assertEquals(4500, worldModel.getBoardWidth());
    }


    @Test
    void testGetTotalScoreReturnsCorrectValue() {
        assertEquals(0, model.player.getTotalScore());

        model.player.visitCoin(coin);
        assertEquals(5, model.player.getTotalScore());
        assertEquals(5, model.getTotalScore());
    }

    @Test
    void testGetCoinCounterReturnsCorrectValue() {
        assertEquals(0, model.player.getCoinCounter());

        model.player.visitCoin(coin);
        assertEquals(1, model.player.getCoinCounter());
        assertEquals(1, model.getCoinCounter());


    }

    @Test
    void testGetPlayerLivesReturnsCorrectValue() {
        Assertions.assertEquals(3, model.player.getLives());
        assertEquals(3, model.getPlayerLives());

        model.player.receiveDamage(1);

        Assertions.assertEquals(2, model.player.getLives());
        assertEquals(2, model.getPlayerLives());
    }


    @Test
    public void testLegalMove_validMove() {
        Visitor visitor = model.player;
        CollisionBox validMove = new CollisionBox(new Transform(new Vector2(10, 10), new Vector2(50, 50)));
        assertTrue(model.isLegalMove(visitor, validMove));  // Sjekker om bevegelsen er lovlig
    }

    @Test
    public void testLegalMove_invalidMove_outOfBounds() {
        Visitor visitor = model.player;
        CollisionBox invalidMove = new CollisionBox(new Transform(new Vector2(-10, -10), new Vector2(50, 50)));
        assertFalse(model.isLegalMove(visitor, invalidMove));  // Sjekker om bevegelsen er ulovlig (utenfor grenser)
    }

    @Test
    public void testGetMovementDirection() {
        assertEquals(0, model.getMovementDirection());

        model.player.setMovementDirection(1);
        assertEquals(1, model.getMovementDirection());

        model.player.setMovementDirection(-1);
        assertEquals(-1, model.getMovementDirection());
    }

    @Test
    void testPositionIsOnBoard() {

        Transform transform = new Transform(new Vector2(100, 100), new Vector2(200, 200));
        CollisionBox box = new CollisionBox(transform);

        assertTrue(model.isLegalMove(mock(Visitor.class), box));
    }
}
