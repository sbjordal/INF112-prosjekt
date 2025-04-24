package inf112.starhunt.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import inf112.starhunt.model.GameState;
import inf112.starhunt.model.LevelManager;

/**
 * A class that handles keyboard input and manipulates the model accordingly.
 *
 * Reading material on event handling for use of LigGDX:
 * https://libgdx.com/wiki/input/event-handling
 */
public class Controller extends InputAdapter {

    private final ControllableWorldModel controllableModel;

    public Controller(ControllableWorldModel controllableModel) {
        this.controllableModel = controllableModel;
    }

    private boolean isGameState(GameState state) {
        return controllableModel.getGameState() == state;
    }

    private void handleMovement(int keyCode, boolean isPressed) {
        switch (keyCode) {
            case Input.Keys.LEFT, Input.Keys.A:
                controllableModel.setMovingLeft(isPressed);
                break;
            case Input.Keys.RIGHT, Input.Keys.D:
                controllableModel.setMovingRight(isPressed);
                break;
            case Input.Keys.UP, Input.Keys.W, Input.Keys.SPACE:
                controllableModel.setJumping(isPressed);
                break;
        }
    }

    private void toggleInfoMode() {
        controllableModel.setInfoMode(!controllableModel.getInfoMode());
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.ESCAPE) {
            Gdx.app.exit();
        } else if (isGameState(GameState.GAME_MENU) && keyCode == Input.Keys.ENTER) {
            controllableModel.startLevel(LevelManager.Level.LEVEL_3); // TODO: denne bør bruke WorldModel sin currentLevel variabel.
        } else if (isGameState(GameState.GAME_ACTIVE)) {
            handleMovement(keyCode, true);
        } else if (isGameState(GameState.GAME_OVER) && keyCode == Input.Keys.ENTER) {
            controllableModel.backToGameMenu();
        }
        return true;
    }

    @Override
    public boolean keyUp(int keyCode) {
        if (isGameState(GameState.GAME_ACTIVE)) {
            handleMovement(keyCode, false);
        }
        return true;
    }

    @Override
    public boolean keyTyped(char c) {
        if (isGameState(GameState.GAME_MENU) && c == 'i') {
            toggleInfoMode();
        } else if (isGameState(GameState.GAME_ACTIVE) && c == 'p') {
            controllableModel.setMovingLeft(false);
            controllableModel.setMovingRight(false);
            controllableModel.pause();
        } else if (isGameState(GameState.GAME_PAUSED)) {
            switch (c) {
                case 'p' -> controllableModel.resume();
                case 'r' -> controllableModel.backToGameMenu();
                case 'i' -> toggleInfoMode();
            }
        }
        return true;
    }

}
