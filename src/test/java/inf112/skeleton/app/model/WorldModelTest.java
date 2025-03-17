package inf112.skeleton.app.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.WorldBoard;
import inf112.skeleton.model.WorldModel;
import inf112.skeleton.model.gameobject.CollisionBox;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.gameobject.fixedobject.FixedObject;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// TODO: Testene fungerer ikke, får nullpointerexception fra gdx.
//  Må finne løsning for å kunne teste alt som har med gdx å gjøre. Mockito?
public class WorldModelTest {
    private WorldModel worldModel;

    @Before
    public void setUp() {
        WorldBoard board = new WorldBoard(100, 100);
//        worldModel = new WorldModel(board);
        worldModel.create();  // Initialiser spillobjekter
    }

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
}

