package com.allen.george.artificiallife.graphics.particles;


import com.allen.george.artificiallife.simulation.world.World;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by George on 26/06/2014.
 */
public class ParticleManager {

    private ArrayList<BaseParticle> particles = new ArrayList<BaseParticle>();
    private int maxParticles;
    private World world;
    private Random random = new Random();

    public ParticleManager(int maxParticles, World world, boolean raining){
        this.maxParticles = maxParticles;
        this.world = world;
        for(int i = 0; i < maxParticles; i ++){
            if(raining){
                this.particles.add(new Rain(world));
            }
        }
    }

    public void update(boolean raining, double timeSpeed){
        if(raining){
            if(particles.size() < maxParticles) particles.add(new Rain(world));
        }

        for (int i = 0; i < particles.size(); i++) {
            BaseParticle p = particles.get(i);
            if (p == null)
                break;
            if (!p.removed)
                p.update(timeSpeed);
            if (p.removed) {
                if(p.position.x > 3 && p.position.x < world.getWidth() - 3 && p.position.y > 3 && p.position.y < world.getHeight() - 3 && random.nextInt(200) > 198){
                    world.getMap().getInteractiveLayer().generateWater((int)p.position.x,(int)p.position.y, 0);
                }
                particles.remove(i--);
            }
        }
    }

    public void render(SpriteBatch spriteBatch, int scrollX, int scrollY, OrthographicCamera camera, boolean raining){
        if(!raining) return;
        for (int i = 0; i < particles.size(); i++) {
            if(raining){
                particles.get(i).render(spriteBatch, scrollX, scrollY, camera);
            }
        }
    }

}
