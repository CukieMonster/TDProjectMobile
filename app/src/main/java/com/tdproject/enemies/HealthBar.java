package com.tdproject.enemies;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
// This class has specific android functionality
public class HealthBar {

    private final Enemy enemy;
    private final int width;

    public HealthBar(Enemy enemy) {
        this.enemy = enemy;
        width = enemy.getSprite().getWidth() / 2;
    }

    public void draw(Object o, double healthPercentage) {
        var position = enemy.getPosition();
        Canvas canvas = (Canvas) o;
        int x = (int) position.x - width / 2;
        int y = (int) position.y - enemy.getSprite().getHeight() / 2;

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawRect(x, y, x + width, y + 5, paint);
        paint.setColor(Color.GREEN);
        canvas.drawRect(x, y, x + (width * (float) healthPercentage), y + 5, paint);
    }

}
