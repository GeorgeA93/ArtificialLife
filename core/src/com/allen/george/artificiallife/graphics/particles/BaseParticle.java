package com.allen.george.artificiallife.graphics.particles;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Color;


import java.util.Random;


/**
 * Created by George on 26/06/2014.
 */
public abstract class BaseParticle {

    public ParticleType type;
    public Vector2 position;
    public float speed;
    public float direction;
    public float rotation;
    public float scaleX;
    public float scaleY;
    public float lifeTime;
    public float lifeTick;
    public Random random;
    public Color color;
    public boolean removed = false;

    public abstract void update(double timeSpeed);

    public abstract void render(SpriteBatch spriteBatch, OrthographicCamera camera);

    public void remove(){
        removed = true;
    }

}
