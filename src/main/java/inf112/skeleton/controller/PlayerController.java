package inf112.skeleton.controller;

import com.badlogic.gdx.Input;
import inf112.skeleton.model.GameState;
import inf112.skeleton.view.WorldView;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ApplicationListener;


/**
 * https://libgdx.com/wiki/input/event-handling
 *
 */
public class PlayerController implements InputProcessor {

    private ControllableWorldModel controllableModel;
    private WorldView view;
    //private Timer timer;

    public PlayerController(ControllableWorldModel controllableModel, WorldView view) {
        this.controllableModel = controllableModel;
        this.view = view;

        //this.timer = new Timer();
    }

    @Override
    public boolean keyDown(int keyCode) {
        switch (keyCode)
        {
            case Input.Keys.LEFT:
                //model.movePlayer(-1, 0); // TODO, her må navn og returtype på metode sjekkes opp mot model
                break;
            case Input.Keys.RIGHT:
                //model.movePlayer(1, 0);// (x,y)
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keyCode) {
        if (controllableModel.getGameState() == GameState.GAME_ACTIVE) {
            switch (keyCode) {
                case Input.Keys.LEFT:
                    controllableModel.movePlayerLeft();
                    break;
                case Input.Keys.RIGHT:
                    controllableModel.movePlayerRight();
                    break;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        if (controllableModel.getGameState() == GameState.GAME_ACTIVE) {
            switch (c) {
                case Input.Keys.P:
                    if (controllableModel.getGameState() == GameState.GAME_ACTIVE) {
                        controllableModel.pause();
                    }
                    controllableModel.resume();
                    break;
//            case Input.Keys.D: // TODO Denne er ikke helt definert enda
//                if (model.getGameState() == GameState.GAME_ACTIVE) {
//                    model.pause();
//                }
//                break;
            }
        }
        return true;
    }

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
