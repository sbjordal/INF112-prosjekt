package inf112.starhunt.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Class that represents the moving background in the game.
 */
public class ParallaxBackground {
    private Texture[] layers;
    private float[] layerX;
    private float screenWidth;
    private float screenHeight;
    private final float[] scrollSpeeds;
    private final int levelWidth;

    /**
     * Constructor for the moving background
     *
     * @param levelWidth the width of the whole game level
     */
    public ParallaxBackground(int levelWidth) {
        layers = new Texture[5];
        layerX = new float[5];
        scrollSpeeds = new float[]{0.4f, 0.8f, 4.0f, 10.0f, 14.0f};
        this.levelWidth = levelWidth;
        loadTextures();
    }

    /**
     * Loads all textures needed to create the ParralaxBr
     */
    void loadTextures(){
       for (int i = 0; i < 5; i++) {
           String file = "background/plx-" + (i + 1) + ".png";
           layers[i] = new Texture(Gdx.files.internal(file));
       }
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
    }

    /**
     * Updates the horizontal positions of background layers based on movement direction.
     * This method applies parallax scrolling effects, adjusting layer positions depending
     * on the movement direction and elapsed time.
     * The update is skipped if the game is paused.
     *
     * @param movementDirection the direction of movement; positive moves right, negative moves left.
     * @param deltaTime the time elapsed since the last update, used for movement calculations.
     * @param isPaused whether the game is currently paused; if true, no movement occurs.
     */

    void update(int movementDirection, float deltaTime, boolean isPaused) {
        if (!isPaused) {
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
    }

    /**
     * Renders the parallaxBackground.
     *
     * @param batch to be rendered
     */
    void render(SpriteBatch batch) {
        float parallaxSpacing = 3.0f;
        for (int i = 0; i < layers.length; i++) {
            float textureWidth = layers[i].getWidth();
            float scaledTextureWidth = textureWidth * parallaxSpacing;
            for (float x = layerX[i]; x < levelWidth + scaledTextureWidth; x += scaledTextureWidth) {
                batch.draw(layers[i], x, 0, scaledTextureWidth, screenHeight);
            }
        }
    }

    /**
     * Disposes all textures.
     */
    void dispose() {
        for (Texture texture : layers) {
            texture.dispose();
        }
    }

    float[] getLayerX() {
        return layerX;
    }

    void setLayers(Texture texture, int i) {
        layers[i] = texture;
    }
}

