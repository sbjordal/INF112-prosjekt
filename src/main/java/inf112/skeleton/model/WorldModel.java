package inf112.skeleton.model;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.controller.ControllableWorldModel;
import inf112.skeleton.model.gameobject.Position;
import inf112.skeleton.model.gameobject.mobileobject.Player;
import inf112.skeleton.view.ViewableWorldModel;

import java.util.List;

public class WorldModel implements ViewableWorldModel, ControllableWorldModel, ApplicationListener {

    private GameState gameState;
    private Player player;
    private WorldBoard board;
//    private int gameScore;

    public WorldModel(WorldBoard board) {
        this.gameState = GameState.GAME_ACTIVE; // TODO, må endres etter at game menu er laget.
        this.player = new Player(1, 1); // TODO, legg til argument (foreløpig argumenter for å kunne kompilere prosjektet)
        this.board = board;}

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
    public void movePlayerLeft() {
//        if (isLegalMove()) { // må endre logikk i isLegalMove eller legge til pos her
//            player.setMoveLeft(true);
//        } else player.setMoveLeft(false);
    }

    @Override
    public void movePlayerRight() {
//        if (isLegalMove()) { // må endre logikk i isLegalMove eller legge til pos her
//            player.setMoveRight(true);
//        } else player.setMoveRight(false);
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

    @Override
    public Texture getEnemySprite() {
        return null;
    }

    @Override
    public Texture getPlayerSprite() {
        return null;
    }

    @Override
    public List<Position> getFixedObjectPosition() {
        return List.of();
    }

    @Override
    public List<Position> getEnemysPosition() {
        return List.of();
    }

    @Override
    public Position getPlayerPosition() {
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
    public int getScore() {
        return 0;
    }

}
