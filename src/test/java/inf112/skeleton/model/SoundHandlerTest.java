package inf112.skeleton.model;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import com.badlogic.gdx.files.FileHandle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;


public class SoundHandlerTest {

    private Sound mockSound;
    private SoundHandler handler;

    @BeforeEach
    public void setup() {
       mockSound = mock(Sound.class);
       handler = new SoundHandler(mockSound);
    }


    @Test
    public void testPlayCoinSound_PlaysSoundAtCorrectVolume () {
        handler.playCoinSound();
        verify(mockSound).play(0.25f);
    }

    @Test
    public void testCoinSoundIsStoredAndAccessible() {

        assertNotNull(handler.getCoinSound());
        assertEquals(mockSound, handler.getCoinSound());
    }

//    @Test
//    public void testDefaultConstructor_LoadsSoundFromGdx() {
//        // Mocks
//        Sound mockSound = mock(Sound.class);
//        Audio mockAudio = mock(Audio.class);
//        Files mockFiles = mock(Files.class);
//        FileHandle mockFileHandle = mock(FileHandle.class);
//
//        try (MockedStatic<Gdx> gdxMock = mockStatic(Gdx.class)) {
//            // Sett mockede statiske felter
////            gdxMock.when(() -> Gdx.audio).thenReturn(mockAudio);
////            gdxMock.when(() -> Gdx.files).thenReturn(mockFiles);
//            when(mockFiles.internal("sfx/coinrecieved.mp3")).thenReturn(mockFileHandle);
//            when(mockAudio.newSound(mockFileHandle)).thenReturn(mockSound);
//
//            // Når vi nå kaller standardkonstruktøren, skal den bruke mockene
//            SoundHandler handler = new SoundHandler();
//
//            assertEquals(mockSound, handler.getCoinSound());
//        }
//    }
}


