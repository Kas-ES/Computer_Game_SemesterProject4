package dk.sdu.mmmi.osgimapping;

import com.badlogic.gdx.Gdx;
import dk.sdu.mmmi.cbse.common.data.MapElement;
import dk.sdu.mmmi.cbse.common.data.MapElementBlock;
import dk.sdu.mmmi.cbse.common.data.Player;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IMappingInterface;
import dk.sdu.mmmi.cbse.common.data.RoomBase;
import java.util.ArrayList;
import java.util.HashMap;

public class Room extends RoomBase implements IMappingInterface {

    private final int doorWidth = 20, wallSesitivity = 5;
    private int currentMoveKey = 0;
    private HashMap<Integer, Object> blockedEntries;
    private ArrayList<MapElementBlock> blockedPaths;

    //Temporary list. Will be removed once players got an inventory
    private ArrayList<Integer> playerKeys;

    public Room(int roomID) {
        super(roomID);

        this.blockedPaths = new ArrayList<>();
        this.blockedEntries = new HashMap<>();

        //Temporary Keys, MASTER key
        this.playerKeys = new ArrayList<>();
        this.playerKeys.add(42069);
        setWallLogic();
    }

    public Room(int roomID, int eNorth, int eEast, int eSouth, int eWest) {
        super(roomID, eNorth, eEast, eSouth, eWest);

        this.blockedEntries = new HashMap<>();
        this.blockedPaths = new ArrayList<>();

        //Temporary Keys, MASTER key
        this.playerKeys = new ArrayList<>();
        this.playerKeys.add(42069);
        setWallLogic();
    }

    public Room(int roomID, int eNorth, int eEast, int eSouth, int eWest, String description) {
        super(roomID, eNorth, eEast, eSouth, eWest, description);
        this.blockedEntries = new HashMap<>();
        this.blockedPaths = new ArrayList<>();

        //Temporary Keys, MASTER key
        this.playerKeys = new ArrayList<>();
        this.playerKeys.add(42069);
        setWallLogic();
    }

    private void setWallLogic() {
        for (int f = 0; f <= Gdx.graphics.getHeight(); f += (super.getPosSize() * 2)) {
            for (int i = 0; i <= Gdx.graphics.getWidth(); i += (super.getPosSize() * 2)) {
                //this.contentTable.add(createMapElementSpecial(this.posSize, i, f));
            }
        }
        //Create Top and Bottom Wall and Doors
        for (int k = 0; k <= Gdx.graphics.getWidth(); k += (8)) {
            //Walls
            if (!(this.geteSouth() > 0 && Math.abs((Gdx.graphics.getWidth() / 2) - k) < doorWidth)) {
                MapElement m = createMapElementSpecial(k, 0);
                this.getContentTable().add(m);
            }
            if (!(super.geteNorth() > 0 && Math.abs(((Gdx.graphics.getWidth() / 2) - k)) < doorWidth)) {
                MapElement m = createMapElementSpecial(k, Gdx.graphics.getHeight());
                this.getContentTable().add(m);
            }

            //Doors
            if (this.geteNorth() > 0) {
                MapElement val = createMapElementSpecial(doorWidth, (Gdx.graphics.getWidth() / 2), Gdx.graphics.getHeight());
                this.blockedEntries.put(this.geteNorth(), val);
                this.getContentTable().add(val);
            }
            if (this.geteSouth() > 0) {
                MapElement val = createMapElementSpecial(doorWidth, (Gdx.graphics.getWidth() / 2), 0);
                this.blockedEntries.put(this.geteSouth(), val);
                this.getContentTable().add(val);
            }
        }
        //Create Left and Right Wall & Doors
        for (int k = 0; k <= Gdx.graphics.getHeight(); k += (8)) {

            //Sets Walls
            if (!(this.geteWest() > 0 && Math.abs(((Gdx.graphics.getHeight() / 2) - k)) < doorWidth)) {
                MapElement m = createMapElementSpecial(0, k);
                this.getContentTable().add(m);
            }
            if (!(this.geteEast() > 0 && Math.abs((Gdx.graphics.getHeight() / 2) - k) < doorWidth)) {
                MapElement m = createMapElementSpecial(Gdx.graphics.getWidth(), k);
                this.getContentTable().add(m);
            }

            //Sets Doors
            if (this.geteWest() > 0) {
                MapElement val = createMapElementSpecial(doorWidth, 0, Gdx.graphics.getHeight() / 2);
                this.blockedEntries.put(this.geteWest(), val);
                this.getContentTable().add(val);
            }
            if (this.geteEast() > 0) {
                MapElement val = createMapElementSpecial(doorWidth, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 2);
                this.blockedEntries.put(this.geteEast(), val);
                this.getContentTable().add(val);
            }
        }
    }

    //Position map elements with given position, fixed size
    private MapElement createMapElementSpecial(int valX, int valY) {
        MapElement map = new MapElement(valX, valY);
        map.add(new PositionPart(valX, valY, 0));
        return map;
    }

