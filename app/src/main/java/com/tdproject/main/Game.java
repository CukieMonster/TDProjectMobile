package com.tdproject.main;

import com.tdproject.gamestates.GameState;
import com.tdproject.gamestates.MainMenu;
import com.tdproject.gamestates.Playing;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Game {

    @Getter(AccessLevel.NONE)
    private static Game instance;
//    private final int FPS_SET = 60;
    private final int UPS_SET = 60;
    private int updateCycle;
//    private int updates;

    @Setter
    private GameState.States currentGameState = GameState.States.MAIN_MENU;

    private Game() {
        //background = new Background();
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void update(int u) {
        updateCycle = u;
        //gamePanel.updateGame();
        switch (currentGameState) {
            case MAIN_MENU:
                break;
            case SETTINGS:
                break;
            case INVENTORY:
                break;
            case PLAYING:
                Playing.getInstance().update(u);
                break;
            case PAUSED:
                break;
        }
    }

    public void render(Object o) {
        switch (currentGameState) {
            case MAIN_MENU:
                MainMenu.getInstance().render(o);
                break;
            case SETTINGS:
                break;
            case INVENTORY:
                break;
            case PLAYING:
                Playing.getInstance().render(o);
                break;
            case PAUSED:
                break;
        }
    }

    public int getUpsSet() {
        return UPS_SET;
    }

}
