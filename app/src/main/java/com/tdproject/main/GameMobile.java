package com.tdproject.main;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

// This class has specific Android functionality
public class GameMobile implements Runnable {

//    private static GameMobile instance;
    private GameWindow gameWindow;
    private SurfaceHolder surfaceHolder;
    private Thread gameThread;
    private final int FPS_SET = 60;
    private final int UPS_SET = 60;
    private int updates;

    public GameMobile(GameWindow gameWindow, SurfaceHolder surfaceHolder) {
        this.gameWindow = gameWindow;
        this.surfaceHolder = surfaceHolder;
    }

    public void startGameLoop() {
        System.err.println("Starting game loop...");
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;
        long previousTime = System.nanoTime();
        int frames = 0;
        //int updates = 0;
        double deltaF = 0;
        double deltaU = 0;

        Canvas canvas;
        while (true) {
            long currentTime = System.nanoTime();

            deltaF += (currentTime - previousTime) / timePerFrame;
            deltaU += (currentTime - previousTime) / timePerUpdate;
            previousTime = currentTime;

            if (deltaF >= 1) {
                canvas = surfaceHolder.lockCanvas();
                if (canvas == null) {
                    continue;
                }
                gameWindow.draw(canvas);
                surfaceHolder.unlockCanvasAndPost(canvas);
                //GamePanel.getInstance().repaint();
                frames++;
                deltaF--;
            }

            if (deltaU >= 1) {
                Game.getInstance().update(updates);
                updates++;
                deltaU--;
            }
        }
    }

}
