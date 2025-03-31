package inf112.skeleton.controller;

import com.badlogic.gdx.Input;
import inf112.skeleton.model.GameState;
import com.badlogic.gdx.InputProcessor;
import inf112.skeleton.model.LevelManager;

/**
 * A class that handles key input and manipulates the model accordingly.
 *
 * Reading material on event handling for use of LigGDX:
 * https://libgdx.com/wiki/input/event-handling
 */
public class Controller implements InputProcessor {

    private final ControllableWorldModel controllableModel;

    public Controller(ControllableWorldModel controllableModel) {
        this.controllableModel = controllableModel;
    }

    /**
     * When keyboard key is pressed down once. Used for player movement.
     * Only works for player movement if Gamestate is Active.
     *
     * @param keyCode   integer corresponding to a key pressed.
     * @return          true if successful, false otherwise.
     */
    @Override
    public boolean keyDown(int keyCode) {
        if (controllableModel.getGameState() == GameState.GAME_MENU) {
            switch (keyCode) {
                case Input.Keys.ENTER:
                    controllableModel.startLevel(LevelManager.Level.LEVEL_1);
                    break;
                case Input.Keys.I:
                    controllableModel.setToInfoMode();
                    break;
            }
            return true;
        }
        else if (controllableModel.getGameState() == GameState.GAME_INFO) {
            switch (keyCode) {
//                case Input.Keys.ENTER: // TODO: Fikk problemer med å starte spillet fra infomode
//                    controllableModel.resume();
//                    break;
                case Input.Keys.I:
                    controllableModel.backToGameMenu();
                    break;
            }
            return true;

        }
        else if (controllableModel.getGameState() == GameState.GAME_ACTIVE) { // TODO, denne linjen blir brukt mange ganger her, mulig å gjøre mer generisk?
            switch (keyCode) {
                case Input.Keys.LEFT, Input.Keys.A:
                    controllableModel.setMovingLeft(true);
                    break;
                case Input.Keys.RIGHT, Input.Keys.D:
                    controllableModel.setMovingRight(true);
                    break;
                case Input.Keys.UP, Input.Keys.W, Input.Keys.SPACE:
                    controllableModel.setJumping(true);
                    break;
            }
            return true;
        }
        else if (controllableModel.getGameState() == GameState.GAME_OVER) {
            switch (keyCode) {
                case Input.Keys.ENTER:
                    controllableModel.backToGameMenu();
                    break;
            }
            return true;
        }

        return false;
    }

    /**
     * When finger is lifted from key. Used for player movement.
     * Only works for player movement if Gamestate is Active.
     *
     * @param keyCode   integer corresponding to a key pressed.
     * @return          true if successful, false otherwise.
     */
    @Override
    public boolean keyUp(int keyCode) {
        if (controllableModel.getGameState() == GameState.GAME_ACTIVE) {
            switch (keyCode) {
                case Input.Keys.LEFT, Input.Keys.A:
                    controllableModel.setMovingLeft(false);
                    break;
                case Input.Keys.RIGHT, Input.Keys.D:
                    controllableModel.setMovingRight(false);
                    break;
                case Input.Keys.UP, Input.Keys.W, Input.Keys.SPACE:
                    controllableModel.setJumping(false);
                    break;
            }
            return true;
        }
        return false;
    }

    /**
     * When a keyboard key is pressed, and not held down.
     * Mainly used for changing game state, like start, pause, unpause.
     * @param c     specific keyboard key
     * @return      true if successful, false if not
     */
    @Override
    public boolean keyTyped(char c) {
        if (controllableModel.getGameState() == GameState.GAME_ACTIVE) {
            if (c == 'p') {
                controllableModel.setMovingLeft(false);
                controllableModel.setMovingRight(false);
                controllableModel.pause();
            }
        }
        else if (controllableModel.getGameState() == GameState.GAME_PAUSED) {
            if (c == 'p')  {
                controllableModel.resume();
            }
            else if (c == 'r')  {
                controllableModel.backToGameMenu();
            }
        }
        return true;
    }


    // TODO, få slettet metodene under, kommer ikke til å bruke disse, forslag: Lag en "mellomklasse" / grensesnitt
    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

}
