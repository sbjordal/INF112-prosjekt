package inf112.starhunt.view;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void testDependencyInjectedConstructor_AddsStandardSounds() {
        // Mock dependencies
        FileHandle mockCoinFileHandle = mock(FileHandle.class);
        FileHandle mockOuchFileHandle = mock(FileHandle.class);
        Sound mockCoinSound = mock(Sound.class);
        Sound mockOuchSound = mock(Sound.class);

        // Configure mock behavior
        when(mockFiles.internal("sfx/coinrecieved.mp3")).thenReturn(mockCoinFileHandle);
        when(mockFiles.internal("sfx/characterouch.mp3")).thenReturn(mockOuchFileHandle);
        when(mockAudio.newSound(mockCoinFileHandle)).thenReturn(mockCoinSound);
        when(mockAudio.newSound(mockOuchFileHandle)).thenReturn(mockOuchSound);

        // Initialize SoundHandler with mocks
        SoundHandler handler = new SoundHandler(mockFiles, mockAudio);

        // Verify that the sounds were added correctly
        assertEquals(mockCoinSound, handler.getSound("coin"));
        assertEquals(mockOuchSound, handler.getSound("ouch"));
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
        when(mockFiles.internal("sfx/coinrecieved.mp3")).thenReturn(mockCoinFileHandle);
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

}
