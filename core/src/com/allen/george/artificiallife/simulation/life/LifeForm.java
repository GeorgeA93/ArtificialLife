package com.allen.george.artificiallife.simulation.life;

import com.allen.george.artificiallife.simulation.world.World;
import com.allen.george.artificiallife.utils.Content;
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

    private boolean up, down, left, right;

    public LifeForm(String bitString, World world){
        this.world = world;
        this.position = new Vector2(20 * 32 , 20 * 32);
        this.up = down = left = right = false;
    }

    public void update(){

    }


    public void render(SpriteBatch spriteBatch, int scrollX, int scrollY){

        //spriteBatch.draw(Content.lifeForm,  position.x - scrollX,  position.y - scrollY);

    }

}
