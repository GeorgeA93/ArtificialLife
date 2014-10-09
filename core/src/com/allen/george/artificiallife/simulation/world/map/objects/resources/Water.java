package com.allen.george.artificiallife.simulation.world.map.objects.resources;

import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.artificiallife.simulation.world.World;
import com.allen.george.artificiallife.simulation.world.map.Map;
import com.allen.george.artificiallife.simulation.world.map.objects.MapObject;
import com.allen.george.artificiallife.utils.Content;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by George on 27/06/2014.
 */
public class Water extends MapObject {

    private int cycleToChange;


    public Water(int width, int height, Vector2 position, World world){
        super(width,height, position, world);
        this.cycleToChange = 1 + (int)(Math.random() * ((world.getDayNightCycler().getMaxCycles() - 1) + 1));
    }

    public void update(){
        if(drinkPositionsX == null){
            calculatedDrinkPositions();
        }
        if(world.getDayNightCycler().getCycles() == cycleToChange){
            if(!world.getWeatherManager().isRaining()){
                world.getMap().getInteractiveLayer().removeWater(this, this.positionX, this.positionY, this.width);
            }
        }

        for(int i = 0; i < world.getEvolver().getLifeForms().size(); i ++){
            int lx = world.getEvolver().getLifeForms().get(i).positionX;
            int ly = world.getEvolver().getLifeForms().get(i).positionY;

            for(int y = 0; y < drinkPositionsY.length; y ++){
                for(int x = 0; x < drinkPositionsX.length; x ++){
                    if(lx == drinkPositionsX[x] && ly == drinkPositionsY[y] && world.getEvolver().getLifeForms().get(i).getTargetObject() == this){
                        world.getEvolver().getLifeForms().get(i).setTargetObject(null);
                        apply((world.getEvolver().getLifeForms().get(i)));
                        world.getMap().getInteractiveLayer().removeWater(this, positionX, positionY, width);
                    }
                }
            }
        }
    }

    private void apply(LifeForm lifeForm){
        //lifeForm.setThirst(lifeForm.getThirst() - ((lifeForm.getWeight() / LifeForm.FACTOR) / 2f)); //this value should be determined by weights
        lifeForm.setThirst(lifeForm.getThirst() - 2f);
    }

