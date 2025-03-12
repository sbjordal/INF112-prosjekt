package inf112.skeleton.model;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import inf112.skeleton.controller.ControllableWorldModel;
import inf112.skeleton.controller.Controller;
import inf112.skeleton.model.gameobject.*;
import inf112.skeleton.model.gameobject.fixedobject.FixedObject;
import inf112.skeleton.model.gameobject.fixedobject.item.Coin;
import inf112.skeleton.model.gameobject.mobileobject.actor.Enemy;
import inf112.skeleton.model.gameobject.mobileobject.actor.Player;
import inf112.skeleton.view.ViewableWorldModel;
import inf112.skeleton.view.WorldView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorldModel implements ViewableWorldModel, ControllableWorldModel, ApplicationListener {

    private static final int GRAVITY_FORCE = -1600;
    private static final int JUMP_FORCE = 30000; // Må være høy

    private GameState gameState;
    private Player player;
    private WorldBoard board;
    private WorldView worldView;
    private Controller controller;
    private ArrayList<GameObject> objectList;
    private SoundHandler soundHandler;
    private int totalScore;
    private int coinCounter;
    private long lastScoreUpdate = System.currentTimeMillis();
    private long lastEnemyCollisionTime = 0;
    private static final long COLLISION_COOLDOWN = 800;
    private boolean isMovingRight;
    private boolean isMovingLeft;

    public WorldModel(WorldBoard board) {
        this.gameState = GameState.GAME_ACTIVE; // TODO, må endres etter at game menu er laget.
        this.worldView = new WorldView(this, new ExtendViewport(board.width(),board.height()));
        this.board = board;
        this.coinCounter = 0;
        this.totalScore = 150;
        this.isMovingRight = false;
        this.isMovingLeft = false;
    }

    @Override
    public void create() {
        this.player = new Player(1, 300); // TODO, legg til argument (foreløpig argumenter for å kunne kompilere prosjektet)
        Enemy enemy = EnemyFactory.createEnemy(40, 100, 50, 50, 1, 1, 10, 1);

        Vector2 coinPos = new Vector2(600, 105);
        Vector2 coinSize = new Vector2(30, 30);
        Coin coin = new Coin(new Transform(coinPos, coinSize));

        // TODO: en stygg måte å lage hindring på for nå
        this.objectList = new ArrayList<>();
        createGround();
        createObstacles();

        Gdx.graphics.setForegroundFPS(60);
        worldView.show();
        worldView.resize(board.width(), board.height());

        // Fill up the object list
        this.objectList.add(enemy); // TODO: må endres når vi har flere enemies.
        this.objectList.add(coin); // TODO: test coin for å teste collision.

        this.controller = new Controller(this);
        Gdx.input.setInputProcessor(this.controller);

        this.soundHandler = new SoundHandler();
    }

    private void createGround() {
        Texture groundTexture = new Texture("obstacles/castleCenter.png");
        Texture otherTexture = new Texture("obstacles/castleMid.png");
        Vector2 size = new Vector2(50, 50);
        int y = 0;

        for (int i = 0; i < 2; i++) {
            int widthFilled = 0;
            int x = 0;
            while (widthFilled < board.width()) {
                FixedObject groundObject = new FixedObject(new Transform(new Vector2(x, y), size), groundTexture);
                objectList.add(groundObject);
                widthFilled += 50;
                x += 50;
            }
            y += 50;
            groundTexture = otherTexture;
        }
    }

    private void createObstacles() {
        Texture platformTextureMid = new Texture("obstacles/castleMid.png");
        Texture platformTextureCen = new Texture("obstacles/castleCenter.png");
        int x = 1130;
        int y = 100;
        int width = 50;
        int height = 50;
        Vector2 platformSize = new Vector2(width, height);


        FixedObject platform1 = new FixedObject(new Transform(new Vector2(x, y), platformSize), platformTextureMid);
        FixedObject platform2 = new FixedObject(new Transform(new Vector2(x+width, y), platformSize), platformTextureCen);
        FixedObject platform3 = new FixedObject(new Transform(new Vector2(x+width*2, y), platformSize), platformTextureCen);
        FixedObject platform4 = new FixedObject(new Transform(new Vector2(x+width, y+height), platformSize), platformTextureMid);
        FixedObject platform5 = new FixedObject(new Transform(new Vector2(x+width*2, y+height), platformSize), platformTextureCen);
        FixedObject platform6 = new FixedObject(new Transform(new Vector2(x+width*2, y+2*height), platformSize), platformTextureMid);

        objectList.add(platform1);
        objectList.add(platform2);
        objectList.add(platform3);
        objectList.add(platform4);
        objectList.add(platform5);
        objectList.add(platform6);
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

        // TODO: finskriv denne!
        // TODO: inkluder deltaX i beregningen
        boolean isDeltaYNegative = (deltaY < 0);
        for (int i = Math.abs(deltaY); i >= 0; i--) {
            int i2 = i;
            if (isDeltaYNegative) {
                i2 = -i2;
            }

            Vector2 newPosition = new Vector2(position.x + deltaX, position.y + i2);
            Transform newTransform = new Transform(newPosition, size);
            CollisionBox newCollisionBox = new CollisionBox(newTransform);

            if (isLegalMove(newCollisionBox)) {
                return newPosition;
            }
        }

        return position;
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
        boolean isWithinWidthBound = collisionBox.botLeft.x >= 0 && collisionBox.topRight.x < board.width();
        boolean isWithinHeightBound = collisionBox.botLeft.y >= 0  && collisionBox.topRight.y < board.height();

        return isWithinWidthBound && isWithinHeightBound;
    }

    private boolean positionIsOnBoard(Vector2 pos) {
        boolean isWithinWidthBound = pos.x >= 0 && pos.x < board.width();
        boolean isWithinHeightBound = pos.y >= 0  && pos.y < board.height();

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
                } else if (gameObject instanceof Enemy enemy) { // TODO: legge til at dersom man hopper på en enemy får man poeng og fienden dør
                    handleEnemyCollision(enemy);
                }

                return true;
            }
        }

        return false;
    }

    private void handleEnemyCollision(Enemy enemy) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastEnemyCollisionTime >= COLLISION_COOLDOWN) {

            // Enemy deals damage to the player
            player.receiveDamage(enemy.getDamage());

            // Reduce total score
            final int scorePenalty = 4;
            if (totalScore >= scorePenalty) {
                totalScore -= scorePenalty;
            }
            lastEnemyCollisionTime = currentTime;
        }
    }

    private void handleCoinCollision(Coin coin) {
        final int objectScore = coin.getObjectScore();
        soundHandler.playCoinSound();
        coinCounter++;
        totalScore += objectScore;
        objectList.remove(coin);
    }

    @Override
    public void jump() {
        if (isTouchingGround()) {
            final int distance = (int) (JUMP_FORCE * Gdx.graphics.getDeltaTime());
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

        if (!player.isAlive()){
            gameState = GameState.GAME_OVER;
        }

        worldView.render(deltaTime);
    }

    private void updateScore() {
        if (shouldUpdateScore()) {
            totalScore--;
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

    private boolean shouldUpdateScore() {
        long currentTime = System.currentTimeMillis();
        return currentTime - lastScoreUpdate >= 1000 && totalScore >0 && gameState == GameState.GAME_ACTIVE;
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
    public ViewableObject getViewablePlayer() {
        return player;
    }

    @Override
    public List<ViewableObject> getObjectList() {
        return Collections.unmodifiableList(this.objectList);
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
        return this.gameState;
    }

    @Override
    public int getTotalScore() {
        return this.totalScore;
    }

    @Override
    public int getCoinCounter() {
        return this.coinCounter;
    }

    @Override
    public int getPlayerHealth() {
        return player.getHealth();
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
