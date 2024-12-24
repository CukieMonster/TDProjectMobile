package com.tdproject.gamestates;

import com.tdproject.graphics.ButtonPanel;
import com.tdproject.graphics.Sprite;
import com.tdproject.graphics.Text;
import com.tdproject.inputs.MyEvent;
import com.tdproject.main.FieldParameters;
import com.tdproject.ui.Button;
import com.tdproject.ui.MainMenuButtons;
import lombok.Getter;

public class MainMenu extends GameState {

    private static MainMenu instance;
    private final ButtonPanel buttonPanel;
    @Getter
    private int difficulty = 0;
    private final Text difficultyText = new Text(String.valueOf(difficulty), 60, Text.Color.RED, 1470, 300);
    private final ButtonPanel difficultyUpButtons;
    private final ButtonPanel difficultyDownButtons;

    private MainMenu() {
        buttonPanel = new ButtonPanel(
                FieldParameters.X_RESOLUTION / 2,
                FieldParameters.Y_RESOLUTION / 2,
                500,
                500,
                MainMenuButtons.buttons
        );
        Button[] tmpUp = {
                new Button(Sprite.SpriteId.MISSING_SPRITE, b -> adjustDifficulty(100), 10, 10),
                new Button(Sprite.SpriteId.MISSING_SPRITE, b -> adjustDifficulty(10), 10, 10),
                new Button(Sprite.SpriteId.MISSING_SPRITE, b -> adjustDifficulty(1), 10, 10)
        };
        Button[] tmpDown = {
                new Button(Sprite.SpriteId.MISSING_SPRITE, b -> adjustDifficulty(-100), 10, 10),
                new Button(Sprite.SpriteId.MISSING_SPRITE, b -> adjustDifficulty(-10), 10, 10),
                new Button(Sprite.SpriteId.MISSING_SPRITE, b -> adjustDifficulty(-1), 10, 10)
        };
        difficultyUpButtons = new ButtonPanel(1500, 200, 200, 64, tmpUp, 3);
        difficultyDownButtons = new ButtonPanel(1500, 370, 200, 64, tmpDown, 3);
    }
    
    private void adjustDifficulty(int value) {
        difficulty += value;
        if (difficulty > 100) {
            difficulty = 100;
        }
        if (difficulty < 0) {
            difficulty = 0;
        }
        difficultyText.setString(String.valueOf(difficulty));
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
        difficultyText.draw(o);
        difficultyUpButtons.draw(o);
        difficultyDownButtons.draw(o);
    }

    @Override
    public void mouseReleased(MyEvent e) {
        //buttonManager.mouseReleased(new MyEvent(e));
        buttonPanel.mouseReleased(e);
        difficultyUpButtons.mouseReleased(e);
        difficultyDownButtons.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MyEvent e) {
        buttonPanel.mouseMoved(e);
        difficultyUpButtons.mouseMoved(e);
        difficultyDownButtons.mouseMoved(e);
    }
}
