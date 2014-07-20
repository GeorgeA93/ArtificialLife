package com.allen.george.artificiallife.simulation.world.map.objects;

import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.artificiallife.simulation.world.World;
import com.allen.george.artificiallife.simulation.world.map.Map;
import com.allen.george.artificiallife.utils.Content;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by George on 20/07/2014.
 */
public class Apple extends Object{

    public Apple(int width, int height, Vector2 position, World world) {
        super(width, height, position, world);
    }

    public void update() {

        for(int i = 0; i < world.getEvolver().getLifeForms().size(); i ++){
            int lx = (int)world.getEvolver().getLifeForms().get(i).getPosition().x;
            int ly = (int)world.getEvolver().getLifeForms().get(i).getPosition().y;

            if(lx == (int)this.position.x && ly == (int)this.position.y){
                this.remove();
            }
        }
    }

    public void render(SpriteBatch spriteBatch, int xScroll, int yScroll, OrthographicCamera camera) {
        spriteBatch.draw(Content.apple, position.x * Map.TILE_SIZE - xScroll, position.y * Map.TILE_SIZE - yScroll);
    }


}
