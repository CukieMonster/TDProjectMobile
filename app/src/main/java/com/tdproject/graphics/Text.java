package com.tdproject.graphics;

import android.graphics.Canvas;
import android.graphics.Paint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// This class has specific Android functionality
public class Text {

    public final static int DEFAULT_FONT_SIZE = 30;
    public final static Color DEFAULT_COLOR = Color.WHITE;
    public final static Integer DEFAULT_FONT_STYLE = null;

    private String string;
    private int fontSize;
    private Color color;

    private int x, y;

    public Text(String string) {
        this(string, DEFAULT_FONT_SIZE, DEFAULT_COLOR, 0, 0);
    }

    public Text(String string, int x, int y) {
        this(string, DEFAULT_FONT_SIZE, DEFAULT_COLOR, x, y);
    }

    public Text(String string, int fontSize, Color color) {
        this(string, fontSize, color, 0, 0);
    }

    public Text(String string, int fontSize, Color color, int x, int y) {
        this.string = string;
        this.fontSize = fontSize;
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public void draw(Object g) {
        Paint paint = new Paint();
        paint.setColor(switch (color) {
            case WHITE -> android.graphics.Color.WHITE;
            case RED -> android.graphics.Color.RED;
        });
        ((Canvas)g).drawText(string, x, y, paint);
    }

    public enum Color {
        WHITE,
        RED
    }

}
