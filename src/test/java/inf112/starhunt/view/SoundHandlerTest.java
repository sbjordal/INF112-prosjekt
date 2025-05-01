package inf112.starhunt.view;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.files.FileHandle;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class SoundHandlerTest {
    // Where not able to properly test every line due to the dependency of libGDX.
    private Files mockFiles;
    private Audio mockAudio;
    private FileHandle mockFileHandle;
    private Sound mockSound;

    @BeforeEach
    void setup() {
        // Initialize mocks
        mockFiles = mock(Files.class);
        mockAudio = mock(Audio.class);
        mockFileHandle = mock(FileHandle.class);
        mockSound = mock(Sound.class);
    }

    @Test
    void testDependencyInjectedConstructor_AddsAllStandardSoundsAndMusic() {
        // Mock FileHandles for sounds
        FileHandle mockCoinFile = mock(FileHandle.class);
        FileHandle mockOuchFile = mock(FileHandle.class);
        FileHandle mockBounceFile = mock(FileHandle.class);
        FileHandle mockPowerupFile = mock(FileHandle.class);
        FileHandle mockGameoverFile = mock(FileHandle.class);
        FileHandle mockNewlevelFile = mock(FileHandle.class);

        // Mock FileHandles for music
        FileHandle mockMenuMusicFile = mock(FileHandle.class);
        FileHandle mockActiveMusicFile = mock(FileHandle.class);

        // Mock Sounds
        Sound mockCoinSound = mock(Sound.class);
        Sound mockOuchSound = mock(Sound.class);
        Sound mockBounceSound = mock(Sound.class);
        Sound mockPowerupSound = mock(Sound.class);
        Sound mockGameoverSound = mock(Sound.class);
        Sound mockNewlevelSound = mock(Sound.class);

        // Mock Music
        Music mockMenuMusic = mock(Music.class);
        Music mockActiveMusic = mock(Music.class);

        // Configure sound behavior
        when(mockFiles.internal("sfx/coinrecieved.wav")).thenReturn(mockCoinFile);
        when(mockFiles.internal("sfx/characterouch.wav")).thenReturn(mockOuchFile);
        when(mockFiles.internal("sfx/bounce.wav")).thenReturn(mockBounceFile);
        when(mockFiles.internal("sfx/powerup.wav")).thenReturn(mockPowerupFile);
        when(mockFiles.internal("sfx/gameover.wav")).thenReturn(mockGameoverFile);
        when(mockFiles.internal("sfx/newlevel.wav")).thenReturn(mockNewlevelFile);

        when(mockAudio.newSound(mockCoinFile)).thenReturn(mockCoinSound);
        when(mockAudio.newSound(mockOuchFile)).thenReturn(mockOuchSound);
        when(mockAudio.newSound(mockBounceFile)).thenReturn(mockBounceSound);
        when(mockAudio.newSound(mockPowerupFile)).thenReturn(mockPowerupSound);
        when(mockAudio.newSound(mockGameoverFile)).thenReturn(mockGameoverSound);
        when(mockAudio.newSound(mockNewlevelFile)).thenReturn(mockNewlevelSound);

        // Configure music behavior
        when(mockFiles.internal("sfx/menu_music.wav")).thenReturn(mockMenuMusicFile);
        when(mockFiles.internal("sfx/activeGame_music.wav")).thenReturn(mockActiveMusicFile);

        when(mockAudio.newMusic(mockMenuMusicFile)).thenReturn(mockMenuMusic);
        when(mockAudio.newMusic(mockActiveMusicFile)).thenReturn(mockActiveMusic);

        // Initialize SoundHandler
        SoundHandler handler = new SoundHandler(mockFiles, mockAudio);

        // Verify all sounds are added
        assertEquals(mockCoinSound, handler.getSound("coin"));
        assertEquals(mockOuchSound, handler.getSound("ouch"));
        assertEquals(mockBounceSound, handler.getSound("bounce"));
        assertEquals(mockPowerupSound, handler.getSound("powerup"));
        assertEquals(mockGameoverSound, handler.getSound("gameover"));
        assertEquals(mockNewlevelSound, handler.getSound("newlevel"));

        // Verify all music is added
        assertEquals(mockMenuMusic, handler.getMusic("menu"));
        assertEquals(mockActiveMusic, handler.getMusic("active"));
    }



    @Test
    void testAddSound_AddsNewSoundSuccessfully() {
        // Set up mock behavior
        when(mockFiles.internal("sfx/exampleSound.mp3")).thenReturn(mockFileHandle);
        when(mockAudio.newSound(mockFileHandle)).thenReturn(mockSound);

        // Create an instance of SoundHandler with mocks (you can pass these directly if refactored for dependency injection)
        SoundHandler handler = new SoundHandler(mockFiles, mockAudio);

        // Call addSound method
        handler.addSound("exampleSound", "sfx/exampleSound.mp3", mockFiles, mockAudio);

        // Verify the sound was added
        assertEquals(mockSound, handler.getSound("exampleSound"));
    }

    @Test
    void testPlaySound_PlaysCorrectSound() {
        // Create mocks
        Files mockFiles = mock(Files.class);
        Audio mockAudio = mock(Audio.class);
        FileHandle mockCoinFileHandle = mock(FileHandle.class);
        Sound mockCoinSound = mock(Sound.class);

        // Configure mock behaviors
        when(mockFiles.internal("sfx/coinrecieved.wav")).thenReturn(mockCoinFileHandle);
        when(mockAudio.newSound(mockCoinFileHandle)).thenReturn(mockCoinSound);

        // Initialize SoundHandler with mocks
        SoundHandler handler = new SoundHandler(mockFiles, mockAudio);

        // Play sound and verify it was played with the correct volume
        handler.playSound("coin");
        verify(mockCoinSound).play(0.25f);
    }

    @Test
    void testPlaySound_WhenNameIsNull_LogsError() {
        Files mockFiles = mock(Files.class);
        Audio mockAudio = mock(Audio.class);

        // Initialize SoundHandler with mocks
        SoundHandler handler = new SoundHandler(mockFiles, mockAudio);
        // Redirect System.err
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        // Call playSound with null
        handler.playSound(null);

        // Verify the error message
        assertTrue(errContent.toString().contains("Sound not found: null"));

        // Reset System.err
        System.setErr(System.err);
    }

    @Test
    void testAddAndGetMusic() {
        FileHandle mockFileHandle = mock(FileHandle.class);
        Music mockMusic = mock(Music.class);

        when(mockFiles.internal("sfx/menu_music.wav")).thenReturn(mockFileHandle);
        when(mockAudio.newMusic(mockFileHandle)).thenReturn(mockMusic);

        SoundHandler handler = new SoundHandler(mockFiles, mockAudio);
        handler.addMusic("menu", "sfx/menu_music.wav", mockFiles, mockAudio);

        assertEquals(mockMusic, handler.getMusic("menu"), "Music should be retrievable after adding");
    }

    @Test
    void testPlayMusicCallsCorrectMethods() {
        Music mockMusic = mock(Music.class);

        SoundHandler handler = new SoundHandler(mockFiles, mockAudio);
        handler.playMusic(mockMusic);

        verify(mockMusic).setVolume(0.20f);
        verify(mockMusic).setLooping(true);
        verify(mockMusic).play();
    }


}
