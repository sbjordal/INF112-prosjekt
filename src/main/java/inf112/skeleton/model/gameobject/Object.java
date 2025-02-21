package inf112.skeleton.model.gameobject;

import com.badlogic.gdx.graphics.Texture;

// Kan hente ut verdiene ved å ta object.pos() og object.sprite()
public record Object(Tansform transform, Texture sprite) {
}
