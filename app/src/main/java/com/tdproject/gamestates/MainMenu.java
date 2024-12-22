package com.tdproject.gamestates;

import com.tdproject.graphics.ButtonPanel;
import com.tdproject.inputs.MyEvent;
import com.tdproject.main.FieldParameters;
import com.tdproject.ui.MainMenuButtons;

public class MainMenu extends GameState {

    private static MainMenu instance;
    private final ButtonPanel buttonPanel;

    private MainMenu() {
        buttonPanel = new ButtonPanel(
                FieldParameters.X_RESOLUTION / 2,
                FieldParameters.Y_RESOLUTION / 2,
                500,
                500,
                MainMenuButtons.buttons
        );
    }

    public static MainMenu getInstance() {
        if (instance == null) {
            instance = new MainMenu();
        }
        return instance;
    }

    @Override
    public void update(int u) {

    }

    @Override
    public void render(Object o) {
        //buttonManager.draw(o);
        buttonPanel.draw(o);
    }

    @Override
    public void mouseReleased(MyEvent e) {
        //buttonManager.mouseReleased(new MyEvent(e));
        buttonPanel.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MyEvent e) {
        buttonPanel.mouseMoved(e);
    }
}
