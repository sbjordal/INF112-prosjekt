package inf112.starhunt.model.gameobject;

import com.badlogic.gdx.math.Vector2;

/**
 * Helper class that creates a Transform out of objects.
 */
public class TransformUtils {

    /**
     * Helper method for creating a transform out from an object.
     * @param xPos
     * @param yPos
     * @param width
     * @param height
     * @return a new {@link Transform}
     */
    public static Transform createTransformForObjects(float xPos, float yPos, float width, float height) {
        Vector2 size = new Vector2(width, height);
        Vector2 position = new Vector2(xPos, yPos);
        return new Transform(position, size);
    }
}
