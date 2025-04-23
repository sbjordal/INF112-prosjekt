package inf112.starhunt.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

//import inf112.skeleton.view.SoundHandler;

// TODO: Må plasseres riktig i testhierarkiet + skrive om tester til å passe ny kode
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

//TODO: Kommentert ut for å få prosjektet til å kompilere


public class SoundHandlerTest {

//    private Sound mockCoinSound;
//    private Sound mockOuchSound;
//    private SoundHandler handler;
//
//    @BeforeEach
//    public void setup() {
//       mockCoinSound = mock(Sound.class);
//       mockOuchSound = mock(Sound.class);
//       handler = new SoundHandler(mockCoinSound, mockOuchSound);
//    }
//
//
//    @Test
//    public void testPlayCoinSound_PlaysSoundAtCorrectVolume () {
//        handler.playCoinSound();
//        verify(mockCoinSound).play(0.25f);
//    }
//
//
//    @Test
//    public void testPlayOuchSound_PlaysSoundAtCorrectVolume () {
//        handler.playCoinSound();
//        verify(mockOuchSound).play(0.25f);
//    }
//
//    @Test
//    void testGetCoinSound() {
//        // Verify that getCoinSound() returns the mocked coinSound object
//        assert handler.getCoinSound() == mockCoinSound;
//    }
//
//    @Test
//    void testGetOuchSound() {
//        // Verify that getOuchSound() returns the mocked ouchSound object
//        assert handler.getOuchSound() == mockOuchSound;
//    }
//
//
//    @Test
//    public void testCoinSoundIsStoredAndAccessible() {

//        assertNotNull(handler.getCoinSound());
//        assertEquals(mockSound, handler.getCoinSound());
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



