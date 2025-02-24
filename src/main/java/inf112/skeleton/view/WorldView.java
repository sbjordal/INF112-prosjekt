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
    private Map<String, Texture> textures = new HashMap<String, Texture>();
    private SpriteBatch batch;
    private Viewport viewport;
    private ShapeRenderer player;
    private Texture playerTexture;
    private Texture backgroundTexture;
    private BitmapFont font;



    public WorldView(WorldModel model, Viewport viewport) {
//        this.font = new BitmapFont(Gdx.files.internal("skeleton.fnt")); Lag fil med font
        this.viewport = viewport;
        this.screenRect = new Rectangle();
        this.model = model;
//       this.batch = new SpriteBatch();
//        this.player = model.getPlayerTexture();

    }

//    public Texture loadTexture(String path) {}
//    public void drawFrame() {}
//    public void drawObjects() {}

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
//        for (ShapeRenderer platform : platforms) {
//            platform.dispose();
//        }
    }

    @Override
    public void show() {
        viewport = new ExtendViewport(100, 100);
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        // TODO -  Fjern '//' når metoden er implementert
        //playerTexture = model.getPlayerTexture(); // Kommenter inn når den er implementert
        playerTexture = new Texture(Gdx.files.internal("sprite.png")); // Fjern denne når model.getPlayerTexture() er implementert
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
        drawBasics();
    }

    private void drawGameActive() {
        loadBackground("backgroundTest.jpg");
        drawBasics();
    }

    private void drawGamePaused() {
        drawBasics();
    }

    private void drawGameOver() {
        drawBasics();
    }

    private void drawBasics() {
        // Player-data
        // TODO - fjern tallene og sett inn de som er kommenert når de er implementert
        float playerX = 0; //model.getPlayerTransform().pos().x();
        float playerY = 0; //model.getPlayerTransform().pos().y();
        float playerWidth = 50; // model.getPlayerTransform().size().width();
        float playerHeight = 70; //model.getPlayerTransform().size().height();

        ScreenUtils.clear(Color.CLEAR);

        // Camera center = sprite center
        viewport.getCamera().position.set(playerX + playerWidth/2, playerY + playerHeight/2, 0);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        // Make a big enough rectangle to cover the view for demonstration purposes
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.CLEAR);
        shapeRenderer.rect(-5000, -5000, 10000, 10000);
        shapeRenderer.end();

        // Finding the left corner of the window
        float camX = viewport.getCamera().position.x - viewport.getWorldWidth() / 2;
        float camY = viewport.getCamera().position.y - viewport.getWorldHeight() / 2;

        // Drawing
        batch.begin();
        batch.draw(backgroundTexture, camX, camY, viewport.getWorldWidth(), viewport.getWorldHeight());
        batch.draw(playerTexture, playerX, playerY, playerWidth, playerHeight);
        batch.end();
    }

    public void loadBackground(String path) {
        backgroundTexture = new Texture(Gdx.files.internal(path));
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

    // test for å sjekke merging


//
//    @Override
//    public void resize(int width, int height) {
//        screenRect.width = width;
//        screenRect.height = height;
//    }
//
//    @Override
//    public void render() {
//        // 60fps
//        // objectene har texture som parameter i konstruktøren som kan gis fra modellen
//        shapeRenderer.setColor(1, 0, 0, 0);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.rect(0, 0, 100, 100);
//        shapeRenderer.end();
//    }
//

}
