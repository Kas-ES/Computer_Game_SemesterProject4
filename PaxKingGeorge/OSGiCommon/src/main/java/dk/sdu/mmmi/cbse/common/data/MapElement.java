package dk.sdu.mmmi.cbse.common.data;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;

public class MapElement extends Entity {

    private int size;
    private Entity containedItem;
    private int defaultSize = 8;
    private boolean blocked = false;

    public MapElement(int size, int x, int y) {
        this.size = size;
        float[] shapex = new float[4];
        float[] shapey = new float[4];
        shapex[0] = (float) (x - size);
        shapey[0] = (float) (y - size);
        shapex[1] = (float) (x + size);
        shapey[1] = (float) (y - size);
        shapex[2] = (float) (x + size);
        shapey[2] = (float) (y + size);
        shapex[3] = (float) (x - size);
        shapey[3] = (float) (y + size);
        super.setShapeX(shapex);
        super.setShapeY(shapey);
        super.add(new PositionPart(x, y, 0)); //radians doesnt matter
    }

    public MapElement(int x, int y) {
        this.size = defaultSize;
        float[] shapex = new float[4];
        float[] shapey = new float[4];
        shapex[0] = (float) (x - size);
        shapey[0] = (float) (y - size);
        shapex[1] = (float) (x + size);
        shapey[1] = (float) (y - size);
        shapex[2] = (float) (x + size);
        shapey[2] = (float) (y + size);
        shapex[3] = (float) (x - size);
        shapey[3] = (float) (y + size);
        super.setShapeX(shapex);
        super.setShapeY(shapey);
        super.add(new PositionPart(x, y, 0)); //radians doesnt matter
    }

    public Entity getContainedItem() {
        return containedItem;
    }

    public void setContainedItem(Entity containedItem) {
        this.containedItem = containedItem;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
