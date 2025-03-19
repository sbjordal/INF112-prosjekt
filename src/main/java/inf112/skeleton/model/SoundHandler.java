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

    public void playCoinSound() {
        coinSound.play(1.0f);
    }

}
