package com.allen.george.artificiallife.simulation.world.daynight;

import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.artificiallife.utils.MathHelper;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by George on 25/06/2014.
 */
public class DayNightCycler {

    private ShapeRenderer shapeRenderer;

    private double time;
    private double timeSpeed;
    private String timeString = "";
    private int cycles = 0;
    private int maxCycles = 200;

    private int width, height;

    public DayNightCycler(int width, int height){
        this.width = width;
        this.height = height;
        this.time = 0.8;
        this.timeSpeed = 0.0001;
        this.shapeRenderer = new ShapeRenderer();
    }

    public void update(){
        if(time >= 0.8) timeSpeed = timeSpeed * -1;
        if(time <= 0){
            timeSpeed =  timeSpeed * -1;
            cycles += 1;
        }
        time += timeSpeed;
        timeString = convertTime("timeString");
    }

    public void render(SpriteBatch spriteBatch, int scrollX, int scrollY, OrthographicCamera camera){
        //shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, (float)time);
        shapeRenderer.rect(0, 0, width * 32, height * 32);
        shapeRenderer.end();
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