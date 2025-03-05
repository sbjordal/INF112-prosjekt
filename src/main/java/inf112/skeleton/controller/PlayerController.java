package inf112.skeleton.controller;

import com.badlogic.gdx.Input;
import inf112.skeleton.model.GameState;
import com.badlogic.gdx.InputProcessor;
import inf112.skeleton.view.WorldView;

/**
 * A class that handles key input and manipulates the model accordingly.
 *
 * Reading material on event handling for use of LigGDX:
 * https://libgdx.com/wiki/input/event-handling
 */
public class PlayerController implements InputProcessor {

    private ControllableWorldModel controllableModel;
    private boolean isPressingRight;
    private boolean isPressingLeft;
    //private Timer timer;

    public PlayerController(ControllableWorldModel controllableModel) {
        this.controllableModel = controllableModel;
        this.isPressingRight = false;
        this.isPressingLeft = false;
    }

    /**
     * When keyboard key is pressed down once.
     *
     * @param keyCode
     * @return True if successful
     */
    @Override
    public boolean keyDown(int keyCode) {
        if (controllableModel.getGameState() == GameState.GAME_ACTIVE) { // TODO, denne linjen blir brukt mange ganger her, mulig å gjøre mer generisk?
            switch (keyCode) {
                case Input.Keys.LEFT:
                    this.isPressingLeft = true;
                    break;
                case Input.Keys.RIGHT:
                    this.isPressingRight = true;
                    break;
            }
            return true;
        }
        return false;
    }

    /**
     * When finger is lifted from key.
     *
     * @param keyCode
     * @return
     */
    @Override
    public boolean keyUp(int keyCode) {
        if (controllableModel.getGameState() == GameState.GAME_ACTIVE) {
            switch (keyCode) {
                case Input.Keys.LEFT:
                    this.controllableModel.setMovementSpeed(0);
                    this.isPressingLeft = false;
                    break;
                case Input.Keys.RIGHT:
                    this.controllableModel.setMovementSpeed(0);
                    this.isPressingRight = false;
                    break;
            }
            return true;
        }
        return false;
    }

    //TODO, her må det endres slik at model ikke endrer direkte, men controller oppdaterer pos.
    public void update(){
        if (isPressingRight){
            this.controllableModel.setMovementSpeed(1);
            controllableModel.move(1,0);
        }
        if (isPressingLeft){
            this.controllableModel.setMovementSpeed(-1);
            controllableModel.move(-1,0);
        }
    }

    @Override
    public boolean keyTyped(char c) {
        if (controllableModel.getGameState() == GameState.GAME_ACTIVE) {
            if (c == 'p') {
                controllableModel.pause();
                System.out.println(controllableModel.getGameState()); //TODO, temp debugging
            }
        }
        else if (controllableModel.getGameState() == GameState.GAME_PAUSED) {
            if (c == 'p')  {
                controllableModel.resume();
                System.out.println(controllableModel.getGameState()); //TODO, temp debugging
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
