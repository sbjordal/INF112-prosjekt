package inf112.skeleton.view;

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


    private WorldModel model;
   // private ShapeRenderer player;
    private ShapeRenderer shapeRenderer;
    private List<ShapeRenderer> platforms;
    private Rectangle screenRect;
    private Map<String, Texture> textures = new HashMap<String, Texture>();
    private SpriteBatch batch;
    private Viewport viewport;
    private Sprite playerSprite;
    private Texture background;
    private BitmapFont font;


    public WorldView(WorldModel model, Viewport viewport) {
        //this.font = new BitmapFont(Gdx.files.internal("skeleton.fnt")); Lag fil med font
        this.viewport = viewport;
        this.screenRect = new Rectangle();
        this.model = model;
//        this.batch = new SpriteBatch();
//        this.background = new Texture(Gdx.files.internal("skeleton/background.png")); // Eivind: må sikkert avhenge av level og gamestate
        //this.player = model.getPlayerTexture();

    }

    public Texture loadTexture(String path) {
        return background;
    }

//    public void loadTexture(Texture texture) {}
//    public void drawFrame() {}
//    public void drawObjects() {}

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        for (ShapeRenderer platform : platforms) {
            platform.dispose();
        }
    }

    @Override
    public void show() {
        viewport = new ExtendViewport(100, 100);
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        playerSprite = new Sprite(model.getPlayerTexture());
    }

    @Override
    public void render(float v) {
        if (model.getGameState() == GameState.GAME_MENU) {
            drawGameMenu();
        }
        else if (model.getGameState() == GameState.GAME_ACTIVE) {
            drawGameActive();
        }
        else if (model.getGameState() == GameState.GAME_PAUSED) {
            drawGamePaused();
        }
        else if (model.getGameState() == GameState.GAME_OVER) {
            drawGameOver();
        }
    }

    private void drawGameMenu() {
        drawBasics();
    }

    private void drawGameActive() {
        drawBasics();
    }

    private void drawGamePaused() {
        drawBasics();
    }

    private void drawGameOver() {
        drawBasics();
    }

    private void drawBasics() {
        ScreenUtils.clear(Color.CLEAR);

        // Camera center = sprite center
        viewport.getCamera().position.set(playerSprite.getX() + playerSprite.getWidth()/2, playerSprite.getY() + playerSprite.getHeight()/2, 0);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        // Make a big enough rectangle to cover the view for demonstration purposes
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(-5000, -5000, 10000, 10000);
        shapeRenderer.end();

        batch.begin();
        playerSprite.draw(batch);
        batch.end();
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
