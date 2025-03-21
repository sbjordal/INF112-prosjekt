package inf112.skeleton.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import inf112.skeleton.model.gameobject.GameObject;
import inf112.skeleton.model.gameobject.Transform;
import inf112.skeleton.model.gameobject.fixedobject.FixedObject;
import inf112.skeleton.model.gameobject.fixedobject.item.ItemFactory;
import inf112.skeleton.model.gameobject.mobileobject.actor.Player;
import inf112.skeleton.model.gameobject.mobileobject.actor.enemy.EnemyFactory;
import inf112.skeleton.model.gameobject.mobileobject.actor.enemy.EnemyType;

import java.io.File;
import java.io.IOException;
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
//        String levelFile = getLevelFile(level);
        ArrayList<GameObject> objects = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        // TODO: kanskje flytt hele try-catch statement før heile greien.
        // TODO: fiks file path til å fungere med getLevelFile().
        // TODO: få player til å bli inkludert her istedenfor i WorldModel.
        // TODO: legg til star.
        // TODO: legg til template prosjekt (Tiled) for level-designer til å bruke.
        try {
            JsonNode root = objectMapper.readTree(new File("C:/Users/eivin/Desktop/projects/INF112/sjette-etasje/src/main/resources/levels/level_one.json"));
            int mapHeight = root.get("height").asInt() * root.get("tileheight").asInt();

            int playerCount = 0;
            int starCount = 0;

            for (JsonNode layer : root.get("layers")) {
                if (!layer.get("type").asText().equals("objectgroup")) continue;

                String layerName = layer.get("name").asText();

                // Fill up the objects list
                for (JsonNode obj : layer.get("objects")) {
                    int x = obj.get("x").asInt();
                    int y = mapHeight - obj.get("y").asInt() - obj.get("height").asInt();

                    switch (layerName) {
                        case "ground" -> objects.add(new FixedObject(new Transform(new Vector2(x, y), new Vector2(50, 50))));
//                        case "player" -> {
//                            objects.add(new Player(1, 300, new Transform(new Vector2(x, y), new Vector2(40, 80))));
//                            playerCount++;
//                        }
                        case "coin" -> objects.add(ItemFactory.createCoin(x, y));
                        case "banana" -> objects.add(ItemFactory.createMushroom(x, y));
                        case "snail" -> objects.add(EnemyFactory.createSnail(x, y, EnemyType.SNAIL));
                        case "leopard" -> objects.add(EnemyFactory.createLeopard(x, y, EnemyType.LEOPARD));
//                        case "Star" -> starCount++; // TODO: implementer star tilfelle
                        default -> System.out.println("Unknown layer: " + layerName + ". Case sensitivity maybe?");
                    }
                }
            }
//
//            if (playerCount != 1) {
//                throw new IllegalStateException("Level must have exactly one Player, but found: " + playerCount);
//            }
//            if (starCount != 1) {
//                throw new IllegalStateException("Level must have exactly one Star, but found: " + starCount);
//            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to load level: " + level);
        }

        return objects;
    }

//    private static String getLevelFile(Level level) {
//        String internalPath = "levels/";
//
//        switch (level) {
//            case LEVEL_1: internalPath += "level_one.json"; break;
//            case LEVEL_2: internalPath += "level_two.json"; break;
//            case LEVEL_3: internalPath += "level_three.json"; break;
//            default: throw new IllegalStateException("No level file found for: " + level);
//        };
//
//        return Gdx.files.internal(internalPath);
//    }
}