    private void calculatedDrinkPositions(){
        drinkPositionsX = new int[width *  4];
        drinkPositionsY = new int[width *  4];
       if(width == 2){
          if(world.getMap().getCollisionAt(positionX - 1, positionY) != 1){
              drinkPositionsX[0] = positionX - 1;
              drinkPositionsY[0] = positionY;
          }
          if(world.getMap().getCollisionAt(positionX - 1, positionY + 1) != 1){
               drinkPositionsX[1] = positionX - 1;
               drinkPositionsY[1] = positionY + 1;
          }
          if(world.getMap().getCollisionAt(positionX + width, positionY) != 1){
              drinkPositionsX[2] = positionX + width;
              drinkPositionsY[2] = positionY;
          }
           if(world.getMap().getCollisionAt(positionX + width, positionY + 1) != 1){
               drinkPositionsX[3] = positionX + width;
               drinkPositionsY[3] = positionY + 1;
           }
           if(world.getMap().getCollisionAt(positionX, positionY - 1) != 1){
               drinkPositionsX[4] = positionX;
               drinkPositionsY[4] = positionY - 1;
           }
           if(world.getMap().getCollisionAt(positionX + 1, positionY - 1) != 1){
               drinkPositionsX[5] = positionX + 1;
               drinkPositionsY[5] = positionY - 1;
           }
           if(world.getMap().getCollisionAt(positionX, positionY + width) != 1){
               drinkPositionsX[6] = positionX;
               drinkPositionsY[6] = positionY + width;
           }
           if(world.getMap().getCollisionAt(positionX + 1, positionY + width) != 1){
               drinkPositionsX[7] = positionX + 1;
               drinkPositionsY[7] = positionY + width;
           }
       } else if(width == 3){
           if(world.getMap().getCollisionAt(positionX - 1, positionY) != 1){
               drinkPositionsX[0] = positionX;
               drinkPositionsY[0] = positionY ;
           }
           if(world.getMap().getCollisionAt(positionX - 1, positionY + 1) != 1){
               drinkPositionsX[1] = positionX - 1;
               drinkPositionsY[1] = positionY + 1;
           }
           if(world.getMap().getCollisionAt(positionX - 1, positionY + 2) != 1){
               drinkPositionsX[2] = positionX - 1;
               drinkPositionsY[2] = positionY + 2;
           }
           if(world.getMap().getCollisionAt(positionX, positionY + 3) != 1){
               drinkPositionsX[3] = positionX;
               drinkPositionsY[3] = positionY + 3;
           }
           if(world.getMap().getCollisionAt(positionX + 1, positionY + 3) != 1){
               drinkPositionsX[4] = positionX + 1;
               drinkPositionsY[4] = positionY + 3;
           }
           if(world.getMap().getCollisionAt(positionX + 2, positionY + 3) != 1){
               drinkPositionsX[5] = positionX + 2;
               drinkPositionsY[6] = positionY + 3;
           }
           if(world.getMap().getCollisionAt(positionX + 3, positionY + 2) != 1){
               drinkPositionsX[6] = positionX + 3;
               drinkPositionsY[6] = positionY + 2;
           }
           if(world.getMap().getCollisionAt(positionX + 3, positionY + 1) != 1){
               drinkPositionsX[7] = positionX + 3;
               drinkPositionsY[7] = positionY + 1;
           }
           if(world.getMap().getCollisionAt(positionX + 3, positionY) != 1){
               drinkPositionsX[8] = positionX + 3;
               drinkPositionsY[8] = positionY ;
           }
           if(world.getMap().getCollisionAt(positionX + 2, positionY - 1) != 1){
               drinkPositionsX[9] = positionX + 2;
               drinkPositionsY[9] = positionY - 1;
           }
           if(world.getMap().getCollisionAt(positionX + 1, positionY - 1) != 1){
               drinkPositionsX[10] = positionX + 1;
               drinkPositionsY[10] = positionY - 1;
           }
           if(world.getMap().getCollisionAt(positionX, positionY - 1) != 1){
               drinkPositionsX[11] = positionX;
               drinkPositionsY[11] = positionY - 1;
           }

       } else if(width == 4){
           if(world.getMap().getCollisionAt(positionX - 1, positionY ) != 1){
               drinkPositionsX[0] = positionX - 1;
               drinkPositionsY[0] = positionY;
           }
           if(world.getMap().getCollisionAt(positionX - 1, positionY + 1) != 1){
               drinkPositionsX[1] = positionX - 1;
               drinkPositionsY[1] = positionY + 1;
           }
           if(world.getMap().getCollisionAt(positionX - 1, positionY + 2) != 1){
               drinkPositionsX[2] = positionX - 1;
               drinkPositionsY[2] = positionY + 2;
           }
           if(world.getMap().getCollisionAt(positionX - 1, positionY + 3) != 1){
               drinkPositionsX[3] = positionX - 1;
               drinkPositionsY[3] = positionY + 3;
           }
           if(world.getMap().getCollisionAt(positionX, positionY + 4) != 1){
               drinkPositionsX[4] = positionX;
               drinkPositionsY[4] = positionY + 4;
           }
           if(world.getMap().getCollisionAt(positionX + 1, positionY + 4) != 1){
               drinkPositionsX[5] = positionX + 1;
               drinkPositionsY[5] = positionY + 4;
           }
           if(world.getMap().getCollisionAt(positionX + 2, positionY + 4) != 1){
               drinkPositionsX[6] = positionX + 2;
               drinkPositionsY[6] = positionY + 4;
           }
           if(world.getMap().getCollisionAt(positionX + 3, positionY + 4) != 1){
               drinkPositionsX[7] = positionX + 3;
               drinkPositionsY[7] = positionY + 4;
           }
           if(world.getMap().getCollisionAt(positionX + 4, positionY + 3) != 1){
               drinkPositionsX[8] = positionX + 4;
               drinkPositionsY[8] = positionY + 3;
           }
           if(world.getMap().getCollisionAt(positionX + 4, positionY + 2) != 1){
               drinkPositionsX[9] = positionX + 4;
               drinkPositionsY[9] = positionY + 2;
           }
           if(world.getMap().getCollisionAt(positionX + 4, positionY + 1) != 1){
               drinkPositionsX[10] = positionX + 4;
               drinkPositionsY[10] = positionY + 1;
           }
           if(world.getMap().getCollisionAt(positionX, positionY - 1) != 1){
               drinkPositionsX[11] = positionX;
               drinkPositionsY[11] = positionY - 1;
           }
           if(world.getMap().getCollisionAt(positionX + 4, positionY ) != 1){
               drinkPositionsX[12] = positionX + 4;
               drinkPositionsY[12] = positionY;
           }
           if(world.getMap().getCollisionAt(positionX + 3, positionY - 1 ) != 1){
               drinkPositionsX[13] = positionX + 3;
               drinkPositionsY[13] = positionY - 1;
           }
           if(world.getMap().getCollisionAt(positionX + 2, positionY - 1) != 1){
               drinkPositionsX[14] = positionX + 2;
               drinkPositionsY[14] = positionY - 1;
           }
           if(world.getMap().getCollisionAt(positionX + 1, positionY - 1) != 1){
               drinkPositionsX[15] = positionX + 1;
               drinkPositionsY[15] = positionY - 1;
           }
       }
    }
}
