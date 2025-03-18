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
import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.gameobject.ViewableObject;

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

    public WorldView(ViewableWorldModel model, int width, int height) {
        this.viewport = new ExtendViewport(width, height);
        this.model = model;
        this.layout = new GlyphLayout();
        this.textures = new HashMap<>();
    }

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
        this.parallaxBackground = new ParallaxBackground(model.getLevelWidth());
        this.playerAnimation = new PlayerAnimation();
        loadTextures();
        this.font = new BitmapFont(); //new BitmapFont(Gdx.files.internal("skeleton.fnt")); Lag fil med font
        font.setColor(Color.WHITE);
        batch = new SpriteBatch();
        this.menuBackgroundTexture = new Texture("background/plx-1.png");
    }

    @Override
    public void render(float v) {
        switch (model.getGameState()) {
            case GAME_MENU -> drawGameMenu();
            case GAME_INFO -> drawGameInfo();
            case GAME_ACTIVE -> drawGameActive();
            case GAME_PAUSED -> drawGamePaused();
            case GAME_OVER -> drawGameOver();
        }
    }


    private void drawGameMenu() {
        ScreenUtils.clear(Color.CLEAR);
        float leftX = getViewportLeftX();
        float bottomY = viewport.getCamera().position.y - viewport.getWorldHeight() / 2;
        batch.begin();
        batch.draw(menuBackgroundTexture, leftX, bottomY, viewport.getWorldWidth(), viewport.getWorldHeight());
        batch.end();

        drawCenteredText("Press ENTER to start the game");
    }

    private void drawGameInfo() {
        font.getData().setScale(2);
        batch.begin();
        font.draw(batch, "Her kommer masse spillinfo", 50, 100);
        batch.end();
    }

    private void drawGameActive() {
        drawLevel();
    }

    private void drawGamePaused() {
        drawLevel();
        drawCenteredText("PAUSED");
    }

    private void drawGameOver() {
        ScreenUtils.clear(Color.CLEAR);
        drawCenteredText("GAME OVER");
    }

    private void drawCenteredText(String text) {
        font.getData().setScale(3);
        layout.setText(font, text);

        float centerX = viewport.getCamera().position.x;
        float centerY = viewport.getCamera().position.y;
        float width = layout.width;
        float height = layout.height;

        batch.begin();
        font.draw(batch, text, centerX- width/2, centerY - height/2);
        batch.end();
    }

    private void drawLevel() {
        // Map-data
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Player-data
        Transform playerTransform = model.getViewablePlayer().getTransform();
        float playerX = playerTransform.getPos().x;
        float playerY = playerTransform.getPos().y;
        float playerWidth = playerTransform.getSize().x;
        float playerHeight = playerTransform.getSize().y;

        // Parallax background
        int movementDirection = model.getMovementDirection();
        parallaxBackground.update(movementDirection, deltaTime);

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

        // Drawing objects
        batch.begin();
        parallaxBackground.render(batch);
        font.draw(batch, totalScore, leftX, screenHeight-10);
        font.draw(batch, coinCount, leftX + 300, screenHeight-10);
        font.draw(batch, lives, leftX + 500, screenHeight - 10);
        TextureRegion currentFrame = playerAnimation.getFrame(movementDirection);
        playerAnimation.update(deltaTime);
        batch.draw(currentFrame, playerX, playerY, playerWidth, playerHeight);
        font.draw(batch, countDown, leftX + 700, screenHeight - 10);
        drawObjects();
        batch.end();

    }

    private void updateViewportCamera() {
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
    }

    private void loadTextures(){
        this.textures.put("enemy", new Texture("assets/enemy.png"));
        this.textures.put("coin", new Texture("assets/coin.png"));
        this.textures.put("powerup", new Texture("assets/mushroom.png"));
        this.textures.put("ground", new Texture("obstacles/castleCenter.png"));
    }

    private Texture getTexture(ViewableObject obj){
        String className = obj.getClass().getSimpleName();
        return switch (className) {
            case "Enemy" -> textures.get("enemy");
            case "Coin" -> textures.get("coin");
            case "Mushroom" -> textures.get("powerup");
            case "FixedObject" -> textures.get("ground");
            default -> null;
        };
    }

    private void drawObjects() {
        for (ViewableObject object : model.getObjectList()) {
            Texture objectTexture = getTexture(object);
            float objectX = object.getTransform().getPos().x;
            float objectY = object.getTransform().getPos().y;
            float objectWidth = object.getTransform().getSize().x;
            float objectHeight = object.getTransform().getSize().y;
            batch.draw(objectTexture, objectX, objectY, objectWidth, objectHeight);
        }
    }

    public float getViewportLeftX() {
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
