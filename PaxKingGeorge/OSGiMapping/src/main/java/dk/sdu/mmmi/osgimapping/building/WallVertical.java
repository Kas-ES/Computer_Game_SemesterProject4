package dk.sdu.mmmi.osgimapping.building;

import dk.sdu.mmmi.cbse.common.data.MapElementBlock;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.osgimapping.Room;
import java.util.ArrayList;

public class WallVertical {

    private ArrayList<MapElementBlock> blockedPaths = new ArrayList<MapElementBlock>();
    private int boxSize = 10;

    public WallVertical(Room room, int length, float pX, float pY) {
        if (length <= 10) {
            this.boxSize = 1;
        } else {
            this.boxSize = (int) length / 10;
        }
        for (int val = 0; val <= length; val += this.boxSize) {
            this.blockedPaths.add(createMapElementSpecialB(pX, pY + val));
        }

        for (MapElementBlock val : this.blockedPaths) {
            room.getBlockedTable().add(val);
        }
    }

    public WallVertical(Room room, int length, float pX, float pY, boolean entry) {
        if (length <= 10) {
            this.boxSize = 1;
        } else {
            this.boxSize = (int) length / 10;
        }
        for (int val = 0; val <= length; val += this.boxSize) {
            if (!((Math.abs((length / 2) - val)) <= boxSize * 2) && entry) {
                this.blockedPaths.add(createMapElementSpecialB(pX, pY + val));
            }
            if (entry == false) {
                this.blockedPaths.add(createMapElementSpecialB(pX, pY + val));
            }
        }

        for (MapElementBlock val : this.blockedPaths) {
            room.getBlockedTable().add(val);
        }
    }

    public MapElementBlock createMapElementSpecialB(float valX, float valY) {
        MapElementBlock map = new MapElementBlock((int) valX, (int) valY);
        map.add(new PositionPart(valX, valY, 1));
        map.setCollisionRadius(this.boxSize);
        float[] shapex = new float[4];
        float[] shapey = new float[4];
        shapex[0] = (float) (valX - this.boxSize);
        shapey[0] = (float) (valY - this.boxSize);
        shapex[1] = (float) (valX + this.boxSize);
        shapey[1] = (float) (valY - this.boxSize);
        shapex[2] = (float) (valX + this.boxSize);
        shapey[2] = (float) (valY + this.boxSize);
        shapex[3] = (float) (valX - this.boxSize);
        shapey[3] = (float) (valY + this.boxSize);
        map.setShapeX(shapex);
        map.setShapeY(shapey);
        this.blockedPaths.add(map);
        return map;
    }
}
