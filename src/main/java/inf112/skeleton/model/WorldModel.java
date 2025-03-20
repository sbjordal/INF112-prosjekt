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
import inf112.skeleton.model.gameobject.fixedobject.item.ItemFactory;
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
    private static final int NORMAL_JUMP_FORCE = 30000;
    private static final int BIG_JUMP_FORCE = 45000;
    private static final int LEVEL_WIDTH = 4500;
    private int jumpForce;
    private GameState gameState;
    private Player player;
    private Vector2 standardPlayerSize;
    private WorldBoard board;
    private WorldView worldView;
    private Controller controller;
    private ArrayList<GameObject> objectList;
    private SoundHandler soundHandler;
    private int totalScore;
    private int countDown;
    private int coinCounter;
    private long lastScoreUpdate = System.currentTimeMillis();
    private long lastEnemyCollisionTime = 0;
    private static final long COLLISION_COOLDOWN = 800;
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
        this.coinCounter = 0;
        this.countDown = 150;
        this.totalScore = 0;
        this.isMovingRight = false;
        this.isMovingLeft = false;
        this.jumpForce = NORMAL_JUMP_FORCE;
    }

    @Override
    public void create() {
        board = new WorldBoard(LEVEL_WIDTH, height);

        Gdx.graphics.setForegroundFPS(60);
        worldView.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        worldView.show();

        controller = new Controller(this);
        Gdx.input.setInputProcessor(controller);

        soundHandler = new SoundHandler();
        logger.info("FPS {}", Gdx.graphics.getFramesPerSecond());
        logger.info("Height {}", Gdx.graphics.getHeight());
        logger.info("Width {}", Gdx.graphics.getWidth());
        initiateGameObjects(); //TODO, må muligens endres etter vi bruker input-fil for level design
    }

    /**
     * Initiates all instances of type GameObject (level-design)
     *
     */
    private void initiateGameObjects() { //TODO, må endres etter ny måte for level-design
        objectList = new ArrayList<>();
        createGround();
        createObstacles();

        this.standardPlayerSize = new Vector2(40, 80);
        Vector2 playerPosition = new Vector2(380, 500);
        Transform playerTransform = new Transform(playerPosition, standardPlayerSize);
        player = new Player(1, 300, playerTransform); // TODO, legg til argument (foreløpig argumenter for å kunne kompilere prosjektet)

        Snail snail = (Snail) EnemyFactory.createEnemy(150, 100, EnemyType.SNAIL); // TODO: create classes inside EnemyFactory intead of casing.
        Leopard leopard = (Leopard) EnemyFactory.createEnemy(40, 100, EnemyType.LEOPARD); // TODO: create classes inside EnemyFactory intead of casing.
        Leopard leopard2 = (Leopard) EnemyFactory.createEnemy(2200, 100, EnemyType.LEOPARD); // TODO: create classes inside EnemyFactory intead of casing.

        Coin coin1 = ItemFactory.createCoin(510, 250);
        Coin coin2 = ItemFactory.createCoin(1400, 105);

        Banana banana = ItemFactory.createMushroom(550, 100);
        Banana banana2 = ItemFactory.createMushroom(850, 100);

        // TODO: en stygg måte å lage hindring på for nå
        this.objectList = new ArrayList<>();
        createGround();
        createObstacles();

        Gdx.graphics.setForegroundFPS(60);
        worldView.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        worldView.show();

        // Fill up the object list
        this.objectList.add(snail); // TODO: må endres når vi har flere enemies.
        this.objectList.add(leopard);
        this.objectList.add(leopard2);
        this.objectList.add(coin1); // TODO: må endres til å bruke coinfactory
        this.objectList.add(coin2); // TODO: må endres til å bruke coinfactory
        this.objectList.add(banana); // TODO: må endres til å bruke coinfactory
        this.objectList.add(banana2); // TODO: må endres til å bruke coinfactory

        this.controller = new Controller(this);
        Gdx.input.setInputProcessor(this.controller);

        this.soundHandler = new SoundHandler();
        this.logger.info("FPS {}", Gdx.graphics.getFramesPerSecond());
        this.logger.info("Height {}", Gdx.graphics.getHeight());
        this.logger.info("Width {}", Gdx.graphics.getWidth());
    }

    /**
     * Helper function for level-design.
     */
    private void createGround() { //TODO, må endres etter ny måte for level-design
        Vector2 size = new Vector2(50, 50);
        int y = 0;

        for (int i = 0; i < 2; i++) {
            int widthFilled = 0;
            int x = 0;
            while (widthFilled < board.width()) {
                FixedObject groundObject = new FixedObject(new Transform(new Vector2(x, y), size));
                objectList.add(groundObject);
                widthFilled += 50;
                x += 50;
            }
            y += 50;
        }
    }

    /**
     * Helper function for level-design.
     */
    private void createObstacles() { //TODO, må endres etter ny måte for level-design
        int x = 1130;
        int y = 100;
        int width = 50;
        int height = 50;
        Vector2 platformSize = new Vector2(width, height);

        FixedObject coinPlatform = new FixedObject(new Transform(new Vector2(500, y), platformSize));

        FixedObject platform1 = new FixedObject(new Transform(new Vector2(x, y), platformSize));
        FixedObject platform2 = new FixedObject(new Transform(new Vector2(x+width, y), platformSize));
        FixedObject platform3 = new FixedObject(new Transform(new Vector2(x+width*2, y), platformSize));
        FixedObject platform4 = new FixedObject(new Transform(new Vector2(x+width, y+height), platformSize));
        FixedObject platform5 = new FixedObject(new Transform(new Vector2(x+width*2, y+height), platformSize));
        FixedObject platform6 = new FixedObject(new Transform(new Vector2(x+width*2, y+2*height), platformSize));

        objectList.add(platform1);
        objectList.add(platform2);
        objectList.add(platform3);
        objectList.add(platform4);
        objectList.add(platform5);
        objectList.add(platform6);
        objectList.add(coinPlatform);
    }

    @Override
    public void move(int deltaX, int deltaY) {
        Vector2 newPlayerPosition = filterPlayerPosition(deltaX, deltaY);
        player.move(newPlayerPosition);
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

        int lowX = 0;
        int lowY = 0;
        int highX = Math.abs(deltaX);
        int highY = Math.abs(deltaY);

        boolean isDeltaXNegative = (deltaX < 0);
        boolean isDeltaYNegative = (deltaY < 0);

        // Binary search on x-axis
        while (lowX < highX) {
            int midX = (lowX + highX + 1) / 2;
            int testX = isDeltaXNegative ? -midX : midX;

            Vector2 newPosition = new Vector2(position.x + testX, position.y);
            Transform newTransform = new Transform(newPosition, size);
            CollisionBox newCollisionBox = new CollisionBox(newTransform);

            if (isLegalMove(newCollisionBox)) {
                lowX = midX;
            } else {
                highX = midX - 1;
            }
        }

        // Binary search on y-axis
        while (lowY < highY) {
            int midY = (lowY + highY + 1) / 2;
            int testY = isDeltaYNegative ? -midY : midY;

            Vector2 newPosition = new Vector2(position.x + (isDeltaXNegative ? -lowX : lowX), position.y + testY);
            Transform newTransform = new Transform(newPosition, size);
            CollisionBox newCollisionBox = new CollisionBox(newTransform);

            if (isLegalMove(newCollisionBox)) {
                lowY = midY;
            } else {
                highY = midY - 1;
            }
        }

        int newDeltaX = isDeltaXNegative ? -lowX : lowX;
        int newDeltaY = isDeltaYNegative ? -lowY : lowY;

        return new Vector2(position.x + newDeltaX, position.y + newDeltaY);
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
            return false;
        }

        return true;
    }

    private boolean positionIsOnBoard(CollisionBox collisionBox) {
        boolean isWithinWidthBound = collisionBox.botLeft.x >= 0 &&
                collisionBox.botLeft.x > worldView.getViewportLeftX() &&
                collisionBox.topRight.x < board.width();
        boolean isWithinHeightBound = collisionBox.botLeft.y >= 0  && collisionBox.topRight.y < board.height();

        return isWithinWidthBound && isWithinHeightBound;
    }

    private boolean isColliding(CollisionBox collisionBox) {
        if (gameState != GameState.GAME_ACTIVE) {
            return false;
        }

        for (GameObject gameObject : objectList) {
            if (collisionBox.isCollidingWith(gameObject.getCollisionBox())) {
                if (gameObject instanceof Coin coin) {
                    handleCoinCollision(coin);
                }
                else if (gameObject instanceof Enemy enemy) {
                    handleEnemyCollision(collisionBox, enemy);
                } else if (gameObject instanceof Banana banana) {
                    handleMushroomCollision(banana);
                }
                return true;
            }
        }
        return false;
    }

