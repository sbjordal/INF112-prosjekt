package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import inf112.skeleton.model.WorldModel;

public class Main {
    private final static int WINDOW_WIDTH = 1500;
    private final static int WINDOW_HEIGHT = 920;

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setResizable(false);
        cfg.setTitle("Mario 2");
        cfg.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT);

        new Lwjgl3Application(new WorldModel(WINDOW_WIDTH, WINDOW_HEIGHT), cfg);
    }
}