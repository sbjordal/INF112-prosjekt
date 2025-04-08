package inf112.skeleton.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.audio.Sound;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


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
}


