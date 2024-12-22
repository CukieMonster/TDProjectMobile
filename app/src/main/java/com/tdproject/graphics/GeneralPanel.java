package com.tdproject.graphics;

import android.graphics.Canvas;
import android.graphics.Paint;

// This class has specific android functionality
public abstract class GeneralPanel<C> extends AbstractPanel<C> {

    public GeneralPanel(int xCenterPos, int yCenterPos, int width, int height, C[] content) {
        super(xCenterPos, yCenterPos, width, height, content);
    }

    public GeneralPanel(int xCenterPos, int yCenterPos, int width, int height, C[] content, int columns) {
        super(xCenterPos, yCenterPos, width, height, content, columns);
    }

    public void draw(Object o) {
        Canvas c = (Canvas) o;
        Paint paint = new Paint();
        paint.setARGB(0, 0, 0, 100);
        drawCentered(c, paint);

        for (C item : content) {
            drawContent(o, item);
        }
    }

    private void drawCentered(Canvas c, Paint paint) {
        int x = (int) centerPosition.x - (width / 2);
        int y = (int) centerPosition.y - (height / 2);
        c.drawRect(
                x,
                y,
                width,
                height,
                paint
        );
    }

}
