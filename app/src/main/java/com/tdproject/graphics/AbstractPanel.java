package com.tdproject.graphics;

import javax.vecmath.Vector2d;
import lombok.Getter;

public abstract class AbstractPanel<C> {

    protected final Vector2d centerPosition;
    protected final int width;
    protected final int height;
    @Getter
    protected final C[] content;
    private final int columns;

    public AbstractPanel(int xCenterPos, int yCenterPos, int width, int height, C[] content) {
        centerPosition = new Vector2d(xCenterPos, yCenterPos);
        this.width = width;
        this.height = height;
        this.content = content;
        this.columns = 1;
        int[] xPositions = calculateItemXPositions();
        int[] yPositions = calculateItemYPositions();
        for (int i = 0; i < content.length; i++) {
            setPosition(content[i], xPositions[i], yPositions[i]);
        }
    }

    public AbstractPanel(int xCenterPos, int yCenterPos, int width, int height, C[] content, int columns) {
        centerPosition = new Vector2d(xCenterPos, yCenterPos);
        this.width = width;
        this.height = height;
        this.content = content;
        this.columns = columns;
        int[] xPositions = calculateItemXPositions();
        int[] yPositions = calculateItemYPositions();
        for (int i = 0; i < content.length; i++) {
            setPosition(content[i], xPositions[i], yPositions[i]);
        }
    }

    abstract protected void setPosition(C item, int x, int y);

    private int[] calculateItemXPositions() {
        // TODO is this method necessary?
        return calculateItemXPositions(content.length, columns, getMaxWidth());
    }

    abstract protected int getMaxWidth();

    abstract protected int getMaxHeight();

    private int[] calculateItemXPositions(int buttonsAmount, int columns, int maxWidth) {
        int[] result = new int[buttonsAmount];

        int xGap = (width - (maxWidth * columns)) / (columns + 1);
        if (xGap < 0) {
            xGap = 0;
        }
        int currentXPos = (int) centerPosition.x - (width / 2) + (maxWidth / 2);
        for (int i = 0; i < columns; i++) {
            currentXPos += xGap;
            for (int j = i; j < buttonsAmount; j += columns) {
                result[j] = currentXPos;
            }
            currentXPos += maxWidth;
        }
        return result;
    }

    private int[] calculateItemYPositions() {
        int buttonsAmount = content.length;
        int maxHeight = getMaxHeight();
        int[] result = new int[buttonsAmount];
        int rows = Math.round(Math.round((double) buttonsAmount / columns));

        int yGap = (height - (maxHeight * rows)) / (rows + 1);
        if (yGap < 0) {
            yGap = 0;
        }
        int currentYPos = (int) centerPosition.y - (height / 2) + (maxHeight / 2);
        for (int i = 0; i < buttonsAmount; i+= columns) {
            currentYPos += yGap;
            for (int j = i; j < i + columns; j++) {
                if (j >= buttonsAmount) {
                    break;
                }
                result[j] = currentYPos;
            }
            currentYPos += maxHeight;
        }
        return result;
    }

    abstract public void draw(Object o);

    abstract protected void drawContent(Object o, C item);

}
