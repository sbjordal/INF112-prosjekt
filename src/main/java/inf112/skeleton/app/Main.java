package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.skeleton.model.WorldBoard;
import inf112.skeleton.model.WorldModel;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.time.LocalDateTime;

public class Main {
    private final static int WINDOW_WIDTH = 1500;
    private final static int WINDOW_HEIGHT = 920;

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Main.class);
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        logger.info("App started: {}", LocalDateTime.now());
        cfg.setResizable(false);
        cfg.setTitle("Mario 2");
        cfg.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT);

        new Lwjgl3Application(new WorldModel(WINDOW_WIDTH, WINDOW_HEIGHT), cfg);
    }
}