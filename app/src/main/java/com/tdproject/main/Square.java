package com.tdproject.main;

import static com.tdproject.main.FieldParameters.FIELD_SIZE;
import static com.tdproject.main.FieldParameters.X_CENTERED_OFFSET;
import static com.tdproject.main.FieldParameters.X_OFFSET;
import static com.tdproject.main.FieldParameters.Y_CENTERED_OFFSET;
import static com.tdproject.main.FieldParameters.Y_OFFSET;

import lombok.Getter;
import java.util.Objects;
import javax.vecmath.Vector2d;

@Getter
public class Square {

    //public Vector2d position;
    private int x;
    private int y;

    public Square(int xPos, int yPos) {
        x = xPos;
        y = yPos;
        //x = xPos;
        //y = yPos;
    }

    //public static Square[] getNeighbors(int x, int y) {
    public static Square[] getNeighbors(Vector2d position) {
        Square[] neighbors = new Square[2];
        //moving on x axis
        if (position.x % FIELD_SIZE == X_CENTERED_OFFSET % FIELD_SIZE) {
            neighbors[0] = positionToSquare(position);
            if (neighbors[0].squareToPosition().y < position.y) {
                neighbors[1] = new Square(neighbors[0].x, neighbors[0].y + 1);
                return neighbors;
            }
            else if (neighbors[0].squareToPosition().y > position.y) {
                neighbors[1] = new Square(neighbors[0].x, neighbors[0].y - 1);
                return neighbors;
            }
            else {
                throw new RuntimeException();
            }
        }
        //moving on y axis
        else if (position.y % FIELD_SIZE == Y_CENTERED_OFFSET % FIELD_SIZE) {
            neighbors[0] = positionToSquare(position);
            if (neighbors[0].squareToPosition().x < position.x) {
                neighbors[1] = new Square(neighbors[0].x + 1, neighbors[0].y);
                return neighbors;
            }
            else if (neighbors[0].squareToPosition().x > position.x) {
                neighbors[1] = new Square(neighbors[0].x - 1, neighbors[0].y);
                if (neighbors[1].x == 25) System.err.println(position);
                return neighbors;
            }
            else {
                throw new RuntimeException();
            }
        }
        throw new RuntimeException();
    }

    public static Square positionToSquare(Vector2d position) {
        int squareX = (int) (position.x - X_OFFSET) / FIELD_SIZE;
        int squareY = (int) (position.y - Y_OFFSET) / FIELD_SIZE;
        Square square = new Square(squareX, squareY);
        return square;
    }

    public Vector2d squareToPosition() {
        //return new int[] {(1 + x) * FIELD_SIZE, (1 + y) * FIELD_SIZE};
        return new Vector2d(x * FIELD_SIZE + X_CENTERED_OFFSET, y * FIELD_SIZE + Y_CENTERED_OFFSET);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return x == square.x && y == square.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
