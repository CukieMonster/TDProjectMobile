package com.tdproject.graphics;

import java.util.Arrays;
import java.util.Comparator;

public class TextPanel extends GeneralPanel<Text> {

    public TextPanel(int xCenterPos, int yCenterPos, int width, int height, Text[] content) {
        super(xCenterPos, yCenterPos, width, height, content);
    }

    @Override
    protected void setPosition(Text item, int x, int y) {
        item.setX((int) centerPosition.x - (width / 2));
        item.setY(y);
    }

    @Override
    protected int getMaxWidth() {
        return 0;
    }

    @Override
    protected int getMaxHeight() {
        return Arrays.stream(content).max(Comparator.comparingInt(Text::getFontSize)).get().getFontSize();
    }

    @Override
    protected void drawContent(Object o, Text item) {
        item.draw(o);
    }

}
