package inf112.skeleton.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ParallaxBackground {
    private Texture[] layers;
    private float[] layerX;
    private float[] scrollSpeeds;
    private float screenWidth;
    private float screenHeight;

    public ParallaxBackground() {
        layers = new Texture[5];
        layerX = new float[5];
        scrollSpeeds = new float[]{0.4f, 0.8f, 2.0f, 4.0f, 8.0f};
    }
    public void loadTextures(){
       for (int i = 0; i < 5; i++) {
           String file = "background/plx-" + (i + 1) + ".png";
           layers[i] = new Texture(Gdx.files.internal(file));
       }
        this.screenWidth = Gdx.graphics.getWidth();
        this.screenHeight = Gdx.graphics.getHeight();
    }

    public void update(float playerSpeed, float deltaTime) {
        for (int i = 0; i < layers.length; i++) {
            layerX[i] -= playerSpeed * scrollSpeeds[i] * deltaTime;
            if (layerX[i] < -screenWidth) {
                layerX[i] += screenWidth;
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < layers.length; i++) {
            batch.draw(layers[i], layerX[i], 0, screenWidth, screenHeight);
            batch.draw(layers[i], layerX[i] + screenWidth, 0, screenWidth, screenHeight);
        }
    }

    public void dispose() {
        for (Texture texture : layers) {
            texture.dispose();
        }
    }
}

