package inf112.skeleton.model;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;

//TODO: Soundhandler skal ligge i view, må flytte ting herfra.
// Kommentert ut i view for å få prosjektet til å kompilere
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

    public void playCoinSound() {
        coinSound.play(0.25f);
    }

    public Sound getCoinSound() { return coinSound; }

    public void playOuchSound() {
        ouchSound.play(0.25f);
    }

    public Sound getOuchSound() { return ouchSound; }

}
