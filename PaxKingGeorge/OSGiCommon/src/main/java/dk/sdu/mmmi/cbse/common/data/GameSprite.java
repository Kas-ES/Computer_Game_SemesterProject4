/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data;

/**
 *
 * @author marku
 */
public class GameSprite {

    private String fileName;
    private String pathTextureAtlas;
    private Boolean animationContent;
    private Direction direction;

    public GameSprite(String fileName, String pathTextureAtlas) {
        this.fileName = fileName;
        this.pathTextureAtlas = pathTextureAtlas;
        this.animationContent = false;
        
    }
    
    public GameSprite(String pathTextureAtlas) {
        this.pathTextureAtlas = pathTextureAtlas;
        this.animationContent = true;
    }
    
    public GameSprite(String pathTextureAtlas, Direction direction) {
        this.pathTextureAtlas = pathTextureAtlas;
        this.direction = direction;
        this.animationContent = true;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPathTextureAtlas() {
        return pathTextureAtlas;
    }

    public void setPathTextureAtlas(String pathTextureAtlas) {
        this.pathTextureAtlas = pathTextureAtlas;
    }

    public Boolean getAnimationContent() {
        return animationContent;
    }

    public void setAnimationContent(Boolean animation) {
        this.animationContent = animation;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    
    
    
}
