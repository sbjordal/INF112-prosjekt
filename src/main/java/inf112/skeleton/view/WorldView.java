package inf112.skeleton.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.HashMap;
import java.util.Map;
//import inf112.skeleton.model.WorldModel;


public class WorldView {
//    private WorldModel model;
    private Map<String, Texture> textures = new HashMap<String, Texture>();
    private Viewport viewport;
    private SpriteBatch batch;
    private Texture background;
    private BitmapFont font;

    public WorldView(Viewport viewport) {
        this.font = new BitmapFont(Gdx.files.internal());/// lag fil med font
        this.viewport = viewport;
        this.batch = new SpriteBatch();
        this.background = new Texture(Gdx.files.internal("skeleton/background.png"));// bakgrunnsfil, må sikkert avhenge av level og gamestate
    }

    public void loadTextures() {

    }

    public Texture loadTexture(String name) {

    }

    public void drawFrame() {

    }

    public void drawObjects() {


    }



//    public WorldView() {
//        this.model = model;
//        shapeRenderer = new ShapeRenderer();
//    }


//    @Override
//    public void create() {
//
//    }
//
//    @Override
//    public void dispose() {
//        shapeRenderer.dispose();
//    }
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
//
//    @Override
//    public void pause() {
//
//    }
//
//    @Override
//    public void resume() {
//
//    }

}
