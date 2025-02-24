package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("Mario 2");
        cfg.setWindowedMode(480, 320);

        new Lwjgl3Application(new HelloWorld(), cfg);
        System.out.println("Eivind er dum i hode sitt");
    }
}