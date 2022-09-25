package dk.sdu.mmmi.osgicollision;

import com.badlogic.gdx.Gdx;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.Weapon;
import dk.sdu.mmmi.cbse.common.data.Player;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.data.entityparts.Enemy;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.MapElementBlock;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;

public class CollisionController implements IEntityProcessingService {

    private World world;
    private GameData gameData;
    private final float hitPrecision = 1.1f;

    public CollisionController() {
    }

    private void collisionDetection() {
        for (Entity player : this.world.getEntities(Player.class)) {
            PositionPart pp = player.getPart(PositionPart.class);
            externalWallMovement(player);
            externalElementMovement(player);
            for (Entity val : world.getEntities(Weapon.class)) {
                PositionPart pew = val.getPart(PositionPart.class);
                if (Math.abs(pp.getX() - pew.getX()) <= getAveragePrecision(player.getCollisionRadius(), val.getCollisionRadius()) && Math.abs(pp.getY() - pew.getY()) <= getAveragePrecision(player.getCollisionRadius(), val.getCollisionRadius()) && val.isEnemy()) {
                    LifePart wep2 = val.getPart(LifePart.class);
                    wep2.setIsHit(true);
                    System.out.println("Player Hit!");
                }
            }
        }
        for (Entity enemy : this.world.getEntities(Enemy.class)) {
            PositionPart pp = enemy.getPart(PositionPart.class);
            externalWallMovement(enemy);
            externalElementMovement(enemy);
            for (Entity val : world.getEntities(Weapon.class)) {
                PositionPart pew = val.getPart(PositionPart.class);
                System.out.println(getAveragePrecision(enemy.getCollisionRadius(), val.getCollisionRadius()));
                if (Math.abs(pp.getX() - pew.getX()) <= getAveragePrecision(enemy.getCollisionRadius(), val.getCollisionRadius()) && Math.abs(pp.getY() - pew.getY()) <= getAveragePrecision(enemy.getCollisionRadius(), val.getCollisionRadius()) && val.isFriendly()) {
                    LifePart wep2 = val.getPart(LifePart.class);
                    wep2.setIsHit(true);
                    System.out.println("Enemy Hit!");
                }
            }
        }

        //Remove weapon on blocked Elements
        for (Entity walls : this.world.getEntities(MapElementBlock.class)) {
            PositionPart ww = walls.getPart(PositionPart.class);
            for (Entity weapon : this.world.getEntities(Weapon.class)) {
                PositionPart wep = weapon.getPart(PositionPart.class);
                if (Math.abs(ww.getX() - wep.getX()) <= getAveragePrecision(walls.getCollisionRadius(), weapon.getCollisionRadius()) && Math.abs(ww.getY() - wep.getY()) <= getAveragePrecision(walls.getCollisionRadius(), weapon.getCollisionRadius())) {
                    LifePart wep2 = weapon.getPart(LifePart.class);
                    wep2.setIsHit(true);
                }
            }
        }
    }

    //unused
    public void removeTimed() {
        for (Entity pew : this.world.getEntities(Weapon.class)) {
            LifePart pp = pew.getPart(LifePart.class);
            if (pp.isIsHit()) {
                this.world.removeEntity(pew);
                System.out.println("Bullet Removed");
            }
        }
    }

    public void externalWallMovement(Entity player) {
        PositionPart pp = player.getPart(PositionPart.class);
        if (pp.getX() < 0) {
            pp.setX((float) Math.sqrt(player.getCollisionRadius()));
        }
        if (pp.getX() > Gdx.graphics.getWidth()) {
            pp.setX(Gdx.graphics.getWidth() - (float) Math.sqrt(player.getCollisionRadius()));
        }
        if (pp.getY() > Gdx.graphics.getHeight()) {
            pp.setY(Gdx.graphics.getHeight() - (float) Math.sqrt(player.getCollisionRadius()));
        }
        if (pp.getY() < 0) {
            pp.setY((float) Math.sqrt(player.getCollisionRadius()));
        }
    }

    public void externalElementMovement(Entity entity) {
        PositionPart pp = entity.getPart(PositionPart.class);
        for (Entity val : world.getEntities(MapElementBlock.class)) {
            PositionPart ent = val.getPart(PositionPart.class);
            if (Math.abs(ent.getX() - pp.getX()) < val.getCollisionRadius() && Math.abs(ent.getY() - pp.getY()) < val.getCollisionRadius()) {
                pp.setX((ent.getX() + (pp.getX() - ent.getX()) * this.hitPrecision));
                pp.setY((ent.getY() + (pp.getY() - ent.getY()) * this.hitPrecision));
            }
        }
    }

    private float getAveragePrecision(float val1, float val2) {
        return (float) Math.sqrt(Math.pow(val1, 2) + Math.pow(val2, 2));
    }

    @Override
    public void process(GameData gameData, World world) {
        this.gameData = gameData;
        this.world = world;
        collisionDetection();
    }
}
