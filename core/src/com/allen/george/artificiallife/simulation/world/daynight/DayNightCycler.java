package com.allen.george.artificiallife.simulation.world.daynight;

import com.allen.george.artificiallife.graphics.particles.BaseParticle;
import com.allen.george.artificiallife.graphics.particles.Rain;
import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.artificiallife.simulation.world.World;
import com.allen.george.artificiallife.utils.MathHelper;
import com.allen.george.artificiallife.utils.SimulationSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.compression.lzma.Base;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

/**
 * Created by George on 25/06/2014.
 */
public class DayNightCycler {

    private ShapeRenderer shapeRenderer;

    private double time = 0.8;
    private double timeSpeed = SimulationSettings.INIT_SPEED * 0.0001;
    private String timeString = "";
    private int cycles = 0;
    private int maxCycles = SimulationSettings.NUM_DAYS;

    private int width, height;

    private World world;

    public DayNightCycler(World world){
        this.world = world;
        this.width = world.getWidth();
        this.height = world.getHeight();
        this.shapeRenderer = new ShapeRenderer();
    }

    public void update(){

        if(time >= 0.8) timeSpeed = timeSpeed * -1;
        if(time <= 0){
            timeSpeed =  timeSpeed * -1;
            cycles += 1;
        }
        time += timeSpeed;
        timeString = convertTime(timeString);
    }

    public void render(SpriteBatch spriteBatch, int scrollX, int scrollY, OrthographicCamera camera){
        Gdx.gl.glEnable(GL11.GL_BLEND);
        Gdx.gl.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        //shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, (float)time);
        shapeRenderer.rect(0, 0, width * 32, height * 32);
        shapeRenderer.end();

        Gdx.gl.glDisable(GL11.GL_BLEND);
    }

    private String convertTime(String lastTime){
        String realTime = lastTime;

        if(time >= 0.1 && time < 0.2 && MathHelper.sign(timeSpeed) == 1){
            realTime = "12pm";
        }
        if(time >= 0.2 && time < 0.3 && MathHelper.sign(timeSpeed) == 1){
            realTime = "2pm";
        }
        if(time >= 0.3 && time < 0.4 && MathHelper.sign(timeSpeed) == 1){
            realTime = "4pm";
        }
        if(time >= 0.4 && time < 0.5 && MathHelper.sign(timeSpeed) == 1){
            realTime = "6pm";
        }
        if(time >= 0.5 && time < 0.6 && MathHelper.sign(timeSpeed) == 1){
            realTime = "8pm";
        }
        if(time >= 0.6 && time < 0.7 && MathHelper.sign(timeSpeed) == 1){
            realTime = "10pm";
        }
        if(time >= 0.7 && time < 0.8 && MathHelper.sign(timeSpeed) == 1){
            realTime = "12am";
        }

        if(time >= 0.1 && time < 0.2 && MathHelper.sign(timeSpeed) == -1){
            realTime = "12pm";
        }
        if(time >= 0.2 && time < 0.3 && MathHelper.sign(timeSpeed) == -1){
            realTime = "10am";
        }
        if(time >= 0.3 && time < 0.4 && MathHelper.sign(timeSpeed) == -1){
            realTime = "8am";
        }
        if(time >= 0.4 && time < 0.5 && MathHelper.sign(timeSpeed) == -1){
            realTime = "6am";
        }
        if(time >= 0.5 && time < 0.6 && MathHelper.sign(timeSpeed) == -1){
            realTime = "4am";
        }
        if(time >= 0.6 && time < 0.7 && MathHelper.sign(timeSpeed) == -1){
            realTime = "2am";
        }
        if(time >= 0.7 && time < 0.8 && MathHelper.sign(timeSpeed) == -1){
            realTime = "12am";
        }

        return realTime;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getTimeSpeed() {
        return timeSpeed;
    }

    public void setTimeSpeed(double timeSpeed) {
        this.timeSpeed = timeSpeed;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    public int getCycles() {
        return cycles;
    }

    public void setCycles(int cycles) {
        this.cycles = cycles;
    }

    public int getMaxCycles() {
        return maxCycles;
    }

    public void setMaxCycles(int maxCycles) {
        this.maxCycles = maxCycles;
    }
}
