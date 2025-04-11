package inf112.skeleton.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.skeleton.model.GameState;
import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.gameobject.ViewableObject;
import inf112.skeleton.model.gameobject.mobileobject.actor.Player;

import java.util.HashMap;


public class WorldView implements Screen {

    private ViewableWorldModel model;
    private SpriteBatch batch;
    private Viewport viewport;
    private Texture menuBackgroundTexture;
    private ParallaxBackground parallaxBackground;
    private BitmapFont font;
    private GlyphLayout layout;
    private HashMap<String, Texture> textures;
    private PlayerAnimation playerAnimation;
    private GameState gameState;
    private SoundHandler soundHandler;

    public WorldView(ViewableWorldModel model, int width, int height) {
        this.viewport = new ExtendViewport(width, height);
        this.model = model;
        this.layout = new GlyphLayout();
        this.textures = new HashMap<>();
        this.gameState = model.getGameState();
        this.soundHandler = new SoundHandler();
    }

    /**
     * The following 6 setters are only for testing purposes
     */
    void setFont(BitmapFont font) { this.font = font; }
    void setBatch(SpriteBatch batch) { this.batch = batch; }
    void setParallaxBackground(ParallaxBackground bg) { this.parallaxBackground = bg; }
    void setPlayerAnimation(PlayerAnimation animation) { this.playerAnimation = animation; }
    void setTextures(HashMap<String, Texture> textures) { this.textures = textures; }
    void setLayout(GlyphLayout layout) {this.layout = layout;}
    HashMap<String, Texture> getTextures(){return textures;}

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        parallaxBackground.dispose();
        playerAnimation.dispose();
        for (Texture texture : textures.values()){
            texture.dispose();
        }
    }

    @Override
    public void show() {
        parallaxBackground = new ParallaxBackground(model.getLevelWidth());
        playerAnimation = new PlayerAnimation();
        loadTextures();
        font = new BitmapFont(); //new BitmapFont(Gdx.files.internal("skeleton.fnt")); Lag fil med font
        font.setColor(Color.WHITE);
        batch = new SpriteBatch();
        menuBackgroundTexture = new Texture("background/plx-1.png");
    }

    @Override
    public void render(float v) {
        gameState = model.getGameState();
        switch (model.getGameState()) {
            case GAME_MENU -> drawGameMenu();
            case GAME_ACTIVE -> drawGameActive();
            case GAME_PAUSED -> drawGamePaused();
            case GAME_OVER -> drawGameOver();
        }
        if (!model.getInfoMode() && (gameState == GameState.GAME_MENU || gameState == GameState.GAME_PAUSED)) {
            drawCenteredText("Press 'i' for game info",2, 100);
        }

        else if (model.getInfoMode() && (gameState == GameState.GAME_MENU || gameState == GameState.GAME_PAUSED)) {
            drawGameInfo();
        }
    }


    private void drawGameMenu() {
        ScreenUtils.clear(Color.CLEAR);
        float leftX = getViewportLeftX();
        float bottomY = viewport.getCamera().position.y - viewport.getWorldHeight() / 2;
        batch.begin();
        batch.draw(menuBackgroundTexture, leftX, bottomY, viewport.getWorldWidth(), viewport.getWorldHeight());
        batch.end();
        drawCenteredText("Press ENTER to start the game", 3,0);
    }

    private void drawGameInfo() {
        drawCenteredText("Press 'i' to remove game info", 3,-400);
        drawCenteredText("To jump press 'w', 'space' or up-arrow\n" +
                "To move right press 'd' or right arrow\n" +
                "To move left press 'a' or left arrow\n" +
                "To pause the game when playing press 'p'\n" +
                "To exit the game at any time press ESC", 2,-300);
    }


    private void drawGameActive() {
        drawLevel();
    }

    private void drawGamePaused() {
        drawLevel();
        drawCenteredText("PAUSED", 3, 0);
        drawCenteredText("Press 'r' to return to the game menu", 3, 330);

    }

    private void drawGameOver() {
        ScreenUtils.clear(Color.CLEAR);
        drawCenteredText("GAME OVER", 3, 0);
        font.getData().setScale(1);
        drawCenteredText("Press ENTER to return to the game manu", 2, 100);
    }

    void drawCenteredText(String text, int textScale, float lowerTextBy) {
        font.getData().setScale(textScale);
        layout.setText(font, text);

        float centerX = viewport.getCamera().position.x;
        float centerY = viewport.getCamera().position.y;
        float width = layout.width;
        float height = layout.height;

        batch.begin();
        font.draw(batch, text, centerX- width/2, centerY - height/2 - lowerTextBy);
        batch.end();
    }

    void drawLevel() {

        // Map-data
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Player-data
        Transform playerTransform = model.getViewablePlayer().getTransform();
        float playerX = playerTransform.getPos().x;
        float playerY = playerTransform.getPos().y;
        float playerWidth = playerTransform.getSize().x;
        float playerHeight = playerTransform.getSize().y;

        // Camera
        ScreenUtils.clear(Color.CLEAR);
        updateViewportCamera();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        // Text to be shown
        String totalScore = "Total score: "+ model.getTotalScore();
        String coinCount = "Coins: " + model.getCoinCounter();
        String lives = "Lives: " + model.getPlayerLives();
        String countDown = "CountDown: " + model.getCountDown();
        font.getData().setScale(2);


        float screenHeight = viewport.getWorldHeight();
        float leftX = getViewportLeftX();


        // Parallax background
        int movementDirection = model.getMovementDirection();
        parallaxBackground.update(movementDirection, deltaTime, model.getGameState() != GameState.GAME_ACTIVE);

        // Drawing objects
        batch.begin();
        parallaxBackground.render(batch);
        font.draw(batch, totalScore, leftX, screenHeight-10);
        font.draw(batch, coinCount, leftX + 300, screenHeight-10);
        font.draw(batch, lives, leftX + 500, screenHeight - 10);
        TextureRegion currentFrame = playerAnimation.getFrame(movementDirection);
        playerAnimation.update(deltaTime,  model.getGameState() != GameState.GAME_ACTIVE);
        batch.draw(currentFrame, playerX, playerY, playerWidth, playerHeight);
        font.draw(batch, countDown, leftX + 700, screenHeight - 10);
        drawObjects();
        batch.end();

    }

    void updateViewportCamera() {
        Transform playerTransform = model.getViewablePlayer().getTransform();
        float playerX = playerTransform.getPos().x;
        float playerWidth = playerTransform.getSize().x;
        float playerCenterX = playerX + playerWidth / 2;
        float camX = viewport.getCamera().position.x;
        float camY = viewport.getCamera().position.y;
        float screenWidth = viewport.getWorldWidth();

        if (playerCenterX > camX && camX < model.getBoardWidth() - screenWidth / 2) {
            camX = playerCenterX;
        }
        camX = MathUtils.clamp(camX, screenWidth/2, 5000 - screenWidth/2); // bytt 5000 med bredden av brettet

        viewport.getCamera().position.set(camX, camY, 0);
        viewport.apply();
        model.updateViewportLeftX(getViewportLeftX());
    }

    void loadTextures(){
        textures.put("leopard", new Texture("assets/leopard.png"));
        textures.put("snail", new Texture("assets/snail.png"));
        textures.put("coin", new Texture("assets/coin.png"));
        textures.put("powerup", new Texture("assets/banana.png"));
        textures.put("ground", new Texture("obstacles/castleCenter.png"));
        textures.put("star", new Texture("assets/star.png"));
    }

    Texture getTexture(ViewableObject obj){
        String className = obj.getClass().getSimpleName();
        return switch (className) {
            case "Leopard" -> textures.get("leopard");
            case "Snail" -> textures.get("snail");
            case "Coin" -> textures.get("coin");
            case "Banana" -> textures.get("powerup");
            case "Ground" -> textures.get("ground");
            case "Star" -> textures.get("star");
            default -> throw new IllegalArgumentException("Unsupported class name for texture: " + className);
        };
    }

    void drawObjects() {
        for (ViewableObject object : model.getObjectList()) {

            // Skip drawing player.
            // Player texture is handled differently due to animations.
            if (object instanceof Player) continue;

            Texture objectTexture = getTexture(object);
            float objectX = object.getTransform().getPos().x;
            float objectY = object.getTransform().getPos().y;
            float objectWidth = object.getTransform().getSize().x;
            float objectHeight = object.getTransform().getSize().y;
            batch.draw(objectTexture, objectX, objectY, objectWidth, objectHeight);
        }
    }

    private float getViewportLeftX() {
        return viewport.getCamera().position.x - viewport.getWorldWidth() / 2;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
