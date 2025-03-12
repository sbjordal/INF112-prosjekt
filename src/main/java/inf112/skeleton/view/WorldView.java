package inf112.skeleton.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.gameobject.ViewableObject;


public class WorldView implements Screen {

    private ViewableWorldModel model;
    private SpriteBatch batch;
    private Viewport viewport;
    private Texture playerTexture;
    private Texture menuBackgroundTexture;
    private ParallaxBackground parallaxBackground;
    private BitmapFont font;
    private GlyphLayout layout;



    public WorldView(ViewableWorldModel model, Viewport viewport) {
        this.viewport = viewport;
        this.model = model;
        this.parallaxBackground = new ParallaxBackground();
        this.layout = new GlyphLayout();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        parallaxBackground.dispose();
    }

    @Override
    public void show() {
        this.font = new BitmapFont(); //new BitmapFont(Gdx.files.internal("skeleton.fnt")); Lag fil med font
        font.setColor(Color.WHITE);
        batch = new SpriteBatch();
        playerTexture = model.getViewablePlayer().getTexture();
        parallaxBackground.loadTextures();
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
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float leftX = viewport.getCamera().position.x - worldWidth / 2;
        float bottomY = viewport.getCamera().position.y - worldHeight / 2;
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
        drawBasics();
    }

    private void drawGamePaused() {
        drawBasics();

        // Writes "PAUSED" on the screen when GameState is paused
        String pause = "PAUSED";
        drawCenteredText(pause);
    }

    private void drawGameOver() {
        ScreenUtils.clear(Color.CLEAR);
        String gameOver = "GAME OVER";
        drawCenteredText(gameOver);
    }

    private void drawCenteredText(String text) {
        font.getData().setScale(3);
        layout.setText(font, text);
        float width = layout.width;
        float height = layout.height;

        batch.begin();
        font.draw(batch, text, (viewport.getWorldWidth() - width )/ 2 , (viewport.getWorldHeight() - height)/ 2);
        batch.end();
    }

    private void drawBasics() {
        // Map-data
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Player-data
        Transform playerTransform = model.getViewablePlayer().getTransform();
        float playerX = playerTransform.getPos().x;//model.getPlayerTransform().getPos().x();
        float playerY = playerTransform.getPos().y;//model.getPlayerTransform().getPos().y();
        float playerWidth = playerTransform.getSize().x;//model.getPlayerTransform().getSize().width();
        float playerHeight = playerTransform.getSize().y;//model.getPlayerTransform().getSize().height();

        float playerSpeed = model.getMovementSpeed();

        parallaxBackground.update(playerSpeed, deltaTime);

        ScreenUtils.clear(Color.CLEAR);

        // Kamera viser hele skjermen uavhengig av spiller
        // Kan videreutvikles hvor camera justerer seg etter spillers posisjon
        float camX = viewport.getCamera().position.x;
        float camY = viewport.getCamera().position.y;
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        viewport.getCamera().position.set(camX, camY, 0);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        // Finding the left corner of the window
        float leftX = viewport.getCamera().position.x - worldWidth / 2;
        float bottomY = viewport.getCamera().position.y - worldHeight / 2;

        // Text to be shown
        String totalScore = "Total score: "+ model.getTotalScore();
        String coinCount = "Coins: " + model.getCoinCounter();
        String health = "Health: " + model.getPlayerHealth();
        String countDown = "CountDown: " + model.getCountDown();
        font.getData().setScale(2);

        // Drawing objects
        batch.begin();
        parallaxBackground.render(batch);
        font.draw(batch, totalScore, leftX, worldHeight-10);
        font.draw(batch, coinCount, leftX + 300, worldHeight-10);
        font.draw(batch, health, leftX + 500, worldHeight - 10);
        font.draw(batch, countDown, leftX + 700, worldHeight - 10);
        batch.draw(playerTexture, playerX, playerY, playerWidth, playerHeight);
        drawObjects();
        batch.end();

    }

    private void drawObjects() {
        for (ViewableObject object : model.getObjectList()) {
            Texture objectTexture = object.getTexture();
            float objectX = object.getTransform().getPos().x;
            float objectY = object.getTransform().getPos().y;
            float objectWidth = object.getTransform().getSize().x;
            float objectHeight = object.getTransform().getSize().y;

            batch.draw(objectTexture, objectX, objectY, objectWidth, objectHeight);
        }
    }

    private void loadBackground(String path) {
        // TODO: kommentert ut fordi det skapte memory overflow.
        // backgroundTexture = new Texture(Gdx.files.internal(path));
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
