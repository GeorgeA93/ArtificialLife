package com.allen.george.artificiallife.simulation.world.map.objects.food;

import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.artificiallife.simulation.world.World;

import com.allen.george.artificiallife.simulation.world.map.Tile;
import com.allen.george.artificiallife.simulation.world.map.objects.MapObject;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by George on 31/07/2014.
 */
public class Food extends MapObject {

    public Food(int width, int height, Vector2 position, World world) {
        super(width, height, position, world);
    }

    public void apply(LifeForm lifeForm){

        lifeForm.FOOD_EATEN += 10;

        this.world.getMap().getInteractiveLayer().addTile(this.positionX, this.positionY, Tile.NULL_TILE.getTileID());
        this.remove();
        lifeForm.setTargetObject(null);
    }

    public void update() {

    }

    public void render(SpriteBatch spriteBatch,OrthographicCamera camera) {


    }
}
