package com.allen.george.artificiallife.graphics.particles;

import com.allen.george.artificiallife.simulation.world.World;
import com.allen.george.artificiallife.simulation.world.map.Map;
import com.allen.george.artificiallife.utils.Content;
import com.allen.george.artificiallife.utils.MathHelper;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by George on 26/06/2014.
 */
public class Rain extends BaseParticle {

    private int width, height;
    private World world;

    public Rain(World world){
        this.width = world.getWidth();
        this.height = world.getHeight();
        this.world = world;
        type = ParticleType.Rain;
        position = new Vector2(-40 + (int)(Math.random() * ((width - -40) + 1)), height);
        direction = 290;
        scaleX = 5;
        scaleY = 20 + (int)(Math.random() * ((30 - 20) + 1));
        lifeTime = (5 + (int)(Math.random() * ((200 - 5) + 1))) * scaleX;
        speed =  1000 * (float)world.getDayNightCycler().getTimeSpeed();
        rotation = (float)-Math.toRadians(direction);
        this.removed = false;
    }

    @Override
    public void update(double timeSpeed) {
        lifeTick ++;
        if(lifeTick > lifeTime) remove();
        if(position.x > width * Map.TILE_SIZE) remove();
        if(position.y < 0 ) remove();

        speed =  1000 * (float)timeSpeed;
        if(MathHelper.sign(speed) == -1){
            speed = speed * -1;
        }

        position.x += Math.cos(Math.toRadians(direction)) * speed;
        position.y += Math.sin(Math.toRadians(direction)) * speed;
    }

    @Override
    public void render(SpriteBatch spriteBatch,  OrthographicCamera camera) {
        if(position.x < 0 || position.x > width) return;
        spriteBatch.setColor(0,0,1,0.3f);
        spriteBatch.draw(Content.particle, position.x * Map.TILE_SIZE - (int)camera.position.x  , position.y * Map.TILE_SIZE - (int)camera.position.y , scaleX, scaleY, rotation, direction, 0, 0);
        spriteBatch.setColor(1,1,1,1);
    }
}
