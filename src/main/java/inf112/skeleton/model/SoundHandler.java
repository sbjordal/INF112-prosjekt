package inf112.skeleton.model;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;

// TODO, revisjon, ny klasse for å håndtere lyd, brukes i WorldModel
public class SoundHandler {

    // TODO, lage liste/map med lyder?
    private Sound coinSound;
    
    public SoundHandler() {
        this.coinSound = Gdx.audio.newSound(Gdx.files.internal("sfx/coinrecieved.mp3"));
    }

    public void playCoinSound() {
        coinSound.play(1.0f);
    }

}
