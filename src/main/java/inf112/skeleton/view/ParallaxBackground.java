package inf112.skeleton.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ParallaxBackground {
    private Texture[] layers;
    private float[] layerX;
    private float[] scrollSpeeds;
    private float screenWidth;
    private float screenHeight;
    private final int levelWidth;

    public ParallaxBackground(int levelWidth) {
        layers = new Texture[5];
        layerX = new float[5];
        scrollSpeeds = new float[]{0.4f, 0.8f, 4.0f, 10.0f, 14.0f};
        this.levelWidth = levelWidth;
        loadTextures();
    }
    public void loadTextures(){
       for (int i = 0; i < 5; i++) {
           String file = "background/plx-" + (i + 1) + ".png";
           layers[i] = new Texture(Gdx.files.internal(file));
       }
        this.screenWidth = Gdx.graphics.getWidth();
        this.screenHeight = Gdx.graphics.getHeight();
    }

    public void update(int movementDirection, float deltaTime) {
        for (int i = 0; i < layers.length; i++) {
            layerX[i] -= movementDirection * scrollSpeeds[i] * deltaTime;
            if (layerX[i] < -screenWidth) {
                layerX[i] += 2 * screenWidth;
            }
            if (layerX[i] > screenWidth) {
                layerX[i] -= 2 * screenWidth;
            }
        }
    }

    public void render(SpriteBatch batch) {
        float parallaxSpacing = 3.0f;
        for (int i = 0; i < layers.length; i++) {
            float textureWidth = layers[i].getWidth();
            float scaledTextureWidth = textureWidth * parallaxSpacing;
            for (float x = layerX[i]; x < levelWidth + scaledTextureWidth; x += scaledTextureWidth) {
                batch.draw(layers[i], x, 0, scaledTextureWidth, screenHeight);
            }
        }
    }

    public void dispose() {
        for (Texture texture : layers) {
            texture.dispose();
        }
    }
}

