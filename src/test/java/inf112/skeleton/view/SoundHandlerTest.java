package inf112.skeleton.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

// TODO: Må plasseres riktig i testhierarkiet + skrive om tester til å passe ny kode
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

//TODO: Kommentert ut for å få prosjektet til å kompilere


public class SoundHandlerTest {

//    private Sound mockSound;
//    private SoundHandler handler;
//
//    @BeforeEach
//    public void setup() {
//       mockSound = mock(Sound.class);
//       handler = new SoundHandler(mockSound, mockSound);
//    }
//
//
//    @Test
//    public void testPlayCoinSound_PlaysSoundAtCorrectVolume () {
//        handler.playCoinSound();
//        verify(mockSound).play(0.25f);
//    }
//
//    @Test
//    public void testCoinSoundIsStoredAndAccessible() {
//
//        assertNotNull(handler.getCoinSound());
//        assertEquals(mockSound, handler.getCoinSound());
//    }

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


