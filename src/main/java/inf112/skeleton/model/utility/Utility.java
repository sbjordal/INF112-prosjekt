package inf112.skeleton.model.utility;

import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.WorldModel;
import inf112.skeleton.model.gameobject.CollisionBox;
import inf112.skeleton.model.gameobject.Transform;

/**
 *
 *
 */
public class Utility {
    private static WorldModel model;

    public static float binarySearch(float startX, float startY, int delta, Vector2 size, boolean isX) {
        int low = 0;
        int high = Math.abs(delta);
        boolean isNegative = delta < 0;

        while (low < high) {
            int mid = (low + high + 1) / 2;
            int testDelta = isNegative ? -mid : mid;

            Vector2 newPosition = isX ? new Vector2(startX + testDelta, startY) : new Vector2(startX, startY + testDelta);
            Transform newTransform = new Transform(newPosition, size);
            CollisionBox newCollisionBox = new CollisionBox(newTransform);

            if (model.isLegalMove(newCollisionBox)) {
                low = mid;
            } else {
                high = mid - 1;
            }
        }

        final float startCoordinate = isX ? startX : startY;
        final float endCoordinate = isNegative ? -low : low;

        return startCoordinate + endCoordinate;
    }

}
