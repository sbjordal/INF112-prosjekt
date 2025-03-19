package inf112.skeleton.app.model;

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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

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
    @Test
    public void testScoreIncreasesWhenCoinCollected() {
        int initialScore = worldModel.getTotalScore();
        worldModel.handleCoinCollision(new Coin(transform));
        assertTrue(worldModel.getTotalScore() > initialScore);
    }

}

//    @BeforeAll
//    static void setUpBeforeALl() {
//        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
//        ApplicationListener listener = new ApplicationAdapter() {
//        };
//        new HeadlessApplication(listener, config);
//
//    }
//
//    /**
//     * Setup method called before each of the test methods
//     */
//    @BeforeEach
//    void setUpBeforeEach() {
//        MockitoAnnotations.openMocks(this);
//        worldModel = new WorldModel(800, 600);
//
//        // Mock Gdx.graphics
//        Gdx.graphics = mock(com.badlogic.gdx.Graphics.class);
//        when(Gdx.graphics.getDeltaTime()).thenReturn(1.0f);
//    }
//}


//    @Test
//    public void testSetMovement_rightDirection() {
//        // Før vi setter bevegelse til høyre, skal isMovingRight være false
//        assertFalse(worldModel.isMovingRight());
//
//        // Sett bevegelsen til høyre
//        worldModel.setMovement(Direction.RIGHT);
//
//        // Etter at vi setter bevegelsen til høyre, skal isMovingRight være true
//        assertTrue(worldModel.isMovingRight());
//
//        // Sett bevegelsen til høyre igjen, og den skal reversere seg tilbake til false
//        worldModel.setMovement(Direction.RIGHT);
//        assertFalse(worldModel.isMovingRight());
//    }
//
//    @Test
//    public void testSetMovement_leftDirection() {
//        // Før vi setter bevegelse til venstre, skal isMovingLeft være false
//        assertFalse(worldModel.isMovingLeft());
//
//        // Sett bevegelsen til venstre
//        worldModel.setMovement(Direction.LEFT);
//
//        // Etter at vi setter bevegelsen til venstre, skal isMovingLeft være true
//        assertTrue(worldModel.isMovingLeft());
//
//        // Sett bevegelsen til venstre igjen, og den skal reversere seg tilbake til false
//        worldModel.setMovement(Direction.LEFT);
//        assertFalse(worldModel.isMovingLeft());
//    }

//    @Test
//    public void testLegalMove_validMove() {
//        // Test for en gyldig bevegelse (innenfor brettet, uten kollisjoner)
//        CollisionBox validMove = new CollisionBox(new Transform(new Vector2(10, 10), new Vector2(50, 50)));
//        // Anta at det ikke er objekter i verden som blokkerer på dette punktet
//        assertTrue(worldModel.isLegalMove(validMove));
//    }
//
//
//    @Test
//    public void testLegalMove_invalidMove_outOfBounds() {
//        // Test for en ugyldig bevegelse (utenfor brettet)
//        CollisionBox invalidMove = new CollisionBox(new Transform(new Vector2(-10, -10), new Vector2(50, 50)));
//        assertFalse(worldModel.isLegalMove(invalidMove));
//    }
//
//    @Test
//    public void testLegalMove_invalidMove_collision() {
//        // Test for en ugyldig bevegelse (med kollisjon med et objekt)
//        CollisionBox collisionMove = new CollisionBox(new Transform(new Vector2(40, 40), new Vector2(50, 50)));
//        // Legg til et objekt som blokkerer bevegelsen
//        GameObject obstacle = new FixedObject(new Transform(new Vector2(40, 40), new Vector2(50, 50)), new Texture("obstacle.png"));
//        worldModel.getObjectList().add(obstacle);
//
//        assertFalse(worldModel.isLegalMove(collisionMove));
//    }

