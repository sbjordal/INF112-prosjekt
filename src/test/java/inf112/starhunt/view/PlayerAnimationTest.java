package inf112.starhunt.view;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlayerAnimationTest {

    @BeforeEach
    void setup() {
        Gdx.files = mock(Files.class);
        FileHandle dummyHandle = mock(FileHandle.class);
        when(dummyHandle.readBytes()).thenReturn(new byte[]{1, 2, 3});
        when(dummyHandle.name()).thenReturn("dummy.png");
        when(Gdx.files.internal(anyString())).thenReturn(dummyHandle);
    }

    @Test
    void testAnimationsAreLoaded() {
        Gdx.files = mock(Files.class);
        FileHandle dummyHandle = mock(FileHandle.class);
        when(dummyHandle.name()).thenReturn("dummy.png");
        when(Gdx.files.internal(anyString())).thenReturn(dummyHandle);

        try (MockedConstruction<Texture> mocked = mockConstruction(Texture.class)) {
            PlayerAnimation anim = new PlayerAnimation();

            assertEquals(20, mocked.constructed().size());

            assertNotNull(anim.getFrame(0, 0));
            assertNotNull(anim.getFrame(1, 0));
            assertNotNull(anim.getFrame(-1, 0));
        }
    }

    @Test
    void testUpdateIncreasesStateTimeWhenNotPaused() {
        try (MockedConstruction<Texture> mocked = mockConstruction(Texture.class)) {
            PlayerAnimation animation = new PlayerAnimation();
            animation.update(0.5f,false);
            assertNotNull(animation.getFrame(0, 0));
        }
    }
    @Test
    void testUpdateDoesNotIncreaseStateTimeWhenPaused() {
        try (MockedConstruction<Texture> mocked = mockConstruction(Texture.class)) {
            PlayerAnimation animation = new PlayerAnimation();
            animation.update(0.5f, true);
            assertNotNull(animation.getFrame(0, 0));
        }
    }

    @Test
    void testDisposeCallsTextureDispose() {
        Gdx.files = mock(Files.class);
        FileHandle dummyHandle = mock(FileHandle.class);
        when(dummyHandle.name()).thenReturn("dummy.png");
        when(Gdx.files.internal(anyString())).thenReturn(dummyHandle);

        try (MockedConstruction<Texture> mocked = mockConstruction(Texture.class)) {
            PlayerAnimation anim = new PlayerAnimation();
            anim.dispose();

            for (Texture texture : mocked.constructed()) {
                verify(texture, atLeastOnce()).dispose();
            }
        }
    }
}