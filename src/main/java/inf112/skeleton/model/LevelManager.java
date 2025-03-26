package inf112.skeleton.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the loading of levels in the game.
 * Each level is defined in a JSON file and parsed into a list of {@link GameObject} instances.
 */
public class LevelManager {

    /**
     * Enum representing available game levels.
     */
    public enum Level{
        LEVEL_1,
        LEVEL_2,
        LEVEL_3
    }

    /**
     * Load a level.
     * The level is represented as a list of {@link GameObject} types.
     * The game objects are extracted from a JSON file by parsing it.
     *
     * @param level Level to load.
     * @return The extracted game objects as a list.
     * @throws IllegalStateException If anything other than exactly one player or exactly one star was found.
     */
    public static Pair<List<GameObject>, Player> loadLevel(Level level) {
        FileHandle levelFile = getLevelFile(level);
        ObjectMapper objectMapper = new ObjectMapper();
        String levelContent;
        JsonNode jsonRoot;
        Player player;

        try {
            levelContent = levelFile.readString();
            jsonRoot = objectMapper.readTree(levelContent);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load level: " + level);
        }

        List<GameObject> objects = new ArrayList<>();
        int mapHeight = jsonRoot.get("height").asInt() * jsonRoot.get("tileheight").asInt();
        int playerCount = 0;
        int starCount = 0;
        int playeridx = 0;

        for (JsonNode layer : jsonRoot.get("layers")) {
            if (!layer.get("type").asText().equals("objectgroup")) continue;

            String layerName = layer.get("name").asText();
            for (JsonNode obj : layer.get("objects")) {
                int x = obj.get("x").asInt();
                int y = mapHeight - obj.get("y").asInt() - obj.get("height").asInt();

                switch (layerName) {
                    case "ground" -> {
                        Vector2 size = new Vector2(50, 50);
                        Vector2 position = new Vector2(x, y);
                        Transform transform = new Transform(position, size);
                        FixedObject ground = new FixedObject(transform);
                        objects.add(ground);
                    }
                    case "player" -> {
                        Vector2 size = new Vector2(40, 80);
                        Vector2 position = new Vector2(x, y);
                        Transform transform = new Transform(position, size);
                        player = new Player(3, 350, transform);
                        playeridx = objects.size();
                        objects.add(player);
                        playerCount++;
                    }
                    case "star" -> {
                        objects.add(ItemFactory.createStar(x, y));
                        starCount++;
                    }
                    case "coin" -> objects.add(ItemFactory.createCoin(x, y));
                    case "banana" -> objects.add(ItemFactory.createBanana(x, y));
                    case "snail" -> objects.add(EnemyFactory.createSnail(x, y, EnemyType.SNAIL));
                    case "leopard" -> objects.add(EnemyFactory.createLeopard(x, y, EnemyType.LEOPARD));
                    default -> System.out.println("Unknown layer: " + layerName + ". Case sensitivity maybe?");
                }
            }
        }
        if (playerCount != 1) {
            throw new IllegalStateException("Level must have exactly one Player, but found: " + playerCount);
        }
        if (starCount != 1) {
            throw new IllegalStateException("Level must have exactly one Star, but found: " + starCount);
        }
        GameObject obj = objects.get(playeridx);
        if (!(obj instanceof Player)){
            throw new IllegalArgumentException("object not instance of PLayer");
        }
        return new Pair<>(objects, (Player) objects.get(playeridx));
    }

    /**
     * Retrieves the corresponding JSON file for a given level.
     *
     * @param level The level to retrieve.
     * @return A {@link FileHandle} pointing to the JSON file.
     * @throws IllegalStateException If the level file is not found.
     */
    private static FileHandle getLevelFile(Level level) {
        String levelPath = "levels/";

        switch (level) {
            case LEVEL_1: levelPath += "level_one.json"; break;
            case LEVEL_2: levelPath += "level_two.json"; break;
            case LEVEL_3: levelPath += "level_three.json"; break;
            default: throw new IllegalStateException("No level file found for: " + level);
        };

        return Gdx.files.classpath(levelPath);
    }
}
