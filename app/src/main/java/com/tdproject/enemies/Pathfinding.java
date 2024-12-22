package com.tdproject.enemies;

import static com.tdproject.main.FieldParameters.X_FIELDS;
import static com.tdproject.main.FieldParameters.Y_FIELDS;

import com.tdproject.gamestates.Playing;
import com.tdproject.main.Square;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Pathfinding {

    private static Pathfinding instance;
    private int[][] distanceField = new int[X_FIELDS][Y_FIELDS];
    private List<Square> shortestPath = new LinkedList<>();
    private int shortestPathLength;
    private final int blockedField = X_FIELDS * Y_FIELDS;
    private Square dest = new Square(24, 8);

    private Pathfinding() {
        buildDistanceField();
        //GameObjectList.pathfinding = this;
    }

    public static Pathfinding getInstance() {
        if (instance == null) {
            instance = new Pathfinding();
        }
        return instance;
    }

    public boolean buildDistanceField() {
        int[][] newField = new int[X_FIELDS][Y_FIELDS];
        for (int x = 0; x < X_FIELDS; x++) {
            for (int y = 0; y < Y_FIELDS; y++) {
                if (Playing.getInstance().getCollisionMap()[x][y]) {
                    newField[x][y] = blockedField;
                }
            }
        }

        Queue<Square> frontier = new ConcurrentLinkedQueue<>();
        frontier.add(dest);
        newField[dest.getX()][dest.getY()] = 1;

        while (!frontier.isEmpty()) {
            Square current = frontier.remove();
            if (current.getX() + 1 < X_FIELDS && newField[current.getX() + 1][current.getY()] == 0) {
                Square next = new Square(current.getX() + 1, current.getY());
                newField[next.getX()][next.getY()] = newField[current.getX()][current.getY()] + 1;
                frontier.add(next);
            }
            if (current.getY() + 1 < Y_FIELDS && newField[current.getX()][current.getY() + 1] == 0) {
                Square next = new Square(current.getX(), current.getY() + 1);
                newField[next.getX()][next.getY()] = newField[current.getX()][current.getY()] + 1;
                frontier.add(next);
            }
            if (current.getY() - 1 >= 0 && newField[current.getX()][current.getY() - 1] == 0) {
                Square next = new Square(current.getX(), current.getY() - 1);
                newField[next.getX()][next.getY()] = newField[current.getX()][current.getY()] + 1;
                frontier.add(next);
            }
            if (current.getX() - 1 >= 0 && newField[current.getX() - 1][current.getY()] == 0) {
                Square next = new Square(current.getX() - 1, current.getY());
                newField[next.getX()][next.getY()] = newField[current.getX()][current.getY()] + 1;
                frontier.add(next);
            }
        }

        //check if distanceField is valid
        // TODO allow holes but make sure no enemy is in there
        for (int x = 0; x < X_FIELDS; x++) {
            for (int y = 0; y < Y_FIELDS; y++) {
                if (newField[x][y] == 0) {
                    return false;
                }
            }
        }
        distanceField = newField;
        return true;
    }

    // Getters and setters
    public int[][] getDistanceField() {
        return distanceField;
    }
}
