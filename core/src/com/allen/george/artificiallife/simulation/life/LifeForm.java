package com.allen.george.artificiallife.simulation.life;

import com.allen.george.artificiallife.ga.BehaviourTree;
import com.allen.george.artificiallife.simulation.world.World;
import com.allen.george.artificiallife.simulation.world.map.Map;
import com.allen.george.artificiallife.simulation.world.map.objects.food.Food;
import com.allen.george.artificiallife.utils.Content;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by George on 24/06/2014.
 */
public class LifeForm {

    private World world;
    public int positionX;
    public int positionY;
    private final int MOVE_SPEED = 1;
    private double TIME_SPEED;
    private BehaviourTree tree;

    private int smellingDistance = 10;
    private int seeingDistance = 5;
    private Food targetFood;


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
        this.positionX = (1 + (int)(Math.random() * (((world.getWidth() - 1) - 1) + 1)));
        this.positionY = (1 + (int)(Math.random() * (((world.getHeight() - 1) - 1) + 1)));

        targetFood = null;

        this.tree = new BehaviourTree(this);
    }

    private boolean c  = false;

    public void move(int xa, int ya){
        if(xa < 0) direction = RIGHT;
        if(xa > 0) direction = LEFT;
        if(ya < 0) direction = UP;
        if(ya > 0) direction = DOWN;

        if(!collision(xa, ya)){
            c = false;
            positionX += xa;
            positionY += ya;
        } else {
            c = true;
            targetFood = null;

        }

    }

    public void moveToFood(Food food){
        int xa = 0, ya = 0;
        if (positionX < food.position.x)
            xa += MOVE_SPEED;
        if (positionX > food.position.x)
            xa-=MOVE_SPEED;
        if (positionY < food.position.y)
            ya +=MOVE_SPEED;
        if (positionY > food.position.y)
            ya -=MOVE_SPEED;

        if(xa != 0 | ya != 0) move(xa, ya);

        if (positionX == (int)food.position.x && positionY == (int)food.position.y) {
            targetFood = null;

        }
   }

    public boolean collision(int xa, int ya){
        boolean solid = false;

        if(world.getMap().getCollisionAt(positionX + xa, positionY + ya) == 1) solid = true;

        return solid;
    }



    public void update(double timeSpeed){

        timer += 1;
        this.TIME_SPEED = timeSpeed;
       // this.tree.evaluate();

        if(targetFood == null) {
            if(canSmellFood(smellingDistance)){
                moveToFood(targetFood);
            }
        } else {
            moveToFood(targetFood);
        }


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
      // if(position.y < world.getHeight() - 1 && world.getMap().getCollisionAt(position.x , position.y + 1) != 1){
        positionY += MOVE_SPEED;
            direction = UP;
      //  }
    }

    public void moveDown(){
      // if(position.y > 0 && world.getMap().getCollisionAt(position.x , position.y - 1) != 1){
        positionY -=  MOVE_SPEED;
            direction = DOWN;
      //  }
    }

    public void moveLeft(){
     // if(position.x > 0 && world.getMap().getCollisionAt(position.x - 1 , position.y ) != 1){
        positionX -=  MOVE_SPEED;
            direction = LEFT;
      // }
    }

    public void moveRight(){
       // if(position.x < world.getWidth() - 1 && world.getMap().getCollisionAt(position.x + 1, position.y) != 1){
        positionX+=  MOVE_SPEED;
            direction = RIGHT;
      //  }
    }

    public boolean canSmellFood(int radius){

        for (int i = 0; i < world.getMap().getFoodObjects().size(); i++) {
            // get the current food item
            Food f = world.getMap().getFoodObjects().get(i);
            // get the x and y coordinates of the food
            int fx = (int)f.position.x;
            int fy =  (int)f.position.y;

            // get the x and y distance between the food and the bug
            int dx = Math.abs(fx - positionX);
            int dy = Math.abs(fy - positionY);

            // get the total distance
            double distance = Math.sqrt((dx * dx) + (dy * dy));
            // int distance = dx + dy;
            if (distance <= radius) {
               targetFood = f;
               return true;
            }
        }
        return false;
    }

    public boolean canSeeFood(int radius){
        return canSmellFood(radius);
    }



    public void render(SpriteBatch spriteBatch, OrthographicCamera camera){
        if(c){
            spriteBatch.draw(Content.collision, positionX * Map.TILE_SIZE - (int)camera.position.x, positionY * Map.TILE_SIZE - (int)camera.position.y);
        }

        spriteBatch.draw(Content.cat[frame][direction], positionX * Map.TILE_SIZE - (int)camera.position.x, positionY * Map.TILE_SIZE - (int)camera.position.y);
    }


    public void setTargetFood(Food targetFood) {
        this.targetFood = targetFood;
    }
}
