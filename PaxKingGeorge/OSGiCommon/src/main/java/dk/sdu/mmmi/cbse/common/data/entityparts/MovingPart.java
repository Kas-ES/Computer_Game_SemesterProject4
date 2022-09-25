/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.LEFT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.RIGHT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.UP;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.DOWN;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

/**
 *
 * @author Alexander
 */
public class MovingPart
        implements EntityPart {

    private float dx, dy;
    private float deceleration, acceleration;
    private float maxSpeed, rotationSpeed;
    private boolean left, right, up, down;
    private boolean canWrap;
    private float pi = 3.14159f;

    public MovingPart(float deceleration, float acceleration, float maxSpeed, float rotationSpeed) {
        this.deceleration = deceleration;
        this.acceleration = acceleration;
        this.maxSpeed = maxSpeed;
        this.rotationSpeed = rotationSpeed;
        this.canWrap = false;
    }

    //not needed?
    public MovingPart(float deceleration, float acceleration, float maxSpeed, float rotationSpeed, boolean canWrap) {
        this.deceleration = deceleration;
        this.acceleration = acceleration;
        this.maxSpeed = maxSpeed;
        this.rotationSpeed = rotationSpeed;
        this.canWrap = canWrap;
    }

    public void setDeceleration(float deceleration) {
        this.deceleration = deceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();
        float dt = gameData.getDelta();

        // moving
        if (left) {
            x -= acceleration * dt;
            positionPart.setRadians(pi);
        }

        if (right) {
            x += acceleration * dt;
            positionPart.setRadians(0);
        }

        if (up) {
            positionPart.setRadians(pi / 2);
            y += acceleration * dt;
        }

        if (down) {
            positionPart.setRadians(pi * 3 / 2);
            y -= acceleration * dt;
        }
        if (down && left) {
            positionPart.setRadians(pi + pi / 4);
        } else if (down && right) {
            positionPart.setRadians(pi + pi * 3 / 4);
        } else if (up && right) {
            positionPart.setRadians(pi * 1 / 4);
        } else if (up && left) {
            positionPart.setRadians(pi * 3 / 4);
        }

        /*
        // deccelerating
        float vec = (float) sqrt(dx * dx + dy * dy);
        if (vec > 0) {
            dx -= (dx / vec) * deceleration * dt;
            dy -= (dy / vec) * deceleration * dt;
        }
        if (vec > maxSpeed) {
            dx = (dx / vec) * maxSpeed;
            dy = (dy / vec) * maxSpeed;
        }
         */
        // set position
        //remove? No wrap
        float[] xy = wrap(x, y, gameData, canWrap);
        positionPart.setX(xy[0]);
        positionPart.setY(xy[1]);

    }

    //remove, not needed ? Set canWrap to false to avoid being able to "circle"
    public float[] wrap(float x, float y, GameData gameData, boolean canWrap) {
        float[] coordinates = {x, y};
        float dt = gameData.getDelta();
        if (canWrap) {
            if (x > gameData.getDisplayWidth()) {
                x = 0;
                coordinates[0] = x;
            } else if (x < 0) {
                x = gameData.getDisplayWidth();
                coordinates[0] = x;
            }
            if (y > gameData.getDisplayHeight()) {
                y = 0;
                coordinates[1] = y;
            } else if (y < 0) {
                y = gameData.getDisplayHeight();
                coordinates[1] = y;
            }
            return coordinates;
        }

        return coordinates;
    }

}
