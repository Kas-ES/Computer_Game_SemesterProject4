/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import dk.sdu.mmmi.cbse.common.data.Direction;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import java.io.File;

/**
 *
 * @author marku
 */
public class SpriteManager {

    private String configFilePath;
    private TextureAtlas textureAtlas;
    
    public SpriteManager() {
        configFilePath = new File(System.getProperty("user.dir")).getParent() + "/OSGiCore/assets/";

    }

    public void render(SpriteBatch spriteBatch, World world, float elapsedTime) {
        drawSprite(spriteBatch, world, elapsedTime);
    }
   
    public void drawSprite(SpriteBatch spriteBatch, World world, float elapsedTime) {
        for (Entity entity : world.getEntities()) {
            if (entity.getGameSprite() != null && entity.getGameSprite().getAnimationContent() == false) {
                spriteBatch.begin();
                textureAtlas = new TextureAtlas(new FileHandle(new File(configFilePath + entity.getGameSprite().getPathTextureAtlas())));
                TextureRegion textureRegion = textureAtlas.findRegion(entity.getGameSprite().getFileName());
                //Texture texture = new Texture(new FileHandle(new File(configFilePath + entity.getGameSprite().getFileName())));
                PositionPart position = entity.getPart(PositionPart.class);
                configSprite(textureRegion, position.getX(), position.getY(), spriteBatch, entity);

                spriteBatch.end();
            }
            if(entity.getGameSprite() != null && entity.getGameSprite().getAnimationContent() == true){
                spriteBatch.begin();
                textureAtlas = new TextureAtlas(new FileHandle(new File(configFilePath + entity.getGameSprite().getPathTextureAtlas())));
                Animation animation  = new Animation(1f/5f, textureAtlas.getRegions());
                
                PositionPart position = entity.getPart(PositionPart.class);
                
                TextureRegion textureRegion = animation.getKeyFrame(elapsedTime, true);
                configSprite(textureRegion, position.getX(), position.getY(), spriteBatch, entity);

                spriteBatch.end();
            }
        }
    }
    
    public void configSprite(TextureRegion textureRegion, float x, float y, SpriteBatch batch, Entity entity) {
        Sprite sprite = new Sprite(textureRegion);
        sprite.setX(x);
        sprite.setY(y);
        if(entity.getGameSprite().getAnimationContent() == true){
            if(entity.getGameSprite().getDirection() == Direction.LEFT){
                sprite.flip(true, false);
            }
        }
        sprite.translate(-(sprite.getWidth() / 2), -(sprite.getHeight() / 2));
        sprite.draw(batch);
    }
       
    public void disposeTexureAtlas() {
        textureAtlas.dispose();
    } 
}
