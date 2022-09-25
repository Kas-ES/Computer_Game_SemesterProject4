package dk.sdu.mmmi.osgiai;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Player;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.Enemy;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.services.IAISPI;
import java.util.List;

public class AIController implements IAISPI {

    private Matrix grid;
    private AStar aStar;

    public AIController() {

    }

    @Override
    public void configureAI(World world, int gridSize, int precisionTolerance, int weight) {
        this.grid = new Matrix(world, gridSize, precisionTolerance);

    }

    @Override
    public void getNextMove(World world, GameData gameData, Enemy enemy) {

        if (this.grid == null) {
            this.grid = new Matrix(world);

            System.out.println("the configuration of the AI, hasn't been set, therefore default settings has been set");
        }
        if (this.aStar == null) {
            this.aStar = new AStar();
        }
        Player player = (Player) world.getEntities(Player.class).get(0);
        Node goalNode = this.grid.getEntityMatrixPosition(world, player, this.grid.getPrecisionTolerance()); //this is slow
        Node startNode = this.grid.getEntityMatrixPosition(world, enemy, this.grid.getPrecisionTolerance()); // this is slow
        if (goalNode == null || startNode == null) {
            System.out.println("player or enemyMissing");
            return;
        }
        List<Node> path = this.aStar.process(startNode, goalNode, 1);
        this.grid.clearParents();
        if (path == null) {
            System.out.println("something went wrong in the AStar algorithm");
            return;
        }
        calcMove(path, enemy);

    }

    public void calcMove(List<Node> path, Enemy enemy) {
        Node start;
        Node next;
        MovingPart mp = enemy.getPart(MovingPart.class);

        try {
            start = path.get(path.size() - 1);
            next = path.get(path.size() - 2);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("NullPointer in path.get(index)");
            return;
        }

        if (start.getRow() == next.getRow() && start.getCol() < next.getCol()) {
            mp.setRight(true);
            mp.setLeft(false);
            mp.setUp(false);
            mp.setDown(false);

        } else if (start.getRow() == next.getRow() && start.getCol() > next.getCol()) {
            mp.setLeft(true);
            mp.setRight(false);
            mp.setUp(false);
            mp.setDown(false);

        } else if (start.getRow() < next.getRow() && start.getCol() == next.getCol()) {
            mp.setUp(true);
            mp.setRight(false);
            mp.setLeft(false);

            mp.setDown(false);

        } else if (start.getRow() > next.getRow() && start.getCol() == next.getCol()) {
            mp.setDown(true);
            mp.setRight(false);
            mp.setLeft(false);
            mp.setUp(false);

        } else if (start.getRow() < next.getRow() && start.getCol() < next.getCol()) {
            mp.setRight(true);
            mp.setUp(true);

            mp.setLeft(false);

            mp.setDown(false);

        } else if (start.getRow() > next.getRow() && start.getCol() < next.getCol()) {
            mp.setRight(true);
            mp.setDown(true);

            mp.setLeft(false);
            mp.setUp(false);

        } else if (start.getRow() < next.getRow() && start.getCol() > next.getCol()) {
            mp.setUp(true);
            mp.setLeft(true);

            mp.setRight(false);
            mp.setDown(false);

        } else if (start.getRow() > next.getRow() && start.getCol() > next.getCol()) {
            mp.setDown(true);
            mp.setLeft(true);

            mp.setRight(false);
            mp.setUp(false);

        }

    }

}
