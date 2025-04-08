package inf112.skeleton.model;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;

public class SoundHandler {

    // TODO, lage liste/map med lyder?
    // TODO: Justere implementasjon for å unngå å måtte ta inn soundhandler ved testing av coin?
    private Sound coinSound;
    
    public SoundHandler() {
        this.coinSound = Gdx.audio.newSound(Gdx.files.internal("sfx/coinrecieved.mp3"));
    }

    // Ekstra konstruktør for testing
    public SoundHandler(Sound coinSound) { this.coinSound = coinSound; }

    public void playCoinSound() {
        coinSound.play(0.25f);
    }

    public Sound getCoinSound() { return coinSound; }

}
