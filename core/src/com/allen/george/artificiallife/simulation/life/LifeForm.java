package com.allen.george.artificiallife.simulation.life;

import com.allen.george.artificiallife.ga.BehaviourTree;
import com.allen.george.artificiallife.simulation.world.World;
import com.allen.george.artificiallife.simulation.world.map.Map;
import com.allen.george.artificiallife.simulation.world.map.objects.Apple;
import com.allen.george.artificiallife.utils.Content;
import com.allen.george.artificiallife.utils.MathHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by George on 24/06/2014.
 */
public class LifeForm {

    private World world;
    private Vector2 position;
    private final int MOVE_SPEED = 200;
    private double TIME_SPEED;
    private BehaviourTree tree;

    //ANIMATION
    private final int UP = 3;
    private final int DOWN = 0;
    private final int LEFT = 1;
    private final int RIGHT = 2;
    private int direction = UP;
    private int frame = 0;
    private final int maxFrames = 2;
    private int timer = 0;


    public LifeForm(String bitString, World world){
        this.world = world;
        this.position = new Vector2(0,0);
        this.position.x = (1 + (int)(Math.random() * (((world.getWidth() - 1) - 1) + 1)));
        this.position.y = (1 + (int)(Math.random() * (((world.getHeight() - 1) - 1) + 1)));


        this.tree = new BehaviourTree(this);
    }


    public void update(double timeSpeed){
        timer += 1;
        this.TIME_SPEED = timeSpeed;
        this.tree.evaluate();

        //Animation
        if(timer % 60 == 0){
            timer = 0;
            if(frame != maxFrames){
                frame ++;
            } else {
                frame = 0;
            }
        }
    }

    public void moveUp(){
        if(position.y < world.getHeight() - 1){
            position.y += MOVE_SPEED * TIME_SPEED;
            direction = UP;
        }
    }

    public void moveDown(){
        if(position.y > 0){
            position.y -=  MOVE_SPEED * TIME_SPEED;
            direction = DOWN;
        }
    }

    public void moveLeft(){
        if(position.x > 0){
            position.x -=  MOVE_SPEED * TIME_SPEED;
            direction = LEFT;
        }
    }

    public void moveRight(){
        if(position.x < world.getWidth() - 1){
            position.x +=  MOVE_SPEED * TIME_SPEED;
            direction = RIGHT;
        }
    }

    public boolean canSmellFood(){

        for (int i = 0; i < world.getMap().getMapObjects().size(); i++) {
            // get the current food item
            Apple f = null;
            if(world.getMap().getMapObjects().get(i) instanceof Apple){
                f = (Apple)world.getMap().getMapObjects().get(i);
            } else {
                continue;
            }

            if(f == null){
                continue;
            }

            // get the x and y coordinates of the food
            int fx = (int)f.position.x;
            int fy = (int)f.position.y;

            // get the x and y distance between the food and the bug
            int dx = Math.abs(fx - (int)(this.position.x));
            int dy = Math.abs(fy - (int)(this.position.y ));

            // get the total distance
            double distance = Math.sqrt((dx * dx) + (dy * dy));
            // int distance = dx + dy;
            if (distance <= 10) {
                return true;
            }
        }
        return false;
    }

    public boolean isFoodRight(){
        for(int i = 0; i < world.getMap().getMapObjects().size(); i++) {
            Apple f = null;
            if(world.getMap().getMapObjects().get(i) instanceof Apple){
                f = (Apple)world.getMap().getMapObjects().get(i);
            } else {
                continue;
            }
            if(f == null){
                continue;
            }

            int x = (int)this.position.x;
            int y = (int)this.position.y;
            int fx = (int)f.position.x ;
            int fy = (int)f.position.y ;

            if(fy == y && fx > x){
                return  true;
            }
        }

        return false;
    }

    public boolean isFoodLeft(){
        for(int i = 0; i < world.getMap().getMapObjects().size(); i++) {
            Apple f = null;
            if(world.getMap().getMapObjects().get(i) instanceof Apple){
                f = (Apple)world.getMap().getMapObjects().get(i);
            } else {
                continue;
            }
            if(f == null){
                continue;
            }

            int x = (int)this.position.x;
            int y = (int)this.position.y;
            int fx = (int)f.position.x ;
            int fy = (int)f.position.y ;

            if(fy == y && fx < x){
                return  true;
            }
        }

        return false;
    }

    public boolean isFoodUp(){
        for(int i = 0; i < world.getMap().getMapObjects().size(); i++) {
            Apple f = null;
            if(world.getMap().getMapObjects().get(i) instanceof Apple){
                f = (Apple)world.getMap().getMapObjects().get(i);
            } else {
                continue;
            }
            if(f == null){
                continue;
            }

            int x = (int)this.position.x;
            int y = (int)this.position.y;
            int fx = (int)f.position.x ;
            int fy = (int)f.position.y ;

            if(fx == x && fy > y){
                return  true;
            }
        }

        return false;
    }

    public boolean isFoodDown(){
        for(int i = 0; i < world.getMap().getMapObjects().size(); i++) {
            Apple f = null;
            if(world.getMap().getMapObjects().get(i) instanceof Apple){
                f = (Apple)world.getMap().getMapObjects().get(i);
            } else {
                continue;
            }
            if(f == null){
                continue;
            }

            int x = (int)this.position.x;
            int y = (int)this.position.y;
            int fx = (int)f.position.x ;
            int fy = (int)f.position.y ;

            if(fx == x && fy < y){
                return  true;
            }
        }

        return false;
    }

    public void render(SpriteBatch spriteBatch, int scrollX, int scrollY){
        spriteBatch.draw(Content.cat[frame][direction],  position.x * 32 - scrollX,  position.y * 32 - scrollY);
    }

    public Vector2 getPosition(){
        return position;
    }

}
