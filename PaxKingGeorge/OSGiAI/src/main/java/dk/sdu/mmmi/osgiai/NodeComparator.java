/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.osgiai;

import java.util.Comparator;

/**
 *
 * @author marku
 */
public class NodeComparator implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {
        if (o1.getF() < o2.getF()) {
            return 1;
        } else if (o1.getF() > o2.getF()) {
            return -1;
        } else {
            return 0;
        }
    }

}
