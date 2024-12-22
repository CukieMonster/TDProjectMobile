package com.tdproject.gamestates;

import com.tdproject.inputs.MyEvent;

public abstract class GameState {

    abstract void update(int u);

    abstract void render(Object o);

//    void mouseClicked();
//    void mousedPressed();

    abstract void mouseReleased(MyEvent e);

    abstract void mouseMoved(MyEvent e);

//    void keyPressed();
//    void keyReleased();

    public enum States {

        MAIN_MENU, SETTINGS, INVENTORY, PLAYING, PAUSED;

    }

}
