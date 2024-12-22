package com.tdproject.ui;

import com.tdproject.gamestates.GameState;
import com.tdproject.graphics.Sprite;
import com.tdproject.main.Game;

public class MainMenuButtons {

    public static final Button[] buttons = {
            new Button(
                    Sprite.SpriteId.BUTTON_MAIN_MENU_PLAY,
                    i -> Game.getInstance().setCurrentGameState(GameState.States.PLAYING),
                    Sprite.MENU_BUTTON_WIDTH,
                    Sprite.MENU_BUTTON_HEIGHT
            ),
            new Button(
                    Sprite.SpriteId.BUTTON_MAIN_MENU_INVENTORY,
                    i -> Game.getInstance().setCurrentGameState(GameState.States.INVENTORY),
                    Sprite.MENU_BUTTON_WIDTH,
                    Sprite.MENU_BUTTON_HEIGHT
            ),
            new Button(
                    Sprite.SpriteId.BUTTON_MAIN_MENU_SETTINGS,
                    i -> Game.getInstance().setCurrentGameState(GameState.States.SETTINGS),
                    Sprite.MENU_BUTTON_WIDTH,
                    Sprite.MENU_BUTTON_HEIGHT
            ),
            new Button(
                    Sprite.SpriteId.BUTTON_MAIN_MENU_QUIT,
                    i -> System.exit(0),
                    Sprite.MENU_BUTTON_WIDTH,
                    Sprite.MENU_BUTTON_HEIGHT
            )
    };

    private static MainMenuButtons instance;

    private MainMenuButtons() {

    }

    public static MainMenuButtons getInstance() {
        if (instance == null) {
            instance = new MainMenuButtons();
        }
        return instance;
    }

}
