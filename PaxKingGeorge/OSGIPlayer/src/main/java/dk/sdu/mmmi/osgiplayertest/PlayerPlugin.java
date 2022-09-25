package dk.sdu.mmmi.osgiplayertest;

import com.badlogic.gdx.Gdx;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.Player;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameSprite;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.HitBoxPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class PlayerPlugin implements IGamePluginService {

    private String playerID;
    private Entity playerShip;

    @Override
    public void start(GameData gameData, World world) {
        // Add entities to the world
        Entity player = createPlayer(gameData);
        playerID = world.addEntity(player);
    }

    private Entity createPlayer(GameData gameData) {
        //player attributes
        float speed = 300;
        float x = Gdx.graphics.getWidth() / 2;
        float y = Gdx.graphics.getHeight() / 2;

        float radians = 3.1415f / 2;
        float[] colors = {0f, 255f, 0f, 1f};

        //bullet attributes
        float bulletDeacceleration = 0;
        float bulletAcceleration = speed * 2f;
        float bulletMaxSpeed = 500;

        //making sprite
        String playerPicREST = "RESTwhiteQueen";
        String textureAtlasPath = "sprites.txt";

        GameSprite spriteUP = new GameSprite(playerPicREST, textureAtlasPath);

        playerShip = new Player();
        //setting the sprite to Entity
        if (spriteUP != null) {
            playerShip.setGameSprite(spriteUP);
        } else {
            System.out.println("sprite is null in playerplugin");
        }
        Entity playerShip = new Player();
        playerShip.setSideType(true);

        //playerShip.setColorRgba(colors);
        playerShip.add(new MovingPart(10, 200, 300, 5));
        playerShip.add(new PositionPart(x, y, radians));

        playerShip.add(new LifePart(1, 1));
        HitBoxPart hitBoxPart = new HitBoxPart(9, 9, x, y, "player");
        hitBoxPart.addIgnore("bullet");
        playerShip.add(hitBoxPart);

        return playerShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(playerID);
    }

}
