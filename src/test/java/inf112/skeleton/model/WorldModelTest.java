package inf112.skeleton.model;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.gameobject.CollisionBox;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.gameobject.fixedobject.FixedObject;
import inf112.skeleton.model.gameobject.mobileobject.actor.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WorldModelTest {

    private WorldModel worldModel;
    private Player player;
    private Transform transform;
    private List<GameObject> objectList;

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
        when(Gdx.graphics.getDeltaTime()).thenReturn(1 / 60f); // 60 FPS som eksempel

        transform = new Transform(new Vector2(0, 0), new Vector2(50, 100));
        worldModel.player = new Player(3, 5, transform);
        worldModel.objectList = new ArrayList<>();
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

    @Test
    public void testLegalMove_validMove() {
        CollisionBox validMove = new CollisionBox(new Transform(new Vector2(10, 10), new Vector2(50, 50)));
        assertTrue(worldModel.isLegalMove(validMove));
    }

    @Test
    public void testLegalMove_invalidMove_outOfBounds() {
        CollisionBox invalidMove = new CollisionBox(new Transform(new Vector2(-10, -10), new Vector2(50, 50)));
        assertFalse(worldModel.isLegalMove(invalidMove));
    }

    @Test
    public void testLegalMove_invalidMove_collision() {
        CollisionBox collisionMove = new CollisionBox(new Transform(new Vector2(40, 40), new Vector2(50, 50)));
        GameObject obstacle = new FixedObject(new Transform(new Vector2(40, 40), new Vector2(50, 50)));
        worldModel.objectList.add(obstacle);

        assertFalse(worldModel.isLegalMove(collisionMove));
    }

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
}
