/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.osgiai;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marku
 */
public class Node {

    private final int col;
    private final int row;
    private Node parent;
    private float x, y;
    private double h, f, g;
    private boolean wall;

    private List<Node> neighbours;

    public Node(int col, int row, float x, float y) {
        this.col = col;
        this.row = row;
        this.x = x;
        this.y = y;
        neighbours = new ArrayList<>();
        this.g = 0;
        this.f = 0;
        this.h = 0;
        this.parent = null;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public float getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    public List<Node> getPath() {
        Node currentNode = this;
        List<Node> path = new ArrayList<>();
        path.add(currentNode);

        while (currentNode != null) {
            currentNode = currentNode.getParent();
            path.add(currentNode);
        }
        return null;
    }

    public int getTotalCost() {
        int cost = 0;
        for (Node node : getPath()) {
            cost += node.getG();
        }
        return cost;
    }

    public Node getParent() {
        return parent;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public List<Node> getNeighbours() {
        return neighbours;
    }

    public void addNeighbours(Node[][] grid) {
        if (this.col - 1 > 0 && !grid[this.col - 1][this.row].isWall()) {
            //left neighbour
            this.neighbours.add(grid[this.col - 1][this.row]);
        }
        if (this.row - 1 > 0 && !grid[this.col][this.row - 1].isWall()) {
            // down neighbour
            this.neighbours.add(grid[this.col][this.row - 1]);
        }
        if (this.row + 1 < grid[this.col].length && !grid[this.col][this.row + 1].isWall()) {
            // up neighbour
            this.neighbours.add(grid[this.col][this.row + 1]);
        }
        if (this.col + 1 < grid.length && !grid[this.col + 1][this.row].isWall()) {
            //right neighbour
            this.neighbours.add(grid[this.col + 1][this.row]);
        }
        if (this.col + 1 < grid.length && this.row + 1 < grid[this.col].length && !grid[this.col + 1][this.row + 1].isWall()) {
            // right up
            this.neighbours.add(grid[this.col + 1][this.row + 1]);
        }
        if (this.col + 1 < grid.length && this.row - 1 > 0 && !grid[this.col + 1][this.row - 1].isWall()) {
            //right down
            this.neighbours.add(grid[this.col + 1][this.row - 1]);
        }
        if (this.row + 1 < grid[this.col].length && this.col - 1 > 0 && !grid[this.col - 1][this.row + 1].isWall()) {
            //left up
            this.neighbours.add(grid[this.col - 1][this.row + 1]);
        }
        if (this.row - 1 > 0 && this.col - 1 > 0 && this.col - 1 > 0 && !grid[this.col - 1][this.row - 1].isWall()) {
            //left down
            this.neighbours.add(grid[this.col - 1][this.row - 1]);
        }

    }

    public boolean isWall() {
        return wall;
    }

    public void setWall(boolean wall) {
        this.wall = wall;
    }

}
