package inf112.skeleton.model;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.controller.ControllableWorldModel;
import inf112.skeleton.controller.Controller;
import inf112.skeleton.model.gameobject.*;
import inf112.skeleton.model.gameobject.fixedobject.Ground;
import inf112.skeleton.model.gameobject.fixedobject.item.Banana;
import inf112.skeleton.model.gameobject.fixedobject.item.Coin;
import inf112.skeleton.model.gameobject.fixedobject.item.Item;
import inf112.skeleton.model.gameobject.fixedobject.item.Star;
import inf112.skeleton.model.gameobject.mobileobject.actor.enemy.*;
import inf112.skeleton.model.gameobject.mobileobject.actor.Player;
import inf112.skeleton.view.ViewableWorldModel;
import inf112.skeleton.view.WorldView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorldModel implements ViewableWorldModel, ControllableWorldModel, ApplicationListener {
    public static final int LEVEL_WIDTH = 4500;
    GameState gameState;
    Player player;
    private Rectangle board;
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
    private final Logger logger;
    private final int height;
    private final CollisionHandler collisionHandler;

    public WorldModel(int width, int height) {
        this.height = height;
        this.worldView = new WorldView(this, width, height);
        this.gameState = GameState.GAME_MENU;
        this.logger = LoggerFactory.getLogger(WorldModel.class);
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
        board = new Rectangle(0,0,LEVEL_WIDTH, height); // TODO, Revisjon, trenger ikke lengre WordlBoard
    }

    @Override
    public void create() {
        setupGameObjects();
        setupGraphics();
        setupInput();
        setupLogger();
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
//        collisionHandler.init(); // TODO, må flytte init (denne initierer bare SoundHandler)
    }

    //TODO: Skal denne fjernes?
    private void setupLogger() {
//        logger.info("FPS {}", Gdx.graphics.getFramesPerSecond());
//        logger.info("Height {}", Gdx.graphics.getHeight());
//        logger.info("Width {}", Gdx.graphics.getWidth());
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

    /**
     * Filters player's position to be valid.
     * A valid position is a position that does not overlap with any other {@link GameObject} types.
     * The filter-algorithm will favor the desired distances.
     *
     * @param deltaX    the desired distance in the horizontal direction.
     * @param deltaY    the desired distance in the vertical direction.
     * @return          filtered player position.
     */
    private Vector2 filterPlayerPosition(int deltaX, int deltaY) {
        Transform transform = player.getTransform();
        Vector2 position = transform.getPos();
        Vector2 size = transform.getSize();

        float filteredX = binarySearch(position.x, position.y, deltaX, size, true);
        float filteredY = binarySearch(filteredX, position.y, deltaY, size, false);

        return new Vector2(filteredX, filteredY);
    }

    private float binarySearch(float startX, float startY, int delta, Vector2 size, boolean isX) {
        int low = 0;
        int high = Math.abs(delta);
        boolean isNegative = delta < 0;

        while (low < high) {
            int mid = (low + high + 1) / 2;
            int testDelta = isNegative ? -mid : mid;

            Vector2 newPosition = isX ? new Vector2(startX + testDelta, startY) : new Vector2(startX, startY + testDelta);
            Transform newTransform = new Transform(newPosition, size);
            CollisionBox newCollisionBox = new CollisionBox(newTransform);

            if (isLegalMove(newCollisionBox)) {
                low = mid;
            } else {
                high = mid - 1;
            }
        }

        final float startCoordinate = isX ? startX : startY;
        final float endCoordinate = isNegative ? -low : low;

        return startCoordinate + endCoordinate;
    }

    boolean isLegalMove(Rectangle rectangle) {
        return positionIsOnBoard(rectangle) && !isColliding(rectangle);
    }

    private boolean positionIsOnBoard(Rectangle rectangle) {
        final int belowLevel = -200;
        return board.contains(rectangle) && rectangle.getX() > viewportLeftX;
    }

    //TODO: Må skrives om, ikke lov med instanceof-sjekk av objekter. Flyttes til actor eller player?
    private boolean isColliding(CollisionBox collisionBox){
        Pair<Boolean, GameObject> collided = collisionHandler.checkCollision(player, objectList, collisionBox);
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

    // TODO: Må skrives om og kanskje flyttes til movable? Evt actor eller player?
    private boolean isTouchingGround() {
        for (GameObject object : objectList) {
            if (object instanceof Ground) {
                if (player.getRectangle().overlaps(object.getRectangle())){
                    return true;
                }
            }
        }
        return false;
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
        Vector2 newPlayerPosition = filterPlayerPosition(deltaX, deltaY);
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
        boolean isGrounded = isTouchingGround();
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
    public int getMovementSpeed() {
        return player.getMovementSpeed();
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
