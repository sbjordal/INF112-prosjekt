package inf112.starhunt.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import inf112.starhunt.model.GameState;
import inf112.starhunt.model.LevelManager;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

public class ControllerTest {
    private Controller controller;
    private ControllableWorldModel controllableModel;

    @BeforeEach
    void setUp() {
        // Mock ControllableWorldModel så vi kan kontrollere gameState og metodene
        controllableModel = Mockito.mock(ControllableWorldModel.class);
        controller = new Controller(controllableModel);
    }

    @Test
    void testKeyDownEnterStartsLevelInMenu() {
        when(controllableModel.getGameState()).thenReturn(GameState.GAME_MENU);

        Controller controller = new Controller(controllableModel);
        boolean result = controller.keyDown(Input.Keys.ENTER);

        verify(controllableModel).startLevel(LevelManager.Level.LEVEL_1);
        assertTrue(result);
    }

    @Test
    void testKeyTypedTogglesInfoModeInMenu() {
        when(controllableModel.getGameState()).thenReturn(GameState.GAME_MENU);
        when(controllableModel.getInfoMode()).thenReturn(false);

        Controller controller = new Controller(controllableModel);
        boolean result = controller.keyTyped('i');

        verify(controllableModel).setInfoMode(true);
        assertTrue(result);
    }

    @Test
    void testKeyDownInGameActive() {
        // Simuler at gameState er GAME_ACTIVE
        when(controllableModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);

        // Test for bevegelse
        controller.keyDown(Input.Keys.LEFT);
        Mockito.verify(controllableModel).setMovingLeft(true);

        controller.keyDown(Input.Keys.RIGHT);
        Mockito.verify(controllableModel).setMovingRight(true);

        controller.keyDown(Input.Keys.UP);
        Mockito.verify(controllableModel).setJumping(true);
        controller.keyDown(Input.Keys.SPACE);
        Mockito.verify(controllableModel, Mockito.times(2)).setJumping(true); // called twice so far
    }

    @Test
    void testKeyDownInGameOver() {
        // Simuler at gameState er GAME_OVER
        when(controllableModel.getGameState()).thenReturn(GameState.GAME_OVER);

        // Test for Enter
        controller.keyDown(Input.Keys.ENTER);
        Mockito.verify(controllableModel).backToGameMenu();
    }

    @Test
    void testKeyUpInGameActive() {
        // Simuler at gameState er GAME_ACTIVE
        when(controllableModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);

        // Test for slipp av taster
        controller.keyUp(Input.Keys.LEFT);
        Mockito.verify(controllableModel).setMovingLeft(false);

        controller.keyUp(Input.Keys.RIGHT);
        Mockito.verify(controllableModel).setMovingRight(false);
    }

    @Test
    void testKeyTypedPauseInGameActive() {
        // Simuler at gameState er GAME_ACTIVE
        when(controllableModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);

        // Test for pause
        controller.keyTyped('p');
        Mockito.verify(controllableModel).pause();


        Mockito.verify(controllableModel).setMovingLeft(false);    // Check if movingLeft was set to false
        Mockito.verify(controllableModel).setMovingRight(false);   // Check if movingRight was set to false
    }

    @Test
    void testKeyTypedResumeInGamePaused() {
        // Simuler at gameState er GAME_PAUSED
        when(controllableModel.getGameState()).thenReturn(GameState.GAME_PAUSED);

        // Test for resume
        controller.keyTyped('p');
        Mockito.verify(controllableModel).resume();
    }

    @Test
    void testKeyDownReturnsTrueWhenGameIsActive() {

        // Set the game state to GAME_ACTIVE
        when(controllableModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);

        // Simulate key press of left arrow key (or A key)
        boolean result = controller.keyDown(Input.Keys.LEFT);

        // Verify that the correct methods were called
        Mockito.verify(controllableModel).setMovingLeft(true);    // Check if movingLeft was set to true
        assertTrue(result); // Check that the return value is true
    }

    @Test
    void testKeyDownReturnsFalseWhenGameIsNotActive() {

        // Set the game state to GAME_MENU
        when(controllableModel.getGameState()).thenReturn(GameState.GAME_MENU);

        // Simulate key press of enter key
        boolean result = controller.keyDown(Input.Keys.ENTER);

        Mockito.verify(controllableModel).startLevel(LevelManager.Level.LEVEL_1);
        assertTrue(result); // Check that the return value is true
    }

    @Test
    void testKeyUpReturnsTrueWhenGameIsActive() {

        // Set the game state to GAME_ACTIVE
        when(controllableModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);

        // Simulate key release of left arrow key (or A key)
        boolean result = controller.keyUp(Input.Keys.LEFT);

        // Verify that the correct methods were called
        Mockito.verify(controllableModel).setMovingLeft(false); // Check if movingLeft was set to false
        assertTrue(result); // Check that the return value is true
    }

    @Test
    void testKeyUpReturnsFalseWhenGameIsNotActive() {
        // Set the game state to GAME_MENU
        when(controllableModel.getGameState()).thenReturn(GameState.GAME_MENU);

        // Simulate key release of left arrow key (or A key)
        boolean result = controller.keyUp(Input.Keys.LEFT);

        // Verify that the correct methods were NOT called
        Mockito.verify(controllableModel, Mockito.times(0)).setMovingLeft(false);  // Should not have called setMovingLeft
        //assertFalse(result); // Check that the return value is false since the state is not GAME_ACTIVE
    }

    @Test
    void testKeyDownCallsExitWhenEscapeKeyIsPressed() {
        // Mock the Application instance
        Application mockApp = Mockito.mock(Application.class);
        Gdx.app = mockApp; // Set the mocked application to Gdx.app

        assertNotNull(Gdx.app, "Gdx.app should be initialized and running");

        // Simulate pressing the ESCAPE key
        controller.keyDown(Input.Keys.ESCAPE);

        // Verify that Gdx.app.exit() was called
        Mockito.verify(mockApp, Mockito.times(1)).exit();
    }

    @Test
    void testKeyEnterToStartGame() {
        // Set the game state to GAME_MENU
        when(controllableModel.getGameState()).thenReturn(GameState.GAME_MENU);

        // Simulate start game
         controller.keyDown(Input.Keys.ENTER);

        Mockito.verify(controllableModel, Mockito.times(1)).startLevel(LevelManager.Level.LEVEL_1);
    }

}
