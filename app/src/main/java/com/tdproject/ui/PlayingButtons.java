package com.tdproject.ui;

import com.tdproject.enemies.EnemyManager;
import com.tdproject.gamestates.Playing;
import com.tdproject.graphics.Sprite;

public class PlayingButtons {

    public static final Button SKIP_BUTTON = new Button(
            Sprite.SpriteId.BUTTON_SKIP,
            i -> EnemyManager.getInstance().spawnWave(),
            false
    );

    public static final Button[] buttons = {
            SKIP_BUTTON,
            new Button(
                    Sprite.SpriteId.BUTTON_FAST_FORWARD,
                    i -> Playing.getInstance().changeGameSpeed()
            )
    };

    private static PlayingButtons instance;

    private PlayingButtons() {

    }

    public static PlayingButtons getInstance() {
        if (instance == null) {
            instance = new PlayingButtons();
        }
        return instance;
    }

}
