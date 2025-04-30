package inf112.starhunt.view;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.HashMap;
import java.util.Map;


/**
 * Class for handling sound effects, these are used when colliding or if player is losing a life.
 */
public class SoundHandler {
    private final Map<String, Sound> sounds = new HashMap<>();

    /**
     * Default constructor, adds sounds by getting from the sfx directory.
     */
    public SoundHandler() {
        this(Gdx.files, Gdx.audio);
    }

    // Dependency-injected constructor for easier testing
    public SoundHandler(Files files, Audio audio) {
        addSound("coin", "sfx/coinrecieved.wav", files, audio);
        addSound("ouch", "sfx/characterouch.wav", files, audio);
        addSound("bounce", "sfx/bounce.wav", files, audio);
        addSound("powerup", "sfx/powerup.wav", files, audio);
        addSound("gameover", "sfx/gameover.wav", files, audio);
        addSound("newlevel", "sfx/newlevel.mp3", files, audio);
    }

    /**
     * Overloaded method for easier testing.
     * @param name
     * @param filePath
     * @param files dependency needed for testing
     * @param audio dependency needed for testing
     */
    public void addSound(String name, String filePath, Files files, Audio audio) {
        FileHandle fileHandle = files.internal(filePath);
        Sound sound = audio.newSound(fileHandle);
        sounds.put(name, sound);
    }

    /**
     * Plays a sound based on sound name defined in {@link SoundHandler} constructor
     * @param name sound (nick)name for easier access
     *             Prints error if input string is null
     */
    public void playSound(String name) {
        Sound sound = sounds.get(name);
        if (sound != null) {
            sound.play(0.25f);
        } else {
            System.err.println("Sound not found: " + name);
        }
    }

    /**
     * Gets sound, mainly used for testing
     */
    public Sound getSound(String name) {
        return sounds.get(name);
    }
}
