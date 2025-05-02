package inf112.starhunt.model;

import com.badlogic.gdx.Gdx;
import inf112.starhunt.controller.ControllableWorldModel;
import inf112.starhunt.controller.Controller;
import inf112.starhunt.model.gameobject.*;
import inf112.starhunt.model.gameobject.mobileobject.actor.ModelablePlayer;
import inf112.starhunt.model.gameobject.mobileobject.actor.enemy.*;
import inf112.starhunt.view.ViewableWorldModel;
import inf112.starhunt.view.WorldView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TODO: javadoc
 */
public class WorldModel extends AbstractApplicationListener implements ViewableWorldModel, ControllableWorldModel, PositionValidator {
    public static final int LEVEL_WIDTH = 4500;

    ModelablePlayer player;
    private int countDown;
    private long lastScoreUpdate;
    private boolean isMovingRight;
    private boolean isMovingLeft;
    private boolean isJumping;

    private List<Collidable> collidables;
    private GameState gameState;
    private WorldBoard board;
    private WorldView worldView;
    private Controller controller;
    private List<Enemy> enemies;
    private List<Collidable> toRemove;
    private LevelManager.Level currentLevel;
    private final int height;
    private float viewportLeftX;
    private boolean infoMode;
    private int levelCounter;

    /**
     * TODO: javadoc
     *
     * @param width
     * @param height
     */
    public WorldModel(int width, int height) {
        this.height = height;
        this.worldView = new WorldView(this, width, height);
        this.lastScoreUpdate = System.currentTimeMillis();
        this.gameState = GameState.GAME_MENU;
        this.currentLevel = LevelManager.Level.LEVEL_1;
        this.toRemove = new ArrayList<>();
        setUpModel();

        this.levelCounter = 1;
    }

    /**
     * TODO: javadoc
     */
    public void setUpModel() {
        viewportLeftX = 0;
        countDown = 45;
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
        Triple<List<Enemy>, List<Collidable>, ModelablePlayer> triple = LevelManager.loadLevel(currentLevel);
        enemies = triple.getFirst();
        collidables = triple.getSecond();

        if (levelCounter == 1) {
            player = triple.getThird();
            player.setRespawned(true);
        } else {
            player.resetForNewLevel(triple.getThird().getTransform().getPos());
            collidables.remove(triple.getThird());
            collidables.add(player);
        }
    }

    private void setupGraphics() {
        Gdx.graphics.setForegroundFPS(60);
        worldView.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        worldView.show();
    }

