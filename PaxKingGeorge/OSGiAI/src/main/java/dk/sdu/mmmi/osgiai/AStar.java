/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.osgiai;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author marku
 */
public class AStar {

    public int weight = 10;

    public List<Node> process(Node startNode, Node goalNode, int cost) {
        //containing nodes to be evaluated for best f score. also called the fringe.
        List<Node> openSet = new ArrayList();
        //containing nodes that are done being evaluated
        //List<Node> closedSet = new ArrayList();
        Set<Node> closedSet = new HashSet<>();

        openSet.add(startNode);
        //astar loop
        Node current = startNode;
        // while the fringe/openSet is not empty and has not reached the goalNode we continue search for the goalnode
        while (!openSet.isEmpty()) {

            Node bestNode = null;
            double bestF = Double.POSITIVE_INFINITY;
            for (Node node : openSet) {
                if (node.getF() < bestF) {
                    bestF = node.getF();
                    bestNode = node;
                }
            }

            current = bestNode;

            //current = openSet.poll();
            if (current.equals(goalNode)) {
                //trying cleaning up objects

                openSet.clear();
                closedSet.clear();
                // path found
                return getPath(current);
            }
            openSet.remove(current);
            closedSet.add(current);
            //neighbours are the childs of the current node
            List<Node> neighbours = current.getNeighbours();

            for (Node neighbour : neighbours) {
                //we dont want to reevaluate nodes from the closedSet
                if (!closedSet.contains(neighbour)) {
                    double tempG = current.getG() + cost;
                    //if the neighbour already are in the openSet, we want to see if the new addition of it has a lower g-score
                    if (openSet.contains(neighbour)) {
                        if (tempG < neighbour.getG()) {
                            neighbour.setG(tempG);
                        }
                    } else {
                        //if the neighbour is not in the openSet, we set the gcore and and it to the fringe/openSet()
                        neighbour.setG(tempG);
                        openSet.add(neighbour);
                    }
                    // we add the heuristic and fscore to the node.
                    neighbour.setH(getEuclideanD(neighbour.getX(), neighbour.getY(), goalNode.getX(), goalNode.getY()) * weight);
                    neighbour.setF(neighbour.getG() + neighbour.getH());
                    neighbour.setParent(current);

                }

            }

        }
        return null;

    }

    private List<Node> getPath(Node current) {
        List<Node> path = new ArrayList<>();

        Node temp = current;
        while (temp.getParent() != null) {
            path.add(temp);
            temp = temp.getParent();
        }
        return path;
    }

    public double getEuclideanD(float x1, float y1, float x2, float y2) {
        return Math.sqrt((Math.pow((double) x2 - (double) x1, 2.0) + Math.pow((double) y2 - (double) y1, 2.0)));

    }

}
