package inf112.skeleton.app.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.Input;
import inf112.skeleton.controller.ControllableWorldModel;
import inf112.skeleton.controller.Controller;
import inf112.skeleton.model.GameState;
import inf112.skeleton.model.WorldBoard;
import inf112.skeleton.model.WorldModel;
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
    void testKeyDownInGameMenu() {
        // Simuler at gameState er GAME_MENU
        Mockito.when(controllableModel.getGameState()).thenReturn(GameState.GAME_MENU);

        // Test for Enter og I
        controller.keyDown(Input.Keys.ENTER);
        Mockito.verify(controllableModel).setUpModel();
        Mockito.verify(controllableModel).create();
        Mockito.verify(controllableModel).resume();

        controller.keyDown(Input.Keys.I);
        Mockito.verify(controllableModel).setToInfoMode();
    }

    @Test
    void testKeyDownInGameInfo() {
        // Simuler at gameState er GAME_INFO
        Mockito.when(controllableModel.getGameState()).thenReturn(GameState.GAME_INFO);

        // Test for Enter og I
        controller.keyDown(Input.Keys.ENTER);
        Mockito.verify(controllableModel).resume();

        controller.keyDown(Input.Keys.I);
        Mockito.verify(controllableModel).backToGameMenu();
    }

    @Test
    void testKeyDownInGameActive() {
        // Simuler at gameState er GAME_ACTIVE
        Mockito.when(controllableModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);

        // Test for bevegelse
        controller.keyDown(Input.Keys.LEFT);
        Mockito.verify(controllableModel).setMovingLeft(true);

        controller.keyDown(Input.Keys.RIGHT);
        Mockito.verify(controllableModel).setMovingRight(true);

//        // Test for hopp
//        controller.keyDown(Input.Keys.UP);
//        Mockito.verify(controllableModel).jump();
//
//        // Test for space
//        controller.keyDown(Input.Keys.SPACE);
//        Mockito.verify(controllableModel).jump();
    }

    @Test
    void testKeyDownInGameOver() {
        // Simuler at gameState er GAME_OVER
        Mockito.when(controllableModel.getGameState()).thenReturn(GameState.GAME_OVER);

        // Test for Enter
        controller.keyDown(Input.Keys.ENTER);
        Mockito.verify(controllableModel).backToGameMenu();
    }

    @Test
    void testKeyUpInGameActive() {
        // Simuler at gameState er GAME_ACTIVE
        Mockito.when(controllableModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);

        // Test for slipp av taster
        controller.keyUp(Input.Keys.LEFT);
        Mockito.verify(controllableModel).setMovingLeft(false);

        controller.keyUp(Input.Keys.RIGHT);
        Mockito.verify(controllableModel).setMovingRight(false);
    }

    @Test
    void testKeyTypedPauseInGameActive() {
        // Simuler at gameState er GAME_ACTIVE
        Mockito.when(controllableModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);

        // Test for pause
        controller.keyTyped('p');
        Mockito.verify(controllableModel).pause();
    }

    @Test
    void testKeyTypedResumeInGamePaused() {
        // Simuler at gameState er GAME_PAUSED
        Mockito.when(controllableModel.getGameState()).thenReturn(GameState.GAME_PAUSED);

        // Test for resume
        controller.keyTyped('p');
        Mockito.verify(controllableModel).resume();
    }
}
