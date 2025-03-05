package inf112.skeleton.model;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import inf112.skeleton.controller.ControllableWorldModel;
import inf112.skeleton.controller.PlayerController;
import inf112.skeleton.model.gameobject.*;
import inf112.skeleton.model.gameobject.fixedobject.item.Coin;
import inf112.skeleton.model.gameobject.mobileobject.actor.Enemy;
import inf112.skeleton.model.gameobject.mobileobject.actor.Player;
import inf112.skeleton.view.ViewableWorldModel;
import inf112.skeleton.view.WorldView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorldModel implements ViewableWorldModel, ControllableWorldModel, ApplicationListener {

    private GameState gameState;
    private Player player;
    private Enemy enemy;
    private Coin coin;
    private WorldBoard board;
    private WorldView worldView;
    private PlayerController playerController;
    private ArrayList<GameObject> objectList;
    private int gameScore;
    private int coinScore;
    private long lastScoreUpdate = System.currentTimeMillis();
    private long lastEnemyCollisionTime = 0;
    private static final long COLLISION_COOLDOWN = 800;

    public WorldModel(WorldBoard board) {
        this.gameState = GameState.GAME_ACTIVE; // TODO, må endres etter at game menu er laget.
        this.worldView = new WorldView(this, new ExtendViewport(board.width(),board.height()));
        this.board = board;
        this.coinScore = 0;
        gameScore = 150; //
    }

    /**
     * Checks if MobileObject can be moved where it wants to move or not.
     *
     * @return True if the position is legal, false otherwise
     */
    private boolean isLegalMove(CollisionBox collisionBox) {
        if(!positionIsOnBoard(collisionBox)) {

                return false;
        }

        if (isColliding(collisionBox)) {
            System.out.println("jeg skal ikke bevege meg");
            return false;
        }
        System.out.println("jeg er her nå. det skal jeg ikke være");

        return true;
    }

    private boolean isColliding(CollisionBox collisionBox) {
        if (gameState!=GameState.GAME_ACTIVE) {
            return false;
        }
        for (GameObject gameObject : objectList) {
            if (collisionBox.isCollidingWith(gameObject.getCollisionBox())) {
                if (gameObject instanceof Coin) {
                    System.out.println("jeg kommer hit");
                    System.out.println(gameObject.getCollisionBox().botLeft);
                    handleCoinCollision(gameObject);
                }
                else if (gameObject instanceof Enemy) { // TODO: legge til at dersom man hopper på en enemy får man poeng og fienden dør
                    handleEnemyCollision(gameObject);
                }

                return true;
            }
        }

        return false;
    }

    private boolean positionIsOnBoard(CollisionBox collisionBox) {
        boolean isWithinWidthBound = collisionBox.botLeft.x() >= 0 && collisionBox.topRight.x() < board.width();
        boolean isWithinHeightBound = collisionBox.botLeft.y() >= 0  && collisionBox.topRight.y() < board.height();

        return isWithinWidthBound && isWithinHeightBound;
    }
    private void handleEnemyCollision(GameObject gameObject) { // TODO: legge til if health=0 die() elns
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastEnemyCollisionTime >= COLLISION_COOLDOWN) {
            if (gameScore > 0) {
                gameScore-=4;
            }
            lastEnemyCollisionTime = currentTime;
        }
    }

    private void handleCoinCollision(GameObject coin) {// TODO revisjon: i pickup metoden eller som privat hjelpemetode her
        this.coinScore++;
        this.gameScore++;
        this.objectList.remove(coin);
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

        Size playerSize = player.getTransform().getSize();
        Transform newPlayerTransform = new Transform(newPosition, playerSize);
        CollisionBox newPlayerCollisionBox = new CollisionBox(newPlayerTransform);

        if (isLegalMove(newPlayerCollisionBox)) {
            System.out.println("jeg beveger meg?");
            player.move(newPosition);
        }
    }

    @Override
    public void jump() {
        // TODO: implement this.
    }

    @Override
    public void create() {
        this.player = new Player(1, 0); // TODO, legg til argument (foreløpig argumenter for å kunne kompilere prosjektet)

        Position enemyPos = new Position(40, 105);
        Size enemySize = new Size(50, 50);
        this.enemy = new Enemy(1,1,10,1, new Transform(enemyPos, enemySize));

        Position coinPos = new Position(100, 105);
        Size coinSize = new Size(30, 30);
        this.coin = new Coin(new Transform(coinPos, coinSize));

        Gdx.graphics.setForegroundFPS(60);
        worldView.show();
        worldView.resize(board.width(), board.height());

        // Fill up the object list
        this.objectList = new ArrayList<>();
        this.objectList.add(this.enemy); // TODO: må endres når vi har flere enemies.
        this.objectList.add(this.coin); // TODO: test coin for å teste collision.

        this.playerController = new PlayerController(this);
        Gdx.input.setInputProcessor(this.playerController);
    }

    @Override
    public void resize( int i, int i1){
        // TODO, implement me :)
    }

    @Override
    public void render() {
        if (shouldUpdateScore()) {
            gameScore--;
            lastScoreUpdate = System.currentTimeMillis();
        }
        // TODO, her må controller oppdatere model, og ikke model oppdatere controller.
        if (playerController != null) {
            playerController.update();
        }
        worldView.render(Gdx.graphics.getDeltaTime());
        // TODO, implement me :)
    }

    private boolean shouldUpdateScore() {
        long currentTime = System.currentTimeMillis();
        return currentTime - lastScoreUpdate >= 1000 && gameScore>0 && gameState == GameState.GAME_ACTIVE;
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

    public ViewableObject getViewablePlayer() {
        return player;
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

    @Override
    public Texture getCoinTexture() {
        return coin.getTexture();
    }

    @Override
    public Transform getCoinTransform() {
        return coin.getTransform();
    }

    //TODO- Lagt inn i Interface. Bakgrunn er avhengig av player sin movementspeed
    public int getMovementSpeed(){
        return player.getMovementSpeed();
    }
    //TODO- Lagt inn i interface. Bakgrunn er avhengig av player sin movementspeed
    public void setMovementSpeed(int speed){
        player.setMovementSpeed(speed);
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
        return this.gameScore;
    }

    @Override
    public int getCoinScore() {
        return this.coinScore;
    }

    /**
     * // TODO - er dette en mulig løsning for å tegne tingene i view
     * Read-only list
     *
     * @return an unmodifiable list of objectList
     */
    public List<ViewableObject> getObjectList() {
        return Collections.unmodifiableList(this.objectList);
    }

}
