package inf112.starhunt.model;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import inf112.starhunt.model.gameobject.Collidable;
import inf112.starhunt.model.gameobject.CollisionBox;
import inf112.starhunt.model.gameobject.Transform;
import inf112.starhunt.model.gameobject.Visitor;
import inf112.starhunt.model.gameobject.fixedobject.Ground;
import inf112.starhunt.model.gameobject.fixedobject.item.Coin;
import inf112.starhunt.model.gameobject.mobileobject.actor.Player;
import inf112.starhunt.model.gameobject.mobileobject.actor.enemy.Enemy;
import inf112.starhunt.model.gameobject.mobileobject.actor.enemy.EnemyFactory;
import inf112.starhunt.model.gameobject.mobileobject.actor.enemy.EnemyType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WorldModelTest {

    private WorldModel worldModel;
    private Transform transform;
    private Coin coin;

    @BeforeAll
    static void setUpBeforeALl() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        ApplicationListener listener = new ApplicationAdapter() {
        };
        new HeadlessApplication(listener, config);
    }

    @BeforeEach
    void setUp() {
        worldModel = new WorldModel(1000, 500);

        Graphics mockGraphics = mock(Graphics.class);
        Gdx.graphics = mockGraphics;
        when(Gdx.graphics.getDeltaTime()).thenReturn(1 / 60f);

        transform = new Transform(new Vector2(0, 0), new Vector2(50, 100));
        worldModel.player = new Player(3, 5, transform);
        worldModel.collidables = new ArrayList<>();
        worldModel.enemies = new ArrayList<>();

        Transform coinTransform = new Transform(new Vector2(10, 20), new Vector2(30, 30));
        coin = new Coin(coinTransform);


    }

    @Test
    void testWorldModelInitialization() {
        assertNotNull(worldModel);
        assertEquals(0, worldModel.getTotalScore());
        assertEquals(0, worldModel.getCoinCounter());
        assertEquals(GameState.GAME_MENU, worldModel.getGameState());
    }


    @Test
    public void testSetMovement_rightDirection() {
        assertFalse(worldModel.isMovingRight);
        worldModel.setMovingRight(true);
        assertTrue(worldModel.isMovingRight);

        worldModel.setMovingRight(false);
        assertFalse(worldModel.isMovingRight);
    }

    @Test
    public void testSetMovement_leftDirection() {
        assertFalse(worldModel.isMovingLeft);
        worldModel.setMovingLeft(true);
        assertTrue(worldModel.isMovingLeft);

        worldModel.setMovingLeft(false);
        assertFalse(worldModel.isMovingLeft);
    }

    @Test void testIsJumping(){
        assertFalse(worldModel.isJumping);
        worldModel.setJumping(true);
        assertTrue(worldModel.isJumping);

        worldModel.setJumping(false);
        assertFalse(worldModel.isJumping);
    }

    // TODO: kommentert ut for å kompilere
