package inf112.skeleton.model;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import inf112.skeleton.controller.ControllableWorldModel;
import inf112.skeleton.controller.PlayerController;
import inf112.skeleton.model.gameobject.Position;
import inf112.skeleton.model.gameobject.Size;
import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.gameobject.mobileobject.Player;
import inf112.skeleton.view.ViewableWorldModel;
import inf112.skeleton.view.WorldView;

import java.util.List;

public class WorldModel implements ViewableWorldModel, ControllableWorldModel, ApplicationListener {

    private GameState gameState;
    private Player player;
    private WorldBoard board;
    private WorldView worldView;
//    private int gameScore;

    public WorldModel(WorldBoard board) {
        this.gameState = GameState.GAME_ACTIVE; // TODO, må endres etter at game menu er laget.
        this.worldView = new WorldView(this, new ExtendViewport(board.width(),board.height()));
        this.board = board;
    }

    /**
     * Checks if MobileObject can be moved where it wants to move or not.
     *
     * @return True if the position is legal, false otherwise
     */
    private boolean isLegalMove(Position pos) {

        if(!positionIsOnBoard(pos)) {
            return false;
        } // må legges til mer logikk her for fixedObject, movingObject osv
        return true;
    }

    private boolean positionIsOnBoard(Position pos) {
        boolean isWithinWidthBound = pos.x() >= 0 && pos.x() < board.width();
        boolean isWithinHeightBound = pos.y() >= 0  && pos.y() < board.height();

        return isWithinWidthBound && isWithinHeightBound;
    }


    @Override
    public void move(int deltaX, int deltaY) {
        Position playerPosition = player.getTransform().getPos();
        Position newPosition = new Position(playerPosition.x() + deltaX, playerPosition.y() + deltaY);

        if (isLegalMove(newPosition)) {
            player.move(newPosition);
        }
    }

    @Override
    public void jump() {
        // TODO: implement this.
    }

    @Override
    public void create() {
        this.player = new Player(1, 1); // TODO, legg til argument (foreløpig argumenter for å kunne kompilere prosjektet)

        Gdx.graphics.setForegroundFPS(60);
        worldView.show();
        worldView.resize(board.width(), board.height());

        PlayerController playerController = new PlayerController(this);
        Gdx.input.setInputProcessor(playerController);
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

    @Override
    public Transform getPlayerTransform() {
        return player.getTransform();
    }

    @Override
    public Texture getEnemyTexture() {
        // TODO
        return null;
    }

    @Override
    public Transform getEnemyTransform() {
        // TODO
        return null;
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
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public int getTotalScore() {
        return 0;
    }

}
