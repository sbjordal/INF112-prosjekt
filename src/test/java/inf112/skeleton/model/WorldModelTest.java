package inf112.skeleton.model;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.GameState;
import inf112.skeleton.model.WorldBoard;
import inf112.skeleton.model.WorldModel;
import inf112.skeleton.model.gameobject.CollisionBox;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.gameobject.fixedobject.FixedObject;
import inf112.skeleton.model.gameobject.fixedobject.item.Coin;
import inf112.skeleton.model.gameobject.mobileobject.actor.Player;
import inf112.skeleton.model.gameobject.mobileobject.actor.enemy.Enemy;
import inf112.skeleton.model.gameobject.mobileobject.actor.enemy.EnemyFactory;
import inf112.skeleton.model.gameobject.mobileobject.actor.enemy.EnemyType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// TODO: Testene fungerer ikke, får nullpointerexception fra gdx.
//  Må finne løsning for å kunne teste alt som har med gdx å gjøre. Mockito?
public class WorldModelTest {

    private WorldModel worldModel;
    private Player player;
    private Transform transform;

    @BeforeEach
    void setUp() {
        worldModel = new WorldModel(1000, 500);
        transform = new Transform(new Vector2(10, 20), new Vector2(30, 30));
        player = new Player(3, 5, transform);

    }

    @Test
    void testWorldModelInitialization() {
        assertNotNull(worldModel);
        assertEquals(0, worldModel.getTotalScore());
        assertEquals(0, worldModel.getCoinCounter());
        assertEquals(GameState.GAME_MENU, worldModel.getGameState());
    }


    @BeforeAll
    static void setUpBeforeALl() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        ApplicationListener listener = new ApplicationAdapter() {
        };
        new HeadlessApplication(listener, config);
    }

    /**
     * Setup method called before each of the test methods
     */
    @BeforeEach
    void setUpBeforeEach() {
        MockitoAnnotations.openMocks(this);
        worldModel = new WorldModel(800, 600);
        // Mock Gdx.graphics
        Gdx.graphics = mock(com.badlogic.gdx.Graphics.class);
        when(Gdx.graphics.getDeltaTime()).thenReturn(1.0f);
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

    @Test
    public void testLegalMove_validMove() {
        CollisionBox validMove = new CollisionBox(new Transform(new Vector2(10, 10), new Vector2(50, 50)));
        worldModel.objectList = new ArrayList<>();
        assertTrue(worldModel.isLegalMove(validMove));
    }

    @Test
    public void testLegalMove_invalidMove_outOfBounds() {
        CollisionBox invalidMove = new CollisionBox(new Transform(new Vector2(-10, -10), new Vector2(50, 50)));
        worldModel.objectList = new ArrayList<>();
        assertFalse(worldModel.isLegalMove(invalidMove));
    }

    @Test
    public void testLegalMove_invalidMove_collision() {
        CollisionBox collisionMove = new CollisionBox(new Transform(new Vector2(40, 40), new Vector2(50, 50)));
        worldModel.objectList = new ArrayList<>();
        GameObject obstacle = new FixedObject(new Transform(new Vector2(40, 40), new Vector2(50, 50)));
        worldModel.objectList.add(obstacle);

        assertFalse(worldModel.isLegalMove(collisionMove));
    }

    @Test
    void testIncreasedScore () {
    }
}
