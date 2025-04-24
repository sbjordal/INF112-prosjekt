package inf112.starhunt.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LevelManagerTest {

    private ObjectMapper objectMapper;
    private FileHandle fileHandle;

    @BeforeEach
    void setUp() {
        objectMapper = mock(ObjectMapper.class);
        fileHandle = mock(FileHandle.class);

        // Mock Gdx.files for å unngå NullPointerException
        Gdx.files = mock(com.badlogic.gdx.Files.class);
        when(Gdx.files.classpath(anyString())).thenReturn(fileHandle);
    }

    //TODO: Kommentert ut, må tilpasses oppdatert kode, ser ut som at
    // jsonfilen ikke lagres med riktige objekter etter vi laget ground?
//    @Test
//    void loadLevel_validLevel_returnsCorrectPlayerAndObjects() {
//        String validJson = "{\"height\": 10, \"tileheight\": 32, \"layers\": [" +
//                "{\"type\": \"objectgroup\", \"name\": \"player\", \"objects\": [{\"x\": 0, \"y\": 32, \"height\": 32}]}," +
//                "{\"type\": \"objectgroup\", \"name\": \"star\", \"objects\": [{\"x\": 10, \"y\": 32, \"height\": 32}]}," +
//                "{\"type\": \"objectgroup\", \"name\": \"ground\", \"objects\": [{\"x\": 20, \"y\": 32, \"height\": 32}]}," +
//                "{\"type\": \"objectgroup\", \"name\": \"coin\", \"objects\": [{\"x\": 30, \"y\": 32, \"height\": 32}]}," +
//                "{\"type\": \"objectgroup\", \"name\": \"banana\", \"objects\": [{\"x\": 40, \"y\": 32, \"height\": 32}]}," +
//                "{\"type\": \"objectgroup\", \"name\": \"snail\", \"objects\": [{\"x\": 50, \"y\": 32, \"height\": 32}]}," +
//                "{\"type\": \"objectgroup\", \"name\": \"leopard\", \"objects\": [{\"x\": 60, \"y\": 32, \"height\": 32}]}]" +
//                "}";
//        when(fileHandle.readString()).thenReturn(validJson);
//
//        try {
//            Triple<List<Enemy>, List<Collidable>, Player> result = LevelManager.loadLevel(LevelManager.Level.LEVEL_1);
//
//            // Sjekk at resultatet ikke er null
//            assertNotNull(result);
//
//            // Sjekk at det er minst én spiller i objektlisten
//            assertNotNull(result.getSecond());
            //assertTrue(result.getFirst().contains(result.getSecond()));

            // Sjekk at antall objekter er riktig (7 totalt)
            //assertEquals(7, result.getFirst().size());

            // Sjekk at objektene inneholder hver type
//            boolean hasGround = false;
//            boolean hasCoin = false;
//            boolean hasBanana = false;
//            boolean hasSnail = false;
//            boolean hasLeopard = false;
//
//            for (GameObject obj : result.getFirst()) {
//                if (obj instanceof Ground) hasGround = true;
//                if (obj instanceof Player) assertEquals(0, obj.getTransform().getPos().x);
//                if (obj instanceof Player) assertEquals(256, obj.getTransform().getPos().y);
//                if (obj.getClass().getSimpleName().equals("Coin")) hasCoin = true;
//                if (obj.getClass().getSimpleName().equals("Banana")) hasBanana = true;
//                if (obj.getClass().getSimpleName().equals("Snail")) hasSnail = true;
//                if (obj.getClass().getSimpleName().equals("Leopard")) hasLeopard = true;



//            assertTrue(hasGround, "Ground object not found");
//            assertTrue(hasCoin, "Coin object not found");
//            assertTrue(hasBanana, "Banana object not found");
//            assertTrue(hasSnail, "Snail object not found");
//            assertTrue(hasLeopard, "Leopard object not found");
//
//        } catch (Exception e) {
//            fail("Exception thrown: " + e.getMessage());
//        }
//    }

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

}
