package inf112.skeleton.model;

import com.badlogic.gdx.ApplicationListener;
import inf112.skeleton.controller.ControllableWorldModel;
import inf112.skeleton.model.gameobject.mobileobject.Player;

public class WorldModel implements ControllableWorldModel, ApplicationListener {

    private GameState gameState;
    private Player player;

    public WorldModel() {
        this.gameState = GameState.GAME_MENU;
        this.player = new Player(); // TODO, legg til argument
    }

    /**
     * Checks if MobileObject can be moved where it wants to move or not.
     *
     * @return true if yes, false if nei.
     */
    private boolean isLegalMove() {
        return false; // TODO, implement me :)
    }


    @Override
    public void movePlayerLeft() {
        if (isLegalMove()) {
            player.setMovePlayerLeft(true); // TODO, disse blir fikset når de blir definert i Player klasse
        } else player.setMovePlayerLeft(false);
    }

    @Override
    public void movePlayerRight() {
        if (isLegalMove()) {
            player.setMovePlayerRight(true);
        } else player.setMovePlayerRight(false);
    }

    @Override
    public void create() {
        // TODO, implement me :)
    }

    @Override
    public void resize( int i, int i1){
        // TODO, implement me :)
    }

    @Override
    public void render() {
        // TODO, implement me :)
    }

    @Override
    public void pause() {
        this.gameState = GameState.GAME_PAUSED;

    }

    @Override
    public void resume() {
        this.gameState = GameState.GAME_ACTIVE;
    }

    @Override
    public void dispose() {
        // TODO, implement me :)
    }

    /**
     * Tells us the state of the game
     * @return the state of the game
     */
    @Override
    public GameState getGameState() {
        return this.gameState;
    }

}
