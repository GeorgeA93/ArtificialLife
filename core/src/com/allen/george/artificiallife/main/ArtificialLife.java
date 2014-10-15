package com.allen.george.artificiallife.main;


import com.allen.george.artificiallife.main.forms.MainGui;
import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.artificiallife.simulation.world.World;
import com.allen.george.artificiallife.simulation.world.map.layers.MapLayer;
import com.allen.george.artificiallife.utils.Content;
import com.allen.george.artificiallife.utils.MapFile;
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

import java.util.ArrayList;


public class ArtificialLife extends ApplicationAdapter implements ApplicationListener {

    //Graphics
	private SpriteBatch spriteBatch;
    private World world;
    private MainGui gui;

    //Camera
    private OrthographicCamera camera;
    private float moveSpeed = 1f;
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
        world = new World(SimulationSettings.WORLD_WIDTH,SimulationSettings.WORLD_HEIGHT);

	}

	@Override
	public void render () {
        //clear the screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if(running && world.getDayNightCycler().getCycles() < world.getDayNightCycler().getMaxCycles()){
            if(MathHelper.sign(world.getDayNightCycler().getTimeSpeed()) == -1){
                update(-world.getDayNightCycler().getTimeSpeed());
            } else {
                update(world.getDayNightCycler().getTimeSpeed());
            }
        }


        //render
        spriteBatch.setProjectionMatrix(camera.combined);
        world.render(spriteBatch, camera);

	}

    public void update(double timeSpeed){

        gui.setFPS(String.valueOf(Gdx.graphics.getFramesPerSecond())); //set the fps

        world.update(timeSpeed);

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
                currentFocusLifeForm = world.getEvolver().getLifeForms().size() - 1;
            } else {
                currentFocusLifeForm -= 1;
            }

        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            following = true;

            if(currentFocusLifeForm == world.getEvolver().getLifeForms().size() - 1){
                currentFocusLifeForm = 0;
            } else {

                currentFocusLifeForm += 1;
            }

        }

        if(following){
            ArrayList<LifeForm> tempLife = world.getEvolver().getLifeForms();
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

        //UPDATE THE GUI
        gui.setTime(world.getDayNightCycler().getTimeString());
        gui.setCurrentCycle(world.getDayNightCycler().getCycles());
        gui.setWeather(world.getWeatherManager().getCurrentWeather());

        //current life form information
        ArrayList<LifeForm> tempLife = world.getEvolver().getLifeForms();
        LifeForm temp = tempLife.get(currentFocusLifeForm);
        gui.setLifeFormInformation(temp.getTree().getRootNode().getFunctionName(), (int)temp.getEnergy(), temp.getHunger(), temp.getThirst());


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

    @Override
    public void dispose () {

    }


    public void saveMap(){
        ArrayList<MapLayer> layers = new ArrayList<MapLayer>();
        layers.add(world.getMap().getBackgroundLayer());
        layers.add(world.getMap().getInteractiveLayer());
        layers.add(world.getMap().getForegroundLayer());
        layers.add(world.getMap().getShadowLayer());
        MapFile.saveMap(layers, "Test");
    }



    public void loadMap(){

    }




}
