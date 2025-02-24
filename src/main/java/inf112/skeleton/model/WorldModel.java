package inf112.skeleton.model;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import inf112.skeleton.controller.ControllableWorldModel;
import inf112.skeleton.model.gameobject.mobileobject.Player;
import inf112.skeleton.view.ViewableWorldModel;
import inf112.skeleton.view.WorldView;

public class WorldModel implements ControllableWorldModel, ViewableWorldModel, ApplicationListener {

    public static final int SCREEN_WIDTH = 480;
    public static final int SCREEN_HEIGHT = 320;
    private GameState gameState;
    private Player player;
    private WorldView worldView;

    public WorldModel() {
        this.gameState = GameState.GAME_ACTIVE; // TODO, må endres etter at game menu er laget.
        this.player = new Player(1, 1); // TODO, legg til argument (foreløpig argumenter for å kunne kompilere prosjektet)
        this.worldView = new WorldView(this, new ExtendViewport(100, 100));
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
        Gdx.graphics.setForegroundFPS(60);
        worldView.show();
        worldView.resize(SCREEN_WIDTH, SCREEN_HEIGHT);
        // TODO, implement me :)
    }

    @Override
    public void resize( int i, int i1){
        // TODO, implement me :)
    }

    @Override
    public void render() {
        worldView.render(Gdx.graphics.getDeltaTime());
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

    @Override
    public Texture getPlayerTexture() {
        return player.getTexture();
    }

    /**
     * Tells us the state of the game
     * @return the state of the game
     */
    @Override
    public GameState getGameState() {
        return this.gameState;
    }

    @Override
    public int getScore() {
        // TODO
        return 0;
    }

}
