package inf112.starhunt.model.gameobject;

import inf112.starhunt.model.gameobject.fixedobject.FixedObjectFactory;
import inf112.starhunt.model.gameobject.fixedobject.Ground;
import inf112.starhunt.model.gameobject.fixedobject.item.Banana;
import inf112.starhunt.model.gameobject.fixedobject.item.Coin;
import inf112.starhunt.model.gameobject.fixedobject.item.Star;
import inf112.starhunt.model.gameobject.mobileobject.actor.Player;
import inf112.starhunt.model.gameobject.mobileobject.actor.enemy.Enemy;
import inf112.starhunt.model.gameobject.mobileobject.actor.enemy.Snail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class GameObjectFactoryTest {

    private GameObject star;
    private GameObject ground;
    private GameObject coin;
    private GameObject banana;
    private GameObject enemy;
    private GameObject player;

    @BeforeEach
    void setUp(){
        this.ground = GameObjectFactory.createGameObject("ground_0000", 0.0f, 0.0f);
        this.star = GameObjectFactory.createGameObject("star", 0.0f, 0.0f);
        this.coin = GameObjectFactory.createGameObject("coin", 0.0f, 0.0f);
        this.banana = GameObjectFactory.createGameObject("banana", 0.0f, 0.0f);
        this.enemy = GameObjectFactory.createGameObject("snail", 0.0f, 0.0f);
        this.player = GameObjectFactory.createGameObject("player", 0.0f, 0.0f);

    }

    @Test
    void testCreateGameObjectNotNull(){
        GameObject gameObject = GameObjectFactory.createGameObject("banana", 0, 0);
        assertNotNull(gameObject);
    }

    @Test
    void testShouldCreateBanana() {
        assertNotNull(banana);
        assertEquals(0f, banana.getTransform().getPos().x, 0.001f);
        assertEquals(0f, banana.getTransform().getPos().y, 0.001f);
    }

    @Test
    void testShouldCreatePlayer() {
        assertNotNull(player);
        assertEquals(0f, player.getTransform().getPos().x, 0.001f);
        assertEquals(0f, player.getTransform().getPos().y, 0.001f);
    }

    @Test
    void testShouldCreateGroundWithAlteration() {
        assertNotNull(ground);
        assertEquals(0.0f, ground.getTransform().getPos().x, 0.001f);
        assertEquals(0.0f, ground.getTransform().getPos().y, 0.001f);
    }

    @Test
    void testShouldReturnCorrectObjectTypes() {
        assertTrue(ground instanceof Ground);
        assertTrue(banana instanceof Banana);
        assertTrue(star instanceof Star);
        assertTrue(coin instanceof Coin);
        assertTrue(enemy instanceof Snail);
        assertTrue(enemy instanceof Enemy);
        assertTrue(player instanceof Player);
    }

    @Test
    void testShouldUseGroundBranch() {
        GameObject ground = GameObjectFactory.createGameObject("ground_0000", 1f, 1f);
        assertNotNull(ground);
        assertEquals(1f, ground.getTransform().getPos().x, 0.001f);
    }

    @Test
    void testRegistryShouldReturnValidObject() {
        BiFunction<Float, Float, GameObject> creator = GameObjectFactory.registry.get("banana");
        assertNotNull(creator);
        GameObject obj = creator.apply(1.0f, 2.0f);
        assertNotNull(obj);
    }


}