//    @Test
//    public void testLegalMove_validMove() {
//        CollisionBox validMove = new CollisionBox(new Transform(new Vector2(10, 10), new Vector2(50, 50)));
//        assertTrue(worldModel.isLegalMove(validMove));
//    }
//
//    @Test
//    public void testLegalMove_invalidMove_outOfBounds() {
//        CollisionBox invalidMove = new CollisionBox(new Transform(new Vector2(-10, -10), new Vector2(50, 50)));
//        assertFalse(worldModel.isLegalMove(invalidMove));
//    }
//
//    @Test
//    public void testLegalMove_invalidMove_collision() {
//        CollisionBox collisionMove = new CollisionBox(new Transform(new Vector2(40, 40), new Vector2(50, 50)));
//        Collidable obstacle = new Ground(new Transform(new Vector2(40, 40), new Vector2(50, 50)));
//        worldModel.collidables.add(obstacle);
//
//        assertFalse(worldModel.isLegalMove(collisionMove));
//    }

    @Test
    void testInfoMode () {
        assertFalse(worldModel.getInfoMode());
        worldModel.setInfoMode(true);
        assertTrue(worldModel.getInfoMode());
    }

    @Test
    void testGamestate(){
        //Upon starting the game, the state should be GAME_MENU
        assertTrue(worldModel.gameState.equals(GameState.GAME_MENU));
        worldModel.resume();
        assertTrue(worldModel.gameState.equals(GameState.GAME_ACTIVE));
        worldModel.pause();
        worldModel.gameState.equals(GameState.GAME_PAUSED);
        worldModel.backToGameMenu();
        worldModel.gameState.equals(GameState.GAME_MENU);
        worldModel.resume();
    }

    @Test
    void testCheckForGameOver(){
        worldModel.gameState = GameState.GAME_ACTIVE;
        worldModel.player.receiveDamage(3);
        worldModel.checkForGameOver();
        assertTrue(worldModel.gameState == (GameState.GAME_OVER));
    }

    @Test
    void testShouldUpdateCountDownReturnsTrueWhenValid() {
        worldModel.countDown = 10;
        worldModel.lastScoreUpdate = System.currentTimeMillis() - 1500;
        worldModel.resume();
        assertTrue(worldModel.shouldUpdateCountDown());
    }

    @Test
    void testScoreUpdate(){
        worldModel.countDown = 10;
        worldModel.updateScore(true);
        assertTrue(worldModel.countDown == 9);
        worldModel.updateScore(false);
        assertTrue(worldModel.countDown == 9);
    }

    @Test
    void testEnemiesMovable(){
        Enemy en1 = EnemyFactory.createSnail(10, 10, EnemyType.SNAIL);
        Enemy en2 = EnemyFactory.createLeopard(15, 10, EnemyType.LEOPARD);
        worldModel.enemies.add(en1);
        worldModel.enemies.add(en2);
        worldModel.moveEnemies(1 / 60f);
        assertTrue(worldModel.enemies.get(0).getTransform().getPos().x == 11);
        assertTrue(worldModel.enemies.get(1).getTransform().getPos().x == 13);
    }

    @Test
    void testPauseAndResume() {
        worldModel.resume();
        assertEquals(GameState.GAME_ACTIVE, worldModel.getGameState());

        worldModel.pause();
        assertEquals(GameState.GAME_PAUSED, worldModel.getGameState());

        worldModel.backToGameMenu();
        assertEquals(GameState.GAME_MENU, worldModel.getGameState());
    }


    @Test
    void testGetCountDown() {
        // Standardverdi fra konstruktør/setUpModel
        assertEquals(150, worldModel.getCountDown());
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
        assertEquals(0, worldModel.player.getTotalScore());

        worldModel.player.visit(coin);
        assertEquals(5, worldModel.player.getTotalScore());
    }

    @Test
    void testGetCoinCounterReturnsCorrectValue() {
        assertEquals(0, worldModel.player.getCoinCounter());

        worldModel.player.visit(coin);
        assertEquals(1, worldModel.player.getCoinCounter());


    }

    @Test
    void testGetPlayerLivesReturnsCorrectValue() {
        Assertions.assertEquals(3, worldModel.player.getLives());

        worldModel.player.receiveDamage(1);
        Assertions.assertEquals(2, worldModel.player.getLives());
    }


    @Test
    public void testLegalMove_validMove() {
        Visitor visitor = worldModel.player;
        CollisionBox validMove = new CollisionBox(new Transform(new Vector2(10, 10), new Vector2(50, 50)));
        assertTrue(worldModel.isLegalMove(visitor, validMove));  // Sjekker om bevegelsen er lovlig
    }

    @Test
    public void testLegalMove_invalidMove_outOfBounds() {
        Visitor visitor = worldModel.player;
        CollisionBox invalidMove = new CollisionBox(new Transform(new Vector2(-10, -10), new Vector2(50, 50)));
        assertFalse(worldModel.isLegalMove(visitor, invalidMove));  // Sjekker om bevegelsen er ulovlig (utenfor grenser)
    }



}
