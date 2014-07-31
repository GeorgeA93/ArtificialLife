package com.allen.george.artificiallife.simulation.world.map.objects.food;

import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.artificiallife.simulation.world.World;
import com.allen.george.artificiallife.simulation.world.map.Map;
import com.allen.george.artificiallife.simulation.world.map.objects.*;
import com.allen.george.artificiallife.utils.Content;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by George on 20/07/2014.
 */
public class Apple extends Food {

    public Apple(int width, int height, Vector2 position, World world) {
        super(width, height, position, world);
    }

    public void update() {

        for(int i = 0; i < world.getEvolver().getLifeForms().size(); i ++){
            int lx = world.getEvolver().getLifeForms().get(i).positionX;
            int ly = world.getEvolver().getLifeForms().get(i).positionY;

            if(lx == (int)this.position.x && ly == (int)this.position.y){
                world.getEvolver().getLifeForms().get(i).setTargetFood(null);
                this.remove();
            }
        }
    }

    public void render(SpriteBatch spriteBatch,OrthographicCamera camera) {
        spriteBatch.draw(Content.apple, position.x * Map.TILE_SIZE - (int)camera.position.x, position.y * Map.TILE_SIZE - (int)camera.position.y);
    }


}
