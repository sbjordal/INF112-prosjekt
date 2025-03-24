package inf112.skeleton.app.model.gameobject;

import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.gameobject.mobileobject.actor.Player;
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
        System.out.println("Player reset to position: " + player.getTransform().getPos());

    }
    @Test
    public void testGameObjectHasCollisionBox() {
        assertNotNull(player.getCollisionBox());

    }

    @Test
    public void testCollisionBoxCollisionFromRight() {
        GameObject gameObject= new GameObject(new Transform(new Vector2(-10,0), new Vector2(10,10)));
        assertFalse(player.getCollisionBox().isCollidingFromRight(gameObject.getCollisionBox()));
        player.move(new Vector2(-1,0));
        assertTrue(player.getCollisionBox().isCollidingFromRight(gameObject.getCollisionBox()));
        assertFalse(player.getCollisionBox().isCollidingFromLeft(gameObject.getCollisionBox()));
        assertFalse(player.getCollisionBox().isCollidingFromTop(gameObject.getCollisionBox()));
        assertFalse(player.getCollisionBox().isCollidingFromBottom(gameObject.getCollisionBox()));

    }

    @Test
    public void testCollisionBoxCollisionFromLeft() {
        GameObject gameobject= new GameObject(new Transform(new Vector2(10,0), new Vector2(10,10)));
        assertFalse(player.getCollisionBox().isCollidingFromLeft(gameobject.getCollisionBox()));
        player.move(new Vector2(1,0));
        assertTrue(player.getCollisionBox().isCollidingFromLeft(gameobject.getCollisionBox()));
        assertFalse(player.getCollisionBox().isCollidingFromRight(gameobject.getCollisionBox()));
        assertFalse(player.getCollisionBox().isCollidingFromTop(gameobject.getCollisionBox()));
        assertFalse(player.getCollisionBox().isCollidingFromBottom(gameobject.getCollisionBox()));
    }

    @Test
    public void testCollisionBoxCollisionFromTop() { //Dobbelt sjekke logikk her. står at den kolliderer fra top og bottom samtidig
        GameObject gameobject= new GameObject(new Transform(new Vector2(0, -10), new Vector2(10,10)));
        assertFalse(player.getCollisionBox().isCollidingFromTop(gameobject.getCollisionBox()));
        player.move(new Vector2(0,-1));
        assertTrue(player.getCollisionBox().isCollidingFromTop(gameobject.getCollisionBox()));
        assertFalse(player.getCollisionBox().isCollidingFromLeft(gameobject.getCollisionBox()));
        assertFalse(player.getCollisionBox().isCollidingFromRight(gameobject.getCollisionBox()));
        assertFalse(player.getCollisionBox().isCollidingFromBottom(gameobject.getCollisionBox()));

    }

    @Test
    public void testCollisionBoxCollisionFromBottom() {
        GameObject gameobject= new GameObject(new Transform(new Vector2(0,11 ), new Vector2(10,10)));
        assertFalse(player.getCollisionBox().isCollidingFromBottom(gameobject.getCollisionBox()));
        player.move(new Vector2(0,1));
        assertTrue(player.getCollisionBox().isCollidingFromBottom(gameobject.getCollisionBox()));
        assertFalse(player.getCollisionBox().isCollidingFromLeft(gameobject.getCollisionBox()));
        assertFalse(player.getCollisionBox().isCollidingFromRight(gameobject.getCollisionBox()));
        assertFalse(player.getCollisionBox().isCollidingFromTop(gameobject.getCollisionBox()));
    }


}