    private void setupInput() {
        controller = new Controller(this);
        Gdx.input.setInputProcessor(controller);
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
    public boolean isLegalMove(Visitor visitor, CollisionBox collisionBox) {
        return positionIsOnBoard(collisionBox) && !visitor.isColliding(collidables, collisionBox);
    }

    /**
     * Checks if the {@link GameObject} is within the allowed area.
     * Also takes into consideration that the {@link inf112.starhunt.model.gameobject.mobileobject.actor.Player}
     * can "fall out of the screen", this explains the "Belowlevel variable".
     *
     * @param collisionBox of the {@link GameObject}
     * @return True if within the allowed bounds, false if not.
     */
    private boolean positionIsOnBoard(CollisionBox collisionBox) {
        final int belowLevel = -200;
        boolean isWithinWidthBound = collisionBox.getBotLeft().x >= 0 &&
                collisionBox.getBotLeft().x > viewportLeftX &&
                collisionBox.getTopRight().x < board.width();
        boolean isWithinHeightBound = collisionBox.getBotLeft().y >= belowLevel;

        return isWithinWidthBound && isWithinHeightBound;
    }

    private void goToNextLevel() {
        boolean gotNextLevel = player.getGoToNextLevel();
        if (gotNextLevel) {
            LevelManager.Level nextLevel = LevelManager.getNextLevel(currentLevel);
            currentLevel = nextLevel;
            levelCounter++;
            startLevel(nextLevel);

        }
    }

    @Override
    public void render() {
        final float deltaTime = Gdx.graphics.getDeltaTime();
        if (gameState.equals(GameState.GAME_ACTIVE)) {
            updateScore(shouldUpdateCountDown());
            updatePlayerMovement(deltaTime);
            goToNextLevel();
            moveEnemies(deltaTime);
            checkForGameOver();
            toRemove = player.getObjectsToRemove();
            collidables.removeAll(toRemove);
            enemies.removeAll(toRemove);
            toRemove.clear();
        }
        worldView.render(deltaTime);
    }

    /**
     * TODO: javadoc
     *
     * @param countingDown
     */
    void updateScore(boolean countingDown) {
        if (countingDown) {
            countDown--;
            lastScoreUpdate = System.currentTimeMillis();
        }
    }

    /**
     * TODO: javadoc
     *
     * @return
     */
    boolean shouldUpdateCountDown() {
        long currentTime = System.currentTimeMillis();
        if (countDown == 0) {
            gameState = GameState.GAME_OVER;
        }
        return (currentTime - lastScoreUpdate) >= 1000 && countDown > 0 && gameState == GameState.GAME_ACTIVE;
    }

    /**
     * TODO: javadoc
     *
     * @param deltaTime
     */
    private void updatePlayerMovement(float deltaTime) {
        boolean isGrounded = player.isTouchingGround(Collections.unmodifiableList(collidables));
        if (isJumping) {
            player.jump(isGrounded);
        }

        player.applyGravity(deltaTime, isGrounded);
        float deltaY = player.getVerticalVelocity() * deltaTime;
        player.resolveMovement(0, deltaY, this);

        final boolean isMoving = isMovingRight ^ isMovingLeft;
        boolean actuallyMovedHorizontally = false;
        if (isMoving) {
            int direction = isMovingRight ? 1 : -1;
            player.setMovementDirection(direction);
            float deltaX = (player.getMovementSpeed() * deltaTime) * direction;
            float beforeX = player.getTransform().getPos().x;
            player.resolveMovement(deltaX, 0, this);
            float afterX = player.getTransform().getPos().x;
            actuallyMovedHorizontally = (beforeX != afterX);
            player.setIsMovingHorizontally(actuallyMovedHorizontally);
        }


        final boolean isStandingStill = !(isMovingRight || isMovingLeft);
        if (isStandingStill) {
            player.setMovementDirection(0);
        }
    }

    /**
     * Moves all living enemy objects.
     *
     * @param deltaTime
     */
    void moveEnemies(float deltaTime) {
        for (Enemy enemy : enemies) {
            float deltaX = (enemy.getMovementSpeed() * deltaTime) * enemy.getMovementDirection();
            enemy.resolveMovement(deltaX, 0, this);
        }
    }

    /**
     * TODO: javadoc
     */
    void checkForGameOver() {
        if (!player.getIsAlive()){
            gameState = GameState.GAME_OVER;
        }
    }

    @Override
    public void setInfoMode(boolean infoMode) {
        this.infoMode = infoMode;
    }

    @Override
    public void backToGameMenu() {
        player.resetScores();
        infoMode = false;
        gameState = GameState.GAME_MENU;
        currentLevel= LevelManager.Level.LEVEL_1;
        levelCounter = 1;
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
        return Collections.unmodifiableList(collidables);
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
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public LevelManager.Level getCurrentLevel() {
        return currentLevel;
    }

    @Override
    public int getTotalScore() {
        return player.getTotalScore();
    }

    @Override
    public int getCoinCounter() {
        return player.getCoinCounter();
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
    public int getLevelCounter() {
        return levelCounter;
    }

    @Override
    public void updateViewportLeftX(float leftX) {
        viewportLeftX = leftX;
    }

    @Override
    public float getVerticalVelocity() {
        return player.getVerticalVelocity();
    }

    @Override
    public boolean getPlayerMovement() {
        return player.getIsMovingHorizontally();
    }

    List<Enemy> getEnemies() {
        return Collections.unmodifiableList(enemies);
    }

    void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    void setCollidables(List<Collidable> collidables) {
        this.collidables = collidables;
    }

    void setCountDown(int countDown) {
        this.countDown = countDown;
    }

    void setLastScoreUpdate(long lastScoreUpdate) {
        this.lastScoreUpdate = lastScoreUpdate;
    }

    boolean getIsMovingRight() {
        return isMovingRight;
    }

    boolean getIsMovingLeft() {
        return isMovingLeft;
    }

    boolean getIsJumping() {
        return isJumping;
    }
}
