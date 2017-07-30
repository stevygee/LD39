package com.stevygee.ld39;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PartyIsland extends ApplicationAdapter {
	public static final int NATIVE_WIDTH = 512;
	public static final int NATIVE_HEIGHT = 288;

	private Viewport viewport;
	private OrthographicCamera cam;
	public static Input input;
	private static SpriteBatch batch;
	private static ShapeRenderer shapeRenderer;

	private static float t = 0;
	private final float dt = 1/100f; // 100 updates/second
	private float frameTime = 0f;
	private float accumulator = 0f;

	private float secondAccumulator = 0f;
	private static int updates = 0;
	public static float updatesPerSecond = 0;

	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		cam = new OrthographicCamera(16, 16 * (h / w));
		cam.update();
		viewport = new FitViewport(NATIVE_WIDTH, NATIVE_HEIGHT, cam);

		batch = new SpriteBatch(200);
		shapeRenderer = new ShapeRenderer(200);

		input = new Input();
		Gdx.input.setInputProcessor(input);

		UI.init();
		GameLogic.init();
	}

	public void update(float delta) {
		GameLogic.update(delta);
	}

	@Override
	public void render() {
		// "Free the physics" game loop
		// see: http://gafferongames.com/game-physics/fix-your-timestep/
		// TODO: Implement "the final touch"
		// TODO: Implement integrator

		frameTime = Gdx.graphics.getRawDeltaTime();
		frameTime = Math.min(0.25f, frameTime); // Note: Max frame time to avoid spiral of death

		accumulator += frameTime;
		secondAccumulator += frameTime;

		while( accumulator >= dt ) {
			// Update current game state
			update(dt);

			accumulator -= dt;
			t += dt;
			updates++;
		}

		// Count updates per second
		if( secondAccumulator >= 1f ) {
			secondAccumulator = 0f;
			updatesPerSecond = updates;
			updates = 0;
		}

		// Really render
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		shapeRenderer.setProjectionMatrix(cam.combined);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		GameLogic.render(batch, shapeRenderer);
		UI.render(batch, shapeRenderer);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void dispose() {
		GameLogic.dispose();
		batch.dispose();
		shapeRenderer.dispose();
	}
}
