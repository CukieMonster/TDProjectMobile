package com.tdproject.graphics;

import com.tdproject.main.FieldParameters;

import javax.vecmath.Vector2d;

public class Background extends Sprite {

    public Background() {
        loadSprite(SpriteId.BACKGROUND, Sprite.BACKGROUND_WIDTH, Sprite.BACKGROUND_HEIGHT);
        position = new Vector2d(FieldParameters.X_RESOLUTION / 2D, FieldParameters.Y_RESOLUTION / 2D);
    }
}
