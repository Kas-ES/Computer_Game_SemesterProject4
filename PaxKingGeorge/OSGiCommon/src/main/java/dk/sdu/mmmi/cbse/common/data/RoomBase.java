/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.services.IMappingInterface;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author marku
 */
public class RoomBase implements IMappingInterface {

    private final int roomID;
    private int eNorth, eSouth, eWest, eEast;
    private int posSize = 16;
    private String description;

    private ArrayList<Entity> contentTable;

    public RoomBase(int roomID) {
        this.roomID = roomID;
        this.contentTable = new ArrayList<>();
        this.description = "Room: " + this.roomID;
    }

    public RoomBase(int roomID, int eNorth, int eEast, int eSouth, int eWest) {
        this.roomID = roomID;
        this.contentTable = new ArrayList<>();
        this.eNorth = eNorth;
        this.eSouth = eSouth;
        this.eWest = eWest;
        this.eEast = eEast;
        this.description = "Room: " + this.roomID;

    }

    public RoomBase(int roomID, int eNorth, int eEast, int eSouth, int eWest, String description) {
        this.roomID = roomID;
        this.contentTable = new ArrayList<>();
        this.eNorth = eNorth;
        this.eSouth = eSouth;
        this.eWest = eWest;
        this.eEast = eEast;
        this.description = description + "\nRoom: " + this.roomID;
    }

    @Override
    public int getID() {
        return this.roomID;
    }

    public void createGrid(int mapElementSize, int roomHeight, int roomWidth) {
        for (int f = 0; f <= roomHeight; f += (mapElementSize * 2)) {
            for (int i = 0; i <= roomWidth; i += (mapElementSize * 2)) {
                this.contentTable.add(new MapElement(mapElementSize, i, f));
            }
        }
    }

    public int getPosSize() {
        return posSize;
    }

    public void setPosSize(int posSize) {
        this.posSize = posSize;
    }

    public ArrayList<Entity> getContentTable() {
        return contentTable;
    }

    public void setContentTable(ArrayList<Entity> contentTable) {
        this.contentTable = contentTable;
    }

    public int getRoomID() {
        return roomID;
    }

    public int geteNorth() {
        return eNorth;
    }

    public int geteSouth() {
        return eSouth;
    }

    public int geteWest() {
        return eWest;
    }

    public int geteEast() {
        return eEast;
    }

    public String getDescription() {
        return description;
    }

}
