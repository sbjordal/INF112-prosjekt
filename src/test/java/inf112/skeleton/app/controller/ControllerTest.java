package inf112.skeleton.app.controller;

import static org.junit.jupiter.api.Assertions.*;

import inf112.skeleton.controller.ControllableWorldModel;
import inf112.skeleton.controller.Controller;
import inf112.skeleton.model.GameState;
import inf112.skeleton.model.WorldBoard;
import inf112.skeleton.model.WorldModel;
import org.junit.jupiter.api.*;

public class ControllerTest {
    private ControllableWorldModel controllableModel;
    private Controller controller;

    @BeforeEach
    public void setUpBeforeEach() {
        this.controllableModel = new WorldModel(new WorldBoard(50,50)); // TODO, random argumenter for å initiere, må muligens endres senere
        this.controller = new Controller(controllableModel);
    }

    /**
     * Checks if GameState in model changes after pressed key 'p'
     * and if it again changes GameState back if pressed again.
     */
    @Test
    public void pauseUnpauseGameTest() {
        // Set the game state to GAME_PAUSED
        if (controllableModel.getGameState() == GameState.GAME_ACTIVE) {
            controller.keyTyped('p');
            assertEquals(GameState.GAME_PAUSED, controllableModel.getGameState());
        }
        if (controllableModel.getGameState() == GameState.GAME_PAUSED) {
            controller.keyTyped('p');
            assertEquals(GameState.GAME_ACTIVE, controllableModel.getGameState());
        }
    }
}
