package com.allen.george.artificiallife.main;


import com.allen.george.artificiallife.simulation.world.World;
import com.allen.george.artificiallife.utils.Content;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import org.lwjgl.Sys;


public class ArtificialLife extends ApplicationAdapter {

    //Graphics
	private SpriteBatch spriteBatch;
    private World world;
    private GUI gui;

    //Camera
    private OrthographicCamera camera;
    private float scrollOffsetX = 0;
    private float scrollOffsetY = 0;
    private float moveSpeed = 1f;
    private float zoomSpeed = 0.01f;

    public ArtificialLife(GUI gui){
        this.gui = gui;
    }
	
	@Override
	public void create () {
        Content.load();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.position.set(Gdx.graphics.getWidth() >> 1,Gdx.graphics.getHeight() >> 1,0);
        camera.zoom = 2;

		spriteBatch = new SpriteBatch();
        world = new World(100,100);
	}

	@Override
	public void render () {
        //clear the screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //update
        update();

        //render
        spriteBatch.setProjectionMatrix(camera.projection);
        spriteBatch.setTransformMatrix(camera.view);
        spriteBatch.begin();
        world.render(spriteBatch, (int) scrollOffsetX, (int) scrollOffsetY, camera);
        spriteBatch.end();
	}

    public void update(){
        gui.setFPS(String.valueOf(Gdx.graphics.getFramesPerSecond())); //set the fps
        camera.update();
        world.update();

        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            camera.position.set(new Vector3(camera.position.x,camera.position.y + moveSpeed,camera.position.z));
            scrollOffsetY += moveSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            camera.position.set(new Vector3(camera.position.x ,camera.position.y - moveSpeed,camera.position.z));
            scrollOffsetY -= moveSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            camera.position.set(new Vector3(camera.position.x - moveSpeed,camera.position.y ,camera.position.z));
            scrollOffsetX -= moveSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            camera.position.set(new Vector3(camera.position.x + moveSpeed,camera.position.y,camera.position.z));
            scrollOffsetX += moveSpeed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP) && camera.zoom > 2){ //
           camera.zoom -= zoomSpeed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && camera.zoom < 5){ //
           camera.zoom += zoomSpeed;
        }

    }


    @Override
    public void dispose () {

    }


}
