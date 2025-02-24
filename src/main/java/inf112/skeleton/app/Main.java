package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import inf112.skeleton.model.WorldModel;

public class Main {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("Mario 2");
        cfg.setWindowedMode(WorldModel.SCREEN_WIDTH, WorldModel.SCREEN_HEIGHT);

        new Lwjgl3Application(new WorldModel(), cfg);
        System.out.println("Eivind er dum i hode sitt");
    }
}