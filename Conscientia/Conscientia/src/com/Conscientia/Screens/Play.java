package com.Conscientia.Screens;

import com.Conscientia.inputControl.inputControl;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Play implements Screen{
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private static float animationElapsed;
	private int currentFrame = 0;
	private boolean movementFlag = false;
	private Array<Sprite> arthur;
	private final Vector2 arthurSize = new Vector2(100f, 100f);
	private final Vector2 arthurPos = new Vector2(-1.5f, -1.5f);
	private final int initialFrame = 0;
	private final float frameLength = 0.25f;
	

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Gdx input controls
		Gdx.input.setInputProcessor(new inputControl(){
			@Override
			public boolean keyDown(int keycode) {
				switch(keycode){
				case Keys.A: break;
				case Keys.D: 
					movementFlag = true;
					break;
				case Keys.W: break;
				case Keys.S: break;
				default: break;
				}
				return true;
			}
			
			@Override
			public boolean keyUp(int keycode) {
				switch(keycode){
				case Keys.A: break;
				case Keys.D: 
					movementFlag = false;
					currentFrame = initialFrame;
					break;
				case Keys.W: break;
				case Keys.S: break;
				default: break;
				}
				return true;
			}
		});
		
		// keep track of how much time passed since the last frame
		animationElapsed += delta;
		
		if(movementFlag == true){
			while(animationElapsed > frameLength){
				// reset animationElapsed to minus frameLength
				animationElapsed -= frameLength;
				currentFrame = (currentFrame == arthur.size - 1) ? initialFrame : ++currentFrame;
			}
		}
		
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		arthur.get(currentFrame).draw(batch);
		batch.end();

	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
	}

	@Override
	public void show() {
		
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		
		//getting spriteSheet information
		TextureAtlas spritePaper = new TextureAtlas(Gdx.files.internal("animation/arthur_walking"));
		
		//getting the sprites corresponding to the name_class walking
		arthur = spritePaper.createSprites("walking");
		
		//setting sprites sizes and position
		for(Sprite i: arthur){
			i.setSize(arthurSize.x, arthurSize.y);
			i.setPosition(arthurPos.x, arthurPos.y);
		}
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		batch.dispose();
	}

}
