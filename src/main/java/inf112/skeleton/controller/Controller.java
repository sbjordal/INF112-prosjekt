package inf112.skeleton.controller;

import com.badlogic.gdx.Input;
import inf112.skeleton.model.GameState;
import com.badlogic.gdx.InputProcessor;

/**
 * A class that handles key input and manipulates the model accordingly.
 *
 * Reading material on event handling for use of LigGDX:
 * https://libgdx.com/wiki/input/event-handling
 */
public class Controller implements InputProcessor {

    private ControllableWorldModel controllableModel;

    public Controller(ControllableWorldModel controllableModel) {
        this.controllableModel = controllableModel;
    }

    /**
     * When keyboard key is pressed down once. Used for player movement.
     * Only works for player movement if Gamestate is Active.
     *
     * @param keyCode
     * @return true if successful, false otherwise
     */
    @Override
    public boolean keyDown(int keyCode) {
        if (controllableModel.getGameState() == GameState.GAME_ACTIVE) { // TODO, denne linjen blir brukt mange ganger her, mulig å gjøre mer generisk?
            switch (keyCode) {
                case Input.Keys.LEFT:
                    this.controllableModel.setMovingLeft(true);
                    break;
                case Input.Keys.A:
                    this.controllableModel.setMovingLeft(true);
                    break;
                case Input.Keys.RIGHT:
                    this.controllableModel.setMovingRight(true);
                    break;
                case Input.Keys.D:
                    this.controllableModel.setMovingRight(true);
                    break;
                case Input.Keys.UP:
                    this.controllableModel.jump();
                    break;
                case Input.Keys.W:
                    this.controllableModel.jump();
                    break;
                case Input.Keys.SPACE:
                    this.controllableModel.jump();
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
     * @param keyCode
     * @return true if successful, false otherwise
     */
    @Override
    public boolean keyUp(int keyCode) {
        if (controllableModel.getGameState() == GameState.GAME_ACTIVE) {
            switch (keyCode) {
                case Input.Keys.LEFT:
                    this.controllableModel.setMovingLeft(false);
                    break;
                case Input.Keys.A:
                    this.controllableModel.setMovingLeft(false);
                    break;
                case Input.Keys.RIGHT:
                    this.controllableModel.setMovingRight(false);
                    break;
                case Input.Keys.D:
                    this.controllableModel.setMovingRight(false);
                    break;
            }
            return true;
        }
        return false;
    }

    /**
     * When a keyboard key is pressed, and not held down.
     * Mainly used for changing game state, like start, pause, unpause.
     * @param c, spesific keyboard key
     * @return true if sucessfull, false if not
     */
    @Override
    public boolean keyTyped(char c) {
        if (controllableModel.getGameState() == GameState.GAME_ACTIVE) {
            if (c == 'p') {
                controllableModel.pause();
            }
        }
        else if (controllableModel.getGameState() == GameState.GAME_PAUSED) {
            if (c == 'p')  {
                controllableModel.resume();
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
