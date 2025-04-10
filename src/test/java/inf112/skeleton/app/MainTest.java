package inf112.skeleton.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class MainTest {

    @Test
    public void testLwjgl3ApplicationConfiguration() {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();

        Assertions.assertNotNull(cfg);
    }

}
