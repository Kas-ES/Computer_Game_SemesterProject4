/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.Enemy;

/**
 *
 * @author marku
 */
public interface IAISPI {

    public void getNextMove(World world, GameData gameData, Enemy enemy);

    public void configureAI(World world, int gridSize, int precisionTolerance, int weight);

}
