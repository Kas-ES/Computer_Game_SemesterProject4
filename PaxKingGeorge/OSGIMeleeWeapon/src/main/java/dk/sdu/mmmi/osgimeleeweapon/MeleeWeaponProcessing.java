/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.osgimeleeweapon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.TimerPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.osgicommonmeleeweapon.MeleeWeapon;
import dk.sdu.mmmi.osgicommonmeleeweapon.MeleeWeaponSPI;

import static com.badlogic.gdx.math.MathUtils.cos;
import static com.badlogic.gdx.math.MathUtils.sin;


/**
 *
 * @author K.E.S
 */
public class MeleeWeaponProcessing extends ApplicationAdapter implements IEntityProcessingService, MeleeWeaponSPI {
    
    private float CD;
    private boolean canShoot = true;
    
    private TextureAtlas textureAtlas;
    private Sprite sword;
    private SpriteBatch batch;

    public MeleeWeaponProcessing() {
        
        
    }
    
    @Override
    public void process(GameData gameData, World world) {

        for (Entity meleeWeapon : world.getEntities(MeleeWeapon.class)) {

            PositionPart positionPart = meleeWeapon.getPart(PositionPart.class);
            MovingPart movingPart = meleeWeapon.getPart(MovingPart.class);
            TimerPart timerPart = meleeWeapon.getPart(TimerPart.class);
            //movingPart.setUp(true);
            if (timerPart.getExpiration() < 0) {
                world.removeEntity(meleeWeapon);
            }
            timerPart.process(gameData, meleeWeapon);
            movingPart.process(gameData, meleeWeapon);
            positionPart.process(gameData, meleeWeapon);

            setShape(meleeWeapon);
        }
    }

    @Override
    public Entity createMeleeWeapon(Entity swing, GameData gameData) {

        PositionPart playerPos = swing.getPart(PositionPart.class);
        MovingPart playerMovingPart = swing.getPart(MovingPart.class);
        
        float x = playerPos.getX();
        float y = playerPos.getY();
        float radians = playerPos.getRadians();
    
        Entity meleeWeapon = new MeleeWeapon();
        meleeWeapon.setRadius(2);

        /* currentOrbitDegrees: the degrees around the orbit that the satellite is(can greater that 360(361 would be equivalent to 1))
         * distanceFromCenterPoint: the distance in world units from the center point that the satellite is
         * centerPoint: the vector of the center point of the orbit system
         */

        float bx = cos(radians) * (meleeWeapon.getRadius() * 15);
        float by = sin(radians) * (meleeWeapon.getRadius() * 15);

        meleeWeapon.add(new PositionPart(bx + x, by + y, radians));
        //meleeWeapon.add(new LifePart(1));
        meleeWeapon.add(new MovingPart(0, 0, 0, 100));
        meleeWeapon.add(new TimerPart(2));

        meleeWeapon.setShapeX(new float[2]);
        meleeWeapon.setShapeY(new float[2]);

        return meleeWeapon;
    }

    private void setShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = x;
        shapey[0] = y;

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f));
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f));
        
        entity.setShapeX(shapex);
        entity.setShapeY(shapey);

        //textureAtlas = new TextureAtlas(Gdx.files.internal("C:\\Users\\K.E.S\\Documents\\GitHub\\SemProject4\\SemProject4\\PaxKingGeorge\\OSGIMeleeWeapon\\src\\main\\resources\\sprites.txt"));
        //sword = textureAtlas.createSprite("sword");
        //batch = new SpriteBatch();
    }
   
    private float countDown(){
        float count = 0;
        
        while(count <= 100){
            count += 1;
        }
        
        return count;
    }
    /*
    @Override
    public void render() {
        batch.begin();
        sword.draw(batch);
        batch.end();
    }
    
    @Override
    public void dispose() {
        textureAtlas.dispose();
    }*/

    
}
