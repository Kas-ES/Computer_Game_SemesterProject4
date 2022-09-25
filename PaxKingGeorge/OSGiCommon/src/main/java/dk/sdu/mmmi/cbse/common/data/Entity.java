package dk.sdu.mmmi.cbse.common.data;

import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Entity implements Serializable {

    private final UUID ID = UUID.randomUUID();

    private boolean canMove = true, isFriendly = true;
    private float collisionRadius = 50;
    private float[] shapeX = new float[4];
    private float[] shapeY = new float[4];
    private float radius;
    private Map<Class, EntityPart> parts;
    private GameSprite sprite;
    private HashMap<Direction, GameSprite> directionSprite = new HashMap<>();

    public Entity() {
        parts = new ConcurrentHashMap<>();
    }

    public GameSprite getGameSprite() {
        return sprite;
    }

    public void setGameSprite(GameSprite sprite) {
        this.sprite = sprite;
    }

    public void add(EntityPart part) {
        parts.put(part.getClass(), part);
    }

    public void remove(Class partClass) {
        parts.remove(partClass);
    }

    public <E extends EntityPart> E getPart(Class partClass) {
        return (E) parts.get(partClass);
    }

    public void setRadius(float r) {
        this.radius = r;
    }

    public float getRadius() {
        return radius;
    }

    public String getID() {
        return ID.toString();
    }

    public float[] getShapeX() {
        return shapeX;
    }

    public void setShapeX(float[] shapeX) {
        this.shapeX = shapeX;
    }

    public float[] getShapeY() {
        return shapeY;
    }

    public void setShapeY(float[] shapeY) {
        this.shapeY = shapeY;
    }

    public void setDirectionSprite(HashMap<Direction, GameSprite> directionSprite) {
        this.directionSprite = directionSprite;
    }

    public HashMap<Direction, GameSprite> getDirectionSprite() {
        return directionSprite;
    }

    public void setMoveTrue() {
        this.canMove = true;
    }

    public void setMoveFalse() {
        this.canMove = false;
    }

    public boolean getMove() {
        return this.canMove;
    }

    public boolean isFriendly() {
        if (this.isFriendly == true) {
            return true;
        }
        return false;
    }

    public boolean isEnemy() {
        if (this.isFriendly == false) {
            return true;
        }
        return false;
    }

    public void setCollisionRadius(float val) {
        this.collisionRadius = Math.abs(((float) Math.sqrt(val) + 15));
    }

    public float getCollisionRadius() {
        return this.collisionRadius;
    }

    public void setSideType(boolean val) {
        this.isFriendly = val;
    }
}
