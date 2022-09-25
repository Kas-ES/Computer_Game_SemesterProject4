package dk.sdu.mmmi.osgiplayertest;

import dk.sdu.mmmi.cbse.common.data.Direction;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.LEFT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.RIGHT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.SPACE;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.UP;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.DOWN;
import dk.sdu.mmmi.cbse.common.data.GameSprite;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.HitBoxPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.Player;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.osgicommonmeleeweapon.MeleeWeaponSPI;
import java.util.HashMap;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

public class PlayerControlSystem implements IEntityProcessingService {

    private MeleeWeaponSPI wp;
    private MeleeWeaponSPI meleeWeaponService;
    private HashMap<Direction, GameSprite> directionSprite = new HashMap<>();

    public PlayerControlSystem() {

        //Simple Sprites
        String playerPicREST = "RESTwhiteQueen";
        String playerPicDOWN = "DOWNwhiteQueen";
        String playerPicRIGHT = "RIGHTwhiteQueen";
        String playerPicLEFT = "LEFTwhiteQueen";
        String textureAtlasPath = "sprites.txt";

        GameSprite spriteUP = new GameSprite(playerPicREST, textureAtlasPath);
        GameSprite spriteDOWN = new GameSprite(playerPicDOWN, textureAtlasPath);
        GameSprite spriteRIGHT = new GameSprite(playerPicRIGHT, textureAtlasPath);
        GameSprite spriteLEFT = new GameSprite(playerPicLEFT, textureAtlasPath);

        directionSprite.put(Direction.UP, spriteUP);
        directionSprite.put(Direction.DOWN, spriteDOWN);
        directionSprite.put(Direction.RIGHT, spriteRIGHT);
        //directionSprite.put(Direction.LEFT, spriteLEFT);

        //Animation
        String restAnimationPath = "Hero/Idle/sprites.txt.";
        String runAnimationPath = "Hero/Run/sprites.txt.";

        GameSprite animationREST = new GameSprite(restAnimationPath);
        GameSprite animationRUN = new GameSprite(runAnimationPath);
        GameSprite animationRUNLeft = new GameSprite(runAnimationPath, Direction.LEFT);

        directionSprite.put(Direction.REST, animationREST);
        directionSprite.put(Direction.RUN, animationRUN);
        directionSprite.put(Direction.LEFT, animationRUNLeft);

    }

    @Override
    public void process(GameData gameData, World world) {

        if (meleeWeaponService == null) {
            try {
                this.meleeWeaponService = instantiateMeleeService(wp);
            } catch (Exception e) {
            }
        }

        for (Entity player : world.getEntities(Player.class)) {

            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);

            HitBoxPart hitBoxPart = player.getPart(HitBoxPart.class);
            LifePart lifePart = player.getPart(LifePart.class);

            if (gameData.getKeys().isDown(LEFT)) {
                //player.setGameSprite(directionSprite.get(Direction.LEFT));
                player.setGameSprite(directionSprite.get(Direction.LEFT));
            } else if (gameData.getKeys().isDown(RIGHT)) {
                //player.setGameSprite(directionSprite.get(Direction.RIGHT));
                player.setGameSprite(directionSprite.get(Direction.RUN));
            } else if (gameData.getKeys().isDown(UP)) {
                //player.setGameSprite(directionSprite.get(Direction.UP));
                player.setGameSprite(directionSprite.get(Direction.RUN));
            } else if (gameData.getKeys().isDown(DOWN)) {
                //player.setGameSprite(directionSprite.get(Direction.DOWN));
                player.setGameSprite(directionSprite.get(Direction.RUN));
            } else {
                player.setGameSprite(directionSprite.get(Direction.REST));
            }

            movingPart.setLeft(gameData.getKeys().isDown(LEFT));
            movingPart.setRight(gameData.getKeys().isDown(RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(UP));
            movingPart.setDown(gameData.getKeys().isDown(DOWN));

            movingPart.process(gameData, player);
            lifePart.process(gameData, player);
            positionPart.process(gameData, player);
            hitBoxPart.process(gameData, player);

            if (gameData.getKeys().isDown(GameKeys.SHIFT) && meleeWeaponService != null) {
                Entity bullet = meleeWeaponService.createMeleeWeapon(player, gameData);
                bullet.setCollisionRadius(40);
                bullet.setSideType(true);
                world.addEntity(bullet);
            }

            //updateShape(player);
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * 8);
        shapey[0] = (float) (y + Math.sin(radians) * 8);

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 8);
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * 8);

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 5);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 5);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 8);
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 8);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

    public void drawSprite() {

    }

    public MeleeWeaponSPI instantiateMeleeService(MeleeWeaponSPI weapon) {

        BundleContext context = FrameworkUtil.getBundle(MeleeWeaponSPI.class).getBundleContext();
        ServiceReference meleeWeaponSR = context.getServiceReference(MeleeWeaponSPI.class);
        weapon = (MeleeWeaponSPI) context.getService(meleeWeaponSR);
        return weapon;
    }
}
