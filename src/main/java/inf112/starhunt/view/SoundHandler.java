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
 * Class for handling sound effects, these are used when colliding or if player is losing a life.
 */
public class SoundHandler {
    private final Map<String, Sound> sounds = new HashMap<>();
    private final Map<String, Music> songs = new HashMap<>();

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
        addMusic("menu", "sfx/menu_music.wav", files, audio);
        addMusic("active", "sfx/active_game.wav", files, audio);
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
     * TODO
     * @param name
     * @param filePath
     * @param files dependency needed for testing
     * @param audio dependency needed for testing
     */
    public void addMusic(String name, String filePath, Files files, Audio audio) {
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
            sound.play(0.25f);
        } else {
            System.err.println("Sound not found: " + name);
        }
    }

    /**
     * Starts playing the song on loop
     * @param music song to play
     */
    public void playMusic(Music music) {
        music.setVolume(0.20f);
        music.setLooping(true);
        music.play();
    }

    public Sound getSound(String name) {
        return sounds.get(name);
    }

    public Music getMusic(String name) {return songs.get(name);}
}
