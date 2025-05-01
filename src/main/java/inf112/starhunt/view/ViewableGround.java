package inf112.starhunt.view;

import inf112.starhunt.model.gameobject.fixedobject.Ground;

/**
 * An interface that exposes the alteration of a {@link Ground} object.
 * The alteration defines which ground texture to assign the object.
 * There are a total of 16 different alterations.
 */
public interface ViewableGround {
    String getAlteration();
}
