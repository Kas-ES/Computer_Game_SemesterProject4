package dk.sdu.mmmi.osgimapping;

import dk.sdu.mmmi.cbse.common.data.MapElement;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.Player;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class MapController implements IEntityProcessingService {

    private GameData gamedata;
    private World world;
    private MapWorld1 mapworld;
    private Player player;
    private int mapID = 0;

    public MapController() {
        this.mapworld = new MapWorld1(0);
    }

    public void update() {
        setPlayer();
        this.mapworld.updateRoom(this.player);
        if (this.mapworld.getCurrentRoom().getID() != this.mapID) {
            this.mapID = this.mapworld.getCurrentRoom().getID();
            printProp();
            loadEntities();
        } else if (this.mapID == 0) {
            this.mapID = this.mapworld.getCurrentRoom().getID();
            printProp();
            loadEntities();
        }
    }

    private void printProp() {
        System.out.println(this.mapworld.getCurrentRoom().getDescription());
    }

    private void loadEntities() {
        removeEntities();
        for (Entity val : this.mapworld.getCurrentRoom().getContentTable()) {
            this.world.addEntity(val);
        }
        for (Entity val : this.mapworld.getCurrentRoom().getBlockedTable()) {
            this.world.addEntity(val);
        }
    }

    private void removeEntities() {
        for (Entity val : world.getEntities()) {
            if (val instanceof MapElement) {
                this.world.removeEntity(val);
            }
        }
    }

    private void setPlayer() {
        if (this.player == null) {
            for (Entity val : world.getEntities()) {
                if (val instanceof Player) {
                    this.player = (Player) val;
                    break;
                }
            }
        }
    }

    @Override
    public void process(GameData gameData, World world) {
        this.gamedata = gameData;
        this.world = world;
        update();
    }
}
