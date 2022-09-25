/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.osgiai;

import com.badlogic.gdx.Gdx;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.MapElement;
import dk.sdu.mmmi.cbse.common.data.MapElementBlock;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import java.util.ArrayList;

/**
 *
 * @author marku
 */
public class Matrix {

    private Node[][] matrix;
    private float gridSize;
    private float precisionTolerance;
    private float[] size;
    private float height;
    private float width;
    private int cellWidth;
    private int cellHeight;
    private World world;

    public Matrix(World world, float gridSize, float precisionTolerance) {
        this.world = world;
        this.size = getRoomSize(world);
        this.gridSize = gridSize;
        setMovementGrid();
        this.precisionTolerance = precisionTolerance;

    }

    public Matrix(World world, float gridSize) {
        this.world = world;
        this.size = getRoomSize(world);
        this.gridSize = gridSize;
        setMovementGrid();
        this.precisionTolerance = this.size[0] / this.gridSize;

    }

    public Matrix(World world) {
        this.world = world;
        this.size = getRoomSize(world);
        this.gridSize = 100;
        setMovementGrid();
        this.precisionTolerance = this.size[0] / this.gridSize;

    }

    private void setMovementGrid() {

        this.width = this.size[0];
        this.height = this.size[1];
        this.cellWidth = (int) (width / this.gridSize);
        this.cellHeight = (int) (height / this.gridSize);
        this.matrix = new Node[((int) (this.gridSize))][(int) (this.gridSize)];
        System.out.println(this.world == null);

        for (int i1 = 0; i1 < ((int) (this.gridSize)); i1++) {
            for (int i2 = 0; i2 < (int) (this.gridSize); i2++) {
                Node node = new Node(i1, i2, i1 * cellWidth + (int) (cellWidth / 2), i2 * cellHeight + (int) (cellHeight / 2));
                this.matrix[i1][i2] = node;
                node.setWall(validatePosition(node.getX(), node.getY(), this.world));
                System.out.println(node.getX() + " " + node.getY() + " " + node.isWall());

            }
        }
        for (Node[] matrix1 : this.matrix) {
            for (Node matrix11 : matrix1) {
                matrix11.addNeighbours(this.matrix);
            }
        }
        printGrid();
    }

    private float[] getRoomSize(World world) {
        ArrayList<Entity> elements = new ArrayList<>();
        elements.addAll(world.getEntities(MapElement.class));
        elements.addAll(world.getEntities(MapElementBlock.class));

        float xMin = Gdx.graphics.getWidth() + 1;
        float xMax = -1;
        float yMin = Gdx.graphics.getHeight() + 1;
        float yMax = -1;
        for (Entity entity : elements) {
            for (float i : entity.getShapeX()) {
                if (i > xMax) {
                    xMax = i;
                }
                if (i < xMin) {
                    xMin = i;
                }
            }
            for (float i : entity.getShapeY()) {
                if (i > yMax) {
                    yMax = i;
                }
                if (i < yMin) {
                    yMin = i;
                }
            }
        }
        float[] result = new float[2];
        result[0] = xMax - xMin;
        result[1] = yMax - yMin;
        elements.clear();
        return result;

    }

    public Node getEntityMatrixPosition(World world, Entity entity, float precisionTolerance) {

        PositionPart positionPart = entity.getPart(PositionPart.class);
        if (positionPart != null) {
            float[] cords = new float[]{positionPart.getX(), positionPart.getY()};
            for (Node[] matrix1 : matrix) {
                for (Node po : matrix1) {
                    if (po.getX() - precisionTolerance < cords[0]
                            && po.getX() + precisionTolerance > cords[0]
                            && po.getY() - precisionTolerance < cords[1]
                            && po.getY() + precisionTolerance > cords[1]) {
                        return po;
                    }
                }
            }
        }

        return null;

    }

    public Node[][] getMatrix() {
        return matrix;
    }

    public float getGridSize() {
        return gridSize;
    }

    public void setGridSize(float gridSize) {
        this.gridSize = gridSize;
        setMovementGrid();
    }

    public float getPrecisionTolerance() {
        return precisionTolerance;
    }

    public void setPrecisionTolerance(float precisionTolerance) {
        this.precisionTolerance = precisionTolerance;

    }

    public float[] getSize() {
        return size;
    }

    public void setSize(float[] size) {
        this.size = size;

    }

    public Node getNode(int i, int j) {
        return this.matrix[i][j];
    }

    public void clearParents() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                this.matrix[i][j].setParent(null);
            }
        }

    }

    private boolean validatePosition(float posX, float posY, World world) {
        for (Entity val : world.getEntities(MapElementBlock.class)) {
            PositionPart ent = val.getPart(PositionPart.class);
            //if (ent != null && Math.abs(posX - ent.getX()) <= val.getCollisionRadius() + this.gridSize && Math.abs(posY - ent.getY()) <= val.getCollisionRadius() + this.gridSize) {
            if (ent != null && Math.abs(posX - ent.getX()) <= val.getCollisionRadius() && Math.abs(posY - ent.getY()) <= val.getCollisionRadius()) {

                return true;
            }
        }
        return false;
    }

    public void printGrid() {
        String temp = "|";

        for (Node[] nodes : this.matrix) {
            temp = "|";
            for (Node node : nodes) {
                if (validatePosition(node.getX(), node.getY(), this.world)) {
                    //this.matrix[i1][i2] = new PositionObject(i1 * this.gridSize, i2 * this.gridSize, this.gridSize, this.gridSize);
                    temp += "X|";
                } else {
                    temp += "-|";
                }

            }
            System.out.println(temp);
        }
        System.out.println("\n\n");
        /*
        for (int i1 = 0; i1 < ((int) (Gdx.graphics.getWidth() / this.gridSize)); i1++) {
            temp = "|";
            for (int i2 = 0; i2 < (int) (Gdx.graphics.getHeight() / this.gridSize); i2++) {
                if (validatePosition(i1 * this.gridSize, i2 * this.gridSize, this.world)) {
                    //this.matrix[i1][i2] = new PositionObject(i1 * this.gridSize, i2 * this.gridSize, this.gridSize, this.gridSize);
                    temp += "X|";
                } else {
                    temp += "-|";
                }
            }
            System.out.println(temp);
        }
        System.out.println("\n\n");
         */
    }

}
