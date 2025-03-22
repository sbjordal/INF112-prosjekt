package inf112.skeleton.model;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.controller.ControllableWorldModel;
import inf112.skeleton.controller.Controller;
import inf112.skeleton.model.gameobject.*;
import inf112.skeleton.model.gameobject.fixedobject.item.Banana;
import inf112.skeleton.model.gameobject.fixedobject.item.Coin;
import inf112.skeleton.model.gameobject.fixedobject.item.Item;
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

    private static final int GRAVITY_FORCE = -1600;
    private static final int NORMAL_BOUNCE_FORCE = 29000;
    private static final int SMALL_BOUNCE_FORCE = 17000;
    private static final int NORMAL_JUMP_FORCE = 33000;
    private static final int BIG_JUMP_FORCE = 41000;
    public static final int LEVEL_WIDTH = 4500;
    private static final Vector2 STANDARD_PLAYER_SIZE = new Vector2(40, 80);
    private static final Vector2 LARGE_PLAYER_SIZE = new Vector2(65, 135);
    private static final long ATTACK_COOLDOWN = 800;
    private static final long BOUNCE_COOLDOWN = 64;
    private int jumpForce;
    private GameState gameState;
    private Player player;
    private WorldBoard board;
    private WorldView worldView;
    private Controller controller;
    private ArrayList<GameObject> objectList;
    private SoundHandler soundHandler;
    private int totalScore;
    private int countDown;
    private int coinCounter;
    private long lastScoreUpdate = System.currentTimeMillis();
    private long lastAttackTime;
    private long lastBounceTime;
    private boolean isMovingRight;
    private boolean isMovingLeft;
    private final Logger logger;
    private final int height;

    public WorldModel(int width, int height) {
        this.height = height;
        this.worldView = new WorldView(this, width, height);
        this.gameState = GameState.GAME_MENU;
        this.logger = LoggerFactory.getLogger(WorldModel.class);
        setUpModel();
    }

    public void setUpModel() {
        coinCounter = 0;
        countDown = 150;
        totalScore = 0;
        isMovingRight = false;
        isMovingLeft = false;
        jumpForce = NORMAL_JUMP_FORCE;
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
        objectList = LevelManager.loadLevel(LevelManager.Level.LEVEL_1);
        findPlayer();
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
     * Sets the player reference to the player object.
     *
     * @throws IllegalStateException If anything other than exactly one player was found.
     */
    private void findPlayer() {
        int playerCount = 0;
        for (GameObject object : objectList) {
            if (object instanceof Player) {
                player = (Player) object;
                playerCount++;
            }
        }
        if (playerCount != 1) {
            throw new IllegalStateException("objectList must have exactly one Player, but found: " + playerCount);
        }
    }

    @Override
    public void move(int deltaX, int deltaY) {
        Vector2 newPlayerPosition = filterPlayerPosition(deltaX, deltaY);
        player.move(newPlayerPosition);

        // Player falls to his death
        final int belowLevel = -200;
        if (newPlayerPosition.y <= belowLevel) {
            player.receiveDamage(player.getLives());
        }
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
        if(!positionIsOnBoard(collisionBox)) return false;
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

            if (collisionBox.isCollidingWith(gameObject.getCollisionBox())) {
                if (gameObject instanceof Coin coin) {
                    handleCoinCollision(coin);
                } else if (gameObject instanceof Enemy enemy) {
                    handleEnemyCollision(collisionBox, enemy);
                } else if (gameObject instanceof Banana banana) {
                    handleBananaCollision(banana);
                }
                return true;
            }
        }

        return false;
    }

    private void handleEnemyCollision(CollisionBox newPlayerCollisionBox, Enemy enemy) {
        long currentTime = System.currentTimeMillis();

        if (newPlayerCollisionBox.isCollidingFromBottom(enemy.getCollisionBox())){
            if (currentTime - lastBounceTime >= BOUNCE_COOLDOWN) {
                bounce();
                player.dealDamage(enemy, player.getDamage());

                if (!enemy.isAlive()) {
                    totalScore += enemy.getObjectScore();
                    objectList.remove(enemy);
                }
            }

            lastBounceTime = currentTime;
        } else {
            if (currentTime - lastAttackTime >= ATTACK_COOLDOWN) {

                // TODO...
                // Enemy dealing damage to the player is moved into Enemy.moveEnemy()
                // - This is to make sure that the enemy doesn't deal damage twice.
                // - The logic needs to be inside Enemy class. If not, the enemy won't deal damage
                //   when it collides with the player.

                // Reduce total score
                final int scorePenalty = 4;
                if (totalScore >= scorePenalty) {
                    totalScore -= scorePenalty;
                }

                lastAttackTime = currentTime;
            }
        }
    }

    private void handleCoinCollision(Coin coin) {
        final int objectScore = coin.getObjectScore();
        soundHandler.playCoinSound();
        coinCounter++;
        totalScore += objectScore;
        objectList.remove(coin);
    }

    private void handleBananaCollision(Banana banana) {
        player.setHasPowerUp(true);
        player.setSize(LARGE_PLAYER_SIZE);
        int middleOfPlayer = (int) (player.getTransform().getSize().x / 2);
        player.move(-middleOfPlayer, 0);
        jumpForce = BIG_JUMP_FORCE;
        objectList.remove(banana);
    }

    /**
     * Makes the player bounce.
     * A bounce is a lower altitude jump.
     */
    private void bounce() {
        final int bounceForce = player.getHasPowerUp() ? SMALL_BOUNCE_FORCE : NORMAL_BOUNCE_FORCE;
        final int distance = (int) (bounceForce * Gdx.graphics.getDeltaTime());
        player.jump(distance);
    }

    @Override
    public void jump() {
        if (isTouchingGround()) {
            final int distance = (int) (jumpForce * Gdx.graphics.getDeltaTime());
            player.jump(distance);
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
            movePlayer(deltaTime);
            checkForGameOver();
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

    private void movePlayer(float deltaTime) {
        moveVertically(deltaTime);
        moveHorizontally(deltaTime);
    }

    private void moveVertically(float deltaTime) {
        updateVerticalVelocity();
        final int playerVelocity = player.getVerticalVelocity();
        final int distance = (int) (playerVelocity * deltaTime);
        move(0, distance);
    }

    private void moveHorizontally(float deltaTime) {
        final boolean isMoving = (isMovingLeft && !isMovingRight) || (!isMovingLeft && isMovingRight);
        if (isMoving) {
            final int movementSpeed = getMovementSpeed();
            final int distance = (int) (movementSpeed * deltaTime);

            if (isMovingRight) {
                move(distance, 0);
            } else if (isMovingLeft) {
                move(-distance, 0);
            }
        }
    }

    private void updateVerticalVelocity() {
        if (isTouchingGround() && player.getVerticalVelocity() <= 0 ) {
            player.setVerticalVelocity(0);
        } else {
            final int distance = (int) (GRAVITY_FORCE * Gdx.graphics.getDeltaTime());
            player.addVerticalVelocity(distance);
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
