package inf112.skeleton.app;

import static org.junit.jupiter.api.Assertions.*;

import inf112.skeleton.controller.ControllableWorldModel;
import inf112.skeleton.controller.PlayerController;
import inf112.skeleton.model.GameState;
import inf112.skeleton.model.WorldModel;
import inf112.skeleton.view.WorldView;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


public class PlayerControllerTest {
    private ControllableWorldModel controllableModel;
    private PlayerController playerController;

    @BeforeEach
    public void setUpBeforeEach() {
//        ControllableWorldModel controllableModel = new WorldModel();
//        PlayerController controller = new PlayerController(controllableModel, new WorldView());
    }


// TODO, this test does not work. Fix this.
    /**
     * Checks if GameState in model changes after pressed key 'P'
     * and if it again changes GameState if pressed again.
     */
/*
    @Test
    public void testKeyTypedPause() {
        // Set the game state to GAME_PAUSED
        playerController.keyTyped('P');
        assertEquals(GameState.GAME_PAUSED, controllableModel.getGameState());

        playerController.keyTyped('P');
        assertEquals(GameState.GAME_ACTIVE, controllableModel.getGameState());
    }
*/
}
