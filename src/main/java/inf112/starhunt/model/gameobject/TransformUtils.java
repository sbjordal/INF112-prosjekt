package inf112.starhunt.model.gameobject;

import com.badlogic.gdx.math.Vector2;

public class TransformUtils {

    public static Transform createTransformForObjects(float xPos, float yPos, float width, float height) {
        Vector2 size = new Vector2(width, height);
        Vector2 position = new Vector2(xPos, yPos);
        return new Transform(position, size);
    }

}
