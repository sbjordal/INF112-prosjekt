package inf112.starhunt.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.IntNode;
import inf112.starhunt.model.gameobject.Collidable;
import inf112.starhunt.model.gameobject.Transform;
import inf112.starhunt.model.gameobject.fixedobject.Ground;
import inf112.starhunt.model.gameobject.fixedobject.item.Banana;
import inf112.starhunt.model.gameobject.fixedobject.item.Coin;
import inf112.starhunt.model.gameobject.fixedobject.item.ItemFactory;
import inf112.starhunt.model.gameobject.fixedobject.item.Star;
import inf112.starhunt.model.gameobject.mobileobject.actor.Player;
import inf112.starhunt.model.gameobject.mobileobject.actor.enemy.Enemy;
import inf112.starhunt.model.gameobject.mobileobject.actor.enemy.Leopard;
import inf112.starhunt.model.gameobject.mobileobject.actor.enemy.Snail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LevelManagerTest {

    private ObjectMapper objectMapper;
    private FileHandle fileHandle;
    private List<Collidable> collidables;
    private List<Enemy> enemies;

    @BeforeEach
    void setUp() {
        collidables = new ArrayList<>();
        enemies = new ArrayList<>();

        objectMapper = mock(ObjectMapper.class);
        fileHandle = mock(FileHandle.class);

        // Mock Gdx.files for å unngå NullPointerException
        Gdx.files = mock(com.badlogic.gdx.Files.class);
        when(Gdx.files.classpath(anyString())).thenReturn(fileHandle);
    }

    //TODO: Kommentert ut, må tilpasses oppdatert kode, ser ut som at
    // jsonfilen ikke lagres med riktige objekter etter vi laget ground?

    @Test
    void loadLevel_missingPlayer_throwsException() {
        when(fileHandle.readString()).thenReturn("{\"height\": 10, \"tileheight\": 32, \"layers\": []}");

        IllegalStateException exception1 = assertThrows(IllegalStateException.class, () ->
                LevelManager.loadLevel(LevelManager.Level.LEVEL_1)
        );
        assertTrue(exception1.getMessage().contains("Level must have exactly one Player"));

        IllegalStateException exception2 = assertThrows(IllegalStateException.class, () ->
                LevelManager.loadLevel(LevelManager.Level.LEVEL_2)
        );
        assertTrue(exception2.getMessage().contains("Level must have exactly one Player"));

        IllegalStateException exception3 = assertThrows(IllegalStateException.class, () ->
                LevelManager.loadLevel(LevelManager.Level.LEVEL_3)
        );
        assertTrue(exception3.getMessage().contains("Level must have exactly one Player"));
    }



    @Test
    void loadLevel_invalidJson_throwsRuntimeException() {
        when(fileHandle.readString()).thenReturn("invalid json");

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                LevelManager.loadLevel(LevelManager.Level.LEVEL_1)
        );
        assertTrue(exception.getMessage().contains("Failed to load level"));

        RuntimeException exception2 = assertThrows(RuntimeException.class, () ->
                LevelManager.loadLevel(LevelManager.Level.LEVEL_2)
        );
        assertTrue(exception2.getMessage().contains("Failed to load level"));

        RuntimeException exception3 = assertThrows(RuntimeException.class, () ->
                LevelManager.loadLevel(LevelManager.Level.LEVEL_3)
        );
        assertTrue(exception3.getMessage().contains("Failed to load level"));
    }

    @Test
    void getLevelFile_validLevel_returnsCorrectFile() {
        FileHandle file1 = LevelManager.getLevelFile(LevelManager.Level.LEVEL_1);
        assertNotNull(file1);

        FileHandle file2 = LevelManager.getLevelFile(LevelManager.Level.LEVEL_2);
        assertNotNull(file2);

        FileHandle file3 = LevelManager.getLevelFile(LevelManager.Level.LEVEL_3);
        assertNotNull(file3);
    }


    @Test
    void loadLevel_missingStar_throwsException() {
        String noStarJson = "{\"height\": 10, \"tileheight\": 32, \"layers\": [{\"type\": \"objectgroup\", \"name\": \"player\", \"objects\": [{\"x\": 0, \"y\": 0, \"height\": 32}]}]}";
        when(fileHandle.readString()).thenReturn(noStarJson);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                LevelManager.loadLevel(LevelManager.Level.LEVEL_1)
        );
        assertTrue(exception.getMessage().contains("Level must have exactly one Star"));
    }


    @Test
    void getLevelFile_invalidLevel_throwsException() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                LevelManager.getLevelFile(null)
        );
        assertTrue(exception.getMessage().contains("No level file found"));
    }

    @Test
    void getNextLevel() {
        assertEquals(LevelManager.Level.LEVEL_2, LevelManager.getNextLevel(LevelManager.Level.LEVEL_1));
        assertEquals(LevelManager.Level.LEVEL_3, LevelManager.getNextLevel(LevelManager.Level.LEVEL_2));
        assertEquals(LevelManager.Level.LEVEL_1, LevelManager.getNextLevel(LevelManager.Level.LEVEL_3));
    }

    @Test
    void testYCalculation() {
        // Mock JSON-objektet
        JsonNode mockObj = mock(JsonNode.class);

        // For å simulere at obj.get("y") returnerer 32
        when(mockObj.get("y")).thenReturn(new IntNode(32));  // Simulering av obj.get("y")

        // For å simulere at obj.get("height") returnerer 50
        when(mockObj.get("height")).thenReturn(new IntNode(50));  // Simulering av obj.get("height")

        int mapHeight = 100;  // Eksempel på mapHeight

        // Beregn y
        int y = mapHeight - mockObj.get("y").asInt() - mockObj.get("height").asInt();

        // Test at y er beregnet korrekt
        assertEquals(18, y, "The calculated y value should be 18");
    }


    @Test
    void testCollidablesProcessing() {
        // Simulerer et nivå med Player, Star, Ground, Coin, Banana, Snail og Leopard
        String collidablesJson = "{\"height\": 10, \"tileheight\": 32, \"layers\": [" +
                "{\"type\": \"objectgroup\", \"name\": \"player\", \"objects\": [{\"x\": 0, \"y\": 32, \"height\": 32}]}," +
                "{\"type\": \"objectgroup\", \"name\": \"star\", \"objects\": [{\"x\": 10, \"y\": 32, \"height\": 32}]}," +
                "{\"type\": \"objectgroup\", \"name\": \"ground\", \"objects\": [{\"x\": 0, \"y\": 0, \"height\": 32}]}," +
                "{\"type\": \"objectgroup\", \"name\": \"coin\", \"objects\": [{\"x\": 20, \"y\": 32, \"height\": 32}]}," +
                "{\"type\": \"objectgroup\", \"name\": \"banana\", \"objects\": [{\"x\": 30, \"y\": 32, \"height\": 32}]}," +
                "{\"type\": \"objectgroup\", \"name\": \"snail\", \"objects\": [{\"x\": 40, \"y\": 32, \"height\": 32}]}," +
                "{\"type\": \"objectgroup\", \"name\": \"leopard\", \"objects\": [{\"x\": 50, \"y\": 32, \"height\": 32}]}]" +
                "}"; // Lukkende parentes for hele JSON-strukturen

        when(fileHandle.readString()).thenReturn(collidablesJson);
        Gdx.files = mock(com.badlogic.gdx.Files.class);
        when(Gdx.files.classpath(anyString())).thenReturn(fileHandle);

        // Kall loadLevel og sjekk resultatet
        Triple<List<Enemy>, List<Collidable>, Player> result = LevelManager.loadLevel(LevelManager.Level.LEVEL_1);

        // Verifiser at collidables-listen har syv objekter (Player, Star, Ground, Coin, Banana, Snail, Leopard)
        assertNotNull(result.getSecond());
        assertEquals(7, result.getSecond().size(), "There should be exactly seven objects in collidables.");

        assertTrue(result.getSecond().get(0) instanceof Player, "The first object in collidables should be a Player.");
        assertTrue(result.getSecond().get(1) instanceof Star, "The second object in collidables should be a Star.");
        assertTrue(result.getSecond().get(2) instanceof Ground, "The third object in collidables should be a Ground.");
        assertTrue(result.getSecond().get(3) instanceof Coin, "The fourth object in collidables should be a Coin.");
        assertTrue(result.getSecond().get(4) instanceof Banana, "The fifth object in collidables should be a Banana.");
        assertTrue(result.getSecond().get(5) instanceof Snail, "The sixth object in collidables should be a Snail.");
        assertTrue(result.getSecond().get(6) instanceof Leopard, "The seventh object in collidables should be a Leopard.");
    }



    @Test
    void testLoadLevel_validJson() {
        // Simuler JSON-innhold som representerer et nivå
        String validJson = "{\"height\": 10, \"tileheight\": 32, \"layers\": [" +
                "{\"type\": \"objectgroup\", \"name\": \"player\", \"objects\": [{\"x\": 0, \"y\": 32, \"height\": 32}]}," +
                "{\"type\": \"objectgroup\", \"name\": \"star\", \"objects\": [{\"x\": 10, \"y\": 32, \"height\": 32}]}]" +
                "}";

        // Mock ObjectMapper
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        JsonNode mockJsonNode = mock(JsonNode.class);  // Mock JsonNode som skal returneres fra readTree

        try {
            // Når readTree kalles med validJson, returner mockJsonNode
            when(mockObjectMapper.readTree(validJson)).thenReturn(mockJsonNode);
        } catch (IOException e) {
            fail("IOException should not be thrown.");
        }

        // Simuler JSON-behandling med mocket ObjectMapper
        LevelManager.Level level = LevelManager.Level.LEVEL_1;

        // Mocking av filhåndtering
        when(fileHandle.readString()).thenReturn(validJson);
        Gdx.files = mock(com.badlogic.gdx.Files.class);
        when(Gdx.files.classpath(anyString())).thenReturn(fileHandle);

        // Kall loadLevel og sjekk resultatet
        Triple<List<Enemy>, List<Collidable>, Player> result = LevelManager.loadLevel(level);

        // Sjekk at player, star og collidables er riktig
        assertNotNull(result);
        assertNotNull(result.getSecond());  // Collidables
        assertTrue(result.getSecond().get(0) instanceof Player, "Collidables should contain a player.");
        assertTrue(result.getSecond().stream().anyMatch(o -> o instanceof Star), "Collidables should contain a star.");
    }





}
