package com.allen.george.artificiallife.simulation.world.map.objects;

import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.artificiallife.simulation.world.World;
import com.allen.george.artificiallife.simulation.world.map.Map;
import com.allen.george.artificiallife.utils.Content;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by George on 01/10/2014.
 */
public class Den extends MapObject {

    public Den(int width, int height, Vector2 position, World world) {
        super(width, height, position, world);
    }

    @Override
    public void update() {
        //check to see if a life form is over the apple
        for(int i = 0; i < world.getEvolver().getLifeForms().size(); i ++){
            int lx = world.getEvolver().getLifeForms().get(i).positionX;
            int ly = world.getEvolver().getLifeForms().get(i).positionY;

            if(lx == this.positionX && ly == this.positionY){
                this.apply((world.getEvolver().getLifeForms().get(i)));
            }
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch, OrthographicCamera camera) {
        spriteBatch.draw(Content.denTile, positionX * Map.TILE_SIZE - (int)camera.position.x, positionY * Map.TILE_SIZE - (int)camera.position.y);
    }

    private void apply(LifeForm lifeForm){
        lifeForm.setTargetObject(null);
        lifeForm.setEnergy(lifeForm.getMaxEnergy());
        lifeForm.setThirst(6f);
        lifeForm.setHunger(4f);
    }


}
