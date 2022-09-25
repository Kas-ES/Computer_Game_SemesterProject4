package dk.sdu.mmmi.cbse.osgienemy;

import dk.sdu.mmmi.cbse.common.data.Direction;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameSprite;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.Enemy;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IAISPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import static java.lang.System.currentTimeMillis;
import java.util.HashMap;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

public class EnemyProcessor implements IEntityProcessingService {

    private HashMap<Direction, GameSprite> directionSprite = new HashMap<>();
    private float pi = 3.14159f;
    private long movementTimer = currentTimeMillis();
    private boolean startMovement = true;
    private IAISPI ai;
    private int counter = 0;

    public EnemyProcessor() {
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
        directionSprite.put(Direction.LEFT, spriteLEFT);
    }

    @Override
    public void process(GameData gameData, World world) {
        if (instantiateAIService() != null) {
            ai = instantiateAIService();
        }
        for (Entity entity : world.getEntities(Enemy.class)) {
            PositionPart positionPart = entity.getPart(PositionPart.class);
            MovingPart movingPart = entity.getPart(MovingPart.class);
            double random = Math.random();

            if (positionPart.getRadians() == pi) {
                entity.setGameSprite(directionSprite.get(Direction.LEFT));
            }
            if (positionPart.getRadians() == 0 || positionPart.getRadians() == 2 * pi) {
                entity.setGameSprite(directionSprite.get(Direction.RIGHT));
            }
            if (positionPart.getRadians() == pi * 3 / 2) {
                entity.setGameSprite(directionSprite.get(Direction.DOWN));
            }
            if (positionPart.getRadians() == pi / 2) {
                entity.setGameSprite(directionSprite.get(Direction.UP));
            }

            if (ai != null && counter % 10 == 0) {

                ai.getNextMove(world, gameData, (Enemy) entity);

            }

            counter++;
            movingPart.process(gameData, entity);
            positionPart.process(gameData, entity);
            updateShape(entity);
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

    public IAISPI instantiateAIService() {

        BundleContext context = FrameworkUtil.getBundle(IAISPI.class).getBundleContext();
        ServiceReference meleeWeaponSR = context.getServiceReference(IAISPI.class);
        ai = (IAISPI) context.getService(meleeWeaponSR);
        return ai;
    }
}
