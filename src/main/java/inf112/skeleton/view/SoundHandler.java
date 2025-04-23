package inf112.skeleton.view;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;
public class SoundHandler {
    private final Sound coinSound;
    private final Sound ouchSound;
    public SoundHandler() {
        this.coinSound = Gdx.audio.newSound(Gdx.files.internal("sfx/coinrecieved.mp3"));
        this.ouchSound = Gdx.audio.newSound(Gdx.files.internal("sfx/characterouch.mp3"));
    }
    // Ekstra konstruktør for testing
    public SoundHandler(Sound coinSound, Sound ouchSound) {
        this.coinSound = coinSound;
        this.ouchSound = ouchSound;
    }
/**
 * Class for handling sound effects, these are used when colliding or if player is losing a life.
 */
//public class SoundHandler {

    public void playCoinSound() {
        coinSound.play(0.25f);
    }

    public Sound getCoinSound() { return coinSound; }

    public void playOuchSound() {
        ouchSound.play(0.25f);
    }

    public Sound getOuchSound() {
        return ouchSound;
    }
}
