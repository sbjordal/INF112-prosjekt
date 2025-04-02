package inf112.skeleton.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.mobileobject.actor.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LevelHandlerTest {

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


    @Test
    void loadLevel_validLevel_returnsCorrectPlayerAndObjects() {
        String validJson = "{\"height\": 10, \"tileheight\": 32, \"layers\": [" +
                "{\"type\": \"objectgroup\", \"name\": \"player\", \"objects\": [{\"x\": 0, \"y\": 0, \"height\": 32}]}," +
                "{\"type\": \"objectgroup\", \"name\": \"star\", \"objects\": [{\"x\": 10, \"y\": 10, \"height\": 32}]}" +
                "]}";
        when(fileHandle.readString()).thenReturn(validJson);

        try {
            Pair<List<GameObject>, Player> result = LevelManager.loadLevel(LevelManager.Level.LEVEL_1);

            // Sjekk at resultatet ikke er null
            assertNotNull(result);

            // Sjekk at det er minst én spiller i objektlisten
            assertNotNull(result.getSecond());
            assertTrue(result.getFirst().contains(result.getSecond()));

            // Sjekk at antall objekter er riktig
            assertEquals(2, result.getFirst().size());

        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void loadLevel_missingPlayer_throwsException() {
        when(fileHandle.readString()).thenReturn("{\"height\": 10, \"tileheight\": 32, \"layers\": []}");

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                LevelManager.loadLevel(LevelManager.Level.LEVEL_1)
        );
        assertTrue(exception.getMessage().contains("Level must have exactly one Player"));
    }



    @Test
    void loadLevel_invalidJson_throwsRuntimeException() {
        when(fileHandle.readString()).thenReturn("invalid json");

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                LevelManager.loadLevel(LevelManager.Level.LEVEL_1)
        );
        assertTrue(exception.getMessage().contains("Failed to load level"));
    }

    @Test
    void getLevelFile_validLevel_returnsCorrectFile() {
        FileHandle file = LevelManager.getLevelFile(LevelManager.Level.LEVEL_1);
        assertNotNull(file);
    }

    @Test
    void getLevelFile_invalidLevel_throwsException() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                LevelManager.getLevelFile(null)
        );
        assertTrue(exception.getMessage().contains("No level file found"));
    }


}
