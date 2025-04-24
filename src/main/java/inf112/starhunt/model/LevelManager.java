package inf112.starhunt.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import inf112.starhunt.model.gameobject.Collidable;
import inf112.starhunt.model.gameobject.GameObject;
import inf112.starhunt.model.gameobject.Transform;
import inf112.starhunt.model.gameobject.fixedobject.Ground;
import inf112.starhunt.model.gameobject.fixedobject.item.ItemFactory;
import inf112.starhunt.model.gameobject.mobileobject.actor.Player;
import inf112.starhunt.model.gameobject.mobileobject.actor.enemy.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static inf112.starhunt.model.LevelManager.Level.*;

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
    public static Triple<List<Enemy>, List<Collidable>, Player> loadLevel(Level level) {
        FileHandle levelFile = getLevelFile(level);
        ObjectMapper objectMapper = new ObjectMapper();
        String levelContent;
        JsonNode jsonRoot;

        try {
            levelContent = levelFile.readString();
            jsonRoot = objectMapper.readTree(levelContent);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load level: " + level);
        }

        List<Enemy> enemies = new ArrayList<>();
        List<Collidable> collidables = new ArrayList<>();
        Player player = new Player(1, 0, new Transform(new Vector2(0, 0), new Vector2(0, 0)));

        int mapHeight = jsonRoot.get("height").asInt() * jsonRoot.get("tileheight").asInt();
        int playerCount = 0;
        int starCount = 0;

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
                        Ground ground = new Ground(transform);
                        collidables.add(ground);
                    }
                    case "player" -> {
                        Vector2 size = new Vector2(40, 80);
                        Vector2 position = new Vector2(x, y);
                        Transform transform = new Transform(position, size);
                        player = new Player(3, 350, transform);
                        collidables.add(player);
                        playerCount++;
                    }
                    case "star" -> {
                        collidables.add(ItemFactory.createStar(x, y));
                        starCount++;
                    }
                    case "coin" -> collidables.add(ItemFactory.createCoin(x, y));
                    case "banana" -> collidables.add(ItemFactory.createBanana(x, y));
                    case "snail" -> {
                        final Snail snail = EnemyFactory.createSnail(x, y, EnemyType.SNAIL);
                        collidables.add(snail);
                        enemies.add(snail);
                    }
                    case "leopard" -> {
                        final Leopard leopard = EnemyFactory.createLeopard(x, y, EnemyType.LEOPARD);
                        collidables.add(leopard);
                        enemies.add(leopard);
                    }
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

        return new Triple<>(enemies, collidables, player);
    }

    /**
     * Retrieves the corresponding JSON file for a given level.
     *
     * @param level The level to retrieve.
     * @return A {@link FileHandle} pointing to the JSON file.
     * @throws IllegalStateException If the level file is not found.
     */
    static FileHandle getLevelFile(Level level) {

        if (level == null) {
            throw new IllegalStateException("No level file found for: null");
        }

        String levelPath = "levels/";

        switch (level) {
            case LEVEL_1: levelPath += "level_one.json"; break;
            case LEVEL_2: levelPath += "level_two.json"; break;
            case LEVEL_3: levelPath += "level_three.json"; break;
            default: throw new IllegalStateException("No level file found for: " + level);
        };

        return Gdx.files.classpath(levelPath);
    }

    /**
     * TODO comment
     *
     * @param level
     * @return
     */
    static Level getNextLevel(Level level) {
        switch (level) {
            case LEVEL_1:
                return (LEVEL_2);
            case LEVEL_2:
                return (LEVEL_3);
            case LEVEL_3:
                return (LEVEL_1);
            default: throw new IllegalArgumentException("No such level exists");
        }
    }


}
