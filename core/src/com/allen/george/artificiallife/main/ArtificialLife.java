package com.allen.george.artificiallife.main;


import com.allen.george.artificiallife.data.networking.WebServiceAPI;
import com.allen.george.artificiallife.main.forms.MainGui;
import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.artificiallife.simulation.world.World;
import com.allen.george.artificiallife.utils.Content;
import com.allen.george.artificiallife.utils.FileUtils;
import com.allen.george.artificiallife.utils.MathHelper;
import com.allen.george.artificiallife.utils.SimulationSettings;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.io.IOException;
import java.util.ArrayList;



public class ArtificialLife extends ApplicationAdapter implements ApplicationListener{

    private boolean useBlending = false;
    private boolean render = false;

    //Graphics
	private SpriteBatch spriteBatch;
    private World world;
    private MainGui gui;

    //Camera
    private OrthographicCamera camera;
    private float moveSpeed = 2f;
    private float zoomSpeed = 0.01f;
    private int currentFocusLifeForm = 0;
    private boolean following = false;

    private boolean running;

    public ArtificialLife(MainGui gui){
        this.gui = gui;
        running = false;
        gui.setRunningRadio(running);
        gui.setSimulationSpeed(SimulationSettings.INIT_SPEED);
    }
	
	@Override
	public void create () {

        Content.load();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.translate(Gdx.graphics.getWidth() / 2,Gdx.graphics.getHeight() / 2);
        camera.zoom = 2;
        camera.update();

		spriteBatch = new SpriteBatch();

        world = new World(SimulationSettings.WORLD_WIDTH,SimulationSettings.WORLD_HEIGHT, this);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        try {
            WebServiceAPI.deleteAllGenerations();
            WebServiceAPI.deleteAllNodes();
        } catch (Exception e){

        }
	}


	@Override
	public void render () {
        //clear the screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(world == null) return;

        if(running && world.getDayNightCycler().getCycles() < world.getDayNightCycler().getMaxCycles()){
            if(MathHelper.sign(world.getDayNightCycler().getTimeSpeed()) == -1){
                update(-world.getDayNightCycler().getTimeSpeed());
            } else {
                update(world.getDayNightCycler().getTimeSpeed());
            }
        }

        if(render){
            spriteBatch.setProjectionMatrix(camera.combined);
            world.render(spriteBatch, camera);
        }


	}

    int timer = 0;

    public void update(double timeSpeed){

        timer ++;

        world.update(timeSpeed);

        if(render){

            if (Gdx.input.isKeyPressed(Input.Keys.W)){
                following = false;
                camera.position.set(new Vector3(camera.position.x,camera.position.y + moveSpeed,camera.position.z));

            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)){
                following = false;
                camera.position.set(new Vector3(camera.position.x ,camera.position.y - moveSpeed,camera.position.z));

            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)){
                following = false;
                camera.position.set(new Vector3(camera.position.x - moveSpeed,camera.position.y ,camera.position.z));

            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)){
                following = false;
                camera.position.set(new Vector3(camera.position.x + moveSpeed,camera.position.y,camera.position.z));

            }

            if(Gdx.input.isKeyPressed(Input.Keys.UP) && camera.zoom > 2){ //
                following = false;
                camera.zoom -= zoomSpeed;
            }

            if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && camera.zoom < 10) { //was 5
                following = false;
                camera.zoom += zoomSpeed;
            }

            if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                following = true;

                if(currentFocusLifeForm == 0){
                    currentFocusLifeForm = world.getGeneticEngine().getPhenotypes().size() - 1;
                } else {
                    currentFocusLifeForm -= 1;
                }

            }

            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                following = true;

                if(currentFocusLifeForm == world.getGeneticEngine().getPhenotypes().size() - 1){
                    currentFocusLifeForm = 0;
                } else {

                    currentFocusLifeForm += 1;
                }

            }

            if(following){
                ArrayList<LifeForm> tempLife = world.getGeneticEngine().getPhenotypes();
                LifeForm temp = tempLife.get(currentFocusLifeForm);
                float lerp = 0.01f;
                Vector3 position = camera.position;
                float tempWorldX = (temp.positionX * 32) - (Gdx.graphics.getWidth() / 2);
                float tempWorldY = (temp.positionY * 32) - (Gdx.graphics.getHeight() / 2);
                Vector3 positionTemp  = camera.project(new Vector3(tempWorldX, tempWorldY, camera.position.z));
                position.x += (positionTemp.x - position.x) * lerp;
                position.y += (positionTemp.y - position.y) * lerp;
                camera.zoom = 2.5f;
            }

            camera.update();
        }



        if(timer % 60 == 0){

            //UPDATE THE GUI
            gui.setFPS(String.valueOf(Gdx.graphics.getFramesPerSecond())); //set the fps
            gui.setTime(world.getDayNightCycler().getTimeString());
            gui.setCurrentCycle(world.getDayNightCycler().getCycles());
            gui.setWeather(world.getWeatherManager().getCurrentWeather());

            gui.setEvolutionPanelData(SimulationSettings.IS_OLD_SIM);


            //current life form information
            if(world.getGeneticEngine().getPhenotypes().size() > 0) {
                gui.setLifeFormInformation(world.getGeneticEngine().getPhenotypes().get(currentFocusLifeForm).currentNode, (int)world.getGeneticEngine().getPhenotypes().get(currentFocusLifeForm).getEnergy(), world.getGeneticEngine().getPhenotypes().get(currentFocusLifeForm).getHunger(), world.getGeneticEngine().getPhenotypes().get(currentFocusLifeForm).getThirst());
            }

            timer = 0;
        }

    }


    public World getWorld() {
       return world;
    }


    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setUseBlending(boolean val){
        this.useBlending = val;
    }

    public boolean getUseBlending(){
        return this.useBlending;
    }

    public void setRender(boolean render){
        this.render = render;
    }

    public boolean getRender(){
        return this.render;
    }


    public MainGui getGui() {
        return gui;
    }

    @Override
    public void dispose () {

    }



    public void load(String filePath){
        try{
            FileUtils.loadGenerations(filePath, this);
            SimulationSettings.IS_OLD_SIM = true;
        } catch (IOException e){
            e.printStackTrace();
            System.err.println("Could not load simulation: " + filePath);
        }

    }






}
