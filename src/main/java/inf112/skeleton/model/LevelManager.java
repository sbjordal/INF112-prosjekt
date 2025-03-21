package inf112.skeleton.model;

import inf112.skeleton.model.gameobject.GameObject;

import java.util.ArrayList;

/**
 * TODO: skriv javadoc for alle metoder
 *
 * MEASUREMENTS
 * - level dimensions 	= 4500 x 920
 * - Ground block 		= 50 x 50
 * - Snail 		        = 40 x 40
 * - Leopard 		    = 70 x 70
 * - Coin 			    = 30 x 30
 * - Banana 		    = 50 x 53
 * - Player             = 40 x 80
 *
 * EXCEPTIONS
 * - Missing player object / too many: one-and-only-one player per level.
 * - Missing star object / too many: one-and-only-one star per level.
 *
 * REMARKS
 * - loadLevel() returns ArrayList<>. Be careful with mutability.
 *
 */
public class LevelManager {
    public enum Level{
        LEVEL_1,
        LEVEL_2,
        LEVEL_3
    }

    public static ArrayList<GameObject> loadLevel(Level level) {
        String levelFile = getLevelFile(level);
        ArrayList<GameObject> objects = new ArrayList<>();

        // read JSON file
        // ...

        return objects;
    }

    private static String getLevelFile(Level level) {
        return switch (level) {
            case LEVEL_1 -> "level_one.json";
            case LEVEL_2 -> "level_two.json";
            case LEVEL_3 -> "level_three.json";
            default -> throw new IllegalStateException("No level file found for " + level);
        };
    }
}
