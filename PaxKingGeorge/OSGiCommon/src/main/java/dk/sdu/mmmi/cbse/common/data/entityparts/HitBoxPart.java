/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import java.util.ArrayList;

/**
 *
 * @author marku
 */
public class HitBoxPart implements EntityPart{
    private float x, y, width, height;
    private float cX, cY;
    private boolean isHit;
    private ArrayList<String> ignorations = new ArrayList<>();
    private String owner;
    private float hitRadians;

    public HitBoxPart(float width, float height, float cX, float cY, String owner) {
        this.x = cX - width/2;
        this.y = cY - height/2;
        this.width = width;
        this.height = height;
        this.cX = cX;
        this.cY = cY;
        this.owner = owner;
        this.isHit = false;
        
    }

    public void setHitRadians(float hitRadias) {
        this.hitRadians = hitRadias;
    }

    public float getHitRadians() {
        return hitRadians;
    }
    
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    public void addIgnore(String entity){
        String ignore = entity.trim().toLowerCase();
        this.ignorations.add(ignore);
    }

    public float getcX() {
        return cX;
    }

    public float getcY() {
        return cY;
    }

    public ArrayList<String> getIgnorations() {
        return ignorations;
    }
    
    

    public boolean isIsHit() {
        return isHit;
    }

    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }
    
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {
        PositionPart position = entity.getPart(PositionPart.class);
        float positionX = position.getX();
        float positionY = position.getY();
        this.cX = positionX;
        this.cY = positionY;
        this.x = cX - width/2;
        this.y = cY - height/2;
        
    }
    
}
