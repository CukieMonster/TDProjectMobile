package com.tdproject.ui;

import com.tdproject.gamestates.GameState;

import lombok.Getter;
import java.util.function.Consumer;

@Getter
public abstract class ButtonTemplate {

    private GameState.States gameState;
    private boolean[] defaultState;
    private int[][] position;
    private Consumer[] action;

}
