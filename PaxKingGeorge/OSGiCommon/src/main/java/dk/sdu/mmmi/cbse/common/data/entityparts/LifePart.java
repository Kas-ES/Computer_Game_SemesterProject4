package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Player;
import static java.lang.System.currentTimeMillis;

public class LifePart implements EntityPart {

    private int life;
    private boolean isHit = false;
    private float expiration;
    private final long timeStart;

    public LifePart(int life, float expiration) {
        this.life = life;
        this.expiration = expiration * 1000;
        this.timeStart = currentTimeMillis();
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public boolean isIsHit() {
        return isHit;
    }

    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }

    public float getExpiration() {
        return expiration;
    }

    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }

    public void reduceExpiration(float delta) {
        this.expiration -= delta;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (!(entity instanceof Player) && !(entity instanceof Enemy) && (currentTimeMillis() - this.timeStart) > this.expiration) {
            setIsHit(true);
        }
    }
}
