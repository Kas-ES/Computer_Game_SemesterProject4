package dk.sdu.mmmi.osgimapping.building;
import dk.sdu.mmmi.osgimapping.Room;

public final class Castle{
    private int size = 60;
    public Castle(Room room, int size, float pX, float pY, boolean entryN, boolean entryE, boolean entryS, boolean entryW){
        if(size > this.size){
            this.size = size;
        }
        new WallHorizontal(room, this.size, pX, pY, entryS);
        new WallVertical(room, this.size, pX, pY, entryW);
        new WallHorizontal(room, this.size, pX, pY+size, entryN);
        new WallVertical(room, this.size, pX+size, pY, entryE);
    }

}