    //Position map elements with given position, changeable size
    private MapElement createMapElementSpecial(int size, int valX, int valY) {
        MapElement map = new MapElement(size, valX, valY);
        return map;
    }

    public MapElementBlock createMapElementSpecialB(int valX, int valY) {
        int fixedSize = 8;
        MapElementBlock map = new MapElementBlock(valX, valY);
        map.add(new PositionPart(valX, valY, 1));
        map.setCollisionRadius(fixedSize);
        float[] shapex = new float[4];
        float[] shapey = new float[4];
        shapex[0] = (float) (valX - fixedSize);
        shapey[0] = (float) (valY - fixedSize);
        shapex[1] = (float) (valX + fixedSize);
        shapey[1] = (float) (valY - fixedSize);
        shapex[2] = (float) (valX + fixedSize);
        shapey[2] = (float) (valY + fixedSize);
        shapex[3] = (float) (valX - fixedSize);
        shapey[3] = (float) (valY + fixedSize);
        map.setShapeX(shapex);
        map.setShapeY(shapey);
        this.blockedPaths.add(map);
        return map;
    }

    //Validate whether a player has a valid key to enter current entry door or not
    private boolean gotValidEntryKey(int doorID) {
        if (this.playerKeys.contains(doorID) || this.playerKeys.contains(42069) && doorID != -1) {
            return true;
        }
        return false;
    }

    //Validates whether a player is at a valid entry point on not
    public boolean playerAtEntryPoint(Player player) {
        PositionPart pp = player.getPart(PositionPart.class);
        //North Entry Validation
        if ((Math.abs(Gdx.graphics.getWidth() / 2 - pp.getX()) <= this.doorWidth) && (Math.abs(Gdx.graphics.getHeight() - pp.getY()) <= this.doorWidth) && gotValidEntryKey(this.geteNorth())) {
            this.currentMoveKey = this.geteNorth();
            return true;
        }
        //East Entry Validation
        if ((Math.abs(Gdx.graphics.getWidth() - pp.getX()) <= this.doorWidth) && (Math.abs((Gdx.graphics.getHeight() / 2) - pp.getY()) <= this.doorWidth) && gotValidEntryKey(this.geteEast())) {
            this.currentMoveKey = this.geteEast();
            return true;
        }
        //South Entry Validation
        if ((Math.abs(Gdx.graphics.getWidth() / 2 - pp.getX()) <= this.doorWidth) && (Math.abs(0 - pp.getY()) <= this.doorWidth) && gotValidEntryKey(this.geteSouth())) {
            this.currentMoveKey = this.geteSouth();
            return true;
        }
        //West Entry Validation
        if ((Math.abs(0 - pp.getX()) <= this.doorWidth) && (Math.abs((Gdx.graphics.getHeight() / 2) - pp.getY()) <= this.doorWidth) && gotValidEntryKey(this.geteWest())) {
            this.currentMoveKey = this.geteWest();
            return true;
        }
        this.currentMoveKey = 0;
        return false;
    }

    public void setPlayerPosition(Player player) {
        //North Entry Validation N, E, S, W
        PositionPart pp = player.getPart(PositionPart.class);
        if ((Math.abs(Gdx.graphics.getWidth() / 2 - pp.getX()) <= this.doorWidth * 2) && (Math.abs(Gdx.graphics.getHeight() - pp.getY()) <= this.doorWidth * 2)) {
            pp.setX((Gdx.graphics.getWidth() / 2));
            pp.setY(this.doorWidth / 2);
        } else if ((Math.abs(Gdx.graphics.getWidth() - pp.getX()) <= this.doorWidth * 2) && (Math.abs((Gdx.graphics.getHeight() / 2) - pp.getY()) <= this.doorWidth * 2)) {
            pp.setX(this.doorWidth / 2);
            pp.setY(Gdx.graphics.getHeight() / 2);
        } else if ((Math.abs(Gdx.graphics.getWidth() / 2 - pp.getX()) <= this.doorWidth * 2) && (Math.abs(0 - pp.getY()) <= this.doorWidth * 2)) {
            pp.setX(Gdx.graphics.getWidth() / 2);
            pp.setY(Gdx.graphics.getHeight() - this.doorWidth / 2);
        } else if ((Math.abs(0 - pp.getX()) <= this.doorWidth * 2) && (Math.abs((Gdx.graphics.getHeight() / 2) - pp.getY()) <= this.doorWidth * 2)) {
            pp.setX(Gdx.graphics.getWidth() - this.doorWidth / 2);
            pp.setY(Gdx.graphics.getHeight() / 2);
        }
    }

    @Override
    public int getID() {
        return this.getRoomID();
    }

    public ArrayList<MapElementBlock> getBlockedTable() {
        return this.blockedPaths;
    }

    public void addBlockedTable(MapElementBlock val) {
        this.blockedPaths.add(val);
    }

    public int getCurrentKey() {
        return this.currentMoveKey;
    }
}
