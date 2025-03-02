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
import inf112.skeleton.model.gameobject.mobileobject.actor.Enemy;
import inf112.skeleton.model.gameobject.mobileobject.actor.Player;
import inf112.skeleton.view.ViewableWorldModel;
import inf112.skeleton.view.WorldView;

public class WorldModel implements ViewableWorldModel, ControllableWorldModel, ApplicationListener {

    private GameState gameState;
    private Player player;
    private Enemy enemy;
    private WorldBoard board;
    private WorldView worldView;
    private PlayerController playerController;
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

    // TODO: revisjon - en felles move istedenfor move right og left
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

        // TODO: Denne måten å lage enemy er midlertidig for MVP med bare én enemy
        Position enemyPos = new Position(40, 105);
        Size enemySize = new Size(50, 50);
        this.enemy = new Enemy(1,1,10,1, new Transform(enemyPos, enemySize));

        Gdx.graphics.setForegroundFPS(60);
        worldView.show();
        worldView.resize(board.width(), board.height());

        // TODO: revisjon - dette måtte ligge her for å gjøre koden kjørbar
        this.playerController = new PlayerController(this);
        Gdx.input.setInputProcessor(this.playerController);

    }

    @Override
    public void resize( int i, int i1){
        // TODO, implement me :)
    }

    @Override
    public void render() {
        if (playerController != null) {
            playerController.update();
        }
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

    // TODO - Denne metoden for enemy er potensielt midlertidig for MVP med tanke på én enemy
    @Override
    public Texture getEnemyTexture() {
        return enemy.getTexture();
    }

    // TODO - samme som metoden over
    @Override
    public Transform getEnemyTransform() {
        return enemy.getTransform();
    }
    //TODO- Vurdere om denne skal inn i Interface. Bakgrunn er avhengig av player sin movementspeed
    public int getMovementSpeed(){
        return player.getMovementSpeed();
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
