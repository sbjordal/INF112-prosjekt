package inf112.skeleton.view;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
//import inf112.skeleton.model.WorldModel;


public class WorldView implements ApplicationListener {
//    private WorldModel model;
    private final ShapeRenderer shapeRenderer;
    private final Rectangle screenRect = new Rectangle();

    public WorldView() {
//        this.model = model;
        shapeRenderer = new ShapeRenderer();
    }


    @Override
    public void create() {

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    @Override
    public void resize(int width, int height) {
        screenRect.width = width;
        screenRect.height = height;
    }

    @Override
    public void render() {
        shapeRenderer.setColor(1, 0, 0, 0);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(0, 0, 100, 100);
        shapeRenderer.end();
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

}
