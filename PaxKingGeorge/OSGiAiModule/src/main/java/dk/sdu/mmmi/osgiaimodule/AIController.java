package dk.sdu.mmmi.osgiaimodule;
import com.badlogic.gdx.Gdx;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MapElementBlock;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class AIController implements IEntityProcessingService{
    private GameData gameData;
    private World world;
    private PositionObject[][] matrix;
    private float gridSize = 10;
    
    //Not Needed, remove once grid update syncing is implemented
    private boolean active = true;
    
    public AIController(){   
    }
    
    //creates movement grid with given dims
    public void setMovementGrid(){
        this.matrix = new PositionObject[((int)(Gdx.graphics.getWidth()/this.gridSize))][(int)(Gdx.graphics.getHeight()/this.gridSize)];
        for(int i1 = 0 ; i1 < ((int)(Gdx.graphics.getWidth()/this.gridSize)) ; i1++){
            for(int i2 = 0 ; i2 < (int)(Gdx.graphics.getHeight()/this.gridSize) ; i2++){
                this.matrix[i1][i2] = new PositionObject(i1*this.gridSize, i2*this.gridSize);
            }
        }
    }
    
    //Validate if movement is valid from current position
    private boolean validatePosition(float posX, float posY){
        for(Entity val :  world.getEntities(MapElementBlock.class)){
            PositionPart ent = val.getPart(PositionPart.class);
            if(ent!= null && Math.abs(posX-ent.getX())<=val.getCollisionRadius()+this.gridSize && Math.abs(posY-ent.getY())<=val.getCollisionRadius()+this.gridSize){
                return false;
            }
        }
        return true;
    }
    
    //Visualize grid and locked positions
    public void printGrid(){
        String temp = "|";
        for(int i1 = 0 ; i1 < ((int)(Gdx.graphics.getWidth()/this.gridSize)) ; i1++){
            temp = "|";
            for(int i2 = 0 ; i2 < (int)(Gdx.graphics.getHeight()/this.gridSize) ; i2++){
                if(validatePosition(i1*this.gridSize, i2*this.gridSize)){
                    this.matrix[i1][i2] = new PositionObject(i1*this.gridSize, i2*this.gridSize);
                    temp += "-|";
                }else{
                    temp += "X|";
                }
            }
            System.out.println(temp);
        }
        System.out.println("\n\n");
    }
    
    @Override
    public void process(GameData gameData, World world) {
        this.gameData = gameData;
        this.world = world;
        
        //Not Needed, remove once grid update syncing is implemented
        if(active){
            setMovementGrid();
            printGrid();
            this.active = false;
        }
    }
    
}
