package inf112.starhunt.model.gameobject;

import com.badlogic.gdx.math.Vector2;
import inf112.starhunt.model.gameobject.mobileobject.actor.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CollisionBoxTest {
    Player player;
    @BeforeEach
    public void setUp() {
        Vector2 position= new Vector2(0,0);
        Vector2 size= new Vector2(10,10);
        player= new Player(3,1,new Transform(position,size));
    }
    @Test
    public void testGameObjectHasCollisionBox() {
        assertNotNull(player.getCollisionBox());
    }

    @Test
    public void testCollisionBoxCollisionFromRight() {
        GameObject gameObject= new GameObject(new Transform(new Vector2(20,0), new Vector2(10,10)));
        assertFalse(player.getCollisionBox().isCollidingFromRight(gameObject.getCollisionBox()));
        player.move(new Vector2(10,0));
        assertTrue(player.getCollisionBox().isCollidingFromRight(gameObject.getCollisionBox()));
        assertFalse(player.getCollisionBox().isCollidingFromLeft(gameObject.getCollisionBox()));
        assertFalse(player.getCollisionBox().isCollidingFromTop(gameObject.getCollisionBox()));
        assertFalse(player.getCollisionBox().isCollidingFromBottom(gameObject.getCollisionBox()));
    }

    @Test
    public void testCollisionBoxCollisionFromLeft() {
        GameObject gameobject= new GameObject(new Transform(new Vector2(-20,0), new Vector2(10,10)));
        assertFalse(player.getCollisionBox().isCollidingFromLeft(gameobject.getCollisionBox()));
        player.move(new Vector2(-10,0));
        assertTrue(player.getCollisionBox().isCollidingFromLeft(gameobject.getCollisionBox()));
        assertFalse(player.getCollisionBox().isCollidingFromRight(gameobject.getCollisionBox()));
        assertFalse(player.getCollisionBox().isCollidingFromTop(gameobject.getCollisionBox()));
        assertFalse(player.getCollisionBox().isCollidingFromBottom(gameobject.getCollisionBox()));
    }

    @Test
    public void testCollisionBoxCollisionFromTop() {
        GameObject gameobject= new GameObject(new Transform(new Vector2(0, -10), new Vector2(10,10)));
        assertFalse(player.getCollisionBox().isCollidingFromTop(gameobject.getCollisionBox()));
        player.move(new Vector2(0,-20)); // players collisionbox har str 10, så 10 + 10
        assertFalse(player.getCollisionBox().isCollidingFromLeft(gameobject.getCollisionBox()));
        assertFalse(player.getCollisionBox().isCollidingFromRight(gameobject.getCollisionBox()));
        assertFalse(player.getCollisionBox().isCollidingFromBottom(gameobject.getCollisionBox()));
        assertTrue(player.getCollisionBox().isCollidingFromTop(gameobject.getCollisionBox()));
    }

    @Test
    public void testCollisionBoxCollisionFromBottom() {
        GameObject gameobject= new GameObject(new Transform(new Vector2(0,10 ), new Vector2(10,10)));
        assertFalse(player.getCollisionBox().isCollidingFromBottom(gameobject.getCollisionBox()));
        player.move(new Vector2(0,10));

        assertFalse(player.getCollisionBox().isCollidingFromLeft(gameobject.getCollisionBox()));
        assertFalse(player.getCollisionBox().isCollidingFromRight(gameobject.getCollisionBox()));

        assertFalse(player.getCollisionBox().isCollidingFromTop(gameobject.getCollisionBox()));
        assertTrue(player.getCollisionBox().isCollidingFromBottom(gameobject.getCollisionBox()));
    }

    @Test
    void testSetPositionUpdatesBotLeftAndTopRight() {
        // Lag en CollisionBox med en kjent posisjon og størrelse
        Transform transform = new Transform(new Vector2(0, 0), new Vector2(10, 20));
        CollisionBox collisionBox = new CollisionBox(transform);

        // Ny posisjon som vi setter
        Vector2 newPosition = new Vector2(5, 5);
        collisionBox.setPosition(newPosition);

        // Sjekk at botLeft er oppdatert korrekt
        assertEquals(newPosition, collisionBox.getBotLeft(), "botLeft should be updated to the new position.");

        // Sjekk at topRight er korrekt oppdatert basert på width (10) og height (20)
        Vector2 expectedTopRight = new Vector2(newPosition.x + 10, newPosition.y + 20);
        assertEquals(expectedTopRight, collisionBox.getTopRight(), "topRight should be updated based on new position and size.");
    }

    @Test
    void testIsCollidingWithOverlappingBoxes() {
        // Lag to kollisjonsbokser som overlapper
        CollisionBox box1 = new CollisionBox(new Transform(new Vector2(0, 0), new Vector2(10, 10)));
        CollisionBox box2 = new CollisionBox(new Transform(new Vector2(5, 5), new Vector2(10, 10)));  // Overlapper med box1

        // Sjekk at de kolliderer
        assertTrue(box1.isCollidingWith(box2), "The boxes should collide when they overlap.");
    }

    @Test
    void testIsCollidingWithNonOverlappingBoxes() {
        // Lag to kollisjonsbokser som ikke overlapper
        CollisionBox box1 = new CollisionBox(new Transform(new Vector2(0, 0), new Vector2(10, 10)));
        CollisionBox box2 = new CollisionBox(new Transform(new Vector2(20, 20), new Vector2(10, 10)));  // Ikke overlappende

        // Sjekk at de ikke kolliderer
        assertFalse(box1.isCollidingWith(box2), "The boxes should not collide when they don't overlap.");
    }


    @Test
    void testIsCollidingWithOneBoxInsideAnother() {
        // Lag en kollisjonsboks som er inne i en annen
        CollisionBox box1 = new CollisionBox(new Transform(new Vector2(0, 0), new Vector2(20, 20)));
        CollisionBox box2 = new CollisionBox(new Transform(new Vector2(5, 5), new Vector2(10, 10)));  // Box2 er inne i box1

        // Sjekk at de kolliderer
        assertTrue(box1.isCollidingWith(box2), "The boxes should collide when one is inside the other.");
    }

}
