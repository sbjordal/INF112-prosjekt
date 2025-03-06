package inf112.skeleton.model;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import inf112.skeleton.controller.ControllableWorldModel;
import inf112.skeleton.controller.Direction;
import inf112.skeleton.controller.PlayerController;
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

    private static final int GRAVITY_FORCE = -45;
    private static final int JUMP_FORCE = 950;

    private GameState gameState;
    private Player player;
    private WorldBoard board;
    private WorldView worldView;
    private PlayerController playerController;
    private ArrayList<GameObject> objectList;
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

    private boolean isColliding(CollisionBox collisionBox) {
        if (gameState != GameState.GAME_ACTIVE) {
            return false;
        }

        for (GameObject gameObject : objectList) {
            if (collisionBox.isCollidingWith(gameObject.getCollisionBox())) {
                if (gameObject instanceof Coin) {
                    handleCoinCollision(gameObject);
                } else if (gameObject instanceof Enemy) { // TODO: legge til at dersom man hopper på en enemy får man poeng og fienden dør
                    handleEnemyCollision(gameObject);
                }

                return true;
            }
        }

        return false;
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

    private void handleEnemyCollision(GameObject gameObject) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastEnemyCollisionTime >= COLLISION_COOLDOWN) {

            // Enemy deals damage to the player
            Enemy collidingEnemy = (Enemy) gameObject;
            player.receiveDamage(collidingEnemy.getDamage());

            // Reduce total score
            if (totalScore > 0) {
                totalScore -=4;
            }
            lastEnemyCollisionTime = currentTime;
        }
    }

    private void handleCoinCollision(GameObject coin) {// TODO revisjon: i pickup metoden eller som privat hjelpemetode her
        this.coinCounter++;
        this.totalScore++;
        this.objectList.remove(coin);
    }

    @Override
    public void move(int deltaX, int deltaY) {
        Vector2 playerPosition = player.getTransform().getPos();
        Vector2 playerSize = player.getTransform().getSize();

        // TODO: finskriv
        // TODO: inkluder deltaX i beregningen
        boolean isDeltaYNegative = (deltaY < 0);
        for (int i = Math.abs(deltaY); i >= 0; i--) {
            int i2 = i;
            if (isDeltaYNegative) {
                i2 = -i2;
            }

            Vector2 newPosition = new Vector2(playerPosition.x + deltaX, playerPosition.y + i2);
            Transform newPlayerTransform = new Transform(newPosition, playerSize);
            CollisionBox newPlayerCollisionBox = new CollisionBox(newPlayerTransform);

            if (isLegalMove(newPlayerCollisionBox)) {
                player.move(newPosition);
                // System.out.println("i: " + i2);
                break;
            }
        }
    }

    @Override
    public void jump() {
        if (isTouchingGround()) {
            player.jump(JUMP_FORCE);
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
    public void create() {
        this.player = new Player(1, 0); // TODO, legg til argument (foreløpig argumenter for å kunne kompilere prosjektet)

        Vector2 enemyPos = new Vector2(40, 100);
        Vector2 enemySize = new Vector2(50, 50);
        Enemy enemy = new Enemy(1,1,10,1, new Transform(enemyPos, enemySize));

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

        this.playerController = new PlayerController(this);
        Gdx.input.setInputProcessor(this.playerController);
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
    public void resize( int i, int i1){
        // TODO, implement me :)
    }

    @Override
    public void render() {
        final float deltaTime = Gdx.graphics.getDeltaTime();

        if (gameState.equals(GameState.GAME_ACTIVE)) {
            updateScore();
            moveHorizontally(deltaTime);
            moveVertically(deltaTime);
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
        final int distance = (int) (movementSpeed * deltaTime * 60); // TODO: magic number '60' is to increase the distance to a visually noticeable value. Note that 'deltaTime' is 0.0167 at 60fps.

        if (isMovingRight) {
            move(distance, 0);
        }
        if (isMovingLeft) {
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
            player.addVerticalForce(GRAVITY_FORCE);
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
    public void dispose() {
        // TODO, implement me :)
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
    public int getMovementSpeed(){
        return player.getMovementSpeed();
    }

    @Override
    public void setMovement(Direction direction) {
        if (direction.equals(Direction.RIGHT)){
            this.isMovingRight = !this.isMovingRight;
        }
        else {
            this.isMovingLeft = !this.isMovingLeft;
        }
    }

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

}
