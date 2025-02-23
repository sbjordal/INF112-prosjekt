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
import inf112.skeleton.model.gameobject.mobileobject.Player;

import java.awt.desktop.ScreenSleepListener;
import java.util.HashMap;
import java.util.Map;
//import inf112.skeleton.model.WorldModel;


public class WorldView implements Screen {

    // Her ligger nå masse kode hentet fra ViewportDemo fra https://git.app.uib.no/Daniel.Nguyen/inf112_libgdx_guide
    // Har lyst å ta inspirasjon fra den og  fra SpriteBatchDemo
    // Får foreløping bare opp en svart skjerm


    ShapeRenderer shapeRenderer;
    SpriteBatch batch;
    Viewport viewport;
    Sprite sprite;
    Texture texture;
    //Player player;

    public WorldView() {
        //this.player = model.getPlayer();

    }

    @Override
    public void show() {
        viewport = new ExtendViewport(100, 100);
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        sprite = new Sprite(new Texture(Gdx.files.internal("sprite.png")));
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(Color.CLEAR);

        // Camera center = sprite center
        viewport.getCamera().position.set(sprite.getX() + sprite.getWidth()/2, sprite.getY() + sprite.getHeight()/2, 0);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        // Make a big enough rectangle to cover the view for demonstration purposes
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(-5000, -5000, 10000, 10000);
        shapeRenderer.end();

        batch.begin();
        sprite.draw(batch);
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

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
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