private void handleEnemyCollision(CollisionBox newPlayerCollisionBox, Enemy enemy) {
    if (newPlayerCollisionBox.isCollidingFromBottom(enemy.getCollisionBox())){
        totalScore += enemy.getObjectScore();
        objectList.remove(enemy); // TODO: enemy skal kun bli fjernet om health <= 0. Eventuelt fjern health variabelen fra enemies.
    }
    else{
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastEnemyCollisionTime >= COLLISION_COOLDOWN) {

            // If the player has a powerUp it loses this power up instead of receiving damage
            if (player.getHasPowerUp()) {
                player.setHasPowerUp(false);
                player.setSize(standardPlayerSize);
                jumpForce = NORMAL_JUMP_FORCE;
            }
            else {
                // Enemy deals damage to the player
                player.receiveDamage(enemy.getDamage());
            }

            // Reduce total score
            final int scorePenalty = 4;
            if (totalScore >= scorePenalty) {
                totalScore -= scorePenalty;
            }
            lastEnemyCollisionTime = currentTime;
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

    private void handleMushroomCollision(Banana banana) {
        Vector2 bigSize = new Vector2(150, 150);
        player.setHasPowerUp(true);
        player.setSize(bigSize);
        jumpForce = BIG_JUMP_FORCE;
        objectList.remove(banana);
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
            CollisionBox objectCollisionBox = object.getCollisionBox();
            if (player.getCollisionBox().isCollidingFromBottom(objectCollisionBox)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void render() {
        final float deltaTime = Gdx.graphics.getDeltaTime();
        if (gameState.equals(GameState.GAME_ACTIVE)) {
            updateScore();
            moveVertically(deltaTime);

            final boolean isMoving = (isMovingLeft && !isMovingRight) || (!isMovingLeft && isMovingRight);
            if (isMoving) {
                moveHorizontally(deltaTime);
            }
        }

        if (!player.isAlive() && gameState == GameState.GAME_ACTIVE){
            gameState = GameState.GAME_OVER;
        }



        worldView.render(deltaTime);
    }

    private void updateScore() {
        if (shouldUpdateCountDown()) {
            countDown--;
            lastScoreUpdate = System.currentTimeMillis();
        }
    }

    private void moveHorizontally(float deltaTime) {
        final int movementSpeed = getMovementSpeed();
        final int distance = (int) (movementSpeed * deltaTime);

        if (isMovingRight) {
            move(distance, 0);
        }
        else if (isMovingLeft) {
            move(-distance, 0);
        }
    }

    private void moveVertically(float deltaTime) {
        updateVerticalVelocity();
        final int playerVelocity = player.getVerticalVelocity();
        final int distance = (int) (playerVelocity * deltaTime);
        move(0, distance);
    }

    private void updateVerticalVelocity() {
        if (isTouchingGround() && player.getVerticalVelocity() <= 0 ) {
            player.setVerticalVelocity(0);
        } else {
            final int distance = (int) (GRAVITY_FORCE * Gdx.graphics.getDeltaTime());
            player.addVerticalVelocity(distance);
        }
    }

    private boolean shouldUpdateCountDown() {
        long currentTime = System.currentTimeMillis();
        if (countDown == 0) {
            gameState = GameState.GAME_OVER;
        }

        return currentTime - lastScoreUpdate >= 1000 && countDown >0 && gameState == GameState.GAME_ACTIVE;
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
    public void dispose() {
        // TODO, implement me :)
    }

    @Override
    public void resize( int i, int i1){
        // TODO, implement me :)
    }
}
