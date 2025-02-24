package inf112.skeleton.model;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.controller.ControllableWorldModel;
import inf112.skeleton.model.gameobject.Position;
import inf112.skeleton.model.gameobject.mobileobject.Player;

public class WorldModel implements ControllableWorldModel, ApplicationListener {

    private GameState gameState;
    private Player player;

    public WorldModel() {
        this.gameState = GameState.GAME_ACTIVE; // TODO, må endres etter at game menu er laget.
        this.player = new Player(1, 1, new Position(1,1), new Texture("1")); // TODO, legg til argument (foreløpig argumenter for å kunne kompilere prosjektet)
    }

    /**
     * Checks if MobileObject can be moved where it wants to move or not.
     *
     * @return true if yes, false if no.
     */
    private boolean isLegalMove() {
        return false; // TODO, implement me :)
    }


    @Override
    public void movePlayerLeft() {
        if (isLegalMove()) {
            player.setMoveLeft(true);
        } else player.setMoveLeft(false);
    }

    @Override
    public void movePlayerRight() {
        if (isLegalMove()) {
            player.setMoveRight(true);
        } else player.setMoveRight(false);
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
