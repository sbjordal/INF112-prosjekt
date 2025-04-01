package inf112.skeleton.view;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ParallaxBackgroundTest {

    private ParallaxBackground background;
    private MockedConstruction<Texture> textureConstruction;

    @BeforeAll
    public static void initGdx() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new ApplicationListener() {
            @Override public void create() {}
            @Override public void resize(int width, int height) {}
            @Override public void render() {}
            @Override public void pause() {}
            @Override public void resume() {}
            @Override public void dispose() {}
        }, config);

        Gdx.graphics = mock(com.badlogic.gdx.Graphics.class);
        when(Gdx.graphics.getWidth()).thenReturn(800);
        when(Gdx.graphics.getHeight()).thenReturn(600);

        Gdx.files = mock(Files.class);
    }

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 5; i++) {
            FileHandle mockFileHandle = mock(FileHandle.class);
            when(mockFileHandle.path()).thenReturn("mock-path");
            when(Gdx.files.internal("background/plx-" + (i + 1) + ".png")).thenReturn(mockFileHandle);
        }
        textureConstruction = mockConstruction(Texture.class, (mock, context) -> {
            when(mock.getWidth()).thenReturn(100); // Optional: used in render
        });

        background = new ParallaxBackground(1000);
    }

    @AfterEach
    void tearDown() {
        if (textureConstruction != null) {
            textureConstruction.close();
        }
    }

    @Test
    public void testUpdateMovesLayersWhenNotPaused() {
        float[] originalX = background.layerX.clone();
        background.update(1, 1.0f, false);
        for (int i = 0; i < 5; i++) {
            assertNotEquals(originalX[i], background.layerX[i]);
        }
    }

    @Test
    public void testUpdateDoesNotMoveLayersWhenPaused() {
        float[] originalX = background.layerX.clone();
        background.update(1, 1.0f, true);
        for (int i = 0; i < 5; i++) {
            assertEquals(originalX[i], background.layerX[i]);
        }
    }

    @Test
    public void testDisposeDisposesTextures() {
        Texture mockTexture = mock(Texture.class);
        for (int i = 0; i < 5; i++) {
            background.layers[i] = mockTexture;
        }
        background.dispose();
        verify(mockTexture, times(5)).dispose();
    }

    @Test
    public void testRenderDrawsEachLayer() {
        SpriteBatch mockBatch = mock(SpriteBatch.class);
        Texture mockTexture = mock(Texture.class);
        when(mockTexture.getWidth()).thenReturn(100);

        for (int i = 0; i < 5; i++) {
            background.layers[i] = mockTexture;
        }
        background.render(mockBatch);
        verify(mockBatch, atLeastOnce()).draw(any(Texture.class), anyFloat(), anyFloat(), anyFloat(), anyFloat());
    }
}
