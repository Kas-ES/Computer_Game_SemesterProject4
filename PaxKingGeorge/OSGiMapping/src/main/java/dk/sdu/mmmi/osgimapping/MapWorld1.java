package dk.sdu.mmmi.osgimapping;

import dk.sdu.mmmi.osgimapping.building.Castle;
import dk.sdu.mmmi.cbse.common.data.entityparts.MapElementBlock;
import dk.sdu.mmmi.cbse.common.data.Player;
import dk.sdu.mmmi.cbse.common.services.IMappingInterface;
import dk.sdu.mmmi.osgimapping.building.WallHorizontal;
import dk.sdu.mmmi.osgimapping.building.WallVertical;
import static java.lang.System.currentTimeMillis;
import java.util.HashMap;

public class MapWorld1 implements IMappingInterface {

    private HashMap<Integer, Room> rooms;
    private Room currentRoom;
    private int mapID;
    private long lockoutTime = 3000;
    private long currentTimePassed = currentTimeMillis();

    public MapWorld1(int mapID) {
        this.rooms = new HashMap<>();

        //ID, North, East, South, East, Description
        //Create rooms
        Room room1 = new Room(1, -1, 2, 3, -1, "Sunny Wildlands");
        this.rooms.put(1, room1);
        new Castle(room1, 100, 250, 250, true, false, false, false);
        new Castle(room1, 60, 500, 500, true, false, true, false);
        new WallVertical(room1, 100, 400, 400);
        new WallHorizontal(room1, 100, 100, 100);

        Room room2 = new Room(2, -1, -1, 4, 1, "E. Sunny Wildlands");
        this.rooms.put(2, room2);

        Room room3 = new Room(3, 1, 4, -1, -1, "S. Sunny Wildlands");
        this.rooms.put(3, room3);

        Room room4 = new Room(4, 2, -1, 5, 3, "SE. Sunny Wildlands");
        this.rooms.put(4, room4);

        Room room5 = new Room(5, 4, 7, 6, -1, "S. Wildlands");
        this.rooms.put(5, room5);

        Room room6 = new Room(6, 5, -1, -1, -1, "Deep Wildlands");
        this.rooms.put(6, room6);

        Room room7 = new Room(7, -1, 12, -1, 5, "Path to the Forest");
        this.rooms.put(7, room7);

        Room room12 = new Room(12, 10, 13, 14, 7, "Great Forest");
        this.rooms.put(12, room12);

        Room room14 = new Room(14, 12, -1, -1, -1, "Edge of the Green");
        this.rooms.put(14, room14);

        Room room10 = new Room(10, -1, 11, 12, -1, "N. Great Forest");
        this.rooms.put(10, room10);

        Room room8 = new Room(8, -1, 9, -1, -1, "Hidden End, Great forest");
        this.rooms.put(8, room8);

        Room room9 = new Room(9, -1, -1, 11, 8, "Northern Great Forest");
        this.rooms.put(9, room9);

        Room room11 = new Room(11, 9, 15, 13, 10, "Entry to the Empire");
        this.rooms.put(11, room11);

        Room room13 = new Room(13, 11, -1, -1, 12, "Southern Great Forest");
        this.rooms.put(13, room13);

        Room room15 = new Room(15, -1, 16, -1, 11, "Early Road to the Empire");
        this.rooms.put(15, room15);

        Room room16 = new Room(16, -1, 19, -1, 15, "Road to the Empire");
        this.rooms.put(16, room16);

        Room room19 = new Room(19, 17, 20, 21, 16, "Castle of the King");
        this.rooms.put(19, room19);

        Room room17 = new Room(17, -1, 18, 19, -1, "View of the Empire");
        this.rooms.put(17, room17);

        Room room18 = new Room(18, -1, -1, 20, 17, "Chamber of Treasures");
        this.rooms.put(18, room18);

        Room room20 = new Room(20, 18, 23, 22, 19, "Throne room Entrance");
        this.rooms.put(20, room20);

        Room room22 = new Room(22, 20, -1, -1, 21, "Torture Chamber");
        this.rooms.put(22, room22);

        Room room21 = new Room(21, 19, 22, -1, -1, "Southern view of the Empire");
        this.rooms.put(21, room21);

        Room room23 = new Room(23, -1, 24, -1, 20, "Path to the Throne");
        this.rooms.put(23, room23);

        Room room24 = new Room(24, -1, -1, -1, 23, "Throne Room");
        this.rooms.put(24, room24);

        setStartRoom();
    }

    //Sets a beginning room, first one in the hashmap
    private void setStartRoom() {
        if (this.rooms.isEmpty()) {
            this.rooms.put(1, new Room(1, -1, -1, -1, -1, "Default Room"));
        }
        for (Integer key : this.rooms.keySet()) {
            this.currentRoom = this.rooms.get(key);
            this.mapID = key;
            break;
        }
    }

    @Override
    public int getID() {
        return this.mapID;
    }

    public void updateRoom(Player player) {
        if (setTimer() && this.currentRoom.playerAtEntryPoint(player)) {
            this.currentRoom.setPlayerPosition(player);
            this.currentRoom = this.rooms.get(this.currentRoom.getCurrentKey());
            this.currentTimePassed = currentTimeMillis();
        }
    }

    private boolean setTimer() {
        if ((currentTimeMillis() - this.currentTimePassed) > this.lockoutTime) {
            return true;
        }
        return false;
    }

    public Room getCurrentRoom() {
        return this.currentRoom;
    }

}
