package inf112.skeleton.model;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.controller.ControllableWorldModel;
import inf112.skeleton.controller.Controller;
import inf112.skeleton.model.gameobject.*;
import inf112.skeleton.model.gameobject.fixedobject.item.Banana;
import inf112.skeleton.model.gameobject.fixedobject.item.Coin;
import inf112.skeleton.model.gameobject.fixedobject.item.Star;
import inf112.skeleton.model.gameobject.mobileobject.actor.enemy.*;
import inf112.skeleton.model.gameobject.mobileobject.actor.Player;
import inf112.skeleton.view.ViewableWorldModel;
import inf112.skeleton.view.WorldView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorldModel implements ViewableWorldModel, ControllableWorldModel, ApplicationListener, PositionValidator {
    public static final int LEVEL_WIDTH = 4500;
    GameState gameState;
    Player player;
    private WorldBoard board;
    private WorldView worldView;
    private float viewportLeftX;
    private Controller controller;
    List<GameObject> objectList;
    private final List<GameObject> toRemove;
    private LevelManager.Level currentLevel;
    private Integer totalScore;
    int countDown;
    private Integer coinCounter;
    long lastScoreUpdate = System.currentTimeMillis();
    private boolean infoMode;
    boolean isMovingRight;
    boolean isMovingLeft;
    boolean isJumping;
    private final int height;
    private final CollisionHandler collisionHandler;

    public WorldModel(int width, int height) {
        this.height = height;
        this.worldView = new WorldView(this, width, height);
        this.gameState = GameState.GAME_MENU;
        this.currentLevel = LevelManager.Level.LEVEL_1;
        this.toRemove = new ArrayList<>();
        this.collisionHandler = new CollisionHandler(height);
        this.coinCounter = 0;
        this.totalScore = 0;
        setUpModel();
    }

    public void setUpModel() {
        viewportLeftX = 0;
        countDown = 150;
        isMovingRight = false;
        isMovingLeft = false;
        isJumping = false;
        board = new WorldBoard(LEVEL_WIDTH, height);
    }

    @Override
    public void create() {
        setupGameObjects();
        setupGraphics();
        setupInput();
    }

    private void setupGameObjects() {
        Pair<List<GameObject>, Player> pair = LevelManager.loadLevel(currentLevel);
        objectList = pair.first;
        player = pair.second;
        player.setRespawned(true);
    }

    private void setupGraphics() {
        Gdx.graphics.setForegroundFPS(60);
        worldView.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        worldView.show();
    }

    private void setupInput() {
        controller = new Controller(this);
        Gdx.input.setInputProcessor(controller);
        collisionHandler.init();
    }

    /**
     * Start the specified level.
     *
     * @param level The level to start
     */
    public void startLevel(LevelManager.Level level) {
        currentLevel = level;
        setUpModel();
        create();
        resume();
    }

    @Override
    public boolean isLegalMove(CollisionBox collisionBox) {
        return positionIsOnBoard(collisionBox) && !isColliding(collisionBox);
    }

    private boolean positionIsOnBoard(CollisionBox collisionBox) {
        final int belowLevel = -200;
        boolean isWithinWidthBound = collisionBox.botLeft.x >= 0 &&
                collisionBox.botLeft.x > viewportLeftX &&
                collisionBox.topRight.x < board.width();
        boolean isWithinHeightBound = collisionBox.botLeft.y >= belowLevel  && collisionBox.topRight.y < board.height();

        return isWithinWidthBound && isWithinHeightBound;
    }

    private boolean isColliding(CollisionBox collisionBox){
        Pair<Boolean, GameObject> collided = collisionHandler.checkCollision(player, Collections.unmodifiableList(objectList), collisionBox);
        if (collided.first && !toRemove.contains(collided.second)) {
            if (collided.second instanceof Coin coin) {
                int newScore = collisionHandler.handleCoinCollision(coin, totalScore);
                coinCounter++;
                totalScore = newScore;
                toRemove.add(coin);
            } else if (collided.second instanceof Banana banana) {
                collisionHandler.handleBananaCollision(player, banana);
                toRemove.add(banana);
            } else if (collided.second instanceof Star star) {
                LevelManager.Level nextLevel = collisionHandler.handleStarCollision(currentLevel);
                startLevel(nextLevel);
                toRemove.add(star);

            } else if (collided.second instanceof Enemy enemy) {
                totalScore = collisionHandler.handleEnemyCollision(player, enemy, totalScore, collisionBox);
                if (!enemy.isAlive())  toRemove.add(enemy);
            }
        }
        return collided.first;
    }

    @Override
    public void render() {
        final float deltaTime = Gdx.graphics.getDeltaTime();
        if (gameState.equals(GameState.GAME_ACTIVE)) {
            updateScore(shouldUpdateCountDown());
            updatePlayerMovement(deltaTime);
            moveEnemies(deltaTime);
            checkForGameOver();
            objectList.removeAll(toRemove);
            toRemove.clear();
        }
        worldView.render(deltaTime);
    }

    void updateScore(boolean countingDown) {
        if (countingDown) {
            countDown--;
            lastScoreUpdate = System.currentTimeMillis();
        }
    }

    boolean shouldUpdateCountDown() {
        long currentTime = System.currentTimeMillis();
        if (countDown == 0) {
            gameState = GameState.GAME_OVER;
        }
        return currentTime - lastScoreUpdate >= 1000 && countDown >0 && gameState == GameState.GAME_ACTIVE;
    }

    private void resolvePlayerMovement(int deltaX, int deltaY) {
        Vector2 newPlayerPosition = player.filterPosition(deltaX, deltaY, this);
        if (!player.getRespawned()) {
            player.move(newPlayerPosition);
        }
        player.setRespawned(false);
        final int belowLevel = -200;
        if (newPlayerPosition.y <= belowLevel) {
            player.receiveDamage(player.getLives());
        }
    }

    private void updatePlayerMovement(float deltaTime) {
        boolean isGrounded = player.isTouchingGround(Collections.unmodifiableList(objectList));
        if (isJumping) {
            player.jump(isGrounded);
        }
        player.applyGravity(deltaTime, isGrounded);
        int deltaY = (int)(player.getVerticalVelocity() * deltaTime);
        resolvePlayerMovement(0, deltaY);
        if (isMovingRight ^ isMovingLeft) {
            int direction = isMovingRight ? 1 : -1;
            int deltaX = (int)(player.getMovementSpeed() * deltaTime) * direction;
            resolvePlayerMovement(deltaX, 0);
        }
    }

    void moveEnemies(float deltaTime) {
        for (GameObject gameObject : objectList) {
            if (gameObject instanceof Enemy enemy) {
                enemy.moveEnemy(deltaTime, objectList);
            }
        }
    }

    void checkForGameOver() {
        if (!player.isAlive()){
            gameState = GameState.GAME_OVER;
        }
    }

    @Override
    public void setInfoMode(boolean infoMode) {
        this.infoMode = infoMode;
    }

    @Override
    public void backToGameMenu() {
        coinCounter = 0;
        totalScore = 0;
        infoMode = false;
        gameState = GameState.GAME_MENU;
    }

    @Override
    public void pause() {
        gameState = GameState.GAME_PAUSED;
    }

    @Override
    public void resume() {
        infoMode = false;
        gameState = GameState.GAME_ACTIVE;
    }

    @Override
    public int getBoardWidth() {
        return board.width();
    }

    @Override
    public ViewableObject getViewablePlayer() {
        return player;
    }

    @Override
    public List<ViewableObject> getObjectList() {
        return Collections.unmodifiableList(objectList);
    }

    @Override
    public void setMovingRight(boolean movingRight) {
        isMovingRight = movingRight;
    }

    @Override
    public void setMovingLeft(boolean movingLeft) {
        isMovingLeft = movingLeft;
    }

    @Override
    public void setJumping(boolean isJumping) {
        this.isJumping = isJumping;
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public int getTotalScore() {
        return totalScore;
    }

    @Override
    public int getCoinCounter() {
        return coinCounter;
    }

    @Override
    public int getPlayerLives() {
        return player.getLives();
    }

    @Override
    public int getCountDown() {
        return countDown;
    }

    @Override
    public int getMovementDirection() {
        return player.getMovementDirection();
    }

    @Override
    public int getLevelWidth() {
        return LEVEL_WIDTH;
    }

    @Override
    public boolean getInfoMode() {
        return infoMode;
    }

    @Override
    public void updateViewportLeftX(float leftX) {
        viewportLeftX = leftX;
    }

    @Override
    public void dispose() {}

    @Override
    public void resize( int i, int i1) {}
}