package inf112.skeleton.model;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.controller.ControllableWorldModel;
import inf112.skeleton.controller.Controller;
import inf112.skeleton.model.gameobject.*;
import inf112.skeleton.model.gameobject.fixedobject.FixedObject;
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

    private GameState gameState;
    private Player player;
    private WorldBoard board;
    private WorldView worldView;
    private Controller controller;
    private List<GameObject> objectList;
    private final List<GameObject> toRemove;
    private SoundHandler soundHandler;
    private LevelManager.Level currentLevel;
    private int totalScore;
    private int countDown;
    private int coinCounter;
    private long lastScoreUpdate = System.currentTimeMillis();
    private boolean isMovingRight;
    private boolean isMovingLeft;
    private boolean isJumping;
    private final Logger logger;
    private final int height;
    private static final long ATTACK_COOLDOWN = 800;
    private static final long BOUNCE_COOLDOWN = 64;
    private static final int GRAVITY_FORCE = -3200;

    public WorldModel(int width, int height) {
        this.height = height;
        this.worldView = new WorldView(this, width, height);
        this.gameState = GameState.GAME_MENU;
        this.logger = LoggerFactory.getLogger(WorldModel.class);
        this.currentLevel = LevelManager.Level.LEVEL_1;
        this.toRemove = new ArrayList<>();
        setUpModel();
    }

    public void setUpModel() {
        coinCounter = 0;
        countDown = 150;
        totalScore = 0;
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
        soundHandler = new SoundHandler();
    }

    private void setupInput() {
        controller = new Controller(this);
        Gdx.input.setInputProcessor(controller);
    }

    private void setupLogger() {
        logger.info("FPS {}", Gdx.graphics.getFramesPerSecond());
        logger.info("Height {}", Gdx.graphics.getHeight());
        logger.info("Width {}", Gdx.graphics.getWidth());
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

    /**
     * Checks if MobileObject can be moved where it wants to move or not.
     *
     * @return True if the position is legal, false otherwise
     */
    private boolean isLegalMove(CollisionBox collisionBox) {
        if (!positionIsOnBoard(collisionBox)) return false;
        if (isColliding(collisionBox)) return false;

        return true;
    }

    private boolean positionIsOnBoard(CollisionBox collisionBox) {
        final int belowLevel = -200;
        boolean isWithinWidthBound = collisionBox.botLeft.x >= 0 &&
                collisionBox.botLeft.x > worldView.getViewportLeftX() &&
                collisionBox.topRight.x < board.width();
        boolean isWithinHeightBound = collisionBox.botLeft.y >= belowLevel  && collisionBox.topRight.y < board.height();

        return isWithinWidthBound && isWithinHeightBound;
    }

    private boolean isColliding(CollisionBox collisionBox) {
        if (gameState != GameState.GAME_ACTIVE) {
            return false;
        }
        for (GameObject gameObject : objectList) {
            if (gameObject instanceof Player) continue;

            // Collision from above
            boolean isCollidingFromTop = collisionBox.isCollidingFromTop(gameObject.getCollisionBox());
            boolean isCollidingOnCeilingOfLevel = collisionBox.topRight.y >= height - 1;
            boolean isGround = gameObject instanceof FixedObject && !(gameObject instanceof Item);
            if ((isCollidingFromTop && isGround) || isCollidingOnCeilingOfLevel) {
                if (player.getVerticalVelocity() > 0) {
                    final float bumpForceLoss = 0.1f;
                    final int bumpSpeed = (int) (-player.getVerticalVelocity() * bumpForceLoss);
                    player.setVerticalVelocity(bumpSpeed);
                }
                return true;
            }

            // Any type of collision
            if (collisionBox.isCollidingWith(gameObject.getCollisionBox())) {
                if (gameObject instanceof Enemy enemy) {
                    handleEnemyCollision(collisionBox, enemy);
                } else if (gameObject instanceof Coin coin) {
                    handleCoinCollision(coin);
                } else if (gameObject instanceof Banana banana) {
                    handleBananaCollision(banana);
                } else if (gameObject instanceof Star star) {
                    handleStarCollision(star);
                }
                return true;
            }
        }
        return false;
    }

    private void handleEnemyCollision(CollisionBox newPlayerCollisionBox, Enemy enemy) {
        long currentTime = System.currentTimeMillis();

        if (newPlayerCollisionBox.isCollidingFromBottom(enemy.getCollisionBox())){
            if (currentTime - player.getLastBounceTime() >= BOUNCE_COOLDOWN) {

                player.bounce();
                player.dealDamage(enemy, player.getDamage());

                if (!enemy.isAlive()) {
                    totalScore += enemy.getObjectScore();
                    toRemove.add(enemy);
                }

                player.setLastBounceTime(currentTime);
            }
        } else {
            if (currentTime - player.getLastAttackTime() >= ATTACK_COOLDOWN) {

                // TODO...
                // Enemy dealing damage to the player is moved into Enemy.moveEnemy()
                // - This is to make sure that the enemy doesn't deal damage twice.
                // - The logic needs to be inside Enemy class. If not, the enemy won't deal damage
                //   when it collides with the player.
                // - As of right now, ATTACK_COOLDOWN only affects totalScore. It does NOT affect the frequency of attacks.

                // Reduce total score
                final int scorePenalty = 4;
                totalScore = Math.max(0, totalScore - scorePenalty);

                player.setLastAttackTime(currentTime);
            }
        }
    }

    private void handleCoinCollision(Coin coin) {
        final int objectScore = coin.getObjectScore();
        soundHandler.playCoinSound();
        coinCounter++;
        totalScore += objectScore;
        toRemove.add(coin);
    }

    private void handleBananaCollision(Banana banana) {
        player.bananaCollision();
        toRemove.add(banana);
    }

    private void handleStarCollision(Star star) {
        toRemove.add(star);

        switch (currentLevel) {
            case LEVEL_1: startLevel(LevelManager.Level.LEVEL_2); break;
            case LEVEL_2: startLevel(LevelManager.Level.LEVEL_3); break;
            case LEVEL_3: startLevel(LevelManager.Level.LEVEL_1); break;
        }
    }

    private boolean isTouchingGround() {
        for (GameObject object : objectList) {
            if (!(object instanceof Enemy || object instanceof Item || object instanceof Player)) {
                CollisionBox objectCollisionBox = object.getCollisionBox();
                if (player.getCollisionBox().isCollidingFromBottom(objectCollisionBox)) {
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
            updateScore();
            moveEnemies(deltaTime);
            checkForGameOver();
            updateMovables(deltaTime);
            objectList.removeAll(toRemove);
            toRemove.clear();
        }

        worldView.render(deltaTime);
    }

    private void updateScore() {
        if (shouldUpdateCountDown()) {
            countDown--;
            lastScoreUpdate = System.currentTimeMillis();
        }
    }

    private boolean shouldUpdateCountDown() {
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

    private void updateMovables(float deltaTime) {
        for (GameObject obj : objectList) {
            if (obj instanceof Movable movable) {

                if (obj == player) {
                    boolean isGrounded = isTouchingGround();

                    if (isJumping) {
                        player.jump(isGrounded);
                    }

                    player.applyGravity(GRAVITY_FORCE, deltaTime, isGrounded);

                    int deltaY = (int)(player.getVerticalVelocity() * deltaTime);
                    resolvePlayerMovement(0, deltaY);

                    int deltaX = 0;
                    if (isMovingRight ^ isMovingLeft) {
                        int direction = isMovingRight ? 1 : -1;
                        deltaX = (int)(player.getMovementSpeed() * deltaTime) * direction;
                        resolvePlayerMovement(deltaX, 0);
                    }

                } else {
                    movable.applyGravity(GRAVITY_FORCE, deltaTime, true);
                    movable.moveVertically(deltaTime);
                }
            }
        }
    }


    private void moveEnemies(float deltaTime) {
        for (GameObject gameObject : objectList) {
            if (gameObject instanceof Enemy enemy) {
                enemy.moveEnemy(deltaTime, objectList);
            }
        }
    }

    private void checkForGameOver() {
        if (!player.isAlive()){
            gameState = GameState.GAME_OVER;
        }
    }

    @Override
    public void setToInfoMode() {
        gameState = GameState.GAME_INFO;
    }

    @Override
    public void backToGameMenu() {
        gameState = GameState.GAME_MENU;
    }

    @Override
    public void pause() {
        gameState = GameState.GAME_PAUSED;

    }

    @Override
    public void resume() {
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

    /**
     * Tells us the state of the game
     * @return the state of the game
     */
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
    public void dispose() {}

    @Override
    public void resize( int i, int i1) {}
}
