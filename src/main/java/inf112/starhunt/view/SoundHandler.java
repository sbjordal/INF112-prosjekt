package inf112.starhunt.view;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.HashMap;
import java.util.Map;


/**
 * Singleton class for handling all sound effects.
 * Should only be initialized once for handling the sound effects correctly.
 */
public class SoundHandler {
    private static SoundHandler instance;

    private final Map<String, Sound> sounds = new HashMap<>();
    private final Map<String, Music> songs = new HashMap<>();

    /**
     * Default constructor, needed to initialize libGDX from {@link inf112.starhunt.model.WorldModel}.
     */
    private SoundHandler() {
        this(Gdx.files, Gdx.audio);
    }

    /**
     * Package private for testing
     * Dependency-injected constructor for easier testing
     */
    SoundHandler(Files files, Audio audio) {
        addSound("coin", "sfx/coinrecieved.wav", files, audio);
        addSound("ouch", "sfx/characterouch.wav", files, audio);
        addSound("bounce", "sfx/bounce.wav", files, audio);
        addSound("powerup", "sfx/powerup.wav", files, audio);
        addSound("gameover", "sfx/gameover.wav", files, audio);
        addSound("newlevel", "sfx/newlevel.wav", files, audio);
        addMusic("menu", "sfx/menu_music.wav", files, audio);
        addMusic("active", "sfx/activeGame_music.wav", files, audio);
    }

    /**
     * Adds sounds to map
     * @param name of sound
     * @param filePath placement
     * @param files dependency needed for testing
     * @param audio dependency needed for testing
     */
    void addSound(String name, String filePath, Files files, Audio audio) {
        FileHandle fileHandle = files.internal(filePath);
        Sound sound = audio.newSound(fileHandle);
        sounds.put(name, sound);
    }

    /**
     * Helper method for adding music to map,
     * @param name Music name
     * @param filePath placement
     * @param files dependency needed for testing
     * @param audio dependency needed for testing
     *
     */
    void addMusic(String name, String filePath, Files files, Audio audio) {
        FileHandle fileHandle = files.internal(filePath);
        Music music = audio.newMusic(fileHandle);
        songs.put(name, music);
    }

    /**
     * Plays a sound based on sound name defined in {@link SoundHandler} constructor
     * @param name sound (nick)name for easier access
     *             Prints error if input string is null
     */
    public void playSound(String name) {
        Sound sound = sounds.get(name);
        if (sound != null) {
            sound.play(0.50f);
        } else {
            System.err.println("Sound not found: " + name);
        }
    }

    /**
     * Starts playing the song on loop
     * @param music song to play
     */
    public void playMusic(Music music) {
        music.setVolume(0.50f);
        music.setLooping(true);
        music.play();
    }

    /**
     * Gets the instance to be used.
     * @return the Soundhandler instance to be used in {@link WorldView}
     */
    public static SoundHandler getInstance() {
        if (instance == null) {
            instance = new SoundHandler();
        }
        return instance;
    }

    public Sound getSound(String name) {
        return sounds.get(name);
    }

    public Music getMusic(String name) {
        return songs.get(name);
    }

}
