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
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.ViewableObject;
import inf112.skeleton.model.gameobject.fixedobject.item.Coin;


public class WorldView implements Screen {

    private Rectangle screenRect;
    private WorldModel model;
    private ShapeRenderer shapeRenderer;
    private ShapeRenderer objectRenderer;
    private Map<String, Texture> textures = new HashMap<String, Texture>();
    private SpriteBatch batch;
    private Viewport viewport;
    private int coinCount;
    private ShapeRenderer player;
    private Texture playerTexture;
    private Texture enemyTexture;
    private Texture coinTexture;
    private Texture backgroundTexture;
    private ParallaxBackground parallaxBackground;
    private BitmapFont font;



    public WorldView(WorldModel model, Viewport viewport) {
        this.viewport = viewport;
        this.screenRect = new Rectangle();
        this.model = model;
        this.parallaxBackground = new ParallaxBackground();
        this.coinCount = 0;
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
        coinTexture = model.getCoinTexture(); // TODO - Midlertidig for bare én coin
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

        // Writes "PAUSED" on the screen when GameState is paused
        font.getData().setScale(3);
        batch.begin();
        font.draw(batch, "PAUSED", viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2);
        batch.end();
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

//        // Enemy-data
//        float enemyX = model.getEnemyTransform().getPos().x();
//        float enemyWidth = model.getEnemyTransform().getSize().width();
//        float enemyHeight = model.getEnemyTransform().getSize().height();
//
//        // Coin-data
//        float coinX = model.getCoinTransform().getPos().x();
//        float coinWidth = model.getCoinTransform().getSize().width();
//        float coinHeight = model.getCoinTransform().getSize().height();

        List<ViewableObject> objectList= model.getObjectList();

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
        float leftX = viewport.getCamera().position.x - worldWidth / 2;
        float bottomY = viewport.getCamera().position.y - worldHeight / 2;

        // Text to be shown
        String totalScore = "Total score: "+ model.getTotalScore();//String.valueOf(model.getTotalScore());
        String coinCount = "Coins: " + model.getCoinScore();
        font.getData().setScale(2);

        // Drawing
        batch.begin();
        parallaxBackground.render(batch);
        //batch.draw(backgroundTexture, leftX, bottomY, viewport.getWorldWidth(), viewport.getWorldHeight());
        font.draw(batch, totalScore, leftX, worldHeight-10);
        font.draw(batch, coinCount, leftX + 300, worldHeight-10);
        batch.draw(playerTexture, playerX, playerY, playerWidth, playerHeight);
//        batch.draw(enemyTexture, enemyX, groundY, enemyWidth, enemyHeight);
//        batch.draw(coinTexture, coinX, groundY, coinWidth, coinHeight);
        for (ViewableObject object : objectList) {
            Texture objectTexture = object.getTexture();
            float objectX = object.getTransform().getPos().x();
            float objectWidth = object.getTransform().getSize().width();
            float objectHeight = object.getTransform().getSize().height();

            batch.draw(objectTexture, objectX, groundY, objectWidth, objectHeight);
        }

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
