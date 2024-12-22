package com.tdproject.main;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.tdproject.gamestates.MainMenu;
import com.tdproject.gamestates.Playing;
import com.tdproject.inputs.MyEvent;

// This class has specific Android functionality
public class GameWindow extends SurfaceView implements SurfaceHolder.Callback {

    private GameMobile gameMobile;
    public static Context context;

    public GameWindow(Context context) {
        super(context);
        GameWindow.context = context;

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameMobile = new GameMobile(this, surfaceHolder);
        gameMobile.startGameLoop();

        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP -> {
                switch (Game.getInstance().getCurrentGameState()) {
                    case MAIN_MENU -> MainMenu.getInstance().mouseReleased(new MyEvent(event));
                    case PLAYING -> Playing.getInstance().mouseReleased(new MyEvent(event));
                }
            }
            case MotionEvent.ACTION_MOVE -> {
                switch (Game.getInstance().getCurrentGameState()) {
                    case MAIN_MENU -> MainMenu.getInstance().mouseMoved(new MyEvent(event));
                    case PLAYING -> Playing.getInstance().mouseMoved(new MyEvent(event));
                }
            }
        }

        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Game.getInstance().render(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

}
