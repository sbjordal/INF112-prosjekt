package inf112.skeleton.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.skeleton.model.GameState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import inf112.skeleton.model.WorldModel;


public class WorldView implements Screen {

    private Rectangle screenRect;
    private WorldModel model;
    private ShapeRenderer shapeRenderer;
    private ShapeRenderer objectRenderer;
    private Map<String, Texture> textures = new HashMap<String, Texture>();
    private SpriteBatch batch;
    private Viewport viewport;
    private ShapeRenderer player;
    private Texture playerTexture;
    private Texture enemyTexture;
    private Texture backgroundTexture;
    private ParallaxBackground parallaxBackground;
    private BitmapFont font;



    public WorldView(WorldModel model, Viewport viewport) {
        this.viewport = viewport;
        this.screenRect = new Rectangle();
        this.model = model;
        this.parallaxBackground = new ParallaxBackground();
//       this.batch = new SpriteBatch();
//        this.player = model.getPlayerTexture();

    }

//    public Texture loadTexture(String path) {}
//    public void drawFrame() {}
//    public void drawObjects() {}

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        objectRenderer.dispose();
        batch.dispose();
        font.dispose();
        parallaxBackground.dispose();
    }

    @Override
    public void show() {
        this.font = new BitmapFont(); //new BitmapFont(Gdx.files.internal("skeleton.fnt")); Lag fil med font
        font.setColor(Color.WHITE);
        shapeRenderer = new ShapeRenderer();
        objectRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        playerTexture = model.getPlayerTexture();
        enemyTexture = model.getEnemyTexture(); // TODO - Midlertidig for bare én enemy
        parallaxBackground.loadTextures();
    }

    @Override
    public void render(float v) {
        switch (model.getGameState()) {
            case GAME_MENU -> drawGameMenu();
            case GAME_ACTIVE -> drawGameActive();
            case GAME_PAUSED -> drawGamePaused();
            case GAME_OVER -> drawGameOver();
        }
    }


    private void drawGameMenu() {
        loadBackground("backgroundTest.jpg");
        drawBasics();
    }

    private void drawGameActive() {
        loadBackground("backgroundTest.jpg");
        drawBasics();
    }

    private void drawGamePaused() {
        loadBackground("backgroundTest2.jpg");
        drawBasics();
    }

    private void drawGameOver() {
        loadBackground("backgroundTest.jpg");
        drawBasics();
    }

    private void drawBasics() {
        // Map-data
        float groundY  = 105;
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Player-data
        float playerX = model.getPlayerTransform().getPos().x();
        float playerY = model.getPlayerTransform().getPos().y();
        float playerWidth = model.getPlayerTransform().getSize().width();
        float playerHeight = model.getPlayerTransform().getSize().height();

        float playerSpeed = model.getMovementSpeed();

        parallaxBackground.update(playerSpeed, deltaTime);

        // Enemy-data TODO - hent verdier fra modellen når nødvendige metoder er implementert

        float enemyX = model.getEnemyTransform().getPos().x();
        float enemyWidth = model.getEnemyTransform().getSize().width();
        float enemyHeight = model.getEnemyTransform().getSize().height();


        ScreenUtils.clear(Color.CLEAR);

        // TODO - Holder player i sentrum av kamera, kommer nok til å slette dette
        // Camera center = sprite center
        //viewport.getCamera().position.set(playerX + playerWidth/2, playerY + playerHeight/2, 0);

        // Kamera viser hele skjermen uavhengig av spiller
        // Kan videreutvikles hvor camera justerer seg etter spillers posisjon
        float camX = viewport.getCamera().position.x;
        float camY = viewport.getCamera().position.y;
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        viewport.getCamera().position.set(camX, camY, 0);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        // Make a big enough rectangle to cover the view for demonstration purposes
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.CLEAR);
        shapeRenderer.rect(-5000, -5000, 10000, 10000);
        shapeRenderer.end();


        // Finding the left corner of the window
        float leftX = viewport.getCamera().position.x - viewport.getWorldWidth() / 2;
        float bottomY = viewport.getCamera().position.y - viewport.getWorldHeight() / 2;

        // Text to be shown
        String totalScore = "Total score: "+"100";//String.valueOf(model.getTotalScore());
        font.getData().setScale(2);

        // Drawing
        batch.begin();
        parallaxBackground.render(batch);
        //batch.draw(backgroundTexture, leftX, bottomY, viewport.getWorldWidth(), viewport.getWorldHeight());
        font.draw(batch, totalScore, leftX, worldHeight-10);
        batch.draw(playerTexture, playerX, playerY, playerWidth, playerHeight);
        batch.draw(enemyTexture, enemyX, groundY, enemyWidth, enemyHeight);
        batch.end();

        // Fixed-objects
        objectRenderer.begin(ShapeRenderer.ShapeType.Filled);
        objectRenderer.setColor(Color.BLUE);
        objectRenderer.rect(800, groundY, 50, 50);
        objectRenderer.rect(850, groundY, 50, 100);
        objectRenderer.rect(900, groundY, 50, 150);
        objectRenderer.end();

    }

    public void loadBackground(String path) {
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
